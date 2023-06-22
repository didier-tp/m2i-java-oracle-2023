package tp.dao;

import java.util.List;
import tp.entity.Personne;
//DAO = Data Access Object avec méthodes CRUD
//en cas de problème , les méthodes de ce DAO vont remonter des exceptions 
//qui héritent de RuntimeException
public interface PersonneDAO {
    public Personne createPersonne(Personne p);//en retour , la Personne avec id auto_incrémentée par la base de donnée
    public Personne rechercherPersonneParId(Integer idPers);
    public List<Personne> rechercherToutesPersonnes();
    public void updatePersonne(Personne p);
    public void deletePersonne(Integer idPers);
}
