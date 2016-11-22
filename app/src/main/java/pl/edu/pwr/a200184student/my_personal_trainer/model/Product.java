package pl.edu.pwr.a200184student.my_personal_trainer.model;


import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    private Long Id;
    @SerializedName("productName")
    private String productName;
    @SerializedName("caloriesAmount")
    private int caloriesAmount;
    @SerializedName("proteinAmount")
    private int proteinAmount;
    @SerializedName("carbsAmount")
    private int carbsAmount;
    @SerializedName("fatAmount")
    private int fatAmount;

    public Long getId() {return Id;}

    public void setId(Long id) {Id = id;}

    public String getProductName() {return productName;}

    public void setProductName(String productName) {this.productName = productName;}

    public int getCaloriesAmount() {return caloriesAmount;}

    public void setCaloriesAmount(int caloriesAmount) {this.caloriesAmount = caloriesAmount;}

    public int getProteinAmount() {return proteinAmount;}

    public void setProteinAmount(int proteinAmount) {this.proteinAmount = proteinAmount;}

    public int getCarbsAmount() {return carbsAmount;}

    public void setCarbsAmount(int carbsAmount) {this.carbsAmount = carbsAmount;}

    public int getFatAmount() {return fatAmount;}

    public void setFatAmount(int fatAmount) {this.fatAmount = fatAmount;}
}
