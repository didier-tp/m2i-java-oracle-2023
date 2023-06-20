package tp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tp.figure.Cercle;
import tp.figure.Figure2D;
import tp.figure.Ligne;
import tp.figure.Rectangle;
import tp.svg.MySvgUtil;

public class MyApp {

	public static void main(String[] args) {
		System.out.println("cette application va générer un fichier dessin.svg ou dessin2.svg");
		premiersTests();
		//enchainerTransformationsEtGenerationFichierSvg();
	}
	
	public static List<Figure2D> buildListeFigures() {
		List<Figure2D> listeFigures = new ArrayList<>();
		listeFigures.add(new Rectangle(100,180,200,50,"black",5,"blue"));
		listeFigures.add(new Ligne(150,100,250,100,"green",4,null));
		listeFigures.add(new Ligne(200,50,200,150,"green",4,null));
		listeFigures.add(new Cercle(100,100,30,"black",2,"red"));
		listeFigures.add(new Cercle(300,100,30,"black",2,"red"));
		listeFigures.add(new Cercle(200,150,148,"red",5,null));
		listeFigures.add(new Rectangle(52,2,300,300,"blue",6,null));
		
		return listeFigures;
	}
	
	public static void enchainerTransformationsEtGenerationFichierSvg() {
		int dx=Integer.parseInt(javax.swing.JOptionPane.showInputDialog(null, "dx=" , "0"));
		int dy=Integer.parseInt(javax.swing.JOptionPane.showInputDialog(null, "dy=" , "120"));
		double coeffZoom=Double.parseDouble(javax.swing.JOptionPane.showInputDialog(null, "coeffZoom=" , "0.5"));
		String typeFigExclure=javax.swing.JOptionPane.showInputDialog(null, "typeFigExclure=" , "Ligne");
		
		List<Figure2D> listeInitialeFigures = buildListeFigures();
		List<Figure2D> listeTransformeeFigures =
		    listeInitialeFigures.stream()
		     //effecter une premiere transformation de type zoomer(0.5)
		    //.map((f)->{f.zoomer(0.5);return f;})
		    .map((f)->{f.zoomer(coeffZoom);return f;})
		     //effecter une seconde transformation de type translation(0,120)
		    //.map((f)->{f.translater(0,120);return f;})
		    .map((f)->{f.translater(dx,dy);return f;})
		     //filtrer selon le type de figure "Cercle" via un test de type instanceof Cercle
		    //.filter((f)-> f instanceof Cercle)
		    .filter((f)-> !f.getClass().getSimpleName().equals(typeFigExclure))
		    .collect(Collectors.toList());
		    //.collect(Collectors.toCollection(ArrayList<Figure2D>::new));
		MySvgUtil.generateSvgFile(listeTransformeeFigures, "dessin2.svg"); 
		System.out.println("le fichier dessin2.svg a été regénéré");
	}
	
    public static void premiersTests() {
		List<Figure2D> listeFigures = new ArrayList<>();
		//Figure2D fig = new Figure2D(); //new impossible car Figure2D abstract
		//<rect x='100' y='180' width='200' height='50'  stroke='black' stroke-width='5' fill='blue' />
		Rectangle r = new Rectangle(100,180,200,50,"black",5,"blue");
		System.out.println(r.toString());
		System.out.println("r.perimetre="+r.perimetre());
		System.out.println("r.aire="+r.aire());
		System.out.println(r.toSvgStringWithColor());
		
		//<line x1='150' y1='100' x2='250' y2='100' stroke='green' stroke-width='4' fill='none' />
		Ligne l1 = new Ligne(150,100,250,100,"green",4,null);
		System.out.println(l1.toString());
		System.out.println(l1.toSvgStringWithColor());
		
		//<circle cx='100' cy='100' r='30' stroke='black' stroke-width='3' fill='red' />
		Cercle c1 = new Cercle(100,100,30,"black",3,"red");
		System.out.println(c1.toString());
		System.out.println("c1.perimetre="+c1.perimetre());
		System.out.println("c1.aire="+c1.aire());
		System.out.println(c1.toSvgStringWithColor());
		
		listeFigures.add(r);
		listeFigures.add(l1);
		listeFigures.add(c1);
		MySvgUtil.generateSvgFile(listeFigures, "dessin.svg");
	}

}
