package tp;

public class Adresse {
	private String num; //ex : "2" ou "2bis"
	private String rue; 
	private String codePostal; //ex: "75012"
	private String ville;
	
	
	@Override
	public String toString() {
		return "Adresse [num=" + num + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}

	public Adresse() {
		super();
	}
	
	public Adresse(String num, String rue, String codePostal, String ville) {
		super();
		this.num = num;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
	
	/*
	à faire en Tp :
	   - get/set
	   - constructeurs
	   - toString()
	   - tester ça depuis MyApp
	*/
}
