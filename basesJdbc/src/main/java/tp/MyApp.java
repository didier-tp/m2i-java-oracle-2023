package tp;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tp.dao.PersonneDAO;
import tp.dao.PersonneDaoJdbc;
import tp.dao.PersonneDaoSimu;
import tp.entity.Personne;

public class MyApp {

	public static void main(String[] args) {
		//testCrud();
		//testConnectionJdbc();
		//testCrudJdbc();
		//testEnum();
		//testDate();
		testIntrospection() ;
	}
	
	public static void testIntrospection() {
		try {
			Class<?> c = Class.forName("tp.entity.Personne");
			Field[] fields  = c.getDeclaredFields();
			System.out.println("liste attributs:");
			for(Field f : fields) {
				System.out.println("\t" + f.getName() + " de type=" + f.getType().getSimpleName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
	
	public static void testDate() {
		Date date = new Date(); //en version java.util
		System.out.println("date (aujourd'hui)=" + date);
		//en exercice : avec SimpleDateFormat (page 106)
		//afficher la date au format international "2023-06-22" 
		//et au format francais "22/06/2023"
		SimpleDateFormat dateFormatUs = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatFr = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("today (us): " + dateFormatUs.format(date) );
		System.out.println("aujourdhui (fr): " + dateFormatFr.format(date) );
		
		//dans le sens inverse :
		String dateOnAmarcheSurLaLune_fr = "21/07/1969";
		try {
			Date dateOnAmarcheSurLaLune = dateFormatFr.parse(dateOnAmarcheSurLaLune_fr);
			System.out.println("first step on  moon (us): " + dateFormatUs.format(dateOnAmarcheSurLaLune) );
		} catch (ParseException e) {
				e.printStackTrace();
		}
		
	}
	
	
	public static void testEnum() {
		Personne p1 = new Personne(null,"alex" , "Therieur");
		//V1 (de type String) : on ne contrôle pas trop les valeurs possibles:
		//p1.setNationalite("francais"); //ou bien "francaise" ou bien "french" ou bien "martien"
		
		System.out.println("nationalité par défaut de p1 = " + p1.getNationalite());
		
		//V2 (de type enum Nationalite):
		//p1.setNationalite(Personne.Nationalite.FRANCAIS);
		
		
		p1.setNationalite(Personne.Nationalite.ANGLAIS);
		
		System.out.println("nationalité de p1 = " + p1.getNationalite());
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
		
		//recherche liste personnes pour vérifier:
		List<Personne> personnes = personneDAO.rechercherToutesPersonnes();
		System.out.println("personnes:");
		for(Personne p : personnes) {
			System.out.println(("\t" + p));
		}
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
