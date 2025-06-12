package service;


import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import interfaces.Observer;
import interfaces.Subject;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import model.Abonne;
import model.Message;
import repositorie.AbonneRepository;
import utils.Factory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl implements Subject
{
    AbonneRepository repository = new AbonneRepository();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void sabonner(Observer newAbonne)
    {
        repository.ajouter((Abonne) newAbonne);
    }

    //retirer un abonné de la liste des abonnées
    public void seDesabonner(Observer abonne) throws MessagingException, UnsupportedEncodingException {
        repository.delete((Abonne) abonne);
    }

    //notifier tous les abonnés de la liste
    @Override
    public void notifierAbonne(Observer exp, String sujet, String mText) {
        ArrayNode abonneArray = repository.getAllElements();
        Abonne e = (Abonne) exp;
        List<Abonne> abonnes = new ArrayList<>();
        for(JsonNode node: abonneArray)
        {
            JsonNode employeNode = node.get("employé");
            //exclure l'expeditaire
            if(!employeNode.get("email").asText().equals(e.getEmail()))
            {
                abonnes.add(Factory.abonneFactory(employeNode));
            }
        }

        // Ajouter une nouvelle notification
        for (Abonne abonne : abonnes)
        {
            MailService service = new MailService();
            try {
                //envoi de l'email aux abonnés
                service.envoyerEmail(
                        e.getPrenom() + " " + e.getNom(),
                        abonne.getEmail(),
                        sujet,
                        "Bonjour "+abonne.getNom().toUpperCase()+",\n"+
                        mText
                );
                //afficher la notification au console
                abonne.notifier(abonne.getNom(), e.getNom());
                Message message = new Message("Vous avez reussi un message de "+e.getNom());
                //ajouter la notification à la liste de notification des abonnés
                abonne.getNotifications().add(message);
            } catch (SendFailedException ex) {
                System.err.println("❌ Adresse email invalide : " + abonne.getEmail());
            } catch (MessagingException | UnsupportedEncodingException ex) {
                System.err.println("❌ Échec de l'envoi de l'e-mail à " + abonne.getEmail());
                ex.printStackTrace();
            }

        }

        //mise à jour des notifications
        for(JsonNode node : abonneArray)
        {
            JsonNode employe = node.get("employé");
            String email = employe.get("email").asText();
            if(!email.equals(e.getEmail()))
            {
                //on recupère l'abonné dans la liste des abonnés
                Abonne abonne = abonnes.stream()
                        .filter(a -> a.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);
                if(abonne!=null)
                {
                    ArrayNode notifNode = (ArrayNode) employe.get("notifications");
                    notifNode.removeAll();
                    for (Message notif : abonne.getNotifications()) {
                        ObjectNode message = mapper.createObjectNode();
                        message.put("message",notif.getMessage());
                        message.put("date", notif.getDate());
                        message.put("heure", notif.getTime());
                        notifNode.add(message);
                    }
                }
            }
        }
        repository.saveAllElements(abonneArray);
    }

    public void afficherNotifications(Abonne e)
    {
        if(repository.emailExiste(e.getEmail()))
        {
            repository.getNotifications(e);
        }
        else
        {
            System.out.println("Veillez vous abonnez");
        }
    }

}
