package modele;

import java.util.ArrayList;

public class Agent {

	private ArrayList<Proposition> propositionVraies; 			// S
	private ArrayList<Proposition> propositionButs;	  			// G
	private ArrayList<Proposition> propositionButTerminees;		// R
	
	private ArrayList<Object> possession;

	public Agent() {
		super();
		
		this.propositionVraies = new ArrayList<Proposition>();
		this.propositionButs = new ArrayList<Proposition>();
		this.propositionButTerminees = new ArrayList<Proposition>();
		
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
	
	public ArrayList<Proposition> S(){
		return propositionVraies;
	}
	
	public ArrayList<Proposition> G(){
		return propositionButs;
	}
	
	public ArrayList<Proposition> R(){
		return propositionButTerminees;
	}
	
}
