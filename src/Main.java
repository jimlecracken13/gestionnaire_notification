

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Abonne;
import model.Employe;
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
        String email;
        String motDePasse;
        Scanner entre = new Scanner(System.in);
        //nombre d'essai du mot de passe
        int essai = 3;

        int choice;
        boolean correct = false;
        //Premier Principale
        do{
            System.out.println("Bienvenue sur Notif+, pour mieux gérer vos notifications");
            System.out.println("1. Se connecter");
            System.out.println("0. Quitter");
            choice = entre.nextInt();
            switch (choice){
            case 1:
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
                if(getEmploye(email,motDePasse)!=null)
                {
                    Employe e = getEmploye(email,motDePasse);
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

                                break;
                            case 4:
                                //service.notifierAbonne(Utils.employeToAbonne(e));
                                Abonne ab = Utils.employeToAbonne(e);
                                ab.envoyerMessage(ab);
                                break;
                            case 0:
                                System.out.print("Au revoir");
                                break;
                            default:
                                System.out.println("Choix invalide");
                                break;
                        }
                    }while(choice != 0);
                }
                else
                {
                    System.out.println("Nous n'avons pas pu retrouver l'utilisateur");
                    System.out.println("Veillez verifier l'email et le password");
                }
                break;
            case 0:
                System.out.println("Au revoir");
                break;
                default:
                    System.out.println("Choix invalide");
                    break;
            }
        }while(choice!=0);

    }

    //recuperer l'employer connecter
    public static Employe getEmploye(String email, String motDePasse)
    {
        //fichier
        File file = new File("database.json");
        ObjectMapper mapper = new ObjectMapper();
        Employe em = null;
        try {
            JsonNode root = mapper.readTree(file);
            ArrayNode abonneNode = (ArrayNode) root.get("employés");
            for(JsonNode node: abonneNode)
            {
                if(node.get("email").asText().equals(email) &&
                        node.get("motDePasse").asText().equals(motDePasse))
                {
                    String nom = node.get("nom").asText();
                    String prenom = node.get("prenom").asText();
                     em = new Employe(nom, prenom, email, motDePasse);
                     break;
                }
            }
            return em;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //recuperer le message

}