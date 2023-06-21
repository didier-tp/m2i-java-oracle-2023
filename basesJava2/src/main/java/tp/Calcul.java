package tp;

public class Calcul {
	
	public double racineCarreIndirecte(double x) throws MyException {
		return racineCarre(x);
	}
	
	public double racineCarre(double x) throws MyException {
		if(x>=0)
		   return Math.sqrt(x);
		else 
		   //throw new RuntimeException("racine carrée prévue seulement pour x positif");
		   //throw new IllegalArgumentException("racine carrée prévue seulement pour x positif");
		   throw new MyException("racine carrée prévue seulement pour x positif");
	}
	
	public int division(int a,int b) throws MyException2 {
		
		if(b==0)
			 //throw new IllegalArgumentException("division par zéro interdite");
		     throw new MyException2("division par zéro interdite");
		else 
		    return a/b; //par defaut ça soulève java.lang.ArithmeticException: / by zero
	}

}
