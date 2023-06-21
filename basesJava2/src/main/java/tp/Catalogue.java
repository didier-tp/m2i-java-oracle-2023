package tp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
	
	//Version sans lambda expression
	public void afficherV1() {
		//appeler Collections.sort(listeAtrier,comparateurQuiVaBien); 
		//Collections.sort(this.listeVentes,new ComparateurDeVente()); //compare par domaine croissant
		Collections.sort(this.listeVentes, new 
				/* classe anonyme imbriquée qui de manière sous entendue implémente */
				java.util.Comparator<Vente>() {
					public int compare(Vente o1, Vente o2) {
						return (int) (o1.getCa() - o2.getCa());
					}
		});
		System.out.println("ventes par ca croissants:");
		for(Vente v : this.listeVentes) {
			System.out.println("\t" + v); //v.toString() implicite
		}
		
		Collections.sort(this.listeVentes, new 
				/* classe anonyme imbriquée qui de manière sous entendue implémente */
				java.util.Comparator<Vente>() {
					public int compare(Vente o1, Vente o2) {
						return o1.getDomaine().compareToIgnoreCase(o2.getDomaine());
					}
		});
		System.out.println("ventes par domaines croissants:");
		for(Vente v : this.listeVentes) {
			System.out.println("\t" + v); //v.toString() implicite
		}
	}
	
	//avec lambda expression
	public void afficherV2() {
		//appeler Collections.sort(listeAtrier,comparateurQuiVaBien); 
		//Collections.sort(this.listeVentes,new ComparateurDeVente()); //compare par domaine croissant
		
		//Collections.sort(this.listeVentes, 
		//		        (Vente o1, Vente o2) -> { return (int) (o1.getCa() - o2.getCa()); }  );
		
		//Collections.sort(this.listeVentes, 
		 //                (o1, o2) -> { return (int) (o1.getCa() - o2.getCa()); }  );
		
		//si une seule instruction de type { return expression; }
		//on peut ne pas écrire les { } et ne pas écrire return ... ; (c'est automatique/implicite)
		Collections.sort(this.listeVentes, (o1, o2) ->  (int) (o1.getCa() - o2.getCa())  );
		
		System.out.println("ventes par ca croissants:");
		for(Vente v : this.listeVentes) {
			System.out.println("\t" + v); //v.toString() implicite
		}
		
		Collections.sort(this.listeVentes,
				       (Vente o1, Vente o2) -> {
						    return o1.getDomaine().compareToIgnoreCase(o2.getDomaine());
					        });
		System.out.println("ventes par domaines croissants:");
		for(Vente v : this.listeVentes) {
			System.out.println("\t" + v); //v.toString() implicite
		}
	}
	
	//avec streams
	public void afficher() {
		System.out.println("ventes:");
		
		//this.listeVentes.stream()
		//               .forEach( (v)-> { System.out.println("\t" + v); }   );
		
		/*
		this.listeVentes.stream()
		                .sorted( (o1, o2) ->  (int) (o1.getCa() - o2.getCa()) )
		                .forEach( (v)-> { System.out.println("\t" + v); }   );
		*/
		
		/*
		List<Vente> listeVentesTriesParCa = 
		this.listeVentes.stream()
                        .sorted( (o1, o2) ->  (int) (o1.getCa() - o2.getCa()) )
                        .collect(Collectors.toList());
		*/
		List<Vente> listeVentesTriesParCa = 
				this.listeVentes.stream()
				                .filter( (v) -> v.getDomaine().equalsIgnoreCase("papeterie") )
		                        .sorted( (v1, v2) ->  (int) (v1.getCa() - v2.getCa()) )
		                        //.map(v -> { v.setDomaine(v.getDomaine().toUpperCase()); return v;} ) /MOINS BIEN
		                        .map( v -> new Vente(v.getDomaine().toUpperCase() , v.getMois() , v.getCa()))
		                        //.collect(Collectors.toList());
		                        .toList(); //depuis v15 environ 
		
		
		System.out.println("liste d'origine =");
		for(Vente v : this.listeVentes) {
			System.out.println("\t" + v); 
		}
		
		System.out.println("liste transformée par enchainement de fonctions =");
		for(Vente v : listeVentesTriesParCa) {
			System.out.println("\t" + v); 
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
