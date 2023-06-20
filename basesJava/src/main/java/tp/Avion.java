package tp;

import java.util.ArrayList;

public class Avion {
    private String label; 
    
    private ArrayList<Bagage> bagages = new ArrayList<>();
    
    private ArrayList<Personne> personnes = new ArrayList<>();
    
    public void addBagage(Bagage b) {
    	this.bagages.add(b);
    }
    
    public void addPersonne(Personne p) {
    	this.personnes.add(p);
    }
    
    public void retirerBagage(Bagage b) {
    	this.bagages.remove(b);
    }
    
    public double chargeUtile() {
    	//calculer et retourner le poids total des éléments mis dans l'avion
    	double poidsTotal=0.0;
    	for(Bagage b : this.bagages) {
    		poidsTotal += b.getPoids();
    	}
    	return poidsTotal;
    }
    
    public void afficher() {
    	System.out.println("Avion " + this.label);
    	System.out.println("chargeUtile " + this.chargeUtile());
    	System.out.println("bagages: ");
    	for(Bagage b : this.bagages) {
    		//b est ici quelquefois de type Sac et d'autres fois de  type Valise
    		System.out.println("\t" + b.toString() + " de volume= " + b.getVolume());
    		//b.toString() et b.getVolume() déclenche du POLYMORSPHIME 
    		
    		/*
    		//code compliqué pour rien (qui n'utilise pas le polymorphisme):
    		if(b instanceof Sac) {
    			System.out.println("\t volume de sac =" + ((Sac) b).volumeDeSac());
    		}
    		else if(b instanceof Valise) {
    			System.out.println("\t volume de valise =" + ((Valise) b).volumeDeValise());
    		}
    		*/
    	}
    	
    	System.out.println("personnes: ");
    	for(Personne p : this.personnes) {
    		System.out.println("\t" + p.toString() + " employeur=" + p.getEmployeur());
    	}
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
    
    
    
}
