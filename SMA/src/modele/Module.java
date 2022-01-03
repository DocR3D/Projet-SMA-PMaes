package modele;

import java.util.HashMap;

public class Module {

	private HashMap<String, Boolean> conditions;
	private HashMap<String, Boolean> ajoutes;
	private HashMap<String, Boolean> detruits;
	
	private float seuilActivationLAMBDA;

	public Module(float seuilActivationLAMBDA) {
		super();
		this.seuilActivationLAMBDA = seuilActivationLAMBDA;
		
		this.conditions = new HashMap<String, Boolean>();
		this.ajoutes = new HashMap<String, Boolean>();
		this.detruits = new HashMap<String, Boolean>();
	}
	
	public boolean addCondition(String key, boolean value) {
		if(this.conditions.containsKey(key)) {
			return false;
		}
		else {
			this.conditions.put(key, value);
			return true;
		}
	}
	
	public boolean addAjoutes(String key, boolean value) {
		if(this.ajoutes.containsKey(key)) {
			return false;
		}
		else {
			this.ajoutes.put(key, value);
			return true;
		}
	}
	
	public boolean addDetruits(String key, boolean value) {
		if(this.detruits.containsKey(key)) {
			return false;
		}
		else {
			this.detruits.put(key, value);
			return true;
		}
	}
	
}
