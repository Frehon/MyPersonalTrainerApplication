package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import java.util.List;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Meal;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MealEndpoint {

    @GET("api/meals/{userId}/{date}")
    Call<List<Meal>> getMealsByDateByUserId(@Path("userId") Long userId , @Path("date") String date);

    @POST("api/meals/{userId}")
    Call<List<Meal>> saveMeals(@Body List<Meal> newMeals , @Path("userId") Long userId);

    @PUT("/api/meal")
    Call<Meal> updateMeal(@Body Meal mealToUpdate);
}
