package com.earyant.sys.gank.domain;

public class AllContentBeanWithBLOBs extends AllContentBean {
    private String tImages;

    private String tUrl;

    public String gettImages() {
        return tImages;
    }

    public void settImages(String tImages) {
        this.tImages = tImages;
    }

    public String gettUrl() {
        return tUrl;
    }

    public void settUrl(String tUrl) {
        this.tUrl = tUrl;
    }
}