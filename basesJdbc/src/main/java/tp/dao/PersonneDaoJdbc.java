package tp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	Integer recupValeurAutoIncrPk(PreparedStatement pst){
		Integer  pk=null;
		try {
		  ResultSet rsKeys = pst.getGeneratedKeys();
		if(rsKeys.next()){ pk= rsKeys.getInt(1); }
		} catch (SQLException e) { e.printStackTrace(); }
		  return pk;
	}

	@Override
	public Personne createPersonne(Personne p) {
		
		try( Connection cn = this.etablirConnexion() ) {
			/*
			Statement st = cn.createStatement();
			String reqSql = "INSERT INTO personne(prenom,nom) VALUES(' " 
			                  + p.getPrenom() + "' , '" + p.getNom() + "')" ;
			int nbLignesAffectees = st.executeUpdate(reqSql);
			*/
			PreparedStatement st = 
					cn.prepareStatement("INSERT INTO personne(prenom,nom) VALUES(?,?)",
							            Statement.RETURN_GENERATED_KEYS);
			st.setString(1, p.getPrenom()); //premier ? remplacé par la valeur du prenom
			st.setString(2, p.getNom()); //deuxième ? remplacé par la valeur du nom
			int nbLignesAffectees = st.executeUpdate();
			Integer idAutoIncremente = recupValeurAutoIncrPk(st);
			p.setId(idAutoIncremente);
			//System.out.println("nb lignes inserees : " + nbLignesAffectees);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//finally/close automatique avec try(with_resource_autocloasable) 
		
		return p; //on retourne p avec la clef primaire auto incrémentée
	}

	@Override
	public Personne rechercherPersonneParId(Integer idPers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personne> rechercherToutesPersonnes() {
		List<Personne> listePersonnes = new ArrayList<>();
		try( Connection cn = this.etablirConnexion() ) {
			Statement st = cn.createStatement();
			String reqSql = "SELECT * FROM personne";
			ResultSet rs = st.executeQuery(reqSql);
			while(rs.next()){
				Integer id = rs.getInt("id");
				String nom = rs.getString("nom"); // récupérer la valeur de la colonne "nom"
				String prenom = rs.getString("prenom");
				Personne p = new Personne(id,prenom,nom);
				listePersonnes.add(p);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    //finally/close automatique avec try(with_resource_autocloasable) 
		return listePersonnes;
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
