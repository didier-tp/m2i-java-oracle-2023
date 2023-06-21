package tp;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;

public class MyApp {

	public static void main(String[] args) {
		//test_calcul();
		//test_calcul2();
        test_ventes();
	}
	
	public static void test_ventes() {
		Catalogue catalogue = new Catalogue();
		catalogue.lireFichierVentes("ventes2022.csv.txt");
		catalogue.afficher();
		catalogue.ecrireFichierStats("stats.csv.txt");//refresh eclipse pour voir fichier généré
	}
	
	public static void test_calcul2() {
		//NB: MyException hérite directemment de Exception 
		//et est donc à try/catch obligatoire
		Calcul calcul = new Calcul();
		try {
			double x = Double.parseDouble(JOptionPane.showInputDialog(null, "x="));
			double res = calcul.racineCarre(x);
			System.out.println("pour x=" + x + " res de racineCarree="+res);
		} catch (NumberFormatException e) {
			// en cas d'erreur sur Double.parseDouble
			e.printStackTrace();
		} catch (HeadlessException e) {
			// en cas d'erreur sur showInputDialog
			e.printStackTrace();
		} catch (MyException e) {
			// en cas d'erreur sur racineCarre
			e.printStackTrace();
		}
		
		int a = Integer.parseInt(JOptionPane.showInputDialog(null, "a="));
		int b = Integer.parseInt(JOptionPane.showInputDialog(null, "b="));
		int resDiv = calcul.division(a, b);//renvoie MyException2 à try/catch facultatif
		System.out.println("pour a=" + a + " et b= " + b + " resDiv=a/b="+resDiv);
	}
	
	public static void test_calcul() {
		Calcul calcul = new Calcul();
		try {
			//double x=9;
			//double x=-9;
			double x = Double.parseDouble(JOptionPane.showInputDialog(null, "x="));
			double res = calcul.racineCarreIndirecte(x);
			System.out.println("pour x=" + x + " res de racineCarree="+res);
				
			//en tp , déclencher une division de a par b:
			int a = Integer.parseInt(JOptionPane.showInputDialog(null, "a="));
			int b = Integer.parseInt(JOptionPane.showInputDialog(null, "b="));
			int resDiv = calcul.division(a, b);
			System.out.println("pour a=" + a + " et b= " + b + " resDiv=a/b="+resDiv);
				
			//en cas d'exception, les lignes en fin de try ne sont jamais exécutées !!!
			System.out.println("en fin de try , x*x=" + x*x);
				
		} catch (Exception e) {
			e.printStackTrace();
			//System.err.println(e.getMessage());
		}
		System.out.println("suite du programme qui n'est pas encore planté");
	}

}
