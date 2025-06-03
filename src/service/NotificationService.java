package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Abonne;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService implements interfaces.NotificationService
{
    List<Abonne> listAbonne = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    //ajouter à la liste des abonnées
    @Override
    public void sabonner(Employe newAbonne)
    {
        Abonne abonne =
                new Abonne(newAbonne.getNom(),newAbonne.getPrenom(),newAbonne.getEmail(), newAbonne.getMotDePasse());
        listAbonne.add(abonne);
        System.out.println(listAbonne);
    }

    //retirer un abonné de la liste des abonnées
    public void seDesabonner(Abonne abonne)
    {

        //Recupère la liste des abonnés
        try {
            File file = new File("database");
            JsonNode root = mapper.readTree(file);
            ArrayNode abonneNode = (ArrayNode) root.get("abonnés");
            for(int i =0; i< abonneNode.size(); i++)
            {

                JsonNode employe = abonneNode.get(i).get("employe");
                if(employe.get("email").asText().equals(abonne.getEmail()))
                {
                    abonneNode.remove(i);
                    break;
                }
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //notifier tous les abonnés de la liste
    @Override
    public void notifierAbonne() {
        System.out.println("la taille de la liste d'abonner "+listAbonne.size());
        for (Abonne abonne: listAbonne)
        {
            abonne.notifier(abonne.getPrenom().concat(abonne.getNom()));
        }
    }
}
