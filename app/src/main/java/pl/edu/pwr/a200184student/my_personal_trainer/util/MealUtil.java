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

    public static List<String> prepareStatistics(List<Meal> meals, int userCaloriesAmount, int userProteinAmount, int userCarbsAmount, int userFatAmount) {
        List<String> statistics = new ArrayList<>();
        int allCalories = 0 , allProteins = 0 , allCarbs = 0 , allFat = 0;
        for(Meal meal : meals){
            for(Product product : meal.getProducts()){
                allCalories += meal.getProductsWeight().get(product.getProductName()) * product.getCaloriesAmount()/100;
                allProteins += meal.getProductsWeight().get(product.getProductName()) * product.getProteinAmount()/100;
                allCarbs += meal.getProductsWeight().get(product.getProductName()) * product.getCarbsAmount()/100;
                allFat += meal.getProductsWeight().get(product.getProductName()) * product.getFatAmount()/100;
            }
        }
        statistics.add("Ogólna Ilość Kalorii : " + allCalories + "g / " + userCaloriesAmount + "g (" + (allCalories * 100/userCaloriesAmount) + "%)");
        statistics.add("Białko : " + allProteins + "g / " + userProteinAmount + "g (" +  (allProteins * 100/userProteinAmount) + "%)");
        statistics.add("Węglowodany : " + allCarbs + "g / " + userProteinAmount + "g (" + (allCarbs * 100/userCarbsAmount) + "%)");
        statistics.add("Tłuszcze : " + allFat + "g / " + userProteinAmount + "g (" + (allFat * 100/userFatAmount) + "%)");
        return statistics;
    }
}
