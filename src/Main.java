

import model.Employe;
import model.Message;
import service.NotificationService;

import java.util.ArrayList;
import java.util.List;
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
        List<Employe> employeList = new ArrayList<>();
        Employe employe = new Employe("diallo","amadou","jimlecracken13@gmail.com"
                ,"blablacar");
        Employe employe1 = new Employe("touré", "ousmane", "ousbi@gmail.com",
                "DestinationFinale");
        employeList.add(employe);
        employeList.add(employe1);
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

        //checker si l'employe existe
        if(getEmploye(employeList,email,motDePasse)!=null)
        {
            Employe e = getEmploye(employeList,email,motDePasse);
            System.out.print("Bienvenue dans notre service de notification");
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
                        // service.seDesabonner();
                        break;
                    case 3:
                        System.out.print("choix 3");
                        break;
                    case 4:
                        //service.notifierAbonne();
                        break;
                    case 0:
                        System.out.print("Au revoir");
                        break;
                    default:
                        System.out.print("Choix invalide");
                        break;
                }
            }while(choice != 0);
        }
        else
        {
            System.out.println("Nous n'avons pas pu retrouver l'utilisateur" +
                    "Veillez verifier l'email et le password");
        }



    }

    //recuperer l'employer connecter
    public static Employe getEmploye(List<Employe> employeList, String email, String motDePasse)
    {
        for(Employe e : employeList)
        {
            if(e.getEmail().equals(email) && e.getMotDePasse().equals(motDePasse))
            {
                System.out.print("Bienvenue " + e.getNom());
                return e;
            }
        }
        return null;
    }
    //recuperer le message

}