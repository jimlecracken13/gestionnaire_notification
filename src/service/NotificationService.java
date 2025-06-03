package service;

import model.Abonne;

import java.util.ArrayList;
import java.util.List;

public class NotificationService implements interfaces.NotificationService
{
    List<Abonne> listAbonne = new ArrayList<>();
    //ajouter à la liste des abonnées
    @Override
    public void sabonner(Abonne newAbonne) {
        listAbonne.add(newAbonne);
    }

    //retirer un abonné de la liste des abonnées
    public void seDesabonner(Abonne abonne)
    {
        listAbonne.remove(abonne);
    }

    //notifier tous les abonnés de la liste
    @Override
    public void notifierAbonne() {
        for (Abonne abonne: listAbonne)
        {
            abonne.notifier(abonne.getPrenom().concat(abonne.getNom()));
        }
    }
}
