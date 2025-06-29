package model;

import interfaces.Observer;
import repositorie.AbonneRepository;
import service.NotificationServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Abonne extends Employe implements Observer
{
    //constructeur de la classe
    List<Message> notifications = new ArrayList<>();
    Date debutAbonnement;
    AbonneRepository abonneRepository = new AbonneRepository();

    public List<Message> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Message> notifications) {
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

    public void envoyerMessage(Abonne expediteur)
    {
        //verifier s'il est abonné
        if(abonneRepository.emailExiste(expediteur.getEmail()))
        {
            System.out.println("Veillez saisir le message à envoyer");
            Scanner mScanner = new Scanner(System.in);
            System.out.println("Sujet :");
            String sujet = mScanner.nextLine();
            System.out.println("Message: ");
            String mText = mScanner.nextLine();
            NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl();
            notificationServiceImpl.notifierAbonne(expediteur, sujet, mText);
        }
        else
        {
            System.out.println("Veillez vous abonné");
        }
    }

    public void notifier(String nomDestinataire, String nomExpeditaire)
    {
        System.out.println(nomDestinataire + ", vous avez reçu un message de "+ nomExpeditaire);
    }



}
