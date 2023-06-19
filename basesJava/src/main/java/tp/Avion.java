package tp;

import java.util.ArrayList;

public class Avion {
    private String label; 
    
    private ArrayList<Bagage> bagages = new ArrayList<>();
    
    public void addBagage(Bagage b) {
    	this.bagages.add(b);
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
    		System.out.println("\t" + b.toString() + " de volume= " + b.getVolume());
    	}
    }
    
    
    
}
