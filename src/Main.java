

import service.NotificationService;
import java.util.Scanner;
import static utils.Utils.estEmailValide;


public class Main
{
    public static void main(String[] args)
    {

        //NotificationService instance
        NotificationService service = new NotificationService();
        String email;
        String motDePasse;
        Scanner entre = new Scanner(System.in);
        //nombre d'essai du mot de passe
        int essai = 3;

        int choice;
        boolean correct = false;


        //verifier les informations saisies
        do {
            //saisi des informations de l'employé
            System.out.println("Veillez vous connectez");
            System.out.println("email");
            email = entre.nextLine();
            System.out.println("mot de passe");
            motDePasse = entre.nextLine();
            if(!estEmailValide(email) || motDePasse==null)
            {
                System.out.println("Mot de passe ou email invalide");
                essai--;
            }
            else
            {
                correct = true;
            }
        }while(!correct && essai>=1);


        System.out.print("Bienvenue dans notre service de notification");
        do {

            System.out.println("Entrez un chiffre");
            System.out.println("1 pour s'abonner");
            System.out.println("2 pour se désabonner");
            System.out.println("3 pour afficher mes notifications");
            System.out.println("4 pour Envoyer un message");
            System.out.println("0 pour quitter");
            choice = entre.nextInt();
            switch (choice)
            {
                case 1:
                   // service.sabonner();
                    break;
                case 2:
                   // service.seDesabonner();
                    break;
                case 3:
                    System.out.print("choix 3");
                    break;
                case 4:
                    System.out.print("choix 4");
                case 0:
                    System.out.print("Au revoir");
                    break;
                default:
                    System.out.print("Choix invalide");
                    break;
            }
        }while(choice != 0);
    }
}