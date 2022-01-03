package modele;

import java.util.ArrayList;

public class Agent {

	private ArrayList<Boolean> propositionVraies;
	private ArrayList<Boolean> propositionButs;
	private ArrayList<Boolean> propositionButTerminees;
	
	private ArrayList<Object> possession;

	public Agent() {
		super();
		
		this.propositionVraies = new ArrayList<Boolean>();
		this.propositionButs = new ArrayList<Boolean>();
		this.propositionButTerminees = new ArrayList<Boolean>();
		
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
}
