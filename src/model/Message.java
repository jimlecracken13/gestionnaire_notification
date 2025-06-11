package model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Message {
    private final String message;
    private final LocalDateTime currentdate;
    String date;
    String time;
    //constructeur de la classe
    public Message(String message)
    {
        this.message = message;
        this.currentdate = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH);
        this.date = this.currentdate.toLocalDate().format(dateFormatter);
        this.time = this.currentdate.toLocalTime().toString();
    }

    // Constructeur pour les messages déjà existants
    public Message(String message, String date, String time) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.currentdate = null; //pas besoin
    }


    public String getMessage() {
        return message;
    }

    public String  getDate() {
      return date;
    }

    public String getTime(){
        return time ;
    }

}
