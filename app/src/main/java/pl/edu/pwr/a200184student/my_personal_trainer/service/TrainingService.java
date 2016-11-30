package pl.edu.pwr.a200184student.my_personal_trainer.service;


import java.io.IOException;
import java.util.List;
import pl.edu.pwr.a200184student.my_personal_trainer.endpoints.TrainingEndpoint;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Training;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrainingService {

    private static final String BaseURL = "http://192.168.1.73:8080/";

    public static Training createNewTraining(Training newTraining) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TrainingEndpoint endpoint = retrofit.create(TrainingEndpoint.class);
        Call<Training> call = endpoint.createNewTraining(newTraining);
        try {
            Training training = call.execute().body();
            return training;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Training> getUserTrainings(Long userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TrainingEndpoint endpoint = retrofit.create(TrainingEndpoint.class);
        Call<List<Training>> call = endpoint.getUserTrainings(userId);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteTraining(Long trainingId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TrainingEndpoint endpoint = retrofit.create(TrainingEndpoint.class);
        Call<Void> call = endpoint.deleteTraining(trainingId);
        try {
            call.execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
