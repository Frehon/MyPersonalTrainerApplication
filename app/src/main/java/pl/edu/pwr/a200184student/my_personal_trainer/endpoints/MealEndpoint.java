package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import java.util.List;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Meal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MealEndpoint {

    @GET("api/meals/{userId}/{date}")
    Call<List<Meal>> getMealsByDateByUserId(@Path("userId") Long userId , @Path("date") String date);
}
