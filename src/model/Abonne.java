package model;

import interfaces.Observer;

public class Abonne extends Employe implements Observer
{
    //constructeur de la classe
    public Abonne(String nom, String prenom, String email) {
        super(nom, prenom, email);
    }
    public void envoyerMessage()
    {
        //
    }
    public void notifier(String nomDestinataire)
    {
        System.out.println(nomDestinataire + ", vous avez reçu une notification de nom_expeditaire");
    }
}
