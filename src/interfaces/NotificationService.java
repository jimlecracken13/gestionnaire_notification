package interfaces;

import model.Abonne;

// interface pour notification service
public interface NotificationService {
   //methode pour s'abonner
   void sabonner(Abonne e);
    //methode pour se desabonner
    void seDesabonner(Abonne e);
    //notifier les abonnés
    void notifierAbonne();
}
