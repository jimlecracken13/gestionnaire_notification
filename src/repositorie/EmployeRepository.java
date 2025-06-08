package repositorie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeRepository {
    static ObjectMapper mapper = new ObjectMapper();
    File file = new File("database.json");
    List<Employe> listEmploye = new ArrayList<>();
    //liste des employé en Json
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

    //Enregistre les employés
    private void saveAllEmployes(ArrayNode employeArray) {
        try {
            JsonNode root = mapper.readTree(file);
            ((ObjectNode) root).set("employés",employeArray);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //la liste des employés
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

    //verifier si l'email existe parmi les employés
    public boolean emailExiste(String email)
    {
        ArrayNode employeNode = getAllEmploye();
        for(JsonNode employe: employeNode)
        {
            if(employe.get("email").asText().equals(email))
            {
                    return true;

            }
        }
        return false;
    }
    //recupère un employé à travers son email et son mot de passe
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
                    em.setEstAdmin(node.get("estAdmin").asBoolean());
                    break;
                }
            }
        return em;
    }

    //recupèrer un employé à travers son email
    public Employe getEmploye(String email)
    {
        Employe em = null;
        ArrayNode employes = getAllEmploye();
        for(JsonNode node: employes)
        {
            if(node.get("email").asText().equals(email))
            {
                String nom = node.get("nom").asText();
                String prenom = node.get("prenom").asText();
                String motDePasse = node.get("motDePasse").asText();
                em = new Employe(nom, prenom, email, motDePasse);
                break;
            }
        }
        return em;
    }

    //Mettre à jour un employé
    public void updateEmploye(Employe updatedEmploye) {
        ArrayNode employeArray = getAllEmploye(); // méthode qui lit le fichier JSON complet
        boolean updated = false;

        for (JsonNode node : employeArray) {
            ObjectNode employeNode = (ObjectNode) node;
            String email = employeNode.get("email").asText();

            if (email.equals(updatedEmploye.getEmail())) {
                employeNode.put("nom", updatedEmploye.getNom());
                employeNode.put("prenom", updatedEmploye.getPrenom());
                employeNode.put("motDePasse", updatedEmploye.getMotDePasse());
                employeNode.put("estAdmin", updatedEmploye.estAdmin());
                updated = true;
                break;
            }
        }

        if (updated) {
            saveAllEmployes(employeArray); // méthode qui écrase le fichier JSON avec la nouvelle liste
            System.out.println("✅ Employé " + updatedEmploye.getEmail() + " mis à jour avec succès.");
        } else {
            System.out.println("❌ Employé non trouvé pour mise à jour : " + updatedEmploye.getEmail());
        }
    }



}
