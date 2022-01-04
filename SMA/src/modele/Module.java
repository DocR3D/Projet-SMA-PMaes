package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Module {

	private HashMap<String, Boolean> conditions;
	private HashMap<String, Boolean> ajoutes;
	private HashMap<String, Boolean> detruits;
	
	private ArrayList<Module> pred;
	private ArrayList<Module> succ;
	private ArrayList<Module> conf;
	


	
	private float seuilActivationALPHA;
	private String nom;
	
	public Module(float seuilActivationALPHA,String nom) {
		super();
		this.seuilActivationALPHA = seuilActivationALPHA;
		
		this.conditions = new HashMap<String, Boolean>();
		this.ajoutes = new HashMap<String, Boolean>();
		this.detruits = new HashMap<String, Boolean>();
		
		this.pred = new ArrayList<Module>();
		this.succ = new ArrayList<Module>();
		this.conf = new ArrayList<Module>();
		
		this.nom = nom;

		
		Environnement.addModuleToListModule(this);
	}
	
	public void activateModule() {
		System.out.println("le module " + this + " est activé");
		this.seuilActivationALPHA = 0;
	}
	
	public boolean containCondition(Proposition uneProposition) {
		if(this.conditions.containsKey(uneProposition.getNom())) return true;
		else return false;
	}
	
	public boolean containAjoutes(Proposition uneProposition) {
		if(this.ajoutes.containsKey(uneProposition.getNom())) return true;
		else return false;
	}
	
	public boolean containDetruits(Proposition uneProposition) {
		if(this.detruits.containsKey(uneProposition.getNom())) return true;
		else return false;
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
	
	public int getSizeCondition() {
		return this.conditions.values().size();
	}
	
	public int getSizeAjoutes() {
		return this.ajoutes.values().size();
	}
	
	public int getSizeDetruits() {
		return this.detruits.values().size();
	}
	
	public int getNumberTrueCondition() {
		int i = 0;
		for(Boolean uneCondition : this.conditions.values()) if(uneCondition) i++;
		return i;
	}
	
	public int getNumberTrueAjoutes() {
		int i = 0;
		for(Boolean uneCondition : this.ajoutes.values()) if(uneCondition) i++;
		return i;	}
	
	public int getNumberTrueDetruits() {
		int i = 0;
		for(Boolean uneCondition : this.detruits.values()) if(uneCondition) i++;
		return i;	}

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
	
	public ArrayList<Module> getPred() {
		return pred;
	}

	public ArrayList<Module> getSucc() {
		return succ;
	}
	
	public ArrayList<Module> getConf() {
		return conf;
	}


	
}
