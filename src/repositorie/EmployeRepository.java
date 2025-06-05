package repositorie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeRepository {
    static ObjectMapper mapper = new ObjectMapper();
    File file = new File("database.json")
    List<Employe> listEmploye = new ArrayList<>();
    public ArrayNode getAllEmploye()
    {
        JsonNode root = null;
        try {
            root = mapper.readTree(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (ArrayNode) root.get("employés");
    }
    public List<Employe> getListEmploye()
    {
        ArrayNode employes = getAllEmploye();
        for(JsonNode node: employes)
        {
                String nom = node.get("nom").asText();
                String prenom = node.get("prenom").asText();
                String email = node.get("email").asText();
                String motDePasse = node.get("motDePasse").asText();
                Employe e = new Employe(nom, prenom, email, motDePasse);
                listEmploye.add(e);
            }
        return listEmploye;

    }

    public Employe getEmploye(String email, String motDePasse)
    {
        Employe em = null;
        ArrayNode employes = getAllEmploye();
        for(JsonNode node: employes)
        {
                if(node.get("email").asText().equals(email) &&
                        node.get("motDePasse").asText().equals(motDePasse))
                {
                    String nom = node.get("nom").asText();
                    String prenom = node.get("prenom").asText();
                    em = new Employe(nom, prenom, email, motDePasse);
                    break;
                }
            }
        return em;
    }

}
