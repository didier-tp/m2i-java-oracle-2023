package tp;

public class Personne {
	private String prenom;
	private String nom;
	//private int age; //une variable de type int ne peut être que numérique et jamais null
	private Integer age;//une variable de type Integer peut être numérique ou nulle
	
	public Personne() {
		super();//pour une personne ordinaire , l'essence c'est super .
	}

	public Personne(String prenom, String nom, Integer age) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.age = age;
	}
	
	

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		if(age >=0 && age <= 150)
		    this.age = age;
		else System.out.println("age invalide pas pris en compte dans this.age");
		    // throw new RuntimeException("age invalide");
	}

	@Override
	public String toString() {
		return "Personne [prenom=" + prenom + ", nom=" + nom + ", age=" + age + "]";
	}
	
	public void incrementerAge(){
		// un an de plus (ça modifie l'age interne de l'objet Personne ,
		// mais pas de return)
		if(this.age != null ) {
			//this.age = this.age + 1;
			this.age++;
		} 
		else {
			this.age = 1;
		}
	}
	
	public String nomComplet(){
		return this.prenom + " " + this.nom;
		 // retourner la concaténation de prenom et du nom
		 //valeur de retour de type String
	}
	
	

}
