package org.example.model;

public class IceCream {
    String flavour;
    String price;
    String amount;

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "IceCream{" +
                "flavour='" + flavour + '\'' +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
