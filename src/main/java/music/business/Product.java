package music.business;

import java.io.Serializable;
import java.text.NumberFormat;

public class Product implements Serializable {
    private Long productId;
    private String code;
    private String description;
    private double price;

    public Product() {
    }

    public Product(Long productId, String code, String description, double price) {
        this.productId = productId;
        this.code = code;
        this.description = description;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtistName() {
        String artistName = description.substring(0, description.indexOf(" - "));
        return artistName;
    }

    public String getAlbumName() {
        String albumName = description.substring(0, description.indexOf(" - ") + 3);
        return albumName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceCurrencyFormat(){
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }

    public String getImageURL(){
        String imageURL = "/MusicStore/images/" + code + "_cover.jpg";
        return imageURL;
    }
    public String getProductType(){
        return "Audio CD";
    }
}
