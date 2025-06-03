package model;

import interfaces.Observer;

public class Abonne extends Employe implements Observer
{
    //constructeur de la classe
    public Abonne(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }
    public void envoyerMessage(Message message)
    {
        System.out.println(message.message);
    }
    public void notifier(String nomDestinataire)
    {
        System.out.println(nomDestinataire + ", vous avez reçu une notification de nom_expeditaire");
    }
}
