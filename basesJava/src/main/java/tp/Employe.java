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
public class Employe extends Personne{
	
    private Integer salaire;
    
    //constructeur par défaut:
    public Employe() {
		super();
		this.salaire = 0;
	}

	
	@Override
	public String toString() {
		return "Employe [salaire=" + salaire + ", héritant de " + super.toString() + "]";
	}


	public Integer getSalaire() {
		return salaire;
	}

	public void setSalaire(Integer salaire) {
		this.salaire = salaire;
	}
    
}
