package modele;

import java.util.ArrayList;

public class Proposition {

	String name;
	boolean value;

	//Variables permettant de gérer les scénarios ou on a plusieurs fois la même condition, ex : HAND_IS_EMPTY dans Maes
	int defaultNbOccurence = 1;
	int nbOccurence = 1;

	//Liste permettant de connaitre quand les propositions sont passé à True et à False
	private ArrayList<Integer> whenIsEnable = new ArrayList<Integer>();


	public Proposition(String name, boolean value) {
		super();
		this.name = name;
		this.value = value;
		this.nbOccurence = value ? 1 : 0;
		Environnement.listeDesProposition.add(this);
	}

	public Proposition(String name, boolean value, int nbOccurence) {
		super();
		this.name = name;
		this.value = value;
		this.nbOccurence = nbOccurence;
		this.defaultNbOccurence = nbOccurence;
		Environnement.listeDesProposition.add(this);
	}

	//Met à jours la proposition à false, uniquement dans le cas ou le nombre d'occurence est égale à 1
	public void setFalse() {
		if(nbOccurence == 1) {
			value = false;
			nbOccurence--;
		}else if(nbOccurence > 0) nbOccurence--;

	}

	//Met à jours la proposition à True si elle ne l'était pas déjà.
	public void setTrue() {
		if(nbOccurence == 0) {
			nbOccurence++;
			value = true;
		}else if(this.nbOccurence < this.defaultNbOccurence) this.nbOccurence++;
	}

	public boolean isTrue() {
		return value;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void addEtat() {
		this.whenIsEnable.add(this.value ? 1 : 0);
	}

	public ArrayList<Integer> getEtatStat(){
		return this.whenIsEnable;
	}





}
