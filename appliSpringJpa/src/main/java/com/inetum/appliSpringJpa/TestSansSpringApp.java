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
		daoEmployeJpa.insert(emp1);
		
		List<Employe> employes = daoEmployeJpa.findAll();
		for (Employe emp : employes) {
			System.out.println(emp);
		}
		entityManager.close();
		emf.close();
	}
}
