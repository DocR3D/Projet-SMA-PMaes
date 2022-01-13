package vue;

import modele.Agent;
import modele.Environnement;
import modele.Module;
import modele.Proposition;


public class Main {
	static Agent unAgent;
	static Environnement e;
	static StatsCreator sc;

	public static void Maes(float niveauActivationPI,float seuilActivationTHETA, float energieInjecteeSousButGAMMA, float energieInjecteePropositionVraiePHI, float energiePriseButProtegeDELTA) {
		// Initialisation
		unAgent = new Agent();

		//float niveauActivationPI = 20, seuilActivationTHETA=45,  energieInjecteeSousButGAMMA = 70,  energieInjecteePropositionVraiePHI = 20, energiePriseButProtegeDELTA = 50;
		e = new Environnement(niveauActivationPI, seuilActivationTHETA, energieInjecteeSousButGAMMA, energieInjecteePropositionVraiePHI, energiePriseButProtegeDELTA,unAgent);

		Proposition sprayer_somewhere = new Proposition("sprayer_somewhere", true);
		Proposition hand_is_empty = new Proposition("hand_is_empty", true,2);
		Proposition sprayer_in_hand = new Proposition("sprayer_in_hand", false);
		Proposition sander_somewhere = new Proposition("sander_somewhere", true);
		Proposition sander_in_hand = new Proposition("sander_in_hand", false);
		Proposition board_somewhere = new Proposition("board_somewhere", true);
		Proposition board_in_hand = new Proposition("board_in_hand", false);
		Proposition operationnal = new Proposition("operationnal", true);
		Proposition board_in_vise = new Proposition("board_in_vise", false);
		Proposition self_painted = new Proposition("self_painted", false);
		Proposition board_sanded = new Proposition("board_sanded", false);

		Module pick_up_sprayer = new Module(0,"pick_up_sprayer");
		pick_up_sprayer.addCondition(sprayer_somewhere);
		pick_up_sprayer.addCondition(hand_is_empty);
		pick_up_sprayer.addAjoutes(sprayer_in_hand);
		pick_up_sprayer.addDetruits(sprayer_somewhere);
		pick_up_sprayer.addDetruits(hand_is_empty);

		Module pick_up_sander = new Module(0,"pick_up_sander");
		pick_up_sander.addCondition(sander_somewhere);
		pick_up_sander.addCondition(hand_is_empty);
		pick_up_sander.addAjoutes(sander_in_hand);
		pick_up_sander.addDetruits(sander_somewhere);
		pick_up_sander.addDetruits(hand_is_empty);

		Module pick_up_board = new Module(0,"pick_up_board");
		pick_up_board.addCondition(board_somewhere);
		pick_up_board.addCondition(hand_is_empty);
		pick_up_board.addAjoutes(board_in_hand);
		pick_up_board.addDetruits(board_somewhere);
		pick_up_board.addDetruits(hand_is_empty);

		Module put_down_sprayer = new Module(0,"put_down_sprayer");
		put_down_sprayer.addCondition(sprayer_in_hand);
		put_down_sprayer.addAjoutes(sprayer_somewhere);
		put_down_sprayer.addAjoutes(hand_is_empty);
		put_down_sprayer.addDetruits(sprayer_in_hand);

		Module put_down_sander = new Module(0,"put_down_sander");
		put_down_sander.addCondition(sander_in_hand);
		put_down_sander.addAjoutes(sander_somewhere);
		put_down_sander.addAjoutes(hand_is_empty);
		put_down_sander.addDetruits(sander_in_hand);

		Module put_down_board = new Module(0,"put_down_board");
		put_down_board.addCondition(board_in_hand);
		put_down_board.addAjoutes(board_somewhere);
		put_down_board.addAjoutes(hand_is_empty);
		put_down_board.addDetruits(board_in_hand);

		Module sand_board_in_hand = new Module(0,"sand_board_in_hand");
		sand_board_in_hand.addCondition(operationnal);
		sand_board_in_hand.addCondition(board_in_hand);
		sand_board_in_hand.addCondition(sander_in_hand);
		sand_board_in_hand.addAjoutes(board_sanded);

		Module sand_board_in_vise = new Module(0,"sand_board_in_vise");
		sand_board_in_vise.addCondition(operationnal);
		sand_board_in_vise.addCondition(board_in_vise);
		sand_board_in_vise.addCondition(sander_in_hand);
		sand_board_in_vise.addAjoutes(board_sanded);

		Module spray_paint_self = new Module(0,"spray_paint_self");
		spray_paint_self.addCondition(operationnal);
		spray_paint_self.addCondition(sprayer_in_hand);
		spray_paint_self.addAjoutes(self_painted);
		spray_paint_self.addDetruits(operationnal);

		Module place_board_in_vise = new Module(0,"place_board_in_vise");
		place_board_in_vise.addCondition(board_in_hand);
		place_board_in_vise.addAjoutes(hand_is_empty);
		place_board_in_vise.addAjoutes(board_in_vise);
		place_board_in_vise.addDetruits(board_in_hand);

		unAgent.addPropositionInitiale(hand_is_empty);

		unAgent.addPropositionInitiale(sander_somewhere);
		unAgent.addPropositionInitiale(sprayer_somewhere);
		unAgent.addPropositionInitiale(operationnal);
		unAgent.addPropositionInitiale(board_somewhere);

		unAgent.addBut(board_sanded);
		unAgent.addBut(self_painted);

		e.calculLinkBetweenModules();
		//D�but de la simulation
		sc = new StatsCreator(e.getAllModules(),e.getAllTheta());

	}

