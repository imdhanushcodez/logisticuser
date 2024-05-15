package com.example.logisticsuser;

public class dataclass {

    String imgurl,from,to,name,number,product,date;

    public dataclass(String imgurl, String from, String to, String name, String number, String product, String date) {
        this.imgurl = imgurl;
        this.from = from;
        this.to = to;
        this.name = name;
        this.number = number;
        this.product = product;
        this.date = date;
    }

    public dataclass() {

    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
