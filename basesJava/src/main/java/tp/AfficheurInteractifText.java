package tp;

import java.util.Scanner;

public class AfficheurInteractifText extends AfficheurText implements AfficheurInteractif {

	@Override
	public String saisirReponse(String question) {
		Scanner scanner = new Scanner(System.in);
		System.out.print(question);
		return scanner.nextLine();
	}

}
