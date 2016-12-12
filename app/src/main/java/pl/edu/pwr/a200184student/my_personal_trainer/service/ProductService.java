package pl.edu.pwr.a200184student.my_personal_trainer.service;


import java.io.IOException;

import pl.edu.pwr.a200184student.my_personal_trainer.endpoints.ProductEndpoint;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductService {

    private static final String BaseURL = "http://172.16.94.33:8080/";


    public static Product findProduct(String productName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductEndpoint endpoint = retrofit.create(ProductEndpoint.class);
        Call<Product> call = endpoint.getProductByName(productName);
        try {
            Product product = call.execute().body();
            return product;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Product saveNewProduct(Product newProduct) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductEndpoint endpoint = retrofit.create(ProductEndpoint.class);
        Call<Product> call = endpoint.saveNewProduct(newProduct);
        try {
            Product product = call.execute().body();
            return product;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
