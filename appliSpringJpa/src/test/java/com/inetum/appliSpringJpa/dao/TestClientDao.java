package com.inetum.appliSpringJpa.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inetum.appliSpringJpa.entity.Client;
import com.inetum.appliSpringJpa.entity.Compte;
import com.inetum.appliSpringJpa.entity.Personne;
import com.inetum.appliSpringJpa.entity.Personne.EtatPersonne;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
public class TestClientDao {
	
	Logger logger = LoggerFactory.getLogger(TestClientDao.class);
	
	@Autowired 
	private DaoCompte daoCompteJpa;
	
	@Autowired 
	private DaoClient daoClientJpa;
	
	@Autowired 
	private DaoPersonne daoPersonneJpa;
	
	@Test
	public void testClientEtCompte() {
		
		Personne pers1 = daoPersonneJpa.insert(new Personne(null,"luc" , "SkyWalker"  ));
		logger.debug("pers1=" + pers1);
		
		Client clientX = new Client(null,"jean" , "Aimare");
		clientX.setEtat(Personne.EtatPersonne.ENDORMIE);
		clientX = daoClientJpa.insert(clientX);
		Client clientY = daoClientJpa.insert(new Client(null,"axelle" , "Aire"));
		
		
		Compte compteA = new Compte(null,"compte_A" , 50.0); //à enregistrer
		compteA.getClients().add(clientX); //V2 @ManyToMany
		//compteA.setClient(clientX); //compteA associé au clientX , V1 @ManyToOne
    	compteA = daoCompteJpa.insert(compteA); //sauvegardé
    			
    	Compte compteB = new Compte(null,"compte_B" , 70.0); //à enregistrer
    	compteB = daoCompteJpa.insert(compteB); //sauvegardé
    	//compteB.setClient(clientX); //compteB associé au clientX , V1 @ManyToOne
    	compteB.getClients().add(clientX); //V2 @ManyToMany
    	compteB.getClients().add(clientY); //V2 @ManyToMany
    	daoCompteJpa.update(compteB);//liasion sauvegardée
    	
    	Compte compteC = new Compte(null,"compte_C" , 80.0); //à enregistrer
		//compteC.setClient(clientY); //compteC associé au clientY, V1 @ManyToOne
    	compteC.getClients().add(clientY); //V2 @ManyToMany
    	compteC = daoCompteJpa.insert(compteC); //sauvegardé
    	
    	//V1: Si relation bi-directionnelle (codée dans les deux sens: @ManyToOne et @OneToMany):
    	//Client clientXRelu = daoClientJpa.findById(clientX.getNumero());//with lazy exception
    	Client clientXRelu = daoClientJpa.findClientWithComptesById(clientX.getNumero());
    	logger.debug("clientXRelu="+clientXRelu);
    	logger.debug("comptes de clientXRelu="+clientXRelu.getComptes());
    	assertTrue(clientXRelu.getComptes().size()==2);
    
    	
    	//V2: Si relation uni-directionnelle (codée que dans le sens principal: @ManyToOne ):
    	Client clientXReluV2 = daoClientJpa.findById(clientX.getNumero());
    	logger.debug("clientXReluV2="+clientXReluV2);
    	assertEquals("jean" ,clientXReluV2.getPrenom() );
    
    	List<Compte> comptesDuClientX = daoCompteJpa.findComptesOfClient(clientXReluV2.getNumero());
    	logger.debug("comptesDuClientX="+comptesDuClientX);
    	assertTrue(comptesDuClientX.size()==2);
    	
    	
    	Client clientYRelu = daoClientJpa.findClientWithComptesById(clientY.getNumero());
    	logger.debug("clientYRelu="+clientYRelu);
    	assertEquals("axelle" ,clientYRelu.getPrenom() );
    	logger.debug("comptes de clientYRelu="+clientYRelu.getComptes());
    	assertTrue(clientYRelu.getComptes().size()==2);
    	

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
