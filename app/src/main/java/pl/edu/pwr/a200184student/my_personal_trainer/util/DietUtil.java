package pl.edu.pwr.a200184student.my_personal_trainer.util;

import java.util.Calendar;
import java.util.HashMap;


public class DietUtil {

    public static HashMap<String,String> prepareDiet(String diet_type, String weight, String height, String gender , String birthYear , String activityFactor) {
        HashMap<String,String> dietMap = new HashMap<String,String>();
        double BMR , EPOC , TEF , NEAT = 0 , AF = 0;
        double proteinPercent = 0, carbsPercent = 0 , fatPercent = 0;
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - Integer.parseInt(birthYear);
        if(gender.equals("K")){
            BMR = 9.99 * Integer.parseInt(weight) + 6.25 * Integer.parseInt(height) -  4.92 * age - 161;
            EPOC = 0.05 * BMR;
            switch (diet_type){
                case "Balanced" :
                    NEAT = 500;
                    proteinPercent = 0.4;
                    carbsPercent = 0.4;
                    fatPercent = 0.2;
                    break;
                case "Mass" :
                    NEAT = 700;
                    proteinPercent = 0.3;
                    carbsPercent = 0.5;
                    fatPercent = 0.2;
                    break;
                case "Loss" :
                    NEAT = 300;
                    proteinPercent = 0.5;
                    carbsPercent = 0.2;
                    fatPercent = 0.3;
                    break;
            }
            TEF = (BMR + EPOC + NEAT) * 0.1;
            AF = 0.4 * Double.parseDouble(activityFactor) * (TEF + EPOC + NEAT);
            int allCalories = (int)(BMR + EPOC + NEAT + TEF + AF);
            dietMap.put("CaloriesAmount" , String.valueOf(allCalories));
            dietMap.put("ProteinAmount" , String.valueOf((int)(allCalories * proteinPercent)/4));
            dietMap.put("CarbsAmount" , String.valueOf((int)(allCalories * carbsPercent)/4));
            dietMap.put("FatAmount" , String.valueOf((int)(allCalories * fatPercent)/9));
        }
        else{
            if(gender.equals("M")){
                BMR = 9.99 * Integer.parseInt(weight) + 6.25 * Integer.parseInt(height) -  4.92 * age + 5;
                EPOC = 0.05 * BMR;
                switch (diet_type){
                    case "Balanced" :
                        NEAT = 500;
                        proteinPercent = 0.35;
                        carbsPercent = 0.4;
                        fatPercent = 0.25;
                        break;
                    case "Mass" :
                        NEAT = 700;
                        proteinPercent = 0.3;
                        carbsPercent = 0.5;
                        fatPercent = 0.2;
                        break;
                    case "Loss" :
                        NEAT = 300;
                        proteinPercent = 0.5;
                        carbsPercent = 0.2;
                        fatPercent = 0.3;
                        break;
                }
                TEF = (BMR + EPOC + NEAT) * 0.1;
                AF = 0.5 * Double.parseDouble(activityFactor) * (TEF + EPOC + NEAT);
                int allCalories = (int)(BMR + EPOC + NEAT + TEF + AF);
                dietMap.put("CaloriesAmount" , String.valueOf(allCalories));
                dietMap.put("ProteinAmount" , String.valueOf((int)(allCalories * proteinPercent)/4));
                dietMap.put("CarbsAmount" , String.valueOf((int)(allCalories * carbsPercent)/4));
                dietMap.put("FatAmount" , String.valueOf((int)(allCalories * fatPercent)/9));
            }
            else{
                return null;
            }
        }
        return dietMap;
    }
}
