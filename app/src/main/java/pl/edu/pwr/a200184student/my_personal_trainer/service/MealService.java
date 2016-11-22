package pl.edu.pwr.a200184student.my_personal_trainer.service;


import java.io.IOException;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.endpoints.MealEndpoint;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Meal;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealService {

    public static List<Meal> getUserMealsByDate(Long userId, String selectedDate) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.23:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealEndpoint endpoint = retrofit.create(MealEndpoint.class);
        Call<List<Meal>> call = endpoint.getMealsByDate(userId , selectedDate);
        try {
            List<Meal> meals = call.execute().body();
            return meals;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
