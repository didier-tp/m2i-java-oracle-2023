package tp;

import java.util.Comparator;

public class ComparateurDeVente implements Comparator<Vente> {

	/*
	//V1 (selon ca croissant):
	public int compare(Vente o1, Vente o2) {
		//valeur que doit retourner .compare(o1,o2):
		//si o1 est plus petit que o2 alors return -1 (ou valeur négative);
		//si o1 est égal à o2 alors return 0;
		//si o1 est plus grand que o2 alors return +1 (ou valeur positive);
		return (int) (o1.getCa() - o2.getCa());
	}*/
	
	//V2 (selon .domaine croissant):
		public int compare(Vente o1, Vente o2) {
			//valeur que doit retourner .compare(o1,o2):
			//si o1 est plus petit que o2 alors return -1 (ou valeur négative);
			//si o1 est égal à o2 alors return 0;
			//si o1 est plus grand que o2 alors return +1 (ou valeur positive);
			if(o1 == null || o1.getDomaine() == null) 
				return -1; //null c'est petit !
			/*else*/
			   //return o1.getDomaine().compareTo(o2.getDomaine()); 
			   //Majuscules considérées plus petites que minuscules
			
			   return o1.getDomaine().compareToIgnoreCase(o2.getDomaine()); //sans diff min/MAJ
		}
}
