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
		System.out.println("le module " + this + " est activ�");
		
		//Change la valeur de toute les conditions pour tout les modules contenant la proposition
		for(String uneProposition : this.ajoutes.keySet()) {
			Environnement.A(uneProposition).forEach(m -> m.getConditions().put(uneProposition, true));
			if(Agent.isInPropositionButs(uneProposition))
				Agent.terminerBut(uneProposition);
		}
		
		//Change la valeur de toute les conditions pour tout les modules contenant la proposition
		for(String uneProposition : this.detruits.keySet()) {
			if(Environnement.A(uneProposition) != null )Environnement.A(uneProposition).forEach(m -> m.getConditions().put(uneProposition, false));
			if(Environnement.M(uneProposition) != null ) Environnement.M(uneProposition).forEach(m -> m.getConditions().put(uneProposition, false));
			if(Agent.isInPropositionButs(uneProposition))
				Agent.resetBut(uneProposition);

		}
			
		this.seuilActivationALPHA = 0;
	}
	
	public boolean containCondition(String uneProposition) {
		if(this.conditions.containsKey(uneProposition)) return true;
		else return false;
	}
	
	public boolean containAjoutes(String uneProposition) {
		if(this.ajoutes.containsKey(uneProposition)) return true;
		else return false;
	}
	
	public boolean containDetruits(String uneProposition) {
		if(this.detruits.containsKey(uneProposition)) return true;
		else return false;
	}
	
	public void addSucc(Module unModule) {
		this.succ.add(unModule);
	}
	public void addPred(Module unModule) {
		this.pred.add(unModule);
	}
	public void addConf(Module unModule) {
		this.conf.add(unModule);
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
	
	
	public HashMap<String, Boolean> getConditions() {
		return conditions;
	}

	public HashMap<String, Boolean> getAjoutes() {
		return ajoutes;
	}

	public HashMap<String, Boolean> getDetruits() {
		return detruits;
	}

	public float getSizeCondition() {
		return this.conditions.values().size();
	}
	
	public float getSizeAjoutes() {
		return this.ajoutes.values().size();
	}
	
	public float getSizeDetruits() {
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nom;
	}
	
	


	
}
