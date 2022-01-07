package vue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import modele.Environnement;
import modele.Module;
import modele.Proposition;

public class StatsCreator {

	String CHEMIN = "./graphs/";
	String FILE = CHEMIN + "output";
	ArrayList<Float> seuil;

	ArrayList<Module> listeModulesSurGraph;


	public StatsCreator() {
		super();
		this.listeModulesSurGraph = new ArrayList<Module>();
	}

	public StatsCreator(ArrayList<Module> lesModules, ArrayList<Float> Seuil) {
		super();
		this.listeModulesSurGraph = lesModules;
		this.seuil = Seuil;
	}


	public void addModule(Module unModule) {
		this.listeModulesSurGraph.add(unModule);
	}

	public void exportToExcel(String nomDuFichier){
		File file = new File(CHEMIN + nomDuFichier);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());

			String result = "";

			for(Module unModule : this.listeModulesSurGraph) {

				result = unModule + " : ";
				for(Float uneValeure : unModule.getOldStats()) {
					result = result + uneValeure + ", "; 
				}
				fw.write(result + "\n");
			}
			result = "Seuil : ";
			for(Float uneValeure : seuil) {
				result = result + uneValeure + ", "; 
			}
			fw.write(result + "\n");

			fw.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportToPng(String nomDuFichier) {
		final XYChart chart = new XYChartBuilder().width(600).height(400).title("Seuils des modules").xAxisTitle("L'itération").yAxisTitle("Le seuil").build();
		for(Module unModule : this.listeModulesSurGraph) {
			chart.addSeries(unModule.toString(), unModule.getOldStats());
		}
		chart.addSeries("seuil", this.seuil);

		try {
			BitmapEncoder.saveBitmapWithDPI(chart, CHEMIN + nomDuFichier, BitmapFormat.PNG, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void exportConditionEtat(String nomDuFichier) {
		final XYChart chart = new XYChartBuilder().width(600).height(400).title("Seuils des modules").xAxisTitle("L'itération").yAxisTitle("Le seuil").build();
		for(Proposition uneProposition : Environnement.listeDesProposition) {
			chart.addSeries(uneProposition.toString(), uneProposition.getEtatStat());
		}
		try {
			BitmapEncoder.saveBitmapWithDPI(chart, CHEMIN + nomDuFichier, BitmapFormat.PNG, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}