package com.inetum.appliSpringJpa.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class LigneCommande {
	
	@EmbeddedId
	private LigneCommandePK pk;//avec sous parties .numCmde et .numLigne
	
	@ManyToOne
	@MapsId(value = "numCmde")
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name="ref_produit")
	private Produit produit; 
	
	private Integer quantite =1; //1 par défaut

	public LigneCommande(Commande commande,Integer numLigne, Produit produit, Integer quantite) {
		super();
		this.pk = new LigneCommandePK(commande.getNumero(),numLigne);
		this.commande = commande;
		this.produit = produit;
		this.quantite = quantite;
	}
	
	public LigneCommande(Commande commande, Integer numLigne, Produit produit) {
		this(commande,numLigne,produit,1);
	}

	@Override
	public String toString() {
		return "LigneCommande [numLigne=" + pk.getNumLigne() 
		                      + ", numCommande=" + pk.getNumCmde() 
		                      + ", produit=" + produit + 
		                      ", quantite=" + quantite
				+ "]";
	}
	
	
}
