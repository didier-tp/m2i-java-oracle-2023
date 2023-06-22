package tp;

import java.util.List;

import tp.dao.PersonneDAO;
import tp.dao.PersonneDaoJdbc;
import tp.dao.PersonneDaoSimu;
import tp.entity.Personne;

public class MyApp {

	public static void main(String[] args) {
		//testCrud();
		//testConnectionJdbc();
		testCrudJdbc();
	}
	
	public static void testConnectionJdbc() {
		PersonneDaoJdbc personneDaoJdbc = new PersonneDaoJdbc();
		System.out.println("connexion etablie=" + personneDaoJdbc.etablirConnexion());
	}
	
	public static void testCrudJdbc() {
	
        PersonneDAO personneDAO = new PersonneDaoJdbc();
		
		//créer quelques personnes
		Personne p1 = new Personne(null,"alex" , "Therieur");//id encore inconnu
		Personne savedP1 = personneDAO.createPersonne(p1); //on demande à sauvegarder l'objet en base
		System.out.println("savedP1=" + savedP1.toString());
	}
	
	public static void testCrud() {
		
		PersonneDAO personneDAO = new PersonneDaoSimu();
		
		//créer quelques personnes
		Personne p1 = new Personne(null,"jean" , "Bon");//id encore inconnu
		Personne savedP1 = personneDAO.createPersonne(p1); //on demande à sauvegarder l'objet en base
		System.out.println("savedP1=" + savedP1.toString());
		
		Personne p2 = personneDAO.createPersonne(new Personne(null,"axelle" , "Aire")); 
		System.out.println("savedP2=" + p2.toString());
		
		//changement des valeurs de p2 en mémoire
		p2.setPrenom("sandrine");
		
		//enregistrement des changement en base de données :
		personneDAO.updatePersonne(p2);
		
		//recherche des enregistements modifiés pour vérifier:
		Personne p2MisAJour = personneDAO.rechercherPersonneParId(p2.getId());
		System.out.println("p2MisAJour=" + p2MisAJour.toString());
		
		//supprimer p2 dans la base de données:
		personneDAO.deletePersonne(p2.getId());
		
		//recherche personnes restantes pour vérifier:
		List<Personne> personnes = personneDAO.rechercherToutesPersonnes();
		System.out.println("personnes:");
		for(Personne p : personnes) {
			System.out.println(("\t" + p));
		}
		
	}

}
