package pl.edu.pwr.a200184student.my_personal_trainer.util;

public class Registry_Util {

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
}
