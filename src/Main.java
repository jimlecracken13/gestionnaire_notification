

import jakarta.mail.MessagingException;
import model.Abonne;
import model.Employe;
import repositorie.EmployeRepository;
import service.AdminServiceImpl;
import service.NotificationServiceImpl;
import utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.Scanner;
import static utils.Utils.estEmailValide;

// dir /s /b src\*.java > sources.txt
 // javac -cp "lib/*" -d out @sources.txt
 // java -cp "out;lib/*" Main


public class Main
{
    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {

        //NotificationService instance
        NotificationServiceImpl service = new NotificationServiceImpl();
        //repositorie employés
        EmployeRepository employeRepository = new EmployeRepository();
        AdminServiceImpl adminService = new AdminServiceImpl();
        String email;
        String motDePasse;
        Scanner entre = new Scanner(System.in);
        Scanner entreM = new Scanner(System.in);
        //nombre d'essai du mot de passe
        int essai = 3;

        int choix=-1;
        boolean correct = false;
        System.out.println("Bienvenue sur Notif+, pour mieux gérer vos notifications");
        do{
            System.out.println("1. Se connecter");
            System.out.println("0. Quitter");
            try
            {
                choix = entre.nextInt();
                switch (choix){
                    case 1:
                        //verifier les informations saisies
                        do {
                            //saisi des informations de l'employé

                            System.out.println("Saisissez votre email:");
                            email = entreM.nextLine();
                            System.out.println("Saisissez mot de passe:");
                            motDePasse = entreM.nextLine();
                            if(motDePasse.isBlank() || !estEmailValide(email))
                            {
                                System.out.println("Mot de passe ou email invalide");
                                essai--;
                            }
                            else
                            {
                                correct = true;
                                //checker si l'employe existe
                                if(employeRepository.getEmploye(email,motDePasse)!=null)
                                {
                                    Employe e = employeRepository.getEmploye(email,motDePasse);
                                    //convertir une seule fois en abonné
                                    Abonne abonneConnecte = Utils.employeToAbonne(e);

                                    int choice=-1;
                                    //System.out.println(e.estAdmin());
                                    //verifier que si l'employé est admin
                                    if(e.estAdmin())
                                    {
                                        adminService.sabonner(abonneConnecte);
                                        System.out.println("Bonjour cher admin "+e.getPrenom()+
                                                " "+e.getNom());
                                        do{
                                            System.out.println("Entrez un chiffre");
                                            System.out.println("1: Afficher les employés");
                                            System.out.println("2: Afficher les abonnés");
                                            System.out.println("3: Ajouter un abonné");
                                            System.out.println("4: Supprimer un abonné");
                                            System.out.println("5: Se désabonner");
                                            System.out.println("6: Afficher mes notifications");
                                            System.out.println("7: Envoyer un message");
                                            System.out.println("8: Verifier un abonnement");
                                            System.out.println("0: Se déconnecter");
                                            try
                                            {
                                                choice = entre.nextInt();
                                                switch(choice)
                                                {
                                                    case 1:
                                                        adminService.afficherEmploye();
                                                        break;
                                                    case 2:
                                                        adminService.afficherAbonne();
                                                        break;
                                                    case 3:
                                                        adminService.ajouterAbonne();
                                                        break;
                                                    case 4:
                                                        adminService.retirerAbonne();
                                                        break;
                                                    case 5:
                                                        adminService.seDesabonner(abonneConnecte);
                                                        choice=0;
                                                        break;
                                                    case 6:
                                                        adminService.afficherNotifications(abonneConnecte);
                                                        break;
                                                    case 7:
                                                        abonneConnecte.envoyerMessage(abonneConnecte);
                                                        break;
                                                    case 8:
                                                        adminService.verifierAbonnement();
                                                    case 0:
                                                        System.out.println("Bye");
                                                        break;
                                                    default:
                                                        System.out.println("Choix invalide");
                                                        break;
                                                }
                                            } catch (InputMismatchException ex) {
                                                System.out.println("Veuillez entrer un nombre valide");
                                                entre.next(); // Vide le buffer du scanner
                                            }

                                        }while(choice!=0);
                                    }
                                    else
                                    {
                                        System.out.println("Bonjour "+e.getPrenom()+" "+e.getNom());
                                        do {
                                            System.out.println("Entrez un chiffre");
                                            System.out.println("1 : S'abonner");
                                            System.out.println("2 : Se désabonner");
                                            System.out.println("3 : Afficher mes notifications");
                                            System.out.println("4 : Envoyer un message");
                                            System.out.println("0 : Se déconnecter");
                                            try{
                                                choice = entre.nextInt();
                                                switch (choice)
                                                {
                                                    case 1:
                                                        service.sabonner(abonneConnecte);
                                                        break;
                                                    case 2:
                                                        service.seDesabonner(abonneConnecte);
                                                        break;
                                                    case 3:
                                                        service.afficherNotifications(abonneConnecte);
                                                        break;
                                                    case 4:
                                                        abonneConnecte.envoyerMessage(abonneConnecte);
                                                        break;
                                                    case 0:
                                                        System.out.println("Bye");
                                                        break;
                                                    default:
                                                        System.out.println("Choix invalide");
                                                        break;
                                                }
                                            }catch(InputMismatchException exp){
                                               System.out.println("Entrée non valide");
                                               entre.next();
                                            }
                                        }while(choice != 0);
                                    }
                                }
                                else
                                {
                                    System.out.println("Nous n'avons pas pu retrouver l'utilisateur");
                                    System.out.println("Veillez verifier l'email et le password");
                                }
                            }
                        }while(!correct && essai>=1);
                        break;
                    case 0:
                        System.out.println("Au revoir");
                        break;
                    default:
                        System.out.println("Choix invalide");
                        break;
                }
            }catch (InputMismatchException exception)
            {
                System.out.println("Veuillez entrer un nombre valide");
                entre.next(); // Vide le buffer du scanner
            }
        }while(choix!=0);

    }


    //recuperer le message

}