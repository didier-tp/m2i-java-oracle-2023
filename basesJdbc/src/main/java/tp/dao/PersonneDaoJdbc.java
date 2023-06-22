package tp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		try( Connection cn = this.etablirConnexion() ) {
			Statement st = cn.createStatement();
			String reqSql = "INSERT INTO personne(prenom,nom) VALUES(' " 
			                  + p.getPrenom() + "' , '" + p.getNom() + "')" ;
			int nbLignesAffectees = st.executeUpdate(reqSql);
			System.out.println("nb lignes inserees : " + nbLignesAffectees);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//finally/close automatique avec try(with_resource_autocloasable) 
		
		return p; //code temporaire (à améliorer)
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
