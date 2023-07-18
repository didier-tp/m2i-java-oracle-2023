package com.inetum.mbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inetum.data.Produit;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.event.ValueChangeEvent;

@ManagedBean
//@ApplicationScoped //attention: partagé par tous les utilisateurs si @ApplicationScoped
@SessionScoped
public class ProduitMBean {
	
	private String categorie=null; //categorie choisie/sélectionnée par l'utilisateur
	
	private static Map<String,List<Produit>> mapCategorieProduits ;
	
	private List<String> categoriesSelectionnables;
	
	static {
		mapCategorieProduits = new HashMap<String,List<Produit>>();
		var listeLivres = new ArrayList<Produit>();
		listeLivres.add(new Produit("l1" , "livre1" , 13.56 , "LIVRE"));
		listeLivres.add(new Produit("l2" , "java 17" , 23.56 , "LIVRE"));
		listeLivres.add(new Produit("l3" , "java pour les nuls" , 23.56 , "LIVRE"));
		mapCategorieProduits.put("LIVRE", listeLivres);
		
		var listeDvd = new ArrayList<Produit>();
		listeDvd.add(new Produit("dvd1" , "reine des neiges" , 8.76 , "DVD"));
		listeDvd.add(new Produit("dvd2" , "asterix et obelix" , 9.76 , "DVD"));
		mapCategorieProduits.put("DVD", listeDvd);
	}
	
	//pour variante avec bouton de déclenchement:
	public String selectionnerCategorie() {
		this.produits = mapCategorieProduits.get(categorie);
		return null;//rester sur même page
	}
	
	//pour variante sans bouton de déclenchement:
	public void onSelectionnerCategorie(ValueChangeEvent event) {
		    this.categorie = (String) event.getNewValue();
			this.produits = mapCategorieProduits.get(categorie);
	}
	
	private List<Produit> produits ;
	
	public ProduitMBean() {
		this.categoriesSelectionnables = new ArrayList<String>();
		categoriesSelectionnables.add("LIVRE");
		categoriesSelectionnables.add("DVD");
		
		this.produits = new ArrayList<Produit>();
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public List<String> getCategoriesSelectionnables() {
		return categoriesSelectionnables;
	}

	public void setCategoriesSelectionnables(List<String> categoriesSelectionnables) {
		this.categoriesSelectionnables = categoriesSelectionnables;
	}
	
	

}
