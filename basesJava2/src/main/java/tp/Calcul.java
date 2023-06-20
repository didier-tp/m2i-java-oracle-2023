package tp;

public class Calcul {
	
	public double racineCarre(double x) {
		if(x>=0)
		   return Math.sqrt(x);
		else 
		   //throw new RuntimeException("racine carrée prévue seulement pour x positif");
		   throw new IllegalArgumentException("racine carrée prévue seulement pour x positif");
	}
	
	public int division(int a,int b) {
		//....
		return a/b;
	}

}
