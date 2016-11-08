
package pl.edu.pwr.a200184student.my_personal_trainer.model;


public class User {

    private Long Id;
    private String userName;
    private String birthDate;
    private String gender;
    private String email;
    private String passwordHash;
    private Integer weight;
    private Integer height;
    private Double activityFactor;
    private String dietType;
    private Integer caloriesAmount;
    private Integer proteinAmount;
    private Integer carbsAmount;
    private Integer fatAmount;

    public User() {

    }

    public Long getId() {return Id;}

    public String getUserName() {
        return userName;
    }

    public String getBirthDate() {
        return birthDate;
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

    public void setId(Long id) {
        Id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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
}