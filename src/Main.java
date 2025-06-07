

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Abonne;
import model.Employe;
import repositorie.EmployeRepository;
import service.NotificationService;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static utils.Utils.estEmailValide;

// dir /s /b src\*.java > sources.txt
 // javac -cp "lib/*" -d out @sources.txt
 // java -cp "out;lib/*" Main


public class Main
{
    public static void main(String[] args)
    {

        //NotificationService instance
        NotificationService service = new NotificationService();
        //repositorie employés
        EmployeRepository employeRepository = new EmployeRepository();
        String email;
        String motDePasse;
        Scanner entre = new Scanner(System.in);
        Scanner entreM = new Scanner(System.in);
        //nombre d'essai du mot de passe
        int essai = 3;

        int choix;
        boolean correct = false;
        System.out.println("Bienvenue sur Notif+, pour mieux gérer vos notifications");
        do{
            System.out.println("1. Se connecter");
            System.out.println("0. Quitter");
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
                            int choice;
                            //verifier que si l'employé est admin
                            if(e.estAdmin())
                            {
                                System.out.println("Bonjour cher Admin"+e.getPrenom()+
                                        " "+e.getNom());
                                do{
                                    System.out.println("Entrez un chiffre");
                                    System.out.println("1: Afficher les employés");
                                    System.out.println("2: Afficher les abonnés");
                                    System.out.println("3: Ajouter un abonné");
                                    System.out.println("4: Supprimer un abonné");
                                    System.out.println("5: S'abonner");
                                    System.out.println("6: Se désabonner");
                                    System.out.println("7 : Afficher mes notifications");
                                    System.out.println("8 : Envoyer un message");
                                    System.out.println("0 : Se déconnecter");
                                    choice = entre.nextInt();
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
                                    choice = entre.nextInt();
                                    switch (choice)
                                    {
                                        case 1:
                                            service.sabonner(e);
                                            break;
                                        case 2:
                                            service.seDesabonner(Utils.employeToAbonne(e));
                                            break;
                                        case 3:
                                            service.afficherNotifications(Utils.employeToAbonne(e));
                                            break;
                                        case 4:
                                            //service.notifierAbonne(Utils.employeToAbonne(e));
                                            Abonne ab = Utils.employeToAbonne(e);
                                            ab.envoyerMessage(ab);
                                            break;
                                        case 0:
                                            System.out.println("Bye");
                                            break;
                                        default:
                                            System.out.println("Choix invalide");
                                            break;
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
        }while(choix!=0);

    }


    //recuperer le message

}