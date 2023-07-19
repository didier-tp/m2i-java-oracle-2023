package com.inetum.appliSpringJpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.inetum.appliSpringJpa.dao.DaoEmployeJpaSansSpring;
import com.inetum.appliSpringJpa.entity.Employe;

// classe de démarrage de l'application (sans utiliser spring)
public class TestSansSpringApp {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("appliSpringJpa");
       //NB: appliSpringJpa= name du persistent-unit configuré dans META-INF/persistence.xml
		
		EntityManager entityManager = emf.createEntityManager();
		
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
		entityManager.close();
		emf.close();
	}
}
