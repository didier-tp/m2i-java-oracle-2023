package tp;

public class Valise extends Bagage {
	
	private double largeur; //en cm
	private double hauteur; //en cm
	private double profondeur; //en cm

	@Override
	public double getVolume() {
		System.out.println("getVolume() appelé sur une valise  de dimension=" 
	                   + this.largeur + "*"  + this.hauteur + "*" +  this.profondeur);
		return (this.largeur * this.hauteur * this.profondeur) / 1000;
	}

	
	
	public Valise() {
		super();
	}



	public Valise(String label, String couleur, double poids) {
		super(label, couleur, poids);
		this.largeur = this.hauteur = this.profondeur = 20; //par defaut
	}
	
	public Valise(String label, String couleur, double poids , double largeur, double hauteur , double profondeur) {
		super(label, couleur, poids);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.profondeur = profondeur; 
	}



	public double getLargeur() {
		return largeur;
	}

	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	public double getHauteur() {
		return hauteur;
	}

	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	public double getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(double profondeur) {
		this.profondeur = profondeur;
	}



	@Override
	public String toString() {
		return "Valise [largeur=" + largeur + ", hauteur=" + hauteur + ", profondeur=" + profondeur + " héritant de "
				+ super.toString() + "]";
	}
	
	

}
