package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;


import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {

    // L’email de l'application
    private final String expediteur = "notifplusapp@gmail.com";
    private final String motDePasse = "txjkmukvfznvxkvo";

    public void envoyerEmail(String nomExpediteur, String destinataire, String sujet, String contenu) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expediteur, motDePasse);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteur, nomExpediteur));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            Transport.send(message);
            System.out.println("Email envoyé à " + destinataire);
        }catch(SendFailedException ex){
            System.err.println("❌ L'e-mail n'a pas été envoyé à l'adresse invalide : " + destinataire);
        }catch (MessagingException ex) {
            System.err.println("❌ Une erreur s'est produite lors de l'envoi de l'e-mail à : " + destinataire);
             // Pour le debug
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
