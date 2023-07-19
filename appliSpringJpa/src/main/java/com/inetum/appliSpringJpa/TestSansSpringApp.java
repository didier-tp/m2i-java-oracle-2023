package com.inetum.appliSpringJpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.inetum.appliSpringJpa.dao.DaoCompteJpaSansSpring;
import com.inetum.appliSpringJpa.dao.DaoEmployeJpaSansSpring;
import com.inetum.appliSpringJpa.entity.Compte;
import com.inetum.appliSpringJpa.entity.Employe;

// classe de démarrage de l'application (sans utiliser spring)
public class TestSansSpringApp {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("appliSpringJpa");
       //NB: appliSpringJpa= name du persistent-unit configuré dans META-INF/persistence.xml
		
		EntityManager entityManager = emf.createEntityManager();
		
		testDaoEmploye(entityManager);
		testDaoCompteCrud(entityManager);
		testDaoCompteQueries(entityManager);
		
		entityManager.close();
		emf.close();
	}
	
	public static void testDaoEmploye(EntityManager entityManager) {
		DaoEmployeJpaSansSpring daoEmployeJpa = new DaoEmployeJpaSansSpring();
		daoEmployeJpa.setEntityManager(entityManager);
		
		Employe emp1 = new Employe(null, "prenom1", "Nom");
		Employe emp1Sauvegarde = daoEmployeJpa.insert(emp1);
		Long numEmp1 = emp1Sauvegarde.getNumero();
		System.out.println("numero auto_incrementé de emp1Sauvegarde:" 
		                 + numEmp1);
		
		Employe emp1Relu = daoEmployeJpa.findById(numEmp1);
		System.out.println("emp1Relu=" + emp1Relu);
		
		//modif en mémoire
		emp1Relu.setPrenom("nouveau_prenom");
		emp1Relu.setNom("nouveau_nom");
		
		//update en base:
		daoEmployeJpa.update(emp1Relu);
		
		Employe emp1ReRelu = daoEmployeJpa.findById(numEmp1);
		System.out.println("emp1ReRelu apres mise à jour=" + emp1ReRelu);
		
		daoEmployeJpa.deleteById(numEmp1);
		Employe emp1ReReReluQuiExistePlus = daoEmployeJpa.findById(numEmp1);
		System.out.println("emp1ReReReluQuiExistePlus apres suppression=" 
		        + emp1ReReReluQuiExistePlus);//normalement null
		
		List<Employe> employes = daoEmployeJpa.findAll();
		for (Employe emp : employes) {
			System.out.println(emp);
		}
		
		daoEmployeJpa.insert(new Employe(null,"luc" , "SkuWalker"));
		daoEmployeJpa.insert(new Employe(null,"luc" , "yLucke"));
		
		daoEmployeJpa.insert(new Employe(null,"jean" , "Aimare"));
		daoEmployeJpa.insert(new Employe(null,"jean" , "Bon"));
		
		List<Employe> employesAyantPrenomJean = daoEmployeJpa.findByPrenom("jean");
		System.err.println("employesAyantPrenomJean="+employesAyantPrenomJean);
		
	}
	
    public static void testDaoCompteCrud(EntityManager entityManager) {
		
	}
    
    public static void testDaoCompteQueries(EntityManager entityManager) {
    	DaoCompteJpaSansSpring daoCompteJpa = new DaoCompteJpaSansSpring();
    	daoCompteJpa.setEntityManager(entityManager);
    	daoCompteJpa.insert(new Compte(null,"compte_A" , 50.0));
    	daoCompteJpa.insert(new Compte(null,"compte_B" , 80.0));
    	daoCompteJpa.insert(new Compte(null,"compte_C" , 250.0));
    	daoCompteJpa.insert(new Compte(null,"compte_D" , 540.0));
    			
		List<Compte> comptesAvecSoldeMini100 = daoCompteJpa.findBySoldeMini(100.0);
		System.err.println("comptesAvecSoldeMini100="+comptesAvecSoldeMini100);
		
		List<Compte> comptesAvecSoldeMaxi100 = daoCompteJpa.findBySoldeMaxi(100.0);
		System.err.println("comptesAvecSoldeMaxi100="+comptesAvecSoldeMaxi100);
	}
	
	
	
}
