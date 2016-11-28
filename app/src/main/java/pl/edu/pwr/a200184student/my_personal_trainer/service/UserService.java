package pl.edu.pwr.a200184student.my_personal_trainer.service;


import java.io.IOException;
import pl.edu.pwr.a200184student.my_personal_trainer.endpoints.UserEndpoint;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class UserService{

    private static User loggingUser;
    private static final String BaseURL = "http://192.168.1.73:8080/";


    public static User log_in(String mEmail, final String mPassword){
        loggingUser = new User();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserEndpoint endpoint = retrofit.create(UserEndpoint.class);
        Call<User> call = endpoint.getUserByEmail(mEmail);
        try {
           loggingUser = call.execute().body();
           if(Integer.parseInt(loggingUser.getPasswordHash()) == mPassword.hashCode()){
               return loggingUser;
           }
           return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User getUserByEmail(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserEndpoint endpoint = retrofit.create(UserEndpoint.class);
        Call<User> call = endpoint.getUserByEmail(email);
        try {
            return call.execute().body();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserById(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserEndpoint endpoint = retrofit.create(UserEndpoint.class);
        Call<User> call = endpoint.getUserById(id);
        try {
            return call.execute().body();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User createNewUser(User newUser){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserEndpoint endpoint = retrofit.create(UserEndpoint.class);
        Call<User> call = endpoint.createNewUser(newUser);
        try {
            return call.execute().body();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User updateUser(Long id, User userToUpdate) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserEndpoint endpoint = retrofit.create(UserEndpoint.class);
        Call<User> call = endpoint.updateUser(id , userToUpdate);
        try{
            return call.execute().body();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteUser(Long id) throws Exception{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserEndpoint endpoint = retrofit.create(UserEndpoint.class);
        Call<User> call = endpoint.deleteUser(id);
        int code = call.execute().code();
        if(code == 204){
            return;
        }
        else{
            throw new Exception("sth went wrong ...");
        }
    }
}
