package pl.edu.pwr.a200184student.my_personal_trainer.service;



import pl.edu.pwr.a200184student.my_personal_trainer.controller.Log_In_Controller;
import pl.edu.pwr.a200184student.my_personal_trainer.endpoints.User_Endpoint;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import pl.edu.pwr.a200184student.my_personal_trainer.util.Log_In_Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {

    public User u;

    public UserService(){

    }


    public User log_in(String mEmail, String mPassword) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.23:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User_Endpoint endpoint = retrofit.create(User_Endpoint.class);
        Call<User> call = endpoint.getUserByEmail(mEmail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                u = Log_In_Util.convertResponseToUser(response);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                u = null;
            }
        });
        return u;
    }
}
