package pl.edu.pwr.a200184student.my_personal_trainer.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Meal {
    @SerializedName("id")
    Long Id;
    private String mealName;
    private String date;
    private List<Product> products;
    private Map<String, Integer> productsWeight;

    public Meal(String mealName , String date){
        this.mealName = mealName;
        this.date = date;
        this.products = new ArrayList<>();
        this.productsWeight = new HashMap<>();
    }

    public Long getId() {return Id;}

    public String getMealName() {
        return mealName;
    }

    public String getDate() {
        return date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Integer> getProductsWeight() {
        return productsWeight;
    }

    public void setId(Long id) {Id = id;}

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setProductsWeight(Map<String, Integer> productsWeight) {this.productsWeight = productsWeight;}
}