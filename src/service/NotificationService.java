package service;


import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Abonne;
import repositorie.AbonneRepository;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService implements interfaces.NotificationService
{
    List<Abonne> listAbonne = new ArrayList<>();
    AbonneRepository repository = new AbonneRepository();
    @Override
    public void sabonner(Employe newAbonne)
    {
        repository.ajouter(newAbonne);
    }

    //retirer un abonné de la liste des abonnées
    public void seDesabonner(Abonne abonne)
    {
        repository.delete(abonne);
    }

    //notifier tous les abonnés de la liste
    @Override
    public void notifierAbonne(Abonne e) {
        File file = new File("database.json");
        //verifier que l'utilisateur est abonné
        try {
            JsonNode root = mapper.readTree(file);
            ArrayNode abonneNode = (ArrayNode) root.get("abonnés");

            if(!abonneNode.isEmpty())
            {
                for(JsonNode node : abonneNode)
                {
                    JsonNode employe = node.get("employé");
                    String nom = employe.get("nom").asText();
                    String prenom = employe.get("prenom").asText();
                    String email = employe.get("email").asText();
                    String motDePasse = employe.get("motDePasse").asText();
                    //Date date =  Date node.get("dateAbonnement").asText();
                    ArrayNode notif = (ArrayNode) employe.get("notifications");
                    List<String> notifications = new ArrayList<>();
                    for (JsonNode n : notif) {
                        notifications.add(n.asText());
                    }
                    Abonne abonne = new Abonne(nom,prenom,email,motDePasse);
                    abonne.setNotifications(notifications);
                    listAbonne.add(abonne);
                }
                //notification console des abonnés
                for(Abonne abon: listAbonne)
                {
                    //on exclus l'expéditaire
                    if(!abon.getEmail().equals(e.getEmail()))
                    {
                        abon.notifier(abon.getNom(),e.getNom());
                        abon.getNotifications().add("Vous avez reçu un message de "+ e.getNom());
                    }
                }
                //ajout des notifications au fichier
                for(int i = 0; i<listAbonne.size(); i++)
                {
                    JsonNode employe = abonneNode.get(i).get("employé");
                    ArrayNode nodeNotification = (ArrayNode) employe.get("notifications");
                    for(int j =0; j<listAbonne.get(i).getNotifications().size();j++)
                    {
                        if(!employe.get("email").asText().equals(e.getEmail()))
                        {
                            nodeNotification.add(listAbonne.get(i).getNotifications().get(j));
                        }
                    }
                }
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
            }
            else{
                System.out.println("size abonné"+ abonneNode.size());
                System.out.println("Vous n'êtes pas abonnés");
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
