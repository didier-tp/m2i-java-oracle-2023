package tp;

import javax.swing.JOptionPane;

public class AfficheurDialog implements Afficheur {

	@Override
	public void afficher(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

}
