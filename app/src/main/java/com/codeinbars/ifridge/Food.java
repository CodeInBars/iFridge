package com.codeinbars.ifridge;

public class Food {

    String name, dateBuy, dateExpire;

    public Food() {

    }

    public Food(String name, String dateBuy, String dateExpire) {
        this.name = name;
        this.dateBuy = dateBuy;
        this.dateExpire = dateExpire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(String dateBuy) {
        this.dateBuy = dateBuy;
    }

    public String getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(String dateExpire) {
        this.dateExpire = dateExpire;
    }
}
