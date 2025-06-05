package interfaces;

public interface MessagerieService {
    void envoyerEmail(String nomExpediteur, String destinataire, String sujet, String contenu);
}
