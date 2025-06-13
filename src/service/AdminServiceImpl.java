package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.mail.MessagingException;
import model.Employe;
import model.Abonne;
import repositorie.AbonneRepository;
import repositorie.EmployeRepository;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

public class AdminServiceImpl extends NotificationServiceImpl {
    EmployeRepository employeRepository = new EmployeRepository();
    AbonneRepository abonneRepository = new AbonneRepository();
    Scanner entre = new Scanner(System.in);
    //pour afficher la liste des employés par l'admin
    public void afficherEmploye()
    {
        List<Employe> listEmploye = employeRepository.getListEmploye();
        System.out.println("La liste des employés");
        int i = 1;
        for (Employe employe : listEmploye) {
            System.out.println(i+" ----------------------------------------");
            System.out.println("Nom: " + employe.getNom());
            System.out.println("Prenom: " + employe.getPrenom());
            System.out.println("Email: " + employe.getEmail());
            i++;
        }
    }

    //pour ajouter un abonné par admin
    public void ajouterAbonne()
    {
        System.out.println("Donner l'email");
        String email = entre.nextLine();
        Employe employe = employeRepository.getEmploye(email);
        if(employe!=null)
        {
            abonneRepository.ajouter(employeRepository.getEmploye(email));
        }
        else
        {
            System.out.println("Employé non trouvé ");
            System.out.println("Veillez verifier l'email");
        }

    }

    //pour retirer un abonné par l'admin
    public void retirerAbonne(Abonne abonne)
    {
        System.out.print("Email de l'abonné:");
        String email = entre.nextLine();
        Abonne abon = abonneRepository.getElement(email);
        if(abonne!=null)
        {
           if(!abon.getEmail().equals(abonne.getEmail()))
           {
               repository.delete(abon);
           }
           else
           {
               System.out.println("Veillez vous desabonné");
           }
        }
    }

    //afficher la liste des abonnés par l'admin
    public void afficherAbonne()
    {
        ArrayNode abonneArray = abonneRepository.getAllElements();
        int i = 1;
        for(JsonNode node: abonneArray)
        {
            JsonNode employe = node.get("employé");
            System.out.println(i+" ----------------------------------------");
            System.out.println("Nom: " + employe.get("nom"));
            System.out.println("Prenom: "+ employe.get("prenom"));
            System.out.println("Email: "+ employe.get("email"));
            i++;
        }
    }

    //pour que l'admin se desactive
    public void seDesabonner(Abonne e) throws MessagingException, UnsupportedEncodingException {
        System.out.println("Veuillez choisir un nouveau admin avant de quitter");
        System.out.print("Email du nouveau admin : ");
        String emailNouveauAdmin = entre.nextLine();

        // Récupérer le nouvel employé
        Employe nouveauAdmin = employeRepository.getEmploye(emailNouveauAdmin);
        if (nouveauAdmin == null) {
            System.out.println("Aucun employé trouvé avec cet email.");
            return;
        }

        // Mise à jour des rôles
        Employe ancienAdmin = employeRepository.getEmploye(e.getEmail());
        if (ancienAdmin != null) {
            ancienAdmin.setEstAdmin(false);
        }
        nouveauAdmin.setEstAdmin(true);

        // Sauvegarder les changements
        employeRepository.updateEmploye(ancienAdmin);
        employeRepository.updateEmploye(nouveauAdmin);

        // Envoi d’un email d’information
        MailService mailService = new MailService();
        mailService.envoyerEmail(
                e.getPrenom() + " " + e.getNom(),
                emailNouveauAdmin,
                "Nomination en tant qu'administrateur",
                e.getNom() + " vous a délégué le rôle d'administrateur."
        );
        System.out.println("✅ Le rôle d'administrateur a été transféré à " + nouveauAdmin.getPrenom() + " " + nouveauAdmin.getNom());

        // se desabonner
        super.seDesabonner(e);
    }

    //verifier si un abonné est admin
    public void verifierAbonnement()
    {
        System.out.println("Saisissez l'email");
        String email = entre.nextLine();
        if(repository.getElement(email)!=null)
        {
            Abonne abonne = repository.getElement(email);
            System.out.println("Nom: "+abonne.getNom());
            System.out.println("Prenom: "+abonne.getPrenom());
            System.out.println("Email: "+abonne.getEmail());
        }
        else
        {
            System.out.println("Cet email n'est pas abonné");
        }
    }
}
