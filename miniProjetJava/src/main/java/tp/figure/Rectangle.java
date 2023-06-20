package tp.figure;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Rectangle extends Figure2D implements Surface{
	private int x;
	private int y;
	private int width;
	private int height;
	

	public Rectangle() {
		super();
	}
	



	public Rectangle(int x, int y, int width, int height,String couleur, Integer epaisseur, String couleurFond) {
		super(couleur, epaisseur, couleurFond);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}





	@Override
	public String toSvgSubString() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ps.printf("<rect x='%d' y='%d' width='%d' height='%d'", 
				   this.x , this.y , this.width, this.height);
		return baos.toString();
	}

	@Override
	public double perimetre() {
		return 2*(width+height);
	}

	@Override
	public double aire() {
		return width*height;
	}

	@Override
	public void translater(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		
	}

	@Override
	public void zoomer(double coeff) {
		this.x = (int)(this.x * coeff);
		this.y = (int)(this.y * coeff);
		this.width = (int)(this.width * coeff);
		this.height = (int)(this.height * coeff);
	}


	@Override
	public String toString() {
		return "Rectangle [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", heritant de "
				+ super.toString() + "]";
	}




	public int getX() {
		return x;
	}




	public void setX(int x) {
		this.x = x;
	}




	public int getY() {
		return y;
	}




	public void setY(int y) {
		this.y = y;
	}




	public int getWidth() {
		return width;
	}




	public void setWidth(int width) {
		this.width = width;
	}




	public int getHeight() {
		return height;
	}




	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
