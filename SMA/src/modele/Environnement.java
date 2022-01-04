package modele;

import java.util.ArrayList;


// Alphabet : 
// α = Alpha, π = Pi, θ = Theta, γ = Gamma, φ = Phi, δ = delta,
//
public class Environnement {
	
	private float defaultSeuilActivationTHETA;
	
	private float niveauActivationPI;
	private float seuilActivationTHETA;
	private float energieInjecteeSousButGAMMA;
	private float energieInjecteePropositionVraiePHI;
	private float energiePriseButProtegeDELTA;
	
	private static ArrayList<Module> listeModule;
	private static ArrayList<Module> listeModuleActivable;
	


	public Environnement(float niveauActivationPI, float seuilActivationTHETA, float energieInjecteeSousButGAMMA,
			float energieInjecteePropositionVraiePHI, float energiePriseButProtegeDELTA) {
		super();
		this.niveauActivationPI = niveauActivationPI;
		this.seuilActivationTHETA = seuilActivationTHETA;
		this.defaultSeuilActivationTHETA = seuilActivationTHETA;
		this.energieInjecteeSousButGAMMA = energieInjecteeSousButGAMMA;
		this.energieInjecteePropositionVraiePHI = energieInjecteePropositionVraiePHI;
		this.energiePriseButProtegeDELTA = energiePriseButProtegeDELTA;
		
		new Agent();
		
		//Modules - lambda Ã  0 pour le moment, TO IMPLEMENT
		
		Environnement.listeModule = new ArrayList<Module>();
		Module objet_cle = new Module(0,"objet_cle");
		objet_cle.addCondition("presence_salle_1", true);
		Module objet_outil = new Module(0,"objet_outil");
		objet_outil.addCondition("presence_salle_2", true);
		Module objet_meuble_1 = new Module(0,"objet_meuble_1");
		objet_meuble_1.addCondition("presence_salle_2", true);
		Module objet_meuble_2 = new Module(0,"objet_meuble_2");
		objet_meuble_2.addCondition("presence_salle_2", true);
		Module objet_porte = new Module(0,"objet_porte");
		objet_porte.addAjoutes("presence_salle_2", true);
	} 
	
	public static void addModuleToListModule(Module unModule) {
		listeModule.add(unModule);
	}
	
	public void executable() {
		Environnement.listeModuleActivable = new ArrayList<Module>();
		for(Module unModule: listeModule) {
			if(unModule.getSeuilActivationALPHA() > this.seuilActivationTHETA && unModule.isConditionOkey()) {
				Environnement.listeModuleActivable.add(unModule);
			}
		}
	}
	
	public void calculLinkBetweenModules() {
		for(Module unModuleAModifier : listeModule)
			for(Module unModuleAVerifier: listeModule) {
				for(String uneProposition : unModuleAModifier.getAjoutes().keySet()) {
					if(unModuleAVerifier.containCondition(uneProposition)) unModuleAModifier.addSucc(unModuleAVerifier);
				}
				for(String uneProposition : unModuleAModifier.getConditions().keySet()) {
					if(unModuleAVerifier.containAjoutes(uneProposition)) unModuleAModifier.addPred(unModuleAVerifier);
					if(unModuleAVerifier.containDetruits(uneProposition)) unModuleAModifier.addConf(unModuleAVerifier);
				}
			}
	}
	
	public static ArrayList<Module> M(String uneProposition){
		ArrayList<Module> result = new ArrayList<Module>();
		for(Module unModule : listeModule) {
			if(unModule.containCondition(uneProposition))
				result.add(unModule);
		}
		if(result.size() == 0) return null;
		return result;
	}
	public static  ArrayList<Module> A(String uneProposition){
		ArrayList<Module> result = new ArrayList<Module>();
		for(Module unModule : listeModule) {
			if(unModule.containAjoutes(uneProposition))
				result.add(unModule);
		}
		if(result.size() == 0) return null;
		return result;
	}
	
	public static  ArrayList<Module> U(String uneProposition){
		ArrayList<Module> result = new ArrayList<Module>();
		for(Module unModule : listeModule) {
			if(unModule.containDetruits(uneProposition))
				result.add(unModule);
		}
		if(result.size() == 0) return null;
		return result;
	}
	
	//Si rien n'a été éxécuté, on met à jours theta
	public void updateTheta() {
		if(listeModuleActivable.size() != 0) { 
			this.seuilActivationTHETA = this.seuilActivationTHETA - this.seuilActivationTHETA/10; // On retire 10 pourcents
			System.out.println("L'environnement diminue Theta de 10%, Theta = " + this.seuilActivationTHETA);
		}else {		// Si un module a été activé, on remet la valeur par défaut
			System.out.println("L'environnement à réinitialisé la valeur de Theta, Theta = " + this.defaultSeuilActivationTHETA);
			this.seuilActivationTHETA = this.defaultSeuilActivationTHETA;
		}
	}
	
	public void updateEnergyWhenActivatingModule(Module m1) {
		for(Module unModule : m1.getSucc()) {
			System.out.println("L'environnement donne à " + unModule + " une valeur d'énergie égal à " + this.energieInjecteePropositionVraiePHI);
			unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() + this.energieInjecteePropositionVraiePHI);
		}
	}
	
	public void updateEnergyWhenInactif(Module m1) {
		for(Module unModule : m1.getPred()) {
			unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() + this.energieInjecteeSousButGAMMA);
		}
	}
	
	public void updateEnergyOnConflit(Module m1) {
		for(Module unModule : m1.getConf()) {
			unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() - this.energiePriseButProtegeDELTA);
		}
	}
	
	/** Pas surs de cette version**/
	public void updateEnergy(Module unModule) {
			float valeurToAdd = 0;
			float valeurToRemove = 0;
			// energy injected for each corresponding P
			valeurToAdd = unModule.getNumberTrueCondition() * this.energieInjecteePropositionVraiePHI; // Pour chaque conditions vraie, on ajoute un phi
			
			//energy injected for each corresponding G
			valeurToAdd += unModule.getNumberTrueAjoutes() * this.energieInjecteeSousButGAMMA;
			
			//energy taken away for each corresponding G
			valeurToRemove = unModule.getNumberTrueDetruits() * this.energiePriseButProtegeDELTA;
			
			System.out.println("L'environnement donne à " + unModule + " une valeur d'énergie égal à " + (valeurToAdd - valeurToRemove));
			unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() + valeurToAdd - valeurToRemove); // On met à jours alpha
	}
	
	public Module getModuleToExecute() {
		Module bestModule = null;
		for(Module unModule : listeModule) {
			if(listeModuleActivable.contains(unModule) && (bestModule == null || unModule.getSeuilActivationALPHA() > bestModule.getSeuilActivationALPHA()))
				bestModule = unModule;
		}
		return bestModule;
	}
}
