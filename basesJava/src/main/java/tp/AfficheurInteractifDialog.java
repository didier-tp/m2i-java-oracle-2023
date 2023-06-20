package tp;

import javax.swing.JOptionPane;

public class AfficheurInteractifDialog extends AfficheurDialog implements AfficheurInteractif {

	@Override
	public String saisirReponse(String question) {
		return JOptionPane.showInputDialog(null, question);
	}

}
