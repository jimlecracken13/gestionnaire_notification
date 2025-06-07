package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Employe;
import model.Abonne;
import repositorie.AbonneRepository;
import repositorie.EmployeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    EmployeRepository employeRepository = new EmployeRepository();
    AbonneRepository abonneRepository = new AbonneRepository();
    Scanner entre = new Scanner(System.in);
    public void afficherEmploye()
    {
        List<Employe> listEmploye = employeRepository.getListEmploye();
        for (Employe employe : listEmploye) {
            System.out.println("Nom: " + employe.getNom());
            System.out.println("Prenom: " + employe.getPrenom());
            System.out.println("Email: " + employe.getEmail());
        }
    }

    public void ajouterAbonne()
    {
        System.out.println("Donner l'email");
        String email = entre.nextLine();
        Employe employe = employeRepository.getEmploye(email);
        if(employe!=null)
        {
            abonneRepository.ajouter(employeRepository.getEmploye(email));
            System.out.println("Abonné ajouté");
        }
        else
        {
            System.out.println("Employé non trouvé ");
            System.out.println("Veillez verifier l'email");
        }

    }

    public void retirerAbonne()
    {
        System.out.print("Email de l'abonné:");
        String email = entre.nextLine();
        Abonne abonne = abonneRepository.getAbonne(email);
        if(abonne!=null)
        {
            abonneRepository.delete(abonne);
        }
    }

    public void afficherAbonne()
    {
        ArrayNode abonneArray = abonneRepository.getAllAbonnes();
        for(JsonNode node: abonneArray)
        {
            JsonNode employe = node.get("employe");
            System.out.println("Nom: " + employe.get("nom"));
            System.out.println("Prenom: "+ employe.get("prenom"));
            System.out.println("Email: "+ employe.get("email"));
        }
    }

}
