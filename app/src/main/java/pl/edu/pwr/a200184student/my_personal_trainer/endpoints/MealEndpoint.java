package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import java.util.List;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Meal;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MealEndpoint {

    @GET("api/meals/{userId}/{date}")
    Call<List<Meal>> getMealsByDateByUserId(@Path("userId") Long userId , @Path("date") String date);

    @POST("api/meals/{userId}")
    Call<List<Meal>> saveMeals(@Body List<Meal> newMeals , @Path("userId") Long userId);

    @PUT("api/meal/{meal_id}/{product_id}/{product_weight}")
    Call<Meal> updateMeal(@Path("meal_id") Long mealId , @Path("product_id") Long productId , @Path("product_weight") int productWeight );

    @DELETE("api/meal/{meal_id}/{product_id}")
    Call<Meal> deleteMealItem(@Path("meal_id") Long mealId , @Path("product_id") Long productId);
}
