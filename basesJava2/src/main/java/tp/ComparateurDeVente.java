package tp;

import java.util.Comparator;

//à coder selon exemple page 75 (d'abord selon sous partie.getCa())
public class ComparateurDeVente implements Comparator<Vente> {

	@Override
	public int compare(Vente o1, Vente o2) {
		return (int) (o1.getCa() - o2.getCa());
	}

}
