package modele;

import java.util.ArrayList;

public class Module {

	private ArrayList<Proposition> conditions;
	private ArrayList<Proposition> ajoutes;
	private ArrayList<Proposition> detruits;

	private ArrayList<Module> pred;
	private ArrayList<Module> succ;
	private ArrayList<Module> conf;

	private float seuilActivationALPHA;
	public float takenAwayByProtectedGoals = 0;
	public float takesAway = 0;
	public float inputFromGoals = 0;
	public float inputFromState = 0;
	public float spreadsBackward = 0;
	public float spreadsForward = 0;

	private String nom;

	public Module(float seuilActivationALPHA,String nom) {
		super();
		this.seuilActivationALPHA = seuilActivationALPHA;

		this.conditions = new ArrayList<>();
		this.ajoutes = new ArrayList<>();
		this.detruits = new ArrayList<>();

		this.pred = new ArrayList<>();
		this.succ = new ArrayList<>();
		this.conf = new ArrayList<>();

		this.nom = nom;


		Environnement.addModuleToListModule(this);
	}

	public void activateModule() {
		System.out.println("le module " + this + " est active");

		//Change la valeur de toute les conditions pour tout les modules contenant la proposition
		for(Proposition uneProposition : this.ajoutes) {
			uneProposition.setTrue();

		}

		//Change la valeur de toute les conditions pour tout les modules contenant la proposition
		for(Proposition uneProposition : this.detruits) {
			uneProposition.setFalse();

		}
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

	public void updateSeuil() {
		float futurSeuil = this.seuilActivationALPHA;
		futurSeuil += inputFromState;
		futurSeuil += inputFromGoals;

		futurSeuil -= this.takenAwayByProtectedGoals;
		if(futurSeuil < 0) futurSeuil = 0;
		futurSeuil -= this.takesAway;
		if(futurSeuil < 0) futurSeuil = 0;

		futurSeuil += spreadsBackward;
		futurSeuil += spreadsForward;
		this.setSeuilActivationALPHA(futurSeuil);

		this.inputFromGoals = 0;
		this.inputFromState = 0;
		this.spreadsBackward = 0;
		this.spreadsForward = 0;
		this.takenAwayByProtectedGoals = 0;
		this.takesAway = 0;

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
		return this.nom;
	}

	public String activationDisplay() {
		return "  niveau d'activation de " + this.nom + ": " + this.getSeuilActivationALPHA();
	}





}
