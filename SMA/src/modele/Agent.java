package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

	private static ArrayList<String> propositionVraies; 			// S
	private static ArrayList<String> propositionButs;	  			// G
	private static ArrayList<String> propositionButTerminees;		// R
	
	public Agent() {
		super();
		
		Agent.propositionVraies = new ArrayList<String>();
		Agent.propositionButs = new ArrayList<String>();
		Agent.propositionButTerminees = new ArrayList<String>();
		}
	
	public static  ArrayList<String> S(){
		return propositionVraies;
	}
	
	public static  ArrayList<String> G(){
		return propositionButs;
	}
	
	public static  ArrayList<String> R(){
		return propositionButTerminees;
	}
	
	//public static boolean isInPropositionButs(String) {
		
	//}
	
	public void addPropositionInitiale(String key) {
			Agent.propositionVraies.add(key);
	}
	
	public void addBut(String key) {
		Agent.propositionButs.add(key);
	}
	
	public void addButTermine(String key) {
		Agent.propositionButTerminees.add(key);
	}

	public String printState() {
		String result = "State of the environnement : (";
		for(String uneProposition : S()) result = result + uneProposition + " ";
		result = result + ")\n goals of the environnement : (";
		for(String uneProposition : G()) result = result + uneProposition + " ";
		result = result + ")\n protected goals of the environment : (";
		for(String uneProposition : R()) result = result + uneProposition + " ";
		result = result + ")\n";
		return result;
	}
	
	
	
}
