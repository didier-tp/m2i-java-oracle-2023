package tp.bagages;

public class Sac extends Bagage {
	
	private double volume; //en litres
	
	
	
	public Sac() {
		super();
		this.volume = 10; 
	}

	public Sac(String label, String couleur, double poids) {
		super(label, couleur, poids);
		this.volume = 10 ; //par défaut
	}

	public Sac(String label, String couleur, double poids, double volume) {
		super(label, couleur, poids);
		this.volume = volume;
	}

	@Override
	public double getVolume() {
		//System.out.println("getVolume() appelé sur un sac de volume=" + this.volume);
		return this.volume;
	}
	
	public double volumeDeSac() {
		//System.out.println("getVolume() appelé sur un sac de volume=" + this.volume);
		return this.volume;
	}
	

	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "Sac [volume=" + volume + " heritant de " + super.toString() + "]";
	}

	
	
	
	

}
