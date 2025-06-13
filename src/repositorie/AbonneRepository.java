package repositorie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import interfaces.IAbonneRepository;
import interfaces.Observer;
import model.Abonne;
import model.Employe;
import model.Message;
import utils.FactoryAbonne;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AbonneRepository implements IAbonneRepository {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("database.json");
    FactoryAbonne factoryAbonne = new FactoryAbonne();

    //recupérer tous les abonnés
    public ArrayNode getAllElements() {
        JsonNode root = null;
        try {
            root = mapper.readTree(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (ArrayNode) root.get("abonnés");
    }

    //enregistrer tous les abonnés
    public void saveAllElements(ArrayNode abonneArray)
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
        ArrayNode abonneNode = getAllElements();
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
        //verifié qu'il n'est pas déjà abonné
        if(!emailExiste(newAbonne.getEmail()))
        {
            //creer un nouvel objet employés
            ObjectNode nouvelEmploye = mapper.createObjectNode();
            ArrayNode abonneArray = getAllElements();
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
            saveAllElements(abonneArray);
            System.out.println("Abonnement éffectué");
        }
        else
        {
            System.out.println("Vous êtes déjà abonné");
        }
    }

    //supprimer un abonné
    public void delete(Observer observer)
    {
       //verifier si l'abonné existe avant de le desabonner
        Abonne abonne = (Abonne) observer;
        if(emailExiste(abonne.getEmail()))
        {
            //recuperer la liste des abonnés
            ArrayNode abonneArray = getAllElements();
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
            //enlever ça voir si ça marche
            saveAllElements(abonneArray);
        }

        else {
            System.out.println("Vous êtes déjà désabonné");
        }
    }

    //recupérer un abonné par son Email
    public Abonne getElement(String email)
    {
        ArrayNode abonneArray = getAllElements();
        for(JsonNode node : abonneArray)
        {
            if(node.get("employé").get("email").asText().equals(email))
            {
                JsonNode employe = node.get("employé");
                return (Abonne) factoryAbonne.create(employe);
            }
        }
        return null;
    }

    //recupérer la liste des notifications d'un abonné
    public void getNotifications(Observer observer)
    {
        Abonne abonne = (Abonne)observer;
        Abonne ab = getElement(abonne.getEmail());
        if(ab!=null)
        {
            if(!ab.getNotifications().isEmpty())
            {
                int i = 1;
                for(Message notifications : ab.getNotifications())
                {
                    System.out.println(i+" ---------------------------");
                    //afficher les messages
                    System.out.println(notifications.getMessage());
                    //afficher la date et l'heure d'envois de la notification
                    System.out.println(notifications.getDate());
                    System.out.println(notifications.getTime());
                    i++;
                }
            }
            else
            {
                System.out.println("Vous n'avez pas de nofications");
            }
        }
    }

}
