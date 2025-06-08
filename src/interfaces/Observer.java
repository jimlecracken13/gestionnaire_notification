package interfaces;

import model.Abonne;
import model.Message;

public interface Observer {
    void notifier(String nomDestinateur, String nomExpeditaire);
}
