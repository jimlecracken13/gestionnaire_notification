package utils;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Utils {
    //static ObjectMapper mapper = new ObjectMapper();
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean estEmailValide(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
    /*
    //je recupère la liste des employés
    public static List<Employe> getEmployes()  {
        try
        {
            // Lecture du JSON et transformation en liste d'objets Java
            List<Employe> employes = List.of(mapper.readValue(
                    new File("database.json"),
                    Employe[].class // tableau -> converti en List avec List.of
            ));
            return employes;
        }
        catch (com.fasterxml.jackson.databind.JsonMappingException e) {
        System.err.println("Erreur de mapping JSON : " + e.getMessage());
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
        System.err.println("Erreur de traitement JSON : " + e.getMessage());
        } catch (IOException e) {
        System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }

    }*/
}
