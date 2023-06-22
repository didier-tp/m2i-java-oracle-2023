package tp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import tp.entity.Personne;

public class PersonneDaoJdbc implements PersonneDAO {
	
	public Connection etablirConnexion() {
		Connection cn =null;
		try {
			ResourceBundle ressources = ResourceBundle.getBundle("paramDB") ; // paramDB.properties
			String driver = ressources.getString("driver"); 
			String chUrl = ressources.getString("url");
			String username = ressources.getString("username"); 
			String password = ressources.getString("password");
			Class.forName(driver); 
			cn = DriverManager.getConnection(chUrl,username,password) ;
		} catch (ClassNotFoundException e) {
			System.err.println("le driver jdbc ne peut pas etre chargé en mémoire , erreur de nom ou bien oubli dans pom.xml");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("echec de connexion");
			e.printStackTrace();
		} 
		return cn;
	}

	@Override
	public Personne createPersonne(Personne p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personne rechercherPersonneParId(Integer idPers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personne> rechercherToutesPersonnes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePersonne(Personne p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePersonne(Integer idPers) {
		// TODO Auto-generated method stub

	}

}
