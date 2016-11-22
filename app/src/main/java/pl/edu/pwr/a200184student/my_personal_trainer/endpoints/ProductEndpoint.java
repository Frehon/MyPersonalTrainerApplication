package pl.edu.pwr.a200184student.my_personal_trainer.endpoints;


import pl.edu.pwr.a200184student.my_personal_trainer.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductEndpoint {

    @GET("api/product/{product_name}")
    Call<Product> getProductByName(@Path("product_name") String productName);
}
