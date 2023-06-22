package tp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tp.entity.Personne;


// PersonneDaoSimu = version simulée en mémoire sans réelle base de données
public class PersonneDaoSimu implements PersonneDAO{
	private Map<Integer,Personne> mapIdPersonnes = new HashMap<>();
	private Integer idMax=0;

	public Personne createPersonne(Personne p) {
		p.setId(++idMax); //simuler auto_incrémentation clef primaire
		mapIdPersonnes.put(p.getId(), p);
		return p;
	}

	public Personne rechercherPersonneParId(Integer idPers) {
		return mapIdPersonnes.get(idPers);
	}

	public List<Personne> rechercherToutesPersonnes() {
		return new ArrayList<Personne>( mapIdPersonnes.values() );
	}

	public void updatePersonne(Personne p) {
		mapIdPersonnes.put(p.getId(), p);
	}

	public void deletePersonne(Integer idPers) {
		mapIdPersonnes.remove(idPers);
	}
	
}
