package tp;

public interface Transportable {
	public double getPoids(); //que des operations abstraites (sans code) dans une interface
	public double getVolume();
	
	public static final String UNITE_POIDS= "kg";
	public static final String UNITE_VOLUME= "litre";
}
