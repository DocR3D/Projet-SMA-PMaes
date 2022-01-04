package modele;

import java.util.ArrayList;


// Alphabet : 
// α = Alpha, π = Pi, θ = Theta, γ = Gamma, ϕ = Phi, δ = delta,
//
public class Environnement {
	
	private float defaultSeuilActivationTHETA;
	
	private float niveauActivationPI;
	private float seuilActivationTHETA;
	private float energieInjecteeSousButGAMMA;
	private float energieInjecteePropositionVraiePHI;
	private float energiePriseButProtegeDELTA;
	
	private Agent agent;
	private Boolean but = false;
	private static ArrayList<Module> listeModule;
	
	public Environnement(float niveauActivationPI, float seuilActivationTHETA, float energieInjecteeSousButGAMMA,
			float energieInjecteePropositionVraiePHI, float energiePriseButProtegeDELTA) {
		super();
		this.niveauActivationPI = niveauActivationPI;
		this.seuilActivationTHETA = seuilActivationTHETA;
		this.defaultSeuilActivationTHETA = seuilActivationTHETA;
		this.energieInjecteeSousButGAMMA = energieInjecteeSousButGAMMA;
		this.energieInjecteePropositionVraiePHI = energieInjecteePropositionVraiePHI;
		this.energiePriseButProtegeDELTA = energiePriseButProtegeDELTA;
		
		this.agent = new Agent();
		
		//Modules - lambda Ã  0 pour le moment, TO IMPLEMENT
		
		this.listeModule = new ArrayList<Module>();
		Module objet_cle = new Module(0);
		objet_cle.addCondition("presence_salle_1", true);
		Module objet_outil = new Module(0);
		objet_outil.addCondition("presence_salle_2", true);
		Module objet_meuble_1 = new Module(0);
		objet_meuble_1.addCondition("presence_salle_2", true);
		Module objet_meuble_2 = new Module(0);
		objet_meuble_2.addCondition("presence_salle_2", true);
		Module objet_porte = new Module(0);
		objet_porte.addAjoutes("presence_salle_2", true);
	} 
	
	public static void addModuleToListModule(Module unModule) {
		listeModule.add(unModule);
	}
	
	public boolean executeModule(Module unModule, Agent unAgent) {
		if(unModule.getSeuilActivationALPHA() > this.seuilActivationTHETA && unModule.isConditionOkey()) {
			return true;
		}else return false;
	}
	
	public void updateTheta() {
		if(true) { //TODO : No module has been activated
			this.seuilActivationTHETA = this.seuilActivationTHETA - this.seuilActivationTHETA/10; // On retire 10 pourcents
		}else {		// Si un module a été activé, on remet la valeur par défaut
			this.seuilActivationTHETA = this.defaultSeuilActivationTHETA;
		}
	}
	
	public void updateEnergy() {
		for(Module unModule : this.listeModule) { // Pour chaque module (ou plus particulierement son lambda)
			float valeurToAdd = 0;
			for()
			if(true) { // Si P est compris dans conditions
			
			}else if() { // Si G est compris dans ajoutes
				
			}else if() { //Si G est compris dans detruits;
				
			}
			
		}
	}
}
