package pl.edu.pwr.a200184student.my_personal_trainer.model;


import com.google.gson.annotations.SerializedName;

public class Training {

    @SerializedName("id")
    private Long Id;
    @SerializedName("activityName")
    private String activityName;
    @SerializedName("duration")
    private int duration;
    @SerializedName("date")
    private String date;
    @SerializedName("userId")
    private Long userId;

    public Training(String activityName , int duration , String date  , Long userId){
        this.activityName = activityName;
        this.duration = duration;
        this.date = date;
        this.userId = userId;
    }

    public Training (){}

    public Long getId() {return Id;}

    public void setId(Long id) {Id = id;}

    public String getActivityName() {return activityName;}

    public void setActivityName(String activityName) {this.activityName = activityName;}

    public int getDuration() {return duration;}

    public void setDuration(int duration) {this.duration = duration;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public Long getUserId(){return userId;}

    public void setUserId(Long userId) {this.userId = userId;}
}
