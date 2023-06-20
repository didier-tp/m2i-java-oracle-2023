package tp;

import javax.swing.JOptionPane;

public class MyApp {

	public static void main(String[] args) {
		test_calcul();

	}
	
	public static void test_calcul() {
		Calcul calcul = new Calcul();
		try {
				//double x=9;
			    //double x=-9;
			    double x = Double.parseDouble(JOptionPane.showInputDialog(null, "x="));
				double res = calcul.racineCarre(x);
				System.out.println("res de racineCarree="+res);
				//en cas d'exception, les lignes en fin de try ne sont jamais exécutées !!!
				System.out.println("en fin de try , x*x=" + x*x);
		} catch (Exception e) {
				e.printStackTrace();
				//System.err.println(e.getMessage());
		}
		System.out.println("suite du programme qui n'est pas encore planté");
	}

}
