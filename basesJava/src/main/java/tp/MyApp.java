package tp;

import javax.swing.JOptionPane;

public class MyApp {

	public static void main(String[] args) {
		System.out.println("Hello world");
		//à lancer sous eclipse avec Run as  / java application
		test_types();
		test_personnes();
		test_String();
		test_Adresse();
	}
	
	public static void test_Adresse() {
		Adresse a1 = new Adresse();
		a1.setNum("23");
		a1.setRue("rue xyz");
		a1.setCodePostal("76000");
		a1.setVille("Rouen");
		System.out.println("a1=" + a1);
		
		Adresse a2 = new Adresse("24" , "rue elle" , "75012" , "Paris");
		System.out.println("a2=" + a2);
	}
	
	public static void test_String() {
		//construire et afficher la grosse chaine de caractères suivante:
		//"1,2,3,4,......,64"
		StringBuilder buffer = new StringBuilder(64*2);
		buffer.append("1");
		for(int i=2; i<=64; i++) {
			buffer.append(",");
			buffer.append(i);
		}
		String chSuite = buffer.toString();
		System.out.println("chSuite=" + chSuite);
		
		//extraire de chaineDate la partie "mois" au milieu entre les deux "/"
		String chaineDate = "16/06/2023";
		int posDuPremierSlash = chaineDate.indexOf("/");
		int posDuDernierSlash = chaineDate.lastIndexOf("/");
		String chaineMois = chaineDate.substring(posDuPremierSlash+1 , posDuDernierSlash);
		System.out.println("chaineMois=" + chaineMois);
		
		String m2i ="m2i";
		String m2iMaj = m2i.toUpperCase();
		System.out.println("m2i=" + m2i);
		System.out.println("m2iMaj=" + m2iMaj);
		
	}
	
	public static void test_personnes() {
		Personne p1 = null;
		p1 =new Personne();
		System.out.println("p1=" + p1);//System.out.println("p1=" + p1.toString());
		//p1.prenom="jean"; //impossible depuis que prenom est privé
		p1.setPrenom("jean");
		//p1.nom="Bon"; p1.age=25; //impossible depuis que nom et age sont privés
		p1.setNom("Bon"); 
		p1.setAge(25);
		p1.setAge(-30);//age invalide qui sera refusé (pas pris en compte)
		          //l'age de p1 est toujours 25
		p1.incrementerAge();
		System.out.println("nom complet de p1=" + p1.nomComplet()); 
		System.out.println("p1=" + p1.toString());
		
		Personne p2 = new Personne("Axelle" , "Aire" , 45);
		System.out.println("p2=" + p2.toString());
		//p2.setPrenom("Axelle");
		//p2.setNom("Aire"); p2.setAge(35);
		p2.incrementerAge();
		System.out.println("nom complet de p2=" + p2.nomComplet()); 
		System.out.println("p2=" + p2.toString());
	}
	
	public static void test_types() {
		int a=5;
		System.out.println("a="+a);
		String s1="15" , s2="3"; //s1 et s2 comporte des valeurs que l'on aurait pu saisir au clavier
		//s2 = JOptionPane.showInputDialog(null, "s2=");//pour demander à saisir s2
		//dans une page html ou autre
		
		//on souhaite calculer et afficher le résultat d'une addition de ces 2 valeurs
		//s1+s2 --> concaténation --> 153
		String s3=s1+s2;
		System.out.println("s3=s1+s2="+s3);
		
		//i1+i2 = addition --> 18
		int i1 = Integer.parseInt(s1);
		int i2 = Integer.parseInt(s2);
		int i3 = i1 + i2;
		Integer objI3 = i3 ; //fait automatiquement = new Integer(i3); //BOXING
		System.out.println("objI3="+objI3);
		int i4 = objI3; //fait automatiquement = objI3.intValue(); //UNBOXING
		System.out.println("i4="+i4);
		
		System.out.println("resultat addition="+i3);
		//System.out.println("resultat addition="+(i1+i2));
	}

}
