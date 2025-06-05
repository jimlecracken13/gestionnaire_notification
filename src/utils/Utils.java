package utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import model.Abonne;
import model.Employe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static boolean estEmailValide(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
    //recuperer la liste des employés

    public static Abonne employeToAbonne(Employe e)
    {
        Abonne abonne = new Abonne(e.getNom(),e.getPrenom(),e.getEmail(),
                e.getMotDePasse());
        abonne.setDebutAbonnement(new Date());
        abonne.setNotifications(new ArrayList<>());
        return abonne;
    }
}
