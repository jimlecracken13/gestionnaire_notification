package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import interfaces.Observer;
import repositorie.AbonneRepository;
import service.MailService;
import service.NotificationService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Abonne extends Employe implements Observer
{
    //constructeur de la classe
    List<String> notifications = new ArrayList<>();
    Date debutAbonnement;
    AbonneRepository abonneRepository = new AbonneRepository();

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
            NotificationService notificationService = new NotificationService();
            notificationService.notifierAbonne(expediteur, sujet, mText);
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

    public void afficherNotification(Abonne e)
    {
        if(abonneRepository.emailExiste(e.getEmail()))
        {
            abonneRepository.getNotifications(this);
        }
        else
        {
            System.out.println("Veillez vous abonnez");
        }
    }


}
