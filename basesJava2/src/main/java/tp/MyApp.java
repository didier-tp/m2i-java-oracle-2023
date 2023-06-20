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
