package pl.edu.pwr.a200184student.my_personal_trainer.util;

public class Log_In_Util {

    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*");
    }

    public static  Boolean try_to_log_in(String emailAdress , String password) {
        return false;
    }
}
