package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {
    //on charge properties
    static Properties config = new Properties();


    static
    {
        try(InputStream input = MailService.class.getClassLoader().getResourceAsStream("resources.properties")) {
            if(input==null)
            {
                throw new RuntimeException("fichier de configuration non trouvé");
            }
            config.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // L’email de l'application
    private final String expediteur = config.getProperty("expediter");
    private final String motDePasse = config.getProperty("motDePasse");
    public void envoyerEmail(String nomExpediteur, String destinataire, String sujet, String contenu) throws UnsupportedEncodingException, MessagingException {
        Properties props = new Properties();
       /*
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");*/
        props.put("mail.smtp.auth",config.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", config.getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.host", config.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", config.getProperty("mail.smtp.port"));
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expediteur, motDePasse);
            }
        });


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteur, nomExpediteur));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            Transport.send(message);
            System.out.println("Email envoyé à " + destinataire);
    }
}
