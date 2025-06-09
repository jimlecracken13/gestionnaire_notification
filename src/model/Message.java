package model;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Message {
    String message;
    LocalDateTime currentdate;
    String date;
    String time;
    //constructeur de la classe
    public Message(String message)
    {
        this.message = message;
    }
    public LocalDateTime getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(LocalDateTime currentdate) {
        this.currentdate = currentdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String  getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy");
        return date = currentdate.toLocalDate().format(myFormatObj);
    }
    public String getTime(){
        return time = currentdate.toLocalTime().toString();
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public void setTime(String time)
    {
        this.time = time;
    }
}
