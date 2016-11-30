package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.model.Training;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Path;

public interface TrainingEndpoint {

    @POST("api/training")
    Call<Training> createNewTraining(@Body Training newTraining);

    @GET("api/trainings/{userId}")
    Call<List<Training>> getUserTrainings(@Path("userId") Long userId);
}
