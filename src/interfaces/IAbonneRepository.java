package interfaces;

import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Employe;

public interface IAbonneRepository {
    ArrayNode getAllElements();
    void saveAllElements(ArrayNode abonneArray);
    boolean emailExiste(String email);
    void ajouter(Employe employe);
    void delete(Observer observer);
    Observer getElement(String email);
    void getNotifications(Observer observer);
}
