package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserEndpoint {

    @GET("api/user/{id}")
    Call<User> getUserById(@Path("id") Long id);

    @GET("api/user/logging/{email}/")
    Call<User> getUserByEmail(@Path("email") String email);

    @POST("api/user")
    Call<User> createNewUser(@Body User newUser);

    @PUT("api/user/{id}")
    Call<User> updateUser(@Path("id") Long id , @Body User userToUpdate);

    @DELETE("api/user/{id}")
    Call<User> deleteUser(@Path("id") Long id);

}
