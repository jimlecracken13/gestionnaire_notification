package model;

public class Employe
{
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private boolean estAdmin = false;

    public boolean estAdmin() {
        return estAdmin;
    }

    public void setEstAdmin(boolean estAdmin) {
        this.estAdmin = estAdmin;
    }

    //constructor de la class
    public Employe(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse(){return motDePasse;}
    public void setMotDePasse(String motDePasse){this.motDePasse= motDePasse;}
}
