package pl.edu.pwr.a200184student.my_personal_trainer.util;


import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.model.Training;

public class TrainingUtil {

    public static String[] prepareTrainingsList(List<Training> userTrainings) {
        String[] listItems = new String[userTrainings.size()];
        int secondIterator = 0;
        for(Training t : userTrainings){
            int caloriesPerHour = 0 ;
            switch (t.getActivityName()){
                case "Bieganie" :
                    caloriesPerHour = 600;
                    break;
                case "Trekking" :
                    caloriesPerHour = 300;
                    break;
                case "Jazda na rowerze" :
                    caloriesPerHour = 500;
                    break;
                case "Plywanie" :
                    caloriesPerHour = 750;
                    break;
                case "Trening na silowni" :
                    caloriesPerHour = 550;
                    break;
                case "Jazda Konna" :
                    caloriesPerHour = 350;
                    break;
                case "Jazda na nartach" :
                    caloriesPerHour = 670;
                    break;
                case "Gra w Pilke Nozna" :
                    caloriesPerHour = 560;
                    break;
                case "Gra w Koszykowke" :
                    caloriesPerHour = 560;
                    break;
                case "Gra w Tenisa" :
                    caloriesPerHour = 560;
                    break;
                case "Sporty Walki" :
                    caloriesPerHour = 800;
                    break;
            }
            StringBuilder builder = new StringBuilder("");
            builder.append("Data : " + t.getDate() + " ,  Długość : " + t.getDuration()  + " min  , Spalone Kalorie : " + (caloriesPerHour*t.getDuration())/60 + " , Rodzaj Aktywności : " + t.getActivityName());
            listItems[secondIterator] =  builder.toString();
            secondIterator++;
        }
        return listItems;
    }
}
