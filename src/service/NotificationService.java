package service;


import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Abonne;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationService implements interfaces.NotificationService
{
    List<Abonne> listAbonne = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    //ajouter à la liste des abonnées
    @Override
    public void sabonner(Employe newAbonne)
    {
        try {
            File file = new File("database.json");
            JsonNode root = mapper.readTree(file);
            ArrayNode abonneNode =(ArrayNode) root.get("abonnés");
            boolean found = false;
            //check if already "abonné"
            for(int i =0; i< abonneNode.size(); i++)
            {
                JsonNode employe = abonneNode.get(i).get("employé");
                if(!employe.isNull())
                {
                    if(employe.get("email").asText().equals(newAbonne.getEmail()))
                    {
                        found = true;
                        break;
                    }
                }
            }
            if(!found)
            {
                //creer un nouvel objet employés
                ObjectNode nouvelEmploye = mapper.createObjectNode();
                //ajout des champs
                nouvelEmploye.put("nom",newAbonne.getNom());
                nouvelEmploye.put("prenom",newAbonne.getPrenom());
                nouvelEmploye.put("email", newAbonne.getEmail());
                nouvelEmploye.put("motDePasse", newAbonne.getMotDePasse());
                //je recupère la date courrente
                Date today = new Date();
                nouvelEmploye.put("debutAbonnement", today.toString());
                // on crée la liste des notification
                ArrayNode notiArray = mapper.createArrayNode();
                nouvelEmploye.set("notifications", notiArray);

                ObjectNode nouveauAbonne = mapper.createObjectNode();
                nouveauAbonne.set("employé",nouvelEmploye);
                abonneNode.add(nouveauAbonne);
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
                System.out.println("Nouvel Abonné Ajouté");
            }
            else
            {
                System.out.println("Vous êtes déjà abonné");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //retirer un abonné de la liste des abonnées
    public void seDesabonner(Abonne abonne)
    {
        //Recupère la liste des abonnés
        try {
            File file = new File("database.json");
            JsonNode root = mapper.readTree(file);
            ArrayNode abonneNode = (ArrayNode) root.get("abonnés");
            for(int i =0; i< abonneNode.size(); i++)
            {
                JsonNode employe = abonneNode.get(i).get("employé");
                if(!employe.isNull())
                {
                    if(employe.get("email").asText().equals(abonne.getEmail()))
                    {
                        abonneNode.remove(i);
                        System.out.println("Vous avez été desabonné");
                        break;
                    }
                }
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //notifier tous les abonnés de la liste
    @Override
    public void notifierAbonne(Abonne e) {
        File file = new File("database.json");
        try {
            JsonNode root = mapper.readTree(file);
            ArrayNode abonneNode = (ArrayNode) root.get("abonnés");
            for(JsonNode node : abonneNode)
            {
                System.out.println(node.has("nom"));
                System.out.println(node.isNull());
                String nom = node.get("nom");
                String prenom = node.get("prenom").asText();
                String email = node.get("email").asText();
                String motDePasse = node.get("motDePasse").asText();
                //Date date =  Date node.get("dateAbonnement").asText();
                ArrayNode notif = (ArrayNode) node.get("notifications");
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
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
