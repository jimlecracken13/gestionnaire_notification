package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Abonne;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Factory {
    static public Abonne abonneFactory(JsonNode node)
    {
        String email = node.get("email").asText();
        String nom = node.get("nom").asText();
        String prenom = node.get("prenom").asText();
        String motDePasse = node.get("motDePasse").asText();
        // 🔁 Parsing de la date
        Date dateDebut = null;
        try {
            String dateStr = node.get("debutAbonnement").asText(); // adapte le nom du champ
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
            dateDebut = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayNode notifArray = (ArrayNode) node.get("notifications");

        List<String> notifs = new ArrayList<>();
        for (JsonNode n : notifArray) {
            notifs.add(n.asText());
        }
        Abonne abonne = new Abonne(nom, prenom, email, motDePasse);
        abonne.setNotifications(notifs);
        abonne.setDebutAbonnement(dateDebut);
        return abonne;
    }
}
