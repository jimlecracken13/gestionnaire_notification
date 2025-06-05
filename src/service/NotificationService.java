package service;


import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Abonne;
import repositorie.AbonneRepository;
import model.Employe;
import utils.Factory;

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
        ArrayNode abonneArray = repository.getAllAbonnes();
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
        for (Abonne abonne : abonnes) {
            abonne.notifier(abonne.getNom(), e.getNom());
            abonne.getNotifications().add("Vous avez reçu un message de " + e.getNom());
        }

        //mise à jour des notifications
        for(int i = 0; i<abonneArray.size(); i++)
        {
            JsonNode employeNode = abonneArray.get(i).get("employé");
            if(!employeNode.get("email").asText().equals(e.getEmail()))
            {
                ArrayNode notifNode = (ArrayNode) employeNode.get("notifications");
                for(int j = 0; j<listAbonne.get(i).getNotifications().size(); j++)
                {
                    notifNode.add("Vous avez reçu un message de " + listAbonne.get(i).getNotifications().get(j));
                }
            }
        }
        repository.saveAllAbonnes(abonneArray);
    }
}
