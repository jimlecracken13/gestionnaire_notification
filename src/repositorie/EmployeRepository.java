package repositorie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeRepository {
    static ObjectMapper mapper = new ObjectMapper();
    List<Employe> listEmploye = new ArrayList<>();
    public List<Employe> getListEmploye()
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
