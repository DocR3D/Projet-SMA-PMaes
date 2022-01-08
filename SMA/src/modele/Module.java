package modele;

import java.util.ArrayList;

public class Module {

	//Liste de condition pour activer le module
	private ArrayList<Proposition> conditions;
	//Liste de condition qui seront à vraie apres avoir activé le module
	private ArrayList<Proposition> ajoutes;
	//Liste de condition qui seront à faux apres avoir activé le module
	private ArrayList<Proposition> detruits;

	//Liste des modules à faire avant de faire celui la
	private ArrayList<Module> pred;
	//Liste des modules qui seront faisable apres avoir fait celui la
	private ArrayList<Module> succ;
	//Liste des modules qui ne seront plus faisable apres avoir activer ce module
	private ArrayList<Module> conf;

	//Liste contenant tout les seuils du module au cours du programme
	public ArrayList<Float> oldStats;

	//Premiere valeur pour le seuil d'activation du module
	private float seuilActivationALPHA;

	//variables permettant de stocker temporairement l'energy à recevoir
	public float reduitParObjectifProtege = 0;
	public float takesAway = 0;
	public float recuDepuisObjectif = 0;
	public float recuDepuisEnvironnement = 0;
	public float recuDepuisDerriere = 0;
	public float recuDepuisDevant = 0;

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
		oldStats = new ArrayList<Float>();
		oldStats.add(seuilActivationALPHA);


		Environnement.addModuleToListModule(this);
	}

	//Fonction permettant d'activer un module
	//Il met à jours toute les propositions en fonction des siennes
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

	public ArrayList<Float> getOldStats(){
		return this.oldStats;
	}

	public void setSeuilActivationALPHA(float seuilActivationALPHA) {
		this.seuilActivationALPHA = seuilActivationALPHA;
	}

	//Fonction mettant à jours le seuil du module selon les variables temporaires
	public void updateSeuil() {
		float futurSeuil = this.seuilActivationALPHA;
		futurSeuil += recuDepuisEnvironnement;
		futurSeuil += recuDepuisObjectif;

		futurSeuil -= this.reduitParObjectifProtege;
		if(futurSeuil < 0) futurSeuil = 0;
		futurSeuil -= this.takesAway;
		if(futurSeuil < 0) futurSeuil = 0;

		futurSeuil += recuDepuisDerriere;
		futurSeuil += recuDepuisDevant;
		this.setSeuilActivationALPHA(futurSeuil);

		this.recuDepuisObjectif = 0;
		this.recuDepuisEnvironnement = 0;
		this.recuDepuisDerriere = 0;
		this.recuDepuisDevant = 0;
		this.reduitParObjectifProtege = 0;
		this.takesAway = 0;
		oldStats.add(seuilActivationALPHA);
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
