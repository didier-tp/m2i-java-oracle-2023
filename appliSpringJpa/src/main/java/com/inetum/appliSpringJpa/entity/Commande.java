package com.inetum.appliSpringJpa.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedQuery(name="Commande.findByIdwithAllLines",
            query="SELECT cmde FROM Commande cmde LEFT JOIN FETCH cmde.mapLignesCommande ligne WHERE cmde.numero = ?1")
@Getter @Setter @NoArgsConstructor
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	
	@Temporal(TemporalType.DATE)
	private Date dateCommande;

	
	
	@OneToMany(fetch=FetchType.LAZY,
			   mappedBy = "commande" ,
			   cascade = CascadeType.ALL)
	@MapKey(name="pk.numLigne")
	private Map<Integer,LigneCommande> mapLignesCommande = new HashMap<>();
	
	//méthode qui fabrique , ajoute et retourne une nouvelle ligne de commande
	public LigneCommande addLigne(Produit produit, int quantite) {
		int numLigne=1; //par défaut
		Set<Integer>  keys = this.mapLignesCommande.keySet();
		if(!keys.isEmpty()) {
			for(int k : keys) {
				if(k>numLigne) numLigne=k;
			}
			numLigne++;
		}
		LigneCommande ligneCmde = new LigneCommande(this,numLigne,produit,quantite);
		this.getMapLignesCommande().put(numLigne, ligneCmde);
		return ligneCmde;
	}

	
	
	//private Client client;
	
	public Commande(Long numero, Date dateCommande) {
		super();
		this.numero = numero;
		this.dateCommande = dateCommande;
	}
	
	@Override
	public String toString() {
		return "Commande [numero=" + numero + ", dateCommande=" + dateCommande + "]";
	}

	
}
