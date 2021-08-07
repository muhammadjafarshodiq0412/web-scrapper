package com.api.webscrapper.service;

public interface ProductService {
    String getDataTokopedia();

    void scrapProductTokopedia(int page);

    void scrapDetailProductTokopedia(String url);

    String removeWebProtocol(String url);

    String getStoreFromMetaDataUrl(String metaString);


}
