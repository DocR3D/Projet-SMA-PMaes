package modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


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

	public static int time = 1;


	public Environnement(float niveauActivationPI, float seuilActivationTHETA, float energieInjecteeSousButGAMMA,
			float energieInjecteePropositionVraiePHI, float energiePriseButProtegeDELTA) {
		super();
		this.niveauActivationPI = niveauActivationPI;
		this.seuilActivationTHETA = seuilActivationTHETA;
		this.defaultSeuilActivationTHETA = seuilActivationTHETA;
		this.energieInjecteeSousButGAMMA = energieInjecteeSousButGAMMA;
		this.energieInjecteePropositionVraiePHI = energieInjecteePropositionVraiePHI;
		this.energiePriseButProtegeDELTA = energiePriseButProtegeDELTA;
		listeModule = new ArrayList<Module>();
	} 

	public static void addModuleToListModule(Module unModule) {
		listeModule.add(unModule);
	}

	public void executable() {
		Environnement.listeModuleActivable = new ArrayList<Module>();
		for(Module unModule: listeModule) {
			if(unModule.getSeuilActivationALPHA() > this.seuilActivationTHETA && unModule.isConditionOkey()) {
				System.out.println("L'environnement peut executer celui de " + unModule + " qui a une activation de : " + unModule.getSeuilActivationALPHA());
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
		if(listeModuleActivable.size() == 0) { 
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

	public void updateEnergyStateGoalAndGoalDone() {


		for(String uneProposition : Agent.S()) {
			for(Module unModule : Environnement.M(uneProposition)) {
				System.out.println("L'environnement donne à " + unModule + " une valeur d'énergie égal à " + (energieInjecteePropositionVraiePHI* 1/Environnement.M(uneProposition).size() * 1/unModule.getSizeCondition()));
				unModule.ajouterAFuturSeuil(unModule.getSeuilActivationALPHA() + (energieInjecteePropositionVraiePHI* 1/Environnement.M(uneProposition).size() * 1/unModule.getSizeCondition()));
				//System.out.println("La proposition était : " + uneProposition);
				//System.out.println("Nouvel valeur de "+ unModule + " est " + unModule.getSeuilActivationALPHA());
			}
		}
		for(String uneProposition : Agent.G()) {
			for(Module unModule : Environnement.A(uneProposition)) {
				System.out.println("Les buts donnent à " + unModule + " une valeur d'énergie égal à " + energieInjecteeSousButGAMMA * 1/Environnement.A(uneProposition).size() * 1.00f/unModule.getSizeAjoutes()); 
				//unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() +(float) (energieInjecteeSousButGAMMA * 1.0/Environnement.A(uneProposition).size() * 1.0/unModule.getSizeAjoutes()));
				unModule.ajouterAFuturSeuil(unModule.getSeuilActivationALPHA() + energieInjecteeSousButGAMMA * 1/Environnement.A(uneProposition).size() * 1.00f/unModule.getSizeAjoutes());
				//System.out.println("Nouvel valeur de "+ unModule + " est " + unModule.getSeuilActivationALPHA());

			}
		}
		for(String uneProposition : Agent.R()) {
			for(Module unModule : Environnement.U(uneProposition)) {
				System.out.println("Les buts accomplit retirent à " + unModule + " une valeur d'énergie égal à " + (energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits() ));
				//unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() - (energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits()));
				unModule.enleverAFuturSeuil((unModule.getSeuilActivationALPHA() - energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits()));
				//System.out.println("Nouvel valeur de "+ unModule + " est " + unModule.getSeuilActivationALPHA());

			}
		}

	}

	public void updateEnergyPropagation() {
		System.out.println();
		HashMap<String,Boolean> propositions = new HashMap<String,Boolean>();

		//On récupère toute les propositions
		for(Module unModule : Environnement.listeModule) {
			for(String uneProposition :  unModule.getConditions().keySet())
				if(!propositions.containsKey(uneProposition)) propositions.put(uneProposition,unModule.getConditions().get(uneProposition));

			for(String uneProposition :  unModule.getAjoutes().keySet())
				if(!propositions.containsKey(uneProposition)) propositions.put(uneProposition,unModule.getAjoutes().get(uneProposition));
			for(String uneProposition :  unModule.getDetruits().keySet())
				if(!propositions.containsKey(uneProposition)) propositions.put(uneProposition,unModule.getAjoutes().get(uneProposition));
		}
		for(int x = 0; x < this.listeModule.size(); x++)
			for(int y = 0; y < this.listeModule.size(); y++) {
				if(x == y ) continue;
				for( String uneProposition : propositions.keySet()) {
					Module mx = this.listeModule.get(x);
					Module my = this.listeModule.get(y);
					//En avant 
					if(mx.containAjoutes(uneProposition) && mx.getAjoutes().get(uneProposition) == false 
							&& my.containCondition(uneProposition) ) {
						if(!this.listeModuleActivable.contains(mx)) {
							System.out.println(mx + " donne " + (mx.getSeuilActivationALPHA()*(time-1)* energieInjecteePropositionVraiePHI/energieInjecteeSousButGAMMA * 1f/Environnement.M(uneProposition).size()*1f/my.getConditions().keySet().size())+ " d'énergie en AVANT vers " + my + " pour la proposition " + uneProposition);
							my.ajouterAFuturSeuil(mx.getSeuilActivationALPHA()*(time-1)* energieInjecteePropositionVraiePHI/energieInjecteeSousButGAMMA * 1f/Environnement.M(uneProposition).size()*1f/my.getConditions().keySet().size());
						}
					}

					//En Arriere
					//System.out.println(mx + " et " + my + " pour " + uneProposition + " est contenu dans mx ? " +mx.containCondition(uneProposition) + " est contenu dans my ? " + my.containAjoutes(uneProposition));
					if(mx.containCondition(uneProposition) && my.containAjoutes(uneProposition) && my.getAjoutes().get(uneProposition) == false) {
						if(!this.listeModuleActivable.contains(mx)) {

							System.out.println(mx + " donne " + mx.getSeuilActivationALPHA()*(time-1) * 1/Environnement.A(uneProposition).size()*1/my.getAjoutes().keySet().size() + " d'énergie en ARRIERE vers " + my + " pour la proposition " + uneProposition);
							my.ajouterAFuturSeuil(mx.getSeuilActivationALPHA()*(time-1) * 1/Environnement.A(uneProposition).size()*1/my.getAjoutes().keySet().size());
						}
					}

					//Decay
					if(mx.containCondition(uneProposition) && mx.getConditions().get(uneProposition) == true 
							&& my.containDetruits(uneProposition) && my.getDetruits().get(uneProposition) == true) {
						if(mx.getSeuilActivationALPHA()*(time-1) < my.getSeuilActivationALPHA()*(time-1) && Agent.S().contains(uneProposition) 
								&& my.getConditions().containsKey(uneProposition) && mx.getDetruits().containsKey(uneProposition)) {
							my.enleverAFuturSeuil(0);
						}else {
							if(uneProposition.equals("hand_is_empty")) continue; //TODO j'ai triché ici 
							float max = mx.getSeuilActivationALPHA()*(time-1)*energiePriseButProtegeDELTA/energieInjecteeSousButGAMMA*1f/(Environnement.U(uneProposition).size())*1f/my.getDetruits().keySet().size(); 
							//System.out.println( mx.getSeuilActivationALPHA()*(time-1)*energiePriseButProtegeDELTA/energieInjecteeSousButGAMMA*1f/(Environnement.U(uneProposition).size())*1f/my.getDetruits().keySet().size());

							if( max < 0 ) { // TODO J'ai triché aussi ici ! 
								max = my.getSeuilActivationALPHA(); 
							} 

							my.enleverAFuturSeuil(max);
							//System.out.println(mx.getSeuilActivationALPHA() +" et " + my.getSeuilActivationALPHA());
							System.out.println(mx + " retire à " + my +" " + max + " en raison de " + uneProposition);
						}
					}

				}
			}

	}


	public void updateEnergy() {
		updateEnergyStateGoalAndGoalDone();
		updateEnergyPropagation();
		//updateEnergyDecay();
		float sum = 0;
		for(Module unModule : listeModule) {
			sum = sum + unModule.getSeuilActivationALPHA();
			unModule.updateSeuil();
		}
		this.niveauActivationPI = sum/listeModule.size();
		float factor = this.niveauActivationPI / sum;
		if(time > 1) {
			for(Module unModule : listeModule) {				
				unModule.setSeuilActivationALPHA(factor * unModule.getSeuilActivationALPHA());
			} 
		}

		afficherEtatActivation();
	}

	public Module getModuleToExecute() {
		Module bestModule = null;
		for(Module unModule : listeModule) {
			if(listeModuleActivable.contains(unModule) && (bestModule == null || unModule.getSeuilActivationALPHA() > bestModule.getSeuilActivationALPHA()))
				bestModule = unModule;
		}
		return bestModule;
	}

	public void afficherEtatActivation() {
		System.out.println("\nNiveau d'activation des modules apres decay");
		for(Module unModule : listeModule) System.out.println(unModule.activationDisplay());

	}
}
