package interfaces;

import model.Message;

public interface Observer {
    void envoyerMessage(Message message);
    void notifier(String nomDestinateur, String nomExpeditaire);
}
