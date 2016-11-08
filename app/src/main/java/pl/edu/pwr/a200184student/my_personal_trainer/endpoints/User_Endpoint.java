package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import org.json.JSONObject;

import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface User_Endpoint {

    @GET("api/user/{id}")
    Call<User> getUserById(@Path("id") Long id);

    @GET("api/user/logging/{email}/")
    Call<User> getUserByEmail(@Path("email") String email);

}
