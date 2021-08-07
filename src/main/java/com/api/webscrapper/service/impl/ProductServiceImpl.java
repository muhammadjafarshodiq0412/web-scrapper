package com.api.webscrapper.service.impl;

import com.api.webscrapper.model.Product;
import com.api.webscrapper.service.ProductService;
import com.api.webscrapper.utils.CommonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private CommonUtils commonUtils;

    private List<Product> products = new ArrayList<>();

    @Override
    public String getDataTokopedia() {
        logger.info("getDataTokopedia Started");
        for (int i = 1; i <= 10; i++) {
            scrapProductTokopedia(i);
        }
        commonUtils.writeCsvFile(products);
        logger.info("getDataTokopedia Finished");

        return "Done";
    }

    @Override
    public void scrapProductTokopedia(int page) {
        try{
            Document doc = Jsoup.connect("https://www.tokopedia.com/search?q=handphone"+((page>1)?"&page="+page:"")).timeout(12000).get();
            Elements body = doc.select("div.css-rjanld");

            for(Element e: body.select("a.pcv3__info-content.css-1qnnuob")){
                if(removeWebProtocol(e.attr("href")).startsWith("tokopedia")){
                    System.out.println("Scrapping details link: "+e.attr("href"));
                    scrapDetailProductTokopedia(e.attr("href"));
                }
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void scrapDetailProductTokopedia(String url) {
        try{
            logger.info("scrapDetailProductTokopedia Started");

            Document doc = Jsoup.connect(url).timeout(12000).get(); // 2 minutes
            Elements body = doc.select("div.css-3lpl5n");

//            logger.info("Product Detail Element : "+body);

            products.add(
                    new Product(
                            body.select("h1.css-1wtrxts").text(), //productName
                            body.select("span.css-168ydy0.e1iszlzh1").text(), //description
                            body.select("div.css-19i5z4j img").attr("src"), //image
                            body.select("div.price").text(), //price
                            doc.select("meta[itemprop=ratingValue]").attr("content"), //rating
                            getStoreFromMetaDataUrl(doc.select("meta[property=og:title]").attr("content")))); //store

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String removeWebProtocol(String url) {
        return commonUtils.removeWebProtocol(url);
    }

    @Override
    public String getStoreFromMetaDataUrl(String metaString) {
        String revMetaString = new StringBuilder(metaString).reverse().toString();
        int posPipe = revMetaString.indexOf("|");
        int posDi = revMetaString.indexOf(" id ", posPipe+1);

        if(posPipe == -1 || posDi == -1){
            return metaString;
        }
        revMetaString = revMetaString.substring(posPipe+2, posDi);
        return new StringBuilder(revMetaString).reverse().toString();
    }
}
