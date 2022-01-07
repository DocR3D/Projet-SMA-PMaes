package modele;

import java.util.ArrayList;

public class Agent {

	private static ArrayList<Proposition> propositionVraies; 			// S
	private static ArrayList<Proposition> propositionButs;	  			// G
	private static ArrayList<Proposition> propositionButTerminees;		// R

	public Agent() {
		super();

		propositionVraies = new ArrayList<Proposition>();
		propositionButs = new ArrayList<Proposition>();
		propositionButTerminees = new ArrayList<Proposition>();
	}

	public static  ArrayList<Proposition> S(){
		propositionVraies = new ArrayList<Proposition>();
		for(Proposition uneProposition : Environnement.listeDesProposition) {
			if(uneProposition.isTrue()) propositionVraies.add(uneProposition);
		}
		return propositionVraies;
	}

	public static  ArrayList<Proposition> G(){
		for(Proposition uneProposition : propositionButTerminees) {
			if(!uneProposition.isTrue()) {
				resetBut(uneProposition);
			}
		}
		
		return propositionButs;
	}

	public static  ArrayList<Proposition> R(){
		for(Proposition uneProposition : propositionButs) {
			if(uneProposition.isTrue()) {
				terminerBut(uneProposition);
			}
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

	public static void terminerBut(Proposition uneProposition) {
		propositionButs.remove(uneProposition);
		propositionButTerminees.add(uneProposition);
	}

	public static void resetBut(Proposition uneProposition) {
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

	public String printState() {
		String result = "State of the environnement : (";
		for(Proposition uneProposition : S()) result = result + uneProposition + " ";
		result = result + ")\n goals of the environnement : (";
		for(Proposition uneProposition : G()) result = result + uneProposition + " ";
		result = result + ")\n protected goals of the environment : (";
		for(Proposition uneProposition : R()) result = result + uneProposition + " ";
		result = result + ")\n";
		return result;
	}

	public boolean isDone() {
		for(Proposition uneProposition : this.propositionButs) if(!uneProposition.isTrue()) return false;
		return true;
	}


}
