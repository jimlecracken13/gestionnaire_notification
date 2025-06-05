package model;

import interfaces.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("Veillez saisir le message à envoyer");
        Scanner mScanner = new Scanner(System.in);
        //String message = mScanner.next(Pattern pattern) pour limiter le nombre de caractère?
        String mText = mScanner.nextLine();
        //notificationplus2025@404 : motdepasse du compte google
        //mot de passe de l'application notifplus : sadf chso iqvw aetu
    }
    public void notifier(String nomDestinataire, String nomExpeditaire)
    {
        System.out.println(nomDestinataire + ", vous avez reçu un message de "+ nomExpeditaire);
    }
}
