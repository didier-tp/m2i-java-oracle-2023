package tp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Catalogue {

	private List<Vente> listeVentes = new ArrayList<>();

	public void lireFichierVentes(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			br.readLine();// lecture de la première ligne du fichier .csv avec titres colonnes
			String ligne = br.readLine();
			while (ligne != null) {
				String[] t = ligne.split(";");
				//new Vente(domaineAsString, moisAsInt, caAsLong)
				Vente vente = new Vente(t[0], 
						                Integer.parseInt(t[1]), 
						                Long.parseLong(t[2]));
				this.listeVentes.add(vente);
				ligne = br.readLine();
			}
			br.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void afficher() {
		System.out.println("ventes:");
		for(Vente v : this.listeVentes) {
			System.out.println("\t" + v); //v.toString() implicite
		}
	}

	public void ecrireFichierStats(String fileName) {

		long ca_total = 0;
		// parcourir listeVentes et calculer ca_total
		for(Vente v:this.listeVentes) {
			ca_total += v.getCa();
		}
		// ecrire le fichier de stats (voir page 114)
		try {
			PrintStream ps=new PrintStream(new FileOutputStream(fileName));
			ps.println("domaine;ca_total");
			ps.println("all;" + ca_total);
			//ps.printf("%s;%d", "all" , ca_total);
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
