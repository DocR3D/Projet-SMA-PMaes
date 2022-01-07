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

	public static ArrayList<Proposition> listeDesProposition;

	public int time = 1;


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
		listeDesProposition = new ArrayList<Proposition>();
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
			}else if(unModule.isConditionOkey()) System.out.println("L'environnement peut executer celui de " + unModule);
		}
	}

	public void calculLinkBetweenModules() {
		for(Module unModuleAModifier : listeModule)
			for(Module unModuleAVerifier: listeModule) {
				for(Proposition uneProposition : unModuleAModifier.getAjoutes()) {
					if(unModuleAVerifier.containCondition(uneProposition)) unModuleAModifier.addSucc(unModuleAVerifier);
				}
				for(Proposition uneProposition : unModuleAModifier.getConditions()) {
					if(unModuleAVerifier.containAjoutes(uneProposition)) unModuleAModifier.addPred(unModuleAVerifier);
					if(unModuleAVerifier.containDetruits(uneProposition)) unModuleAModifier.addConf(unModuleAVerifier);
				}
			}
	}

	public static ArrayList<Module> M(Proposition uneProposition){
		ArrayList<Module> result = new ArrayList<Module>();
		for(Module unModule : listeModule) {
			if(unModule.containCondition(uneProposition))
				result.add(unModule);
		}
		if(result.size() == 0) return null;
		return result;
	}
	public static  ArrayList<Module> A(Proposition uneProposition){
		ArrayList<Module> result = new ArrayList<Module>();
		for(Module unModule : listeModule) {
			if(unModule.containAjoutes(uneProposition))
				result.add(unModule);
		}
		if(result.size() == 0) return null;
		return result;
	}

	public static  ArrayList<Module> U(Proposition uneProposition){
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
	public void updateEnergyStateGoalAndGoalDone() {


		for(Proposition uneProposition : Agent.S()) {
			if(Environnement.M(uneProposition) != null)

				for(Module unModule : Environnement.M(uneProposition)) {
					System.out.println("L'environnement donne à " + unModule + " une valeur d'énergie égal à " + (energieInjecteePropositionVraiePHI* 1/Environnement.M(uneProposition).size() * 1/unModule.getSizeCondition()));
					unModule.inputFromState += energieInjecteePropositionVraiePHI * 1/Environnement.M(uneProposition).size() * 1/unModule.getSizeCondition();
					//System.out.println("La proposition était : " + uneProposition);
					//System.out.println("Nouvel valeur de "+ unModule + " est " + unModule.getSeuilActivationALPHA());
				}
		}
		for(Proposition uneProposition : Agent.G()) {
			if(Environnement.A(uneProposition) != null)

				for(Module unModule : Environnement.A(uneProposition)) {
					System.out.println("Les buts donnent à " + unModule + " une valeur d'énergie égal à " + energieInjecteeSousButGAMMA * 1/Environnement.A(uneProposition).size() * 1.00f/unModule.getSizeAjoutes()); 
					//unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() +(float) (energieInjecteeSousButGAMMA * 1.0/Environnement.A(uneProposition).size() * 1.0/unModule.getSizeAjoutes()));
					unModule.inputFromGoals += energieInjecteeSousButGAMMA * 1/Environnement.A(uneProposition).size() * 1.00f/unModule.getSizeAjoutes();
					//System.out.println("Nouvel valeur de "+ unModule + " est " + unModule.getSeuilActivationALPHA());

				}
		}
		for(Proposition uneProposition : Agent.R()) {
			if(Environnement.U(uneProposition) != null)
				for(Module unModule : Environnement.U(uneProposition)) {
					System.out.println("Les buts accomplit retirent à " + unModule + " une valeur d'énergie égal à " + (energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits() ));
					//unModule.setSeuilActivationALPHA(unModule.getSeuilActivationALPHA() - (energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits()));
					unModule.takenAwayByProtectedGoals = energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits();
					//unModule.enleverAFuturSeuil((unModule.getSeuilActivationALPHA() - energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits()));
					//System.out.println("Nouvel valeur de "+ unModule + " est " + unModule.getSeuilActivationALPHA());

				}
		}

	}

	public void updateEnergyPropagation() {
		System.out.println();
		ArrayList<Proposition> propositions = new ArrayList<Proposition>();

		//On récupère toute les propositions
		for(Module unModule : Environnement.listeModule) {
			for(Proposition uneProposition :  unModule.getConditions())
				if(!propositions.contains(uneProposition)) propositions.add(uneProposition);
			for(Proposition uneProposition :  unModule.getAjoutes())
				if(!propositions.contains(uneProposition)) propositions.add(uneProposition);
			for(Proposition uneProposition :  unModule.getDetruits())
				if(!propositions.contains(uneProposition)) propositions.add(uneProposition);
		}
		for(int x = 0; x < listeModule.size(); x++)
			for(int y = 0; y < listeModule.size(); y++) {
				if(x == y ) continue;
				for( Proposition uneProposition : propositions) {
					Module mx = listeModule.get(x);
					Module my = listeModule.get(y);
					//En avant 
					if(mx.containAjoutes(uneProposition) && uneProposition.isTrue() == false 
							&& my.containCondition(uneProposition) ) {
						if(!listeModuleActivable.contains(mx)) {
							System.out.println(mx + " donne " + (mx.getSeuilActivationALPHA()* energieInjecteePropositionVraiePHI/energieInjecteeSousButGAMMA * 1f/Environnement.M(uneProposition).size()*1f/my.getConditions().size())+ " d'énergie en AVANT vers " + my + " pour la proposition " + uneProposition);
							my.spreadsForward += (mx.getSeuilActivationALPHA()* energieInjecteePropositionVraiePHI/energieInjecteeSousButGAMMA * 1f/Environnement.M(uneProposition).size()*1f/my.getConditions().size());
						}
					}

					//En Arriere
					//System.out.println(mx + " et " + my + " pour " + uneProposition + " est contenu dans mx ? " +mx.containCondition(uneProposition) + " est contenu dans my ? " + my.containAjoutes(uneProposition));
					if(mx.containCondition(uneProposition) && my.containAjoutes(uneProposition) && uneProposition.isTrue() == false) {
						if(!listeModuleActivable.contains(mx)) {

							System.out.println(mx + " donne " + mx.getSeuilActivationALPHA() * 1/Environnement.A(uneProposition).size()*1/my.getAjoutes().size() + " d'énergie en ARRIERE vers " + my + " pour la proposition " + uneProposition);
							my.spreadsBackward += mx.getSeuilActivationALPHA() * 1/Environnement.A(uneProposition).size()*1/my.getAjoutes().size();
						}
					}

					//Decay
					if(mx.containCondition(uneProposition) && uneProposition.isTrue() == true 
							&& my.containDetruits(uneProposition)) {
						if(mx.getSeuilActivationALPHA() <= my.getSeuilActivationALPHA() && Agent.S().contains(uneProposition) 
								&& my.getConditions().contains(uneProposition) && mx.getDetruits().contains(uneProposition)) {
							my.takesAway = 0;
						}else {

							if(uneProposition.name.equals("hand_is_empty")) {
								//System.out.println("\n\n\n\n" + mx + " vs " + my +" "+ mx.containCondition(uneProposition) +" et " + uneProposition.isTrue() +" et " + my.containDetruits(uneProposition)); 
								//System.out.println( mx.getSeuilActivationALPHA()*(time-1)+ " < " + my.getSeuilActivationALPHA()*(time-1) +" et " + Agent.S().contains(uneProposition) +my.getConditions().contains(uneProposition) + mx.getDetruits().contains(uneProposition)  +"\n\n\n\n");
								//continue; //TODO j'ai triché ici 
							}
							float max = mx.getSeuilActivationALPHA()*energiePriseButProtegeDELTA/energieInjecteeSousButGAMMA*1f/(Environnement.U(uneProposition).size())*1f/my.getDetruits().size(); 
							//System.out.println( mx.getSeuilActivationALPHA()*(time-1)*energiePriseButProtegeDELTA/energieInjecteeSousButGAMMA*1f/(Environnement.U(uneProposition).size())*1f/my.getDetruits().keySet().size());

							if( max < 0 ) { // TODO J'ai triché aussi ici ! 
								max = my.getSeuilActivationALPHA(); 
							} 

							my.takesAway += max;
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
		float sum = 0f;
		for(Module unModule : listeModule) {
			unModule.updateSeuil();
			sum = sum + unModule.getSeuilActivationALPHA();

		}
		float factor = this.niveauActivationPI / (sum/listeModule.size());
		if(time >= 2 && sum != 0) {
			for(Module unModule : listeModule) {				
				unModule.setSeuilActivationALPHA((float) (factor * unModule.getSeuilActivationALPHA()));
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

	public void afficherPropositions() {
		for(Proposition uneProposition : listeDesProposition)
			System.out.println(uneProposition.name + " : " + uneProposition.isTrue());
	}
	public void ajouterProposition(Proposition uneProposition) {
		listeDesProposition.add(uneProposition);
	}
}
