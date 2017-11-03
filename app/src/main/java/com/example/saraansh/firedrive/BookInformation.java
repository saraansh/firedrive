package com.example.saraansh.firedrive;

/**
 * Created by Hopeless on 03-Nov-17.
 */

public class BookInformation {
    public String bid;
    public String bname;
    public String bgenre;
    public String brefer;

    public BookInformation(String bid, String bname, String bgenre, String brefer) {
        this.bid = bid;
        this.bname = bname;
        this.brefer = brefer;
        this.bgenre = bgenre;
    }

    public BookInformation() {
    }

    public String getBname() {
        return bname;
    }

    public String getBgenre() {
        return bgenre;
    }

    public String getBrefer() {
        return brefer;
    }
}
