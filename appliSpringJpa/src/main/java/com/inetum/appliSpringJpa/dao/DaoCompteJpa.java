package com.inetum.appliSpringJpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringJpa.entity.Compte;
import com.inetum.appliSpringJpa.entity.Operation;

@Repository //pour cette classe de DAO soit prise en charge par Spring
@Transactional //pour demander commit/rollback automatiques
public class DaoCompteJpa implements DaoCompte {
	
	//NB: @PersistenceContext permet d'initialiser l'objet technique
		//entityManager à partir d'une configuration
		// src/main/resources/META-INF/persistence.xml
		// ou bien config spring équivalente dans src/main/resources/application.properties
		@PersistenceContext
		private EntityManager entityManager;

		
	
	@Override
	public Compte findCompteWithOperationsById(Long numero) {
		/*
		// solution1 (bidouille) :
		Compte compte = entityManager.find(Compte.class, numero);
		for(Operation op : compte.getOperations()) {
			//boucle for à vide sur les opartions en mode lazy
			//pour que ça déclenche des petits select qui remontent
			//tout de suite les valeurs en mémoire
			//avec la fin de la transaction  et avant entityManager.close() de Spring ou autre
			//===> ça évite LazyInitailisationException
		}
		return compte;
		*/
		
		//solution 2 (avec query spécifique):
		return entityManager
				.createNamedQuery("Compte.findCompteWithOperationsById",Compte.class)
				.setParameter(1,numero)//pour valeur de ?1
				.getSingleResult();
	}
	
	


	@Override
	public Compte findById(Long numero) {
		return entityManager.find(Compte.class, numero);
	}

	@Override
	public List<Compte> findAll() {
		return entityManager.createNamedQuery("Compte.findAll", Compte.class)
				            .getResultList();
	}

	@Override
	public List<Compte> findBySoldeMini(double soldeMini) {
		return entityManager
				.createNamedQuery("Compte.findBySoldeMini",Compte.class)
				.setParameter(1,soldeMini)//pour valeur de ?1
				.getResultList();
	}

	@Override
	public List<Compte> findBySoldeMaxi(double soldeMaxi) {
		return entityManager
				.createNamedQuery("Compte.findBySoldeMaxi",Compte.class)
				.setParameter(1,soldeMaxi)//pour valeur de ?1
				.getResultList();
	}

	@Override
	public Compte insert(Compte cpt) {
		entityManager.persist(cpt);// .numero n'est normalement plus null si auto-incr
		return cpt; // on retourne l'objet modifié (avec .numro non null)
	}

	@Override
	public void update(Compte cpt) {
			entityManager.merge(cpt);
	}

	@Override
	public void deleteById(Long num) {
			Compte compteAsupprimer = entityManager.find(Compte.class, num);
			entityManager.remove(compteAsupprimer);
	}




	@Override
	public List<Compte> findComptesOfClient(Long numClient) {
		return entityManager
				.createNamedQuery("Compte.findComptesOfClient",Compte.class)
				.setParameter(1,numClient)//pour valeur de ?1
				.getResultList();
	}




	@Override
	public void trouverEtDebiter(Long numCompte, double montantDebit) {
		//le @Transaction fait que les mécanismes de spring/jpa/hibernate
		//créent si nécessaire entityManager et transaction (en début de fonction)
		Compte comptePersistant = entityManager.find(Compte.class, numCompte);
		comptePersistant.setSolde(comptePersistant.getSolde() - 20.0);
		//en fin de fonction , le @Transaction fait que les mécanismes de spring/jpa/hibernate
		//ferment automatiquement transaction et entityManager (avec des variantes ...)
		
		//lorsque spring déclenche automatique entityManager.getTransaction().commit()
		//(selon la demande @Transactional),
		//le .commit() déclenche automatiquement entityManager.flush()
		//qui déclenche un .update/.merge automatique sur tous les objets persistants
		//modifiés en mémoire
	}



}