	public static void scenario(float niveauActivationPI,float seuilActivationTHETA, float energieInjecteeSousButGAMMA, float energieInjecteePropositionVraiePHI, float energiePriseButProtegeDELTA) {

		//float niveauActivationPI = 100, seuilActivationTHETA=20,  energieInjecteeSousButGAMMA = 80,  energieInjecteePropositionVraiePHI = 5, energiePriseButProtegeDELTA = 50;
		unAgent = new Agent();

		e = new Environnement(niveauActivationPI, seuilActivationTHETA, energieInjecteeSousButGAMMA, energieInjecteePropositionVraiePHI, energiePriseButProtegeDELTA,unAgent);
		sc = new StatsCreator(e.getAllModules(),e.getAllTheta());

		Proposition clef_quelque_part = new Proposition("clef_quelquepart", true);
		Proposition outil_quelque_part = new Proposition("outil_quelque_part", true);
		Proposition partie1_du_meuble_quelque_part = new Proposition("partie1_du_meuble_quelquepart", false);
		Proposition partie2_du_meuble_quelque_part = new Proposition("partie2_du_meuble_quelquepart", false);
		Proposition meuble_final_quelque_part = new Proposition("meuble_final_quelque_part", false);
		Proposition porte_quelque_part = new Proposition("porte_quelque_part", true);

		Proposition clef_dans_la_main = new Proposition("clef_dans_la_main", false);
		Proposition outil_dans_la_main = new Proposition("outil_dans_la_main", false);
		Proposition partie1_du_meuble_dans_la_main = new Proposition("partie1_du_meuble_dans_la_main", false);
		Proposition partie2_du_meuble_dans_la_main = new Proposition("partie2_du_meuble_dans_la_main", false);

		Proposition etre_proche_de_la_clef = new Proposition("etre_proche_de_la_clef", false);
		Proposition etre_proche_de_outil = new Proposition("etre_proche_de_outil", false);
		Proposition etre_proche_de_partie1 = new Proposition("etre_proche_de_partie1", false);
		Proposition etre_proche_de_partie2 = new Proposition("etre_proche_de_partie2", false);
		Proposition etre_proche_de_porte = new Proposition("etre_proche_de_porte", false);
		Proposition etre_proche_de_meuble_final = new Proposition("etre_proche_de_meuble_final", false);

		Proposition porte_est_ferme = new Proposition("porte_est_ferme", true);
		Proposition porte_est_ouverte = new Proposition("porte_est_ouverte", false);
		Proposition porte_est_verouille = new Proposition("porte_est_verouille", true);
		Proposition porte_est_deverouille = new Proposition("porte_est_deverouille", false);
		Proposition meuble_non_assemble = new Proposition("meuble_non_assemble", true);
		Proposition meuble_est_assemble = new Proposition("meuble_est_assemble", false);

		//Proposition A_Cherche_Clef = new Proposition("A_Cherche_Clef", false);
		//Proposition A_Cherche_partie1 = new Proposition("A_Cherche_partie1", false);
		//Proposition A_Cherche_partie2 = new Proposition("A_Cherche_partie2", false);

		//================================================
		//===prendre - poser
		//================================================
		Module prendre_partie_1 = new Module(0,"prendre_partie_1");
		prendre_partie_1.addCondition(partie1_du_meuble_quelque_part);
		prendre_partie_1.addCondition(etre_proche_de_partie1);
		prendre_partie_1.addAjoutes(partie1_du_meuble_dans_la_main);
		prendre_partie_1.addCondition(porte_est_ouverte);
		prendre_partie_1.addDetruits(partie1_du_meuble_quelque_part);
		prendre_partie_1.addDetruits(etre_proche_de_partie1);

		Module prendre_partie_2 = new Module(0,"prendre_partie_2");
		prendre_partie_2.addCondition(partie2_du_meuble_quelque_part);
		prendre_partie_2.addCondition(etre_proche_de_partie2);
		prendre_partie_2.addCondition(porte_est_ouverte);
		prendre_partie_2.addAjoutes(partie2_du_meuble_dans_la_main);
		prendre_partie_2.addDetruits(partie2_du_meuble_quelque_part);
		prendre_partie_2.addDetruits(etre_proche_de_partie2);

		Module prendre_outil = new Module(0,"prendre_outil");
		prendre_outil.addCondition(outil_quelque_part);
		prendre_outil.addCondition(etre_proche_de_outil);
		prendre_outil.addAjoutes(outil_dans_la_main);
		prendre_outil.addDetruits(outil_quelque_part);
		prendre_outil.addDetruits(etre_proche_de_outil);

		Module prendre_clef = new Module(0,"prendre_clef");
		prendre_clef.addCondition(clef_quelque_part);
		prendre_clef.addCondition(etre_proche_de_la_clef);
		prendre_clef.addAjoutes(clef_dans_la_main);
		prendre_clef.addDetruits(clef_quelque_part);
		prendre_clef.addDetruits(etre_proche_de_la_clef);

		Module poser_partie_1 = new Module(0,"poser_partie_1");
		poser_partie_1.addCondition(partie1_du_meuble_dans_la_main);
		poser_partie_1.addAjoutes(partie1_du_meuble_quelque_part);
		poser_partie_1.addAjoutes(etre_proche_de_partie1);
		poser_partie_1.addDetruits(partie1_du_meuble_dans_la_main);

		Module poser_partie_2 = new Module(0,"poser_partie_2");
		poser_partie_2.addCondition(partie2_du_meuble_dans_la_main);
		poser_partie_2.addAjoutes(partie2_du_meuble_quelque_part);
		poser_partie_2.addAjoutes(etre_proche_de_partie2);
		poser_partie_2.addDetruits(partie2_du_meuble_dans_la_main);

		Module poser_outil = new Module(0,"poser_outil");
		poser_outil.addCondition(outil_dans_la_main);
		poser_outil.addAjoutes(outil_quelque_part);
		poser_outil.addAjoutes(etre_proche_de_outil);
		poser_outil.addDetruits(outil_dans_la_main);

		Module poser_clef = new Module(0,"poser_clef");
		poser_clef.addCondition(clef_dans_la_main);
		poser_clef.addAjoutes(clef_quelque_part);
		poser_clef.addAjoutes(etre_proche_de_la_clef);
		poser_clef.addDetruits(clef_dans_la_main);
		//================================================
		//===porte
		//================================================
		Module deverouiller_porte = new Module(0, "deverouiller_porte");
		deverouiller_porte.addCondition(clef_dans_la_main);
		deverouiller_porte.addCondition(etre_proche_de_porte);
		deverouiller_porte.addCondition(porte_est_verouille);
		deverouiller_porte.addAjoutes(porte_est_deverouille);
		deverouiller_porte.addDetruits(porte_est_verouille);

		Module verouiller_porte = new Module(0, "verouiller_porte");
		verouiller_porte.addCondition(clef_dans_la_main);
		verouiller_porte.addCondition(etre_proche_de_porte);
		verouiller_porte.addCondition(porte_est_deverouille);
		verouiller_porte.addAjoutes(porte_est_verouille);
		verouiller_porte.addDetruits(porte_est_deverouille);

		Module ouvrir_porte = new Module(0, "ouvrir_porte");
		ouvrir_porte.addCondition(etre_proche_de_porte);
		ouvrir_porte.addCondition(porte_est_deverouille);
		ouvrir_porte.addCondition(porte_est_ferme);
		ouvrir_porte.addAjoutes(porte_est_ouverte);
		ouvrir_porte.addAjoutes(partie1_du_meuble_quelque_part);
		ouvrir_porte.addAjoutes(partie2_du_meuble_quelque_part);
		ouvrir_porte.addAjoutes(porte_est_ouverte);
		ouvrir_porte.addDetruits(porte_est_ferme);

		Module fermer_porte = new Module(0, "fermer_porte");
		fermer_porte.addCondition(etre_proche_de_porte);
		fermer_porte.addCondition(porte_est_deverouille);
		fermer_porte.addCondition(porte_est_ouverte);
		fermer_porte.addAjoutes(porte_est_ferme);
		fermer_porte.addDetruits(porte_est_ouverte);
		fermer_porte.addDetruits(partie1_du_meuble_quelque_part);
		fermer_porte.addDetruits(partie2_du_meuble_quelque_part);
		//================================================
		//===chercher
		//================================================
		Module chercher_cle = new Module(0, "chercher_cle");
		chercher_cle.addCondition(clef_quelque_part);
		chercher_cle.addAjoutes(etre_proche_de_la_clef);

		Module chercher_outil = new Module(0, "chercher_outil");
		chercher_outil.addCondition(outil_quelque_part);
		chercher_outil.addAjoutes(etre_proche_de_outil);

		Module chercher_partie_1 = new Module(0, "chercher_partie_1");
		chercher_partie_1.addCondition(partie1_du_meuble_quelque_part);
		chercher_partie_1.addAjoutes(etre_proche_de_partie1);

		Module chercher_partie_2 = new Module(0, "chercher_partie_2");
		chercher_partie_2.addCondition(partie2_du_meuble_quelque_part);
		chercher_partie_2.addAjoutes(etre_proche_de_partie2);

		Module chercher_porte = new Module(0, "chercher_porte");
		chercher_porte.addCondition(porte_quelque_part);
		chercher_porte.addAjoutes(etre_proche_de_porte);
		//================================================
		//===assembler - desassembler
		//================================================
		Module assembler_meuble_1 = new Module(0, "assembler_meuble_1");
		assembler_meuble_1.addCondition(etre_proche_de_partie1);
		assembler_meuble_1.addCondition(meuble_non_assemble);
		assembler_meuble_1.addCondition(partie2_du_meuble_dans_la_main);
		assembler_meuble_1.addCondition(outil_dans_la_main);
		assembler_meuble_1.addAjoutes(meuble_final_quelque_part);
		assembler_meuble_1.addAjoutes(meuble_est_assemble);
		assembler_meuble_1.addDetruits(partie2_du_meuble_dans_la_main);
		assembler_meuble_1.addDetruits(etre_proche_de_partie1);

		Module assembler_meuble_2 = new Module(0, "assembler_meuble_2");
		assembler_meuble_2.addCondition(etre_proche_de_partie2);
		assembler_meuble_2.addCondition(meuble_non_assemble);
		assembler_meuble_2.addCondition(partie1_du_meuble_dans_la_main);
		assembler_meuble_2.addCondition(outil_dans_la_main);
		assembler_meuble_2.addAjoutes(meuble_final_quelque_part);
		assembler_meuble_2.addAjoutes(meuble_est_assemble);
		assembler_meuble_2.addDetruits(partie1_du_meuble_dans_la_main);
		assembler_meuble_2.addDetruits(etre_proche_de_partie2);

		Module desassembler_meuble_final = new Module(0, "desassembler_meuble_final");
		desassembler_meuble_final.addCondition(etre_proche_de_meuble_final);
		desassembler_meuble_final.addCondition(meuble_est_assemble);
		desassembler_meuble_final.addCondition(outil_dans_la_main);
		desassembler_meuble_final.addAjoutes(partie1_du_meuble_quelque_part);
		desassembler_meuble_final.addAjoutes(partie2_du_meuble_quelque_part);
		desassembler_meuble_final.addAjoutes(meuble_non_assemble);
		desassembler_meuble_final.addAjoutes(etre_proche_de_partie1);
		desassembler_meuble_final.addAjoutes(etre_proche_de_partie2);
		desassembler_meuble_final.addDetruits(etre_proche_de_meuble_final);

		unAgent.addPropositionInitiale(clef_quelque_part);
		unAgent.addPropositionInitiale(outil_quelque_part);
		unAgent.addPropositionInitiale(partie1_du_meuble_quelque_part);
		unAgent.addPropositionInitiale(partie1_du_meuble_quelque_part);
		unAgent.addPropositionInitiale(porte_quelque_part);

		unAgent.addBut(meuble_est_assemble);
		unAgent.addBut(meuble_final_quelque_part);

	}


	public static void main(String[] args) {
		if(Integer.parseInt(args[0]) == 1)
			scenario(Float.parseFloat(args[1]),Float.parseFloat(args[2]),Float.parseFloat(args[3]),Float.parseFloat(args[4]),Float.parseFloat(args[5]));
		else Maes(Float.parseFloat(args[1]),Float.parseFloat(args[2]),Float.parseFloat(args[3]),Float.parseFloat(args[4]),Float.parseFloat(args[5]));
		while(!unAgent.isDone() ) {
			//while (cpt < 50) {
			System.out.println("\n\nTIME : " + e.time);
			unAgent.printState();
			//Calcul des activations des modules
			e.executable();
			// Diffusion d'énergie d'activation

			e.updateEnergy(true);

			e.afficherEtatActivation();

			//Si executable, Execution d'un module
			e.execute();
			e.updateTheta();
			e.time++;
		}
		System.out.println("\nLes missions ont été terminés en " + e.time );
		sc.exportToExcel("test2.txt");
		sc.exportToPng("Seuils5");
		sc.exportConditionEtat("Proposition");

	}

}
