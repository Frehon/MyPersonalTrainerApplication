package pl.edu.pwr.a200184student.my_personal_trainer.service;



import java.io.IOException;
import java.util.HashMap;

import pl.edu.pwr.a200184student.my_personal_trainer.endpoints.UserEndpoint;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserService {

    private static User loggingUser;

    public static User log_in(String mEmail, final String mPassword){
        loggingUser = new User();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.23:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserEndpoint endpoint = retrofit.create(UserEndpoint.class);
        Call<User> call = endpoint.getUserByEmail(mEmail);
        try {
           loggingUser = call.execute().body();
           if(loggingUser.getPasswordHash().equals(String.valueOf(mPassword.hashCode()))){
               return loggingUser;
           }
           return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User createNewUser(HashMap<String, String> newUserData) {
        return null;
    }
}
