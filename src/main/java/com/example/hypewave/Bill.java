package com.example.hypewave;

public class Bill {
    public int id;
    public String Influencer;
    public int Betrag;
    public String Typ;

    public Bill(int id, String influencer, int betrag, String typ) {
        this.id = id;
        Influencer = influencer;
        Betrag = betrag;
        Typ = typ;
    }
}
