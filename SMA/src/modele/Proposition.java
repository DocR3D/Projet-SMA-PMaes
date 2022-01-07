package modele;

import java.util.ArrayList;

public class Proposition {

	String name;
	boolean value;
	int nbOccurence = 1;
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
		Environnement.listeDesProposition.add(this);
	}

	public void setFalse() {
		if(nbOccurence == 1) {
			value = false;
			nbOccurence--;
		}
		else nbOccurence--;

	}

	public void setTrue() {
		if(nbOccurence == 0) {
			nbOccurence++;
			value = true;
		}
		else nbOccurence++;
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
