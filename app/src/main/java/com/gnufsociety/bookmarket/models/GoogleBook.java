package com.gnufsociety.bookmarket.models;

import java.util.List;

public class GoogleBook {

    private String id;
    private VolumeInfo volumeInfo;
    private SaleInfo saleInfo;



    public String title(){
        String title = volumeInfo.getTitle();
        return title != null? title : "";
    }

    public String author(){
        List<String> authors = volumeInfo.getAuthors();
        return authors != null ? authors.get(0) : "";
    }

    public String price() {
        if (saleInfo == null) return "";
        ListPrice lp = saleInfo.getListPrice();
        return lp != null ? lp.amount + " €" : "";
    }

    public String image() {
        if (volumeInfo == null) return "";
        ImageLinks il = volumeInfo.getImageLinks();
        if (il == null) return "";
        String thumb = il.getThumbnail();

        return thumb != null ? thumb : "";
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private ImageLinks imageLinks;

        public ImageLinks getImageLinks() {
            return imageLinks;
        }

        public void setImageLinks(ImageLinks imageLinks) {
            this.imageLinks = imageLinks;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public void setAuthors(List<String> authors) {
            this.authors = authors;
        }
    }

    public static class ImageLinks {
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public static class SaleInfo {
        private ListPrice listPrice;

        public ListPrice getListPrice() {
            return listPrice;
        }

        public void setListPrice(ListPrice listPrice) {
            this.listPrice = listPrice;
        }
    }

    public static class ListPrice {
        private float amount;
        private String currencyCode;

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }
    }

    public static class GoogleBookList{
        private List<GoogleBook> items;

        public List<GoogleBook> getItems() {
            return items;
        }

        public void setItems(List<GoogleBook> items) {
            this.items = items;
        }
    }
}
