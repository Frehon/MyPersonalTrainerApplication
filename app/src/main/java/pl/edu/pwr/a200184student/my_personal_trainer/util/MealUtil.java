package pl.edu.pwr.a200184student.my_personal_trainer.util;


import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.model.Meal;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Product;

public class MealUtil {

    public static List<String> addProductsToMeal(Meal m){
        List<String> mealItems = new ArrayList<>();
        int iterator = 1;
        for(Product p : m.getProducts()){
            mealItems.add(String.valueOf(iterator) + ": " + p.getProductName() + " -  Kalorie: " + p.getCaloriesAmount()*m.getProductsWeight().get(p.getProductName())/100 + " Kcal , Makro - B: " + p.getProteinAmount()*m.getProductsWeight().get(p.getProductName())/100 + " W: " +
                    p.getCarbsAmount()*m.getProductsWeight().get(p.getProductName())/100 + " T: " + p.getFatAmount()*m.getProductsWeight().get(p.getProductName())/100);
            iterator++;
        }
       return mealItems;
    }
}
