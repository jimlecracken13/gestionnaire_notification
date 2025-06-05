package service;

import model.Employe;
import repositorie.EmployeRepository;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    EmployeRepository repository = new EmployeRepository();
    public void afficherEmploye()
    {
        List<Employe> listEmploye = repository.getListEmploye();
        for(int i = 0; i< listEmploye.size(); i++)
        {
            System.out.println("Nom: "+ listEmploye.get(i).getNom());
            System.out.println("Prenom: "+ listEmploye.get(i).getPrenom());
            System.out.println("Email: "+ listEmploye.get(i).getEmail());
        }
    }
}
