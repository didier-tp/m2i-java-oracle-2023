package tp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Catalogue {

	private List<Vente> listeVentes = new ArrayList<>();

	public void lireFichierVentesV1(String fileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			    //pas grave , rien à dire	
			}
		}
	}
	
	public void lireFichierVentes(String fileName) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
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
		} catch (IOException e) {
			e.printStackTrace();
		} //finally et br.close() automatique car try() avec parenthèses , try with resources
		  //et BufferedReader implémente AutoClosable
	}
	
	public void lireFichierVentesV2a(String fileName) {
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(fileName));
			sc.nextLine();// lecture de la première ligne du fichier .csv avec titres colonnes
			String ligne = sc.nextLine();
			while (ligne != null) {
				String[] t = ligne.split(";");
				//new Vente(domaineAsString, moisAsInt, caAsLong)
				Vente vente = new Vente(t[0], 
						                Integer.parseInt(t[1]), 
						                Long.parseLong(t[2]));
				this.listeVentes.add(vente);
				ligne = sc.hasNextLine()?sc.nextLine():null;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(sc!=null)
			   sc.close();
		}
	}
	
	//V3 avec try with resources
	public void lireFichierVentesV3a(String fileName) {
		try( Scanner sc = new Scanner(new FileInputStream(fileName)) ) {
			sc.nextLine();// lecture de la première ligne du fichier .csv avec titres colonnes
			String ligne = sc.nextLine();
			while (ligne != null) {
				String[] t = ligne.split(";");
				//new Vente(domaineAsString, moisAsInt, caAsLong)
				Vente vente = new Vente(t[0], 
						                Integer.parseInt(t[1]), 
						                Long.parseLong(t[2]));
				this.listeVentes.add(vente);
				ligne = sc.hasNextLine()?sc.nextLine():null;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} //finally/close automatique
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
		
		
		Map<String,List<Vente>> mapVentesParDomaine = new HashMap<>();
		for(Vente v:this.listeVentes) {
			String domaine = v.getDomaine();
			List<Vente> sousListe = mapVentesParDomaine.get(domaine);
			if(sousListe == null) {
				sousListe = new ArrayList<Vente>();
				mapVentesParDomaine.put(domaine, sousListe);
			}
			sousListe.add(v);
		}
		
		// ecrire le fichier de stats (voir page 114)
		try {
			PrintStream ps=new PrintStream(new FileOutputStream(fileName));
			ps.println("domaine;ca_total");
			//ps.println("all;" + ca_total);
			ps.printf("%s;%d\n", "all" , ca_total);
			for(String domaine : mapVentesParDomaine.keySet()) {
				List<Vente> sousListe = mapVentesParDomaine.get(domaine);
				long sous_total_ca_par_domaine = 0;
				for(Vente v : sousListe) {
					sous_total_ca_par_domaine += v.getCa();
				}
				ps.printf("%s;%d\n", domaine , sous_total_ca_par_domaine);
			}
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
