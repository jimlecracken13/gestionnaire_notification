package utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import model.Abonne;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static ObjectMapper mapper = new ObjectMapper();
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    List<Employe> listEmploye = new ArrayList<>();
    public static boolean estEmailValide(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
    //recuperer la liste des employés
    public List<Employe>  getListEmploye()
    {
        try {
            JsonNode root = mapper.readTree(new File("database.json"));
            JsonNode employeNode = root.get("employés");
            for(JsonNode node: employeNode)
            {
                String nom = node.get("nom").asText();
                String prenom = node.get("prenom").asText();
                String email = node.get("email").asText();
                String motDePasse = node.get("motDePasse").asText();
                Employe e = new Employe(nom, prenom, email, motDePasse);
                listEmploye.add(e);
            }
            return listEmploye;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
