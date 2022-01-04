package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Module {

	private HashMap<String, Boolean> conditions;
	private HashMap<String, Boolean> ajoutes;
	private HashMap<String, Boolean> detruits;
	


	
	private float seuilActivationALPHA;

	public Module(float seuilActivationALPHA) {
		super();
		this.seuilActivationALPHA = seuilActivationALPHA;
		
		this.conditions = new HashMap<String, Boolean>();
		this.ajoutes = new HashMap<String, Boolean>();
		this.detruits = new HashMap<String, Boolean>();
		
		Environnement.addModuleToListModule(this);
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

	public float getSeuilActivationALPHA() {
		return seuilActivationALPHA;
	}

	public void setSeuilActivationALPHA(float seuilActivationALPHA) {
		this.seuilActivationALPHA = seuilActivationALPHA;
	}
	
	public boolean isConditionOkey() {
		for(boolean isTrue : this.conditions.values()) {
			if(!isTrue) return false;
		}
		return true;
	}
	

	
}
