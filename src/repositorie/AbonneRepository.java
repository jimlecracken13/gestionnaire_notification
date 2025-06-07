package repositorie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Abonne;
import model.Employe;
import utils.Factory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AbonneRepository {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("database.json");
    //recupérer tous les abonnés
    public ArrayNode getAllAbonnes() {
        JsonNode root = null;
        try {
            root = mapper.readTree(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (ArrayNode) root.get("abonnés");
    }

    //enregistrer tous les abonnés
    public void saveAllAbonnes(ArrayNode abonneArray)
    {
        try {
            JsonNode root = mapper.readTree(file);
            ((ObjectNode) root).set("abonnés",abonneArray);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //verifié si est déjà abonné
    public boolean emailExiste(String email)
    {
        ArrayNode abonneNode = getAllAbonnes();
        for(JsonNode node: abonneNode)
        {
            JsonNode employe = node.get("employé");
            if(!employe.isNull())
            {
                if(employe.get("email").asText().equals(email))
                {
                    return true;
                }
            }
        }
        return false;
    }
    //ajouter un abonné
    public void ajouter(Employe newAbonne)
    {
        if(!emailExiste(newAbonne.getEmail()))
        {
            //creer un nouvel objet employés
            ObjectNode nouvelEmploye = mapper.createObjectNode();
            ArrayNode abonneArray = getAllAbonnes();
            //ajout des champs
            nouvelEmploye.put("nom",newAbonne.getNom());
            nouvelEmploye.put("prenom",newAbonne.getPrenom());
            nouvelEmploye.put("email", newAbonne.getEmail());
            nouvelEmploye.put("motDePasse", newAbonne.getMotDePasse());
            //je recupère la date courrente
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH);
            String dateEnFrancais = sdf.format(today);
            nouvelEmploye.put("debutAbonnement", dateEnFrancais);
            // on crée la liste des notification
            ArrayNode notiArray = mapper.createArrayNode();
            nouvelEmploye.set("notifications", notiArray);
            ObjectNode nouveauAbonne = mapper.createObjectNode();
            nouveauAbonne.set("employé",nouvelEmploye);
            abonneArray.add(nouveauAbonne);
            saveAllAbonnes(abonneArray);
        }
        else
        {
            System.out.println("Vous êtes déjà abonné");
        }
    }

    //supprimer un abonné
    public void delete(Abonne abonne)
    {
       //verifier si l'abonné existe avant de le desabonner
        if(emailExiste(abonne.getEmail()))
        {
            //recuperer la liste des abonnés
            ArrayNode abonneArray = getAllAbonnes();
            for(int i =0; i< abonneArray.size(); i++)
            {
                JsonNode employe = abonneArray.get(i).get("employé");
                if(employe.get("email").asText().equals(abonne.getEmail()))
                {
                    abonneArray.remove(i);
                    System.out.println("Vous avez été desabonné");
                    break;
                }
            }
            saveAllAbonnes(abonneArray);
        }
        else {
            System.out.println("Vous êtes déjà désabonné");
        }
    }

    public Abonne getAbonne(String email)
    {
        ArrayNode abonneArray = getAllAbonnes();
        for(JsonNode node : abonneArray)
        {
            if(node.get("employé").get("email").asText().equals(email))
            {
                JsonNode employe = node.get("employé");
                return Factory.abonneFactory(employe);
            }
        }
        return null;
    }

    public void getNotifications(Abonne abonne)
    {
        Abonne ab = getAbonne(abonne.getEmail());
        if(ab!=null)
        {
            if(!ab.getNotifications().isEmpty())
            {
                for(String notifications : ab.getNotifications())
                {
                    System.out.println(notifications);
                }
            }
            else
            {
                System.out.println("Vous n'avez aucune nofications");
            }
        }
    }
}
