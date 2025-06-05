package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Abonne;

import java.util.ArrayList;
import java.util.List;

public class Factory {
    public Abonne abonneFactory(JsonNode node)
    {
        String email = node.get("email").asText();
        String nom = node.get("nom").asText();
        String prenom = node.get("prenom").asText();
        String motDePasse = node.get("motDePasse").asText();
        //Date date = node.get("date").asText();
        ArrayNode notifArray = (ArrayNode) node.get("notifications");

        List<String> notifs = new ArrayList<>();
        for (JsonNode n : notifArray) {
            notifs.add(n.asText());
        }
        Abonne abonne = new Abonne(nom, prenom, email, motDePasse);
        abonne.setNotifications(notifs);
        return abonne;
    }
}
