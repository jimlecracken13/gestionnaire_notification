package interfaces;

import model.Abonne;
import model.Message;

public interface Observer {
    void envoyerMessage(Abonne e);
    void notifier(String nomDestinateur, String nomExpeditaire);
}
