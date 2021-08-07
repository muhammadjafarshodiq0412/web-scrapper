package com.api.webscrapper.utils;

import com.api.webscrapper.model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class CommonUtils {

    @Value("${csv.file-tokopedia-product-list.path}")
    private String TOKOPEDIA_CSV_FILE_PATH;

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

       public void writeCsvFile(List<Product> productList){
        log.info("writeIntoCsvFile Started...");
        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(TOKOPEDIA_CSV_FILE_PATH));
            log.info("Path : "+TOKOPEDIA_CSV_FILE_PATH);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("No", "Product Name", "Description", "Image", "Price", "Rating", "Store"));{
                for(int i=0;i<productList.size();i++){
                    csvPrinter.printRecord((i+1),
                            productList.get(i).getProductName(),
                            productList.get(i).getDescription(),
                            productList.get(i).getImage(),
                            productList.get(i).getPrice(),
                            productList.get(i).getRating(),
                            productList.get(i).getStore());
                }
                csvPrinter.flush();
                csvPrinter.close();
                writer.close();
            }

        }catch (IOException ex){
            System.err.println(ex.toString());
        }
        log.info("writeIntoCsvFile Finished...");
    }

    public String removeWebProtocol(String url){
        log.info("removeWebProtocol : "+url.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)",""));
        return url.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","");
    }

}
