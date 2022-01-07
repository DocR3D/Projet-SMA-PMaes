package modele;

import java.util.ArrayList;

public class Agent {

	private  ArrayList<Proposition> propositionVraies; 			// S
	private  ArrayList<Proposition> propositionButs;	  			// G
	private  ArrayList<Proposition> propositionButTerminees;		// R

	public Agent() {
		super();

		propositionVraies = new ArrayList<>();
		propositionButs = new ArrayList<>();
		propositionButTerminees = new ArrayList<>();
	}

	public   ArrayList<Proposition> S(){
		propositionVraies = new ArrayList<>();
		for(Proposition uneProposition : Environnement.listeDesProposition) {
			if(uneProposition.isTrue()) propositionVraies.add(uneProposition);
		}
		return propositionVraies;
	}

	public   ArrayList<Proposition> G(){
		ArrayList<Proposition> toDelete = new ArrayList<Proposition>();

		for(Proposition uneProposition : propositionButs) {
			if(uneProposition.isTrue()) {
				toDelete.add(uneProposition);
			}
		}
		for(Proposition uneProposition : toDelete) {
			terminerBut(uneProposition);
		}
		return propositionButs;
	}

	public   ArrayList<Proposition> R(){
		ArrayList<Proposition> toDelete = new ArrayList<Proposition>();
		for(Proposition uneProposition : propositionButTerminees) {
			if(!uneProposition.isTrue()) {
				toDelete.add(uneProposition);
			}
		}
		for(Proposition uneProposition : toDelete) {
			resetBut(uneProposition);
		}
		return propositionButTerminees;
	}

	public boolean isInPropositionButs(Proposition uneProposition) {
		return propositionButs.contains(uneProposition);
	}

	public boolean isInPropositionVraies(Proposition uneProposition) {
		return propositionButs.contains(uneProposition);
	}
	public boolean isInPropositionButTerminees(Proposition uneProposition) {
		return propositionButTerminees.contains(uneProposition);
	}

	public  void terminerBut(Proposition uneProposition) {
		propositionButs.remove(uneProposition);
		propositionButTerminees.add(uneProposition);
	}

	public  void resetBut(Proposition uneProposition) {
		propositionButs.add(uneProposition);
		propositionButTerminees.remove(uneProposition);
	}


	public void addPropositionInitiale(Proposition key) {
		propositionVraies.add(key);
	}

	public void addBut(Proposition key) {
		propositionButs.add(key);
	}

	public void addButTermine(Proposition key) {
		propositionButTerminees.add(key);
	}

	public void printState() {
		String result = "State of the environnement : (";
		for(Proposition uneProposition : S()) for(int i = 0; i < uneProposition.nbOccurence ; i++) result = result + uneProposition + " ";
		result = result + ")\n goals of the environnement : (";
		for(Proposition uneProposition : G()) result = result + uneProposition + " ";
		result = result + ")\n protected goals of the environment : (";
		for(Proposition uneProposition : R()) result = result + uneProposition + " ";
		result = result + ")\n";
		System.out.println(result);
	}

	public boolean isDone() {
		for(Proposition uneProposition : propositionButs) if(!uneProposition.isTrue()) return false;
		return true;
	}


}
