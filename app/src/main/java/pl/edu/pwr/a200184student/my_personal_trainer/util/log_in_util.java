package pl.edu.pwr.a200184student.my_personal_trainer.util;

import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import retrofit2.Response;

public class Log_In_Util {

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
        result.setBirthDate(response.body().getBirthDate());
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


}
