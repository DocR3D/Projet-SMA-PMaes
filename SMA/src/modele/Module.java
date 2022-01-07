package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Module {

	private ArrayList<Proposition> conditions;
	private ArrayList<Proposition> ajoutes;
	private ArrayList<Proposition> detruits;
	
	private ArrayList<Module> pred;
	private ArrayList<Module> succ;
	private ArrayList<Module> conf;
	


	
	private float seuilActivationALPHA;
	public float seuilParButProtege = 0;
	public float seuilReduitParAutre = 0;
	private float futurSeuil = 0;
	private String nom;
	
	public Module(float seuilActivationALPHA,String nom) {
		super();
		this.seuilActivationALPHA = seuilActivationALPHA;
		
		this.conditions = new ArrayList<Proposition>();
		this.ajoutes = new ArrayList<Proposition>();
		this.detruits = new ArrayList<Proposition>();
		
		this.pred = new ArrayList<Module>();
		this.succ = new ArrayList<Module>();
		this.conf = new ArrayList<Module>();
		
		this.nom = nom;

		
		Environnement.addModuleToListModule(this);
	}
	
	public void activateModule() {
		System.out.println("le module " + this + " est active");
		
		//Change la valeur de toute les conditions pour tout les modules contenant la proposition
		for(Proposition uneProposition : this.ajoutes) {
			uneProposition.setTrue();
			//Environnement.A(uneProposition).forEach(m -> m.getConditions());
			if(Agent.isInPropositionButs(uneProposition))
				Agent.terminerBut(uneProposition);
		}
		
		//Change la valeur de toute les conditions pour tout les modules contenant la proposition
		for(Proposition uneProposition : this.detruits) {
			uneProposition.setFalse();

			//if(Environnement.A(uneProposition) != null )Environnement.A(uneProposition).forEach(m -> m.getConditions().put(uneProposition, false));
			//if(Environnement.M(uneProposition) != null ) Environnement.M(uneProposition).forEach(m -> m.getConditions().put(uneProposition, false));
			if(Agent.isInPropositionButs(uneProposition))
				Agent.resetBut(uneProposition);

		}
			
		this.futurSeuil = 0;
		this.seuilActivationALPHA = 0;
	}
	
	public boolean containCondition(Proposition uneProposition) {
		if(this.conditions.contains(uneProposition)) return true;
		else return false;
	}
	
	public boolean containAjoutes(Proposition uneProposition) {
		if(this.ajoutes.contains(uneProposition)) return true;
		else return false;
	}
	
	public boolean containDetruits(Proposition uneProposition) {
		if(this.detruits.contains(uneProposition)) return true;
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
	
	
	
	public boolean addCondition(Proposition uneProposition) {
		if(this.conditions.contains(uneProposition)) {
			return false;
		}
		else {
			this.conditions.add(uneProposition);
			return true;
		}
	}
	
	public boolean addAjoutes(Proposition uneProposition) {
		if(this.ajoutes.contains(uneProposition)) {
			return false;
		}
		else {
			this.ajoutes.add(uneProposition);
			return true;
		}
	}
	
	public boolean addDetruits(Proposition uneProposition) {
		if(this.detruits.contains(uneProposition)) {
			return false;
		}
		else {
			this.detruits.add(uneProposition);
			return true;
		}
	}
	
	
	public ArrayList<Proposition> getConditions() {
		return conditions;
	}

	public ArrayList<Proposition> getAjoutes() {
		return ajoutes;
	}

	public ArrayList<Proposition> getDetruits() {
		return detruits;
	}

	public int getSizeCondition() {
		return this.conditions.size();
	}
	
	public int getSizeAjoutes() {
		return this.ajoutes.size();
	}
	
	public int getSizeDetruits() {
		return this.detruits.size();
	}
	
	public int getNumberTrueCondition() {
		int i = 0;
		for(Proposition uneProposition : this.conditions) if(uneProposition.isTrue()) i++;
		return i;
	}
	
	public int getNumberTrueAjoutes() {
		int i = 0;
		for(Proposition uneProposition: this.ajoutes) if(uneProposition.isTrue()) i++;
		return i;	}
	
	public int getNumberTrueDetruits() {
		int i = 0;
		for(Proposition uneProposition : this.detruits) if(uneProposition.isTrue()) i++;
		return i;	}

	public float getSeuilActivationALPHA() {
		return seuilActivationALPHA;
	}

	public void setSeuilActivationALPHA(float seuilActivationALPHA) {
		this.seuilActivationALPHA = seuilActivationALPHA;
	}
	
	public void ajouterAFuturSeuil(float seuilActivationALPHA) {
		this.futurSeuil = this.futurSeuil + seuilActivationALPHA;
	}
	
	public void enleverAFuturSeuil(float seuilActivationALPHA) {
		this.futurSeuil = this.futurSeuil - seuilActivationALPHA;
	}
	
	public void updateSeuil() {
		this.seuilActivationALPHA += this.futurSeuil;
		this.seuilActivationALPHA -= this.seuilParButProtege; 
		if(this.seuilActivationALPHA < 0) this.seuilActivationALPHA = 0;
		this.seuilActivationALPHA -= this.seuilReduitParAutre; 
		if(this.seuilActivationALPHA < 0) this.seuilActivationALPHA = 0;
		this.futurSeuil = 0;
	}

	
	public boolean isConditionOkey() {
		for(Proposition uneProposition : this.conditions) {
			if(!uneProposition.isTrue()) return false;
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
	
	public String activationDisplay() {
		return "  niveau d'activation de " + this.nom + ": " + this.getSeuilActivationALPHA();
	}
	
	


	
}
