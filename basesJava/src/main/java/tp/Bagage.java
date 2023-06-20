package tp;

public abstract class Bagage /* extends Object */ implements Transportable {
	protected String label;
	protected String couleur;
	protected double poids;
	//protected String typeBagage; // "Sac" ou bien "Valise"
	
	public abstract double getVolume();
	
	
	public Bagage() {
		super();
	}


	public Bagage(String label, String couleur, double poids) {
		super();
		this.label = label;
		this.couleur = couleur;
		this.poids = poids;
	}



	@Override
	public String toString() {
		return "Bagage [label=" + label + ", couleur=" + couleur + ", poids=" + poids + "]";
	}



	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public double getPoids() {
		return poids;
	}

	public void setPoids(double poids) {
		this.poids = poids;
	}
	
	

}
