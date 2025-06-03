package interfaces;

import model.Abonne;
import model.Employe;

// interface pour notification service
public interface NotificationService {
   //methode pour s'abonner
   void sabonner(Employe e);

    //methode pour se desabonner
    void seDesabonner(Abonne e);

    //notifier les abonnés
    void notifierAbonne();
}
