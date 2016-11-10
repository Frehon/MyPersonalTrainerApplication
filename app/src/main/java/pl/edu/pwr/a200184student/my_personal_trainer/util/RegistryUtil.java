package pl.edu.pwr.a200184student.my_personal_trainer.util;

import java.util.HashMap;

import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import retrofit2.Response;

public class RegistryUtil {

    // some help methods
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
        result.setBirthDate(newUserData.get("BirthDay") + "/" + newUserData.get("BirthMonth") + "/" + newUserData.get("BirthYear"));
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
}
