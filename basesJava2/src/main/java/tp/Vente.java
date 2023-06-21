package tp;

public class Vente {
	
	private String domaine;
	private int mois;
	private long ca;
	
	//avec .get/set , constructeurs , .toString()
	
	
	
	@Override
	public String toString() {
		return "Vente [domaine=" + domaine + ", mois=" + mois + ", ca=" + ca + "]";
	}
	
	
	public Vente() {
		super();
	}


	public Vente(String domaine, int mois, long ca) {
		super();
		this.domaine = domaine;
		this.mois = mois;
		this.ca = ca;
	}
	
	

	public String getDomaine() {
		return domaine;
	}
	
	
	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	public long getCa() {
		return ca;
	}
	public void setCa(long ca) {
		this.ca = ca;
	} 

	

	
}
