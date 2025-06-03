package model;

import interfaces.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Abonne extends Employe implements Observer
{
    //constructeur de la classe
    List<String> notifications = new ArrayList<>();
    Date debutAbonnement;

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public Date getDebutAbonnement() {
        return debutAbonnement;
    }

    public void setDebutAbonnement(Date debutAbonnement) {
        this.debutAbonnement = debutAbonnement;
    }

    public Abonne(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }
    public void envoyerMessage(Message message)
    {
        System.out.println(message.message);
    }
    public void notifier(String nomDestinataire)
    {
        System.out.println(nomDestinataire + ", vous avez reçu une notification de nom_expeditaire");
    }
}
