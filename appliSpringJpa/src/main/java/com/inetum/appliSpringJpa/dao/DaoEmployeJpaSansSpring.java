package com.inetum.appliSpringJpa.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.inetum.appliSpringJpa.entity.Employe;

public class DaoEmployeJpaSansSpring implements DaoEmploye {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Employe findById(Long numero) {
		return entityManager.find(Employe.class, numero);
		// SELECT FROM Employe WHERE numero=?
	}

	@Override
	public List<Employe> findAll() {
		return entityManager.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
	}
	
	@Override
	public List<Employe> findByPrenom(String prenom) {
		return entityManager
				.createQuery("SELECT e FROM Employe e WHERE e.prenom= :prenomCherche", 
						     Employe.class)
				.setParameter("prenomCherche",prenom)
				.getResultList();
	}

	@Override
	public Employe insert(Employe emp) {
		try {
			entityManager.getTransaction().begin();
			// en entrée , emp est un nouvel objet employé avec .numero à null (encore inconnu)
			// déclenche automatiquement INSERT INTO Employe(prenom, nom....)
			// VALUES(emp.getPrenom() , ....)
			entityManager.persist(emp);// .numero n'est normalement plus null si auto-incr
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("echec insertNew(employe)");
		}
		return emp; // on retourne l'objet modifié (avec .numro non null)
	}

	@Override
	public void update(Employe emp) {
		try {
			entityManager.getTransaction().begin();
			// déclenche automatiquement UPDATE Employe SET prenom=?, nom=? WHERE numero=?
			entityManager.merge(emp);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("echec update(employe)");
		}
	}

	@Override
	public void deleteById(Long num) {
		try {
			entityManager.getTransaction().begin();
			
			Employe empAsupprimer = entityManager.find(Employe.class, num);
			entityManager.remove(empAsupprimer);
			//déclenche automatiquement DELETE FROM Employe WHERE numero=empAsupprimer.getNumero()
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("echec deleteById");
		}

	}

	

}
