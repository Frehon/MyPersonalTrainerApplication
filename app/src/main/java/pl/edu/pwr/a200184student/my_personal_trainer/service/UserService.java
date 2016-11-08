package pl.edu.pwr.a200184student.my_personal_trainer.service;



import java.io.IOException;

import pl.edu.pwr.a200184student.my_personal_trainer.endpoints.User_Endpoint;
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
        User_Endpoint endpoint = retrofit.create(User_Endpoint.class);
        Call<User> call = endpoint.getUserByEmail(mEmail);
        try {
           return loggingUser = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
