
package pl.edu.pwr.a200184student.my_personal_trainer.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class User {

    @SerializedName("userName")
    String userName;
    @SerializedName("birthYear")
    String birthYear;
    @SerializedName("gender")
    String gender;
    @SerializedName("email")
    String email;
    @SerializedName("passwordHash")
    String passwordHash;
    @SerializedName("weight")
    Integer weight;
    @SerializedName("height")
    Integer height;
    @SerializedName("activityFactor")
    Double activityFactor;
    @SerializedName("dietType")
    String dietType;
    @SerializedName("caloriesAmount")
    Integer caloriesAmount;
    @SerializedName("proteinAmount")
    Integer proteinAmount;
    @SerializedName("carbsAmount")
    Integer carbsAmount;
    @SerializedName("fatAmount")
    Integer fatAmount;
    @SerializedName("id")
    Long id;

    @SerializedName("meals")
    private List<Meal> meals;

    public User() {}

    public Long getId() {return id;}

    public String getUserName() {
        return userName;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {return height;}

    public Double getActivityFactor() {
        return activityFactor;
    }

    public String getDietType() {
        return dietType;
    }

    public Integer getCaloriesAmount() {
        return caloriesAmount;
    }

    public Integer getProteinAmount() {
        return proteinAmount;
    }

    public Integer getCarbsAmount() {
        return carbsAmount;
    }

    public Integer getFatAmount() {
        return fatAmount;
    }

    public List<Meal> getMeals() {return meals;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setActivityFactor(Double activityFactor) {
        this.activityFactor = activityFactor;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public void setCaloriesAmount(Integer caloriesAmount) {
        this.caloriesAmount = caloriesAmount;
    }

    public void setProteinAmount(Integer proteinAmount) {
        this.proteinAmount = proteinAmount;
    }

    public void setCarbsAmount(Integer carbsAmount) {
        this.carbsAmount = carbsAmount;
    }

    public void setFatAmount(Integer fatAmount) {
        this.fatAmount = fatAmount;
    }

    public void setMeals(List<Meal> meals) {this.meals = meals;}
}