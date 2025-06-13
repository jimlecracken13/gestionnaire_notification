package utils;

import com.fasterxml.jackson.databind.JsonNode;
import interfaces.IFactory;
import model.Employe;

public class FactoryEmploye implements IFactory {
    public Object create(JsonNode node)
    {
        String nom = node.get("nom").asText();
        String prenom = node.get("prenom").asText();
        String motDePasse = node.get("motDePasse").asText();
        String email = node.get("email").asText();
        return new Employe(nom, prenom, email, motDePasse);
    }
}
