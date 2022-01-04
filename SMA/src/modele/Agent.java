package modele;

import java.util.HashMap;

public class Agent {

	private static HashMap<String,Boolean> propositionVraies; 			// S
	private static HashMap<String,Boolean> propositionButs;	  			// G
	private static HashMap<String,Boolean> propositionButTerminees;		// R
	
	public Agent() {
		super();
		
		Agent.propositionVraies = new HashMap<String,Boolean>();
		Agent.propositionButs = new HashMap<String,Boolean>();
		Agent.propositionButTerminees = new HashMap<String,Boolean>();
		}
	
	public static HashMap<String,Boolean> S(){
		return propositionVraies;
	}
	
	public static HashMap<String,Boolean> G(){
		return propositionButs;
	}
	
	public static HashMap<String,Boolean> R(){
		return propositionButTerminees;
	}
	
	public boolean addPropositionInitiale(String key, Boolean value) {
		if(Agent.propositionVraies.containsKey(key)) {
			return false;
		}
		else {
			Agent.propositionVraies.put(key, value);
			return true;
		}
	}
	
	public boolean addBut(String key, Boolean value) {
		if(Agent.propositionButs.containsKey(key)) {
			return false;
		}
		else {
			Agent.propositionButs.put(key, value);
			return true;
		}
	}
	
	public boolean addButTermine(String key, Boolean value) {
		if(Agent.propositionButTerminees.containsKey(key)) {
			return false;
		}
		else {
			Agent.propositionButTerminees.put(key, value);
			return true;
		}
	}

	public String printState() {
		String result = "State of the environnement : (";
		for(String uneProposition : S().keySet()) result = result + uneProposition + " ";
		result = result + ")\n goals of the environnement : (";
		for(String uneProposition : G().keySet()) result = result + uneProposition + " ";
		result = result + ")\n protected goals of the environment : (";
		for(String uneProposition : R().keySet()) result = result + uneProposition + " ";
		result = result + ")\n";
		return result;
	}
	
	
	
}
