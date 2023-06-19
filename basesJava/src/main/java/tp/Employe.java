package tp;


/*
  la classe Employe va hériter de la classe Personne
  et va ajouter un salaire de type Integer
 
  avec get/set et constructeur par défaut avec "zero paramètre"
  et avec toString() appelant éventuellement super.toString() en interne 
  
  tester via MyApp.test_employe() appelé par MyApp.main()
  
  test_employe(){
   Employe emp1 = new Employe();
   emp1.setNom("Nom1");
   emp1.setSalaire(2000.0);
   System.out.println("emp1=" + emp1.toString());
  }
  
 */

public class Employe extends Personne {
	
	private Integer salaire;
	
	
	

	public Employe() {
		super();//appel du constructeur par défaut (avec 0 param) de la superclasse Personne
		this.salaire= 0;
	}
	
	
  
    //exemple d'appel :  Employe emp2 = new Employe("didier" , "Defrance" , 53, 2000);
	public Employe(String prenom, String nom, Integer age , Integer salaire) {
	   //... à coder en Tp via super(...,...,...) et ...
	   super(prenom,nom,age);//passage de paramètre au constructeur de la superclasse Personne
	                         //qui va initialiser les parties héritées .prenom , .nom , .age
	   this.salaire=salaire; //initialisation du salaire
	   //this.setNom("toto"); ou bien this.nom ="toto" si .nom en protected dans classe Personne
	}
	

	@Override
	public String toString() {
		return "Employe [salaire=" + salaire + ", heritant de" + super.toString() + "]";
	}


	public Integer getSalaire() {
		return salaire;
	}

	public void setSalaire(Integer salaire) {
		this.salaire = salaire;
	}
	
	
	

}
