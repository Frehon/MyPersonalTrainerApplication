
package pl.edu.pwr.a200184student.my_personal_trainer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("passwordHash")
    @Expose
    private String passwordHash;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("activityFactor")
    @Expose
    private Double activityFactor;
    @SerializedName("dietType")
    @Expose
    private String dietType;
    @SerializedName("caloriesAmount")
    @Expose
    private Integer caloriesAmount;
    @SerializedName("proteinAmount")
    @Expose
    private Integer proteinAmount;
    @SerializedName("carbsAmount")
    @Expose
    private Integer carbsAmount;
    @SerializedName("fatAmount")
    @Expose
    private Integer fatAmount;
    @SerializedName("id")
    @Expose
    private Long id;

    public User() {
    }

    public User(String userName, String birthDate, String gender, String email, String passwordHash, Integer weight, Integer height, Double activityFactor, String dietType, Integer caloriesAmount, Integer proteinAmount, Integer carbsAmount, Integer fatAmount, Long id) {
        this.userName = userName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.passwordHash = passwordHash;
        this.weight = weight;
        this.height = height;
        this.activityFactor = activityFactor;
        this.dietType = dietType;
        this.caloriesAmount = caloriesAmount;
        this.proteinAmount = proteinAmount;
        this.carbsAmount = carbsAmount;
        this.fatAmount = fatAmount;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getActivityFactor() {
        return activityFactor;
    }

    public void setActivityFactor(Double activityFactor) {
        this.activityFactor = activityFactor;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public Integer getCaloriesAmount() {
        return caloriesAmount;
    }

    public void setCaloriesAmount(Integer caloriesAmount) {
        this.caloriesAmount = caloriesAmount;
    }

    public Integer getProteinAmount() {
        return proteinAmount;
    }

    public void setProteinAmount(Integer proteinAmount) {
        this.proteinAmount = proteinAmount;
    }

    public Integer getCarbsAmount() {
        return carbsAmount;
    }

    public void setCarbsAmount(Integer carbsAmount) {
        this.carbsAmount = carbsAmount;
    }

    public Integer getFatAmount() {
        return fatAmount;
    }

    public void setFatAmount(Integer fatAmount) {
        this.fatAmount = fatAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}