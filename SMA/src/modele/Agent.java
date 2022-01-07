package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

	private static ArrayList<Proposition> propositionVraies; 			// S
	private static ArrayList<Proposition> propositionButs;	  			// G
	private static ArrayList<Proposition> propositionButTerminees;		// R
	
	public Agent() {
		super();
		
		Agent.propositionVraies = new ArrayList<Proposition>();
		Agent.propositionButs = new ArrayList<Proposition>();
		Agent.propositionButTerminees = new ArrayList<Proposition>();
		}
	
	public static  ArrayList<Proposition> S(){
		propositionVraies = new ArrayList<Proposition>();
		for(Proposition uneProposition : Environnement.listeDesProposition) {
			if(uneProposition.isTrue()) propositionVraies.add(uneProposition);
		}
		return propositionVraies;
	}
	
	public static  ArrayList<Proposition> G(){
		return propositionButs;
	}
	
	public static  ArrayList<Proposition> R(){
		return propositionButTerminees;
	}
	
	public static boolean isInPropositionButs(Proposition uneProposition) {
		return Agent.propositionButs.contains(uneProposition);
	}
	
	public static boolean isInPropositionVraies(String uneProposition) {
		return Agent.propositionButs.contains(uneProposition);
	}
	public static boolean isInPropositionButTerminees(String uneProposition) {
		return Agent.propositionButTerminees.contains(uneProposition);
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
			Agent.propositionVraies.add(key);
	}
	
	public void addBut(Proposition key) {
		Agent.propositionButs.add(key);
	}
	
	public void addButTermine(Proposition key) {
		Agent.propositionButTerminees.add(key);
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
