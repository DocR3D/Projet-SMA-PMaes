package modele;

import java.util.HashMap;

public class Agent {

	private HashMap<String,Boolean> propositionVraies; 			// S
	private HashMap<String,Boolean> propositionButs;	  			// G
	private HashMap<String,Boolean> propositionButTerminees;		// R
	
	public Agent() {
		super();
		
		this.propositionVraies = new HashMap<String,Boolean>();
		this.propositionButs = new HashMap<String,Boolean>();
		this.propositionButTerminees = new HashMap<String,Boolean>();
		}
	
	public HashMap<String,Boolean> S(){
		return propositionVraies;
	}
	
	public HashMap<String,Boolean> G(){
		return propositionButs;
	}
	
	public HashMap<String,Boolean> R(){
		return propositionButTerminees;
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
