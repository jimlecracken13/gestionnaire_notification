package interfaces;

import jakarta.mail.MessagingException;
import model.Abonne;
import model.Employe;

import java.io.UnsupportedEncodingException;

// interface pour notification service
public interface NotificationService {
   //methode pour s'abonner
   void sabonner(Employe e);

    //methode pour se desabonner
    void seDesabonner(Abonne e) throws MessagingException, UnsupportedEncodingException;

    //notifier les abonnés
    void notifierAbonne(Abonne expediteur, String sujet, String message);
}
