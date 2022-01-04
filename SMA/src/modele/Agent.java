package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

	private HashMap<String,Boolean> propositionVraies; 			// S
	private HashMap<String,Boolean> propositionButs;	  			// G
	private HashMap<String,Boolean> propositionButTerminees;		// R
	
	private ArrayList<Object> possession;

	public Agent() {
		super();
		
		this.propositionVraies = new HashMap<String,Boolean>();
		this.propositionButs = new HashMap<String,Boolean>();
		this.propositionButTerminees = new HashMap<String,Boolean>();
		
		this.possession = new ArrayList<Object>();
	}
	
	//Modules de compétences
	
	public Boolean open(Object unObjet) {
		return true;
	}
	
	public Boolean close(Object unObjet) {
		return true;
	}
	
	public Boolean lock(Object unObjet) {
		return true;
	}
	
	public Boolean unlock(Object unObjet) {
		return true;
	}
	
	public Boolean take(Object unObjet) {
		return true;
	}
	
	public Boolean release(Object unObjet) {
		return true;
	}
	
	public Boolean assemble(Object unObjet) {
		return true;
	}
	
	public Boolean disassemble(Object unObjet) {
		return true;
	}
	
	public Boolean moveTo(Object unObjet) {
		return true;
	}
	
	public Boolean search(Object unObjet) {
		return true;
	}
	
	public Boolean isNear() {
		return true;
	}
	
	public Boolean isOwner(Object unObjet) {
		return this.possession.contains(unObjet);
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
