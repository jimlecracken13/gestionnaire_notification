package interfaces;

import jakarta.mail.MessagingException;
import model.Abonne;
import model.Employe;

import java.io.UnsupportedEncodingException;

// interface pour notification service
public interface Subject {
   //methode pour s'abonner
   void sabonner(Observer e);

    //methode pour se desabonner
    void seDesabonner(Observer e) throws MessagingException, UnsupportedEncodingException;


    //notifier les abonnés
    void notifierAbonne(Observer expediteur, String sujet, String message);
}
