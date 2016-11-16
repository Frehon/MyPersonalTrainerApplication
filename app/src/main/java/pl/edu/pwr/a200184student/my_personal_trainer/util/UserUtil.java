package pl.edu.pwr.a200184student.my_personal_trainer.util;


import java.util.Calendar;
import java.util.HashMap;

import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import retrofit2.Response;


public class UserUtil {


    public static HashMap<String,String> prepareNewDiet(String diet_type , int weight , int height , String gender , String birthYear , double activityFactor){
        return prepareDiet(diet_type,String.valueOf(weight),String.valueOf(height),gender,birthYear,String.valueOf(activityFactor));
    }

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
                case "Zbilansowana" :
                    NEAT = 500;
                    proteinPercent = 0.4;
                    carbsPercent = 0.4;
                    fatPercent = 0.2;
                    break;
                case "Masowa" :
                    NEAT = 700;
                    proteinPercent = 0.3;
                    carbsPercent = 0.5;
                    fatPercent = 0.2;
                    break;
                case "Redukcyjna" :
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
                    case "Zbilansowana" :
                        NEAT = 500;
                        proteinPercent = 0.35;
                        carbsPercent = 0.4;
                        fatPercent = 0.25;
                        break;
                    case "Masowa" :
                        NEAT = 700;
                        proteinPercent = 0.3;
                        carbsPercent = 0.5;
                        fatPercent = 0.2;
                        break;
                    case "Redukcyjna" :
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
    public static User updateDietValues(User user, HashMap<String, String> newDietMap) {
        user.setCaloriesAmount(Integer.parseInt(newDietMap.get("CaloriesAmount")));
        user.setProteinAmount(Integer.parseInt(newDietMap.get("ProteinAmount")));
        user.setCarbsAmount(Integer.parseInt(newDietMap.get("CarbsAmount")));
        user.setFatAmount(Integer.parseInt(newDietMap.get("FatAmount")));
        return user;
    }

        public static boolean checkEmailAdresses(String emailAdress , String confirmedEmailAdress){
            if(emailAdress.equals(confirmedEmailAdress)){
                if(emailAdress.contains("@") && emailAdress.contains(".")){
                    return true;
                }
            }
            return false;
        }

        public static boolean checkPasswords(String user_password, String user_confirmed_password) {
            if(user_password.equals(user_confirmed_password)){
                if(user_password.length() >= 8 && user_password.matches(".*\\d.*")){
                    return true;
                }
            }
            return false;
        }

        public static User prepareNewUser(HashMap<String, String> newUserData) {
            User result = new User();
            result.setUserName(newUserData.get("FirstName") + " " +  newUserData.get("LastName"));
            result.setBirthYear(newUserData.get("BirthYear"));
            result.setGender(newUserData.get("Gender"));
            result.setEmail(newUserData.get("Email"));
            result.setPasswordHash(newUserData.get("PasswordHash"));
            result.setWeight(Integer.parseInt(newUserData.get("Weight")));
            result.setHeight(Integer.parseInt(newUserData.get("Height")));
            result.setActivityFactor(Double.parseDouble(newUserData.get("ActivityFactor")));
            result.setDietType(newUserData.get("DietType"));
            result.setCaloriesAmount(Integer.parseInt(newUserData.get("CaloriesAmount")));
            result.setProteinAmount(Integer.parseInt(newUserData.get("ProteinAmount")));
            result.setCarbsAmount(Integer.parseInt(newUserData.get("CarbsAmount")));
            result.setFatAmount(Integer.parseInt(newUserData.get("FatAmount")));
            return result;
        }


        public static boolean isEmailValid(String email) {
            return email.contains("@") && email.contains(".");
        }

        public static boolean isPasswordValid(String password) {
            return password.length() >= 8 && password.matches(".*\\d.*");
        }

        public static User convertResponseToUser(Response<User> response){
            User result = new User();
            result.setUserName(response.body().getUserName());
            result.setGender(response.body().getGender());
            result.setBirthYear(response.body().getBirthYear());
            result.setEmail(response.body().getEmail());
            result.setPasswordHash(response.body().getPasswordHash());
            result.setWeight(response.body().getWeight());
            result.setHeight(response.body().getHeight());
            result.setActivityFactor(response.body().getActivityFactor());
            result.setDietType(response.body().getDietType());
            result.setCaloriesAmount(response.body().getCaloriesAmount());
            result.setProteinAmount(response.body().getProteinAmount());
            result.setCarbsAmount(response.body().getCarbsAmount());
            result.setFatAmount(response.body().getFatAmount());
            return  result;
        }

    public static boolean checkDigits(String parametrToCheck, String identifier) {
        if (identifier.equals("weight") || identifier.equals("height")) {
            try {
                Integer.parseInt(parametrToCheck);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        else{
            try{
                Double.parseDouble(parametrToCheck);
                return true;
            }
            catch(NumberFormatException e){
                return false;
            }
        }
    }
}

