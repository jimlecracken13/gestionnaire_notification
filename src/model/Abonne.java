package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import interfaces.Observer;
import service.MailService;

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
    public void envoyerMessage(Abonne e)
    {
        System.out.println("Veillez saisir le message à envoyer");
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Sujet :");
        String sujet = mScanner.nextLine();
        System.out.println("Message: ");
        String mText = mScanner.nextLine();
        //je recupère la liste des abonnés
        File file = new File("database.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(file);
            ArrayNode abonneNode = (ArrayNode) root.get("abonnés");
            for(JsonNode node: abonneNode)
            {
                JsonNode employe = node.get("employé");
                if(!employe.get("email").asText().equals(e.getEmail()))
                {
                    MailService service = new MailService();
                    service.envoyerEmail(
                            e.getPrenom() + " " + e.getNom(),
                            employe.get("email").asText(),
                            sujet,
                            mText
                    );
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void notifier(String nomDestinataire, String nomExpeditaire)
    {
        System.out.println(nomDestinataire + ", vous avez reçu un message de "+ nomExpeditaire);
    }
}
