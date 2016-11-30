package pl.edu.pwr.a200184student.my_personal_trainer.service;


import java.io.IOException;

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
}
