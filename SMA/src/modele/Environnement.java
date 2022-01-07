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

	private Agent unAgent;
	
	private ArrayList<Float> allTheTheta;

	public Environnement(float niveauActivationPI, float seuilActivationTHETA, float energieInjecteeSousButGAMMA,
			float energieInjecteePropositionVraiePHI, float energiePriseButProtegeDELTA, Agent a) {
		super();
		this.niveauActivationPI = niveauActivationPI;
		this.seuilActivationTHETA = seuilActivationTHETA;
		this.defaultSeuilActivationTHETA = seuilActivationTHETA;
		this.energieInjecteeSousButGAMMA = energieInjecteeSousButGAMMA;
		this.energieInjecteePropositionVraiePHI = energieInjecteePropositionVraiePHI;
		this.energiePriseButProtegeDELTA = energiePriseButProtegeDELTA;
		listeModule = new ArrayList<>();
		listeDesProposition = new ArrayList<>();
		this.unAgent = a;
		allTheTheta = new ArrayList<Float>();
		allTheTheta.add(seuilActivationTHETA);
		

	}

	public static void addModuleToListModule(Module unModule) {
		listeModule.add(unModule);
	}

	public void executable() {
		Environnement.listeModuleActivable = new ArrayList<>();
		for(Module unModule: listeModule) {
			if(unModule.getSeuilActivationALPHA() > this.seuilActivationTHETA && unModule.isConditionOkey()) {
				Environnement.listeModuleActivable.add(unModule);
			}
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
		ArrayList<Module> result = new ArrayList<>();
		for(Module unModule : listeModule) {
			if(unModule.containCondition(uneProposition))
				result.add(unModule);
		}
		if(result.size() == 0) return null;
		return result;
	}
	public static  ArrayList<Module> A(Proposition uneProposition){
		ArrayList<Module> result = new ArrayList<>();
		for(Module unModule : listeModule) {
			if(unModule.containAjoutes(uneProposition))
				result.add(unModule);
		}
		if(result.size() == 0) return null;
		return result;
	}

	public static  ArrayList<Module> U(Proposition uneProposition){
		ArrayList<Module> result = new ArrayList<>();
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
		this.allTheTheta.add(this.seuilActivationTHETA);
		updateStatsProposition();

	}
	public void updateEnergyStateGoalAndGoalDone(boolean afficherInformation) {
		for(Proposition uneProposition : unAgent.S()) {
			if(Environnement.M(uneProposition) != null)

				for(Module unModule : Environnement.M(uneProposition)) {
					if(afficherInformation) System.out.println("L'environnement donne à " + unModule + " une valeur d'énergie égal à " + (energieInjecteePropositionVraiePHI* 1/Environnement.M(uneProposition).size() * 1/unModule.getSizeCondition()));
					unModule.inputFromState += energieInjecteePropositionVraiePHI * 1/Environnement.M(uneProposition).size() * 1/unModule.getSizeCondition();
				}
		}
		for(Proposition uneProposition : unAgent.G()) {
			if(Environnement.A(uneProposition) != null)

				for(Module unModule : Environnement.A(uneProposition)) {
					if(afficherInformation) System.out.println("Les buts donnent à " + unModule + " une valeur d'énergie égal à " + energieInjecteeSousButGAMMA * 1/Environnement.A(uneProposition).size() * 1.00f/unModule.getSizeAjoutes());
					unModule.inputFromGoals += energieInjecteeSousButGAMMA * 1/Environnement.A(uneProposition).size() * 1.00f/unModule.getSizeAjoutes();

				}
		}
		for(Proposition uneProposition : unAgent.R()) {
			if(Environnement.U(uneProposition) != null)
				for(Module unModule : Environnement.U(uneProposition)) {
					if(afficherInformation) System.out.println("Les buts accomplit retirent à " + unModule + " une valeur d'énergie égal à " + (energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits() ));
					unModule.takenAwayByProtectedGoals = energiePriseButProtegeDELTA/Environnement.U(uneProposition).size() + 1/unModule.getNumberTrueDetruits();

				}
		}

	}

	public void updateEnergyPropagation(boolean afficherInformation) {
		System.out.println();
		
		for(int x = 0; x < listeModule.size(); x++)
			for(int y = 0; y < listeModule.size(); y++) {
				if(x == y ) continue;
				for( Proposition uneProposition : listeDesProposition) {
					Module mx = listeModule.get(x);
					Module my = listeModule.get(y);
					//En avant
					if(mx.containAjoutes(uneProposition) && !uneProposition.isTrue()
							&& my.containCondition(uneProposition) ) {
						if(!listeModuleActivable.contains(mx)) {
							if(afficherInformation) System.out.println(mx + " donne " + (mx.getSeuilActivationALPHA()* energieInjecteePropositionVraiePHI/energieInjecteeSousButGAMMA * 1f/Environnement.M(uneProposition).size()*1f/my.getConditions().size())+ " d'énergie en AVANT vers " + my + " pour la proposition " + uneProposition);
							my.spreadsForward += (mx.getSeuilActivationALPHA()* energieInjecteePropositionVraiePHI/energieInjecteeSousButGAMMA * 1f/Environnement.M(uneProposition).size()*1f/my.getConditions().size());
						}
					}

					//En Arriere
					//System.out.println(mx + " et " + my + " pour " + uneProposition + " est contenu dans mx ? " +mx.containCondition(uneProposition) + " est contenu dans my ? " + my.containAjoutes(uneProposition));
					if(mx.containCondition(uneProposition) && my.containAjoutes(uneProposition) && !uneProposition.isTrue()) {
						if(!listeModuleActivable.contains(mx)) {

							if(afficherInformation) System.out.println(mx + " donne " + mx.getSeuilActivationALPHA() * 1/Environnement.A(uneProposition).size()*1/my.getAjoutes().size() + " d'énergie en ARRIERE vers " + my + " pour la proposition " + uneProposition);
							my.spreadsBackward += mx.getSeuilActivationALPHA() * 1/Environnement.A(uneProposition).size()*1/my.getAjoutes().size();
						}
					}

					//Decay
					if(mx.containCondition(uneProposition) && uneProposition.isTrue()
							&& my.containDetruits(uneProposition)) {
						if(mx.getSeuilActivationALPHA() <= my.getSeuilActivationALPHA() && unAgent.S().contains(uneProposition)
								&& my.getConditions().contains(uneProposition) && mx.getDetruits().contains(uneProposition)) {
							my.takesAway = 0;
						}else {
							float max = mx.getSeuilActivationALPHA()*energiePriseButProtegeDELTA/energieInjecteeSousButGAMMA*1f/(Environnement.U(uneProposition).size())*1f/my.getDetruits().size();
							//System.out.println( mx.getSeuilActivationALPHA()*(time-1)*energiePriseButProtegeDELTA/energieInjecteeSousButGAMMA*1f/(Environnement.U(uneProposition).size())*1f/my.getDetruits().keySet().size());

							if( max < 0 ) { // TODO J'ai triché aussi ici !
								max = my.getSeuilActivationALPHA();
							}

							my.takesAway += max;
							//System.out.println(mx.getSeuilActivationALPHA() +" et " + my.getSeuilActivationALPHA());
							if(afficherInformation) System.out.println(mx + " retire à " + my +" " + max + " en raison de " + uneProposition);
						}
					}

				}
			}

	}
	
	public void execute() {
		Module executableModule = getModuleToExecute();
		if(executableModule != null) {
			executableModule.activateModule();
		}else {
			System.out.println("Pas de module activé");
		}
	}


	public void updateEnergy(boolean afficherInformation) {
		updateEnergyStateGoalAndGoalDone(afficherInformation);
		updateEnergyPropagation(afficherInformation);
		//updateEnergyDecay();
		float sum = 0f;
		for(Module unModule : listeModule) {
			unModule.updateSeuil();
			sum = sum + unModule.getSeuilActivationALPHA();

		}
		float factor = this.niveauActivationPI / (sum/listeModule.size());
		if(time >= 2 && sum != 0) {
			for(Module unModule : listeModule) {
				unModule.setSeuilActivationALPHA(factor * unModule.getSeuilActivationALPHA());
			}
		}
		
		
	}
	public ArrayList<Module> getAllModules(){
		return listeModule;
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
		System.out.println("Niveau d'activation des modules apres decay");
		for(Module unModule : listeModule) System.out.println(unModule.activationDisplay());

	}

	public void afficherPropositions() {
		for(Proposition uneProposition : listeDesProposition)
			System.out.println(uneProposition.name + " : " + uneProposition.isTrue());
	}
	public void ajouterProposition(Proposition uneProposition) {
		listeDesProposition.add(uneProposition);
	}
	
	public void updateStatsProposition() {
		for(Proposition uneProposition : listeDesProposition) {
			uneProposition.addEtat();
		}
	}
	
	public ArrayList<Float> getAllTheta() {
		return this.allTheTheta;
	}
}
