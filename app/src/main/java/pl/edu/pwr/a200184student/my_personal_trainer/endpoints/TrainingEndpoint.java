package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import pl.edu.pwr.a200184student.my_personal_trainer.model.Training;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface TrainingEndpoint {

    @POST("api/training")
    Call<Training> createNewTraining(@Body Training newTraining);
}
