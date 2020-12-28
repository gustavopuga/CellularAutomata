package br.edu.sp.mackenzie.ppgeec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;

import br.edu.sp.mackenzie.ppgeec.ca.CellularAutomata;
import br.edu.sp.mackenzie.ppgeec.ca.neighborhood.MooreNeighborhood;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiaseState;
import br.edu.sp.mackenzie.ppgeec.edo.EDOGraphGenerator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.AlfaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.BetaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.EpsilonCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.GammaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.VariablesCalculator;

public class Simulation {

	public static void execute(SimulationCase simulationCase) throws IOException {

		Instant start = Instant.now();
		TwoDiaseState.initGraph(simulationCase);
		CellularAutomata ca = new CellularAutomata(Constants.COLUMNS, Constants.ROWS,
				new MooreNeighborhood(Constants.RADIUS), TwoDiaseState.class);
		ca.nextGeneration(Constants.TIME);
		Instant end = Instant.now();
		Duration between = Duration.between(start, end);

		List<CellularAutomataState[][]> generations = ca.getGenerations();
		int lastGenerations = 50;

		double a = AlfaCalculator.getA(generations, lastGenerations);
		double alfa = AlfaCalculator.getAlfa(generations, lastGenerations);
		double alfa1 = AlfaCalculator.getAlfa1(generations, lastGenerations);
		double alfa2 = AlfaCalculator.getAlfa2(generations, lastGenerations);
		double y = AlfaCalculator.getY(generations, lastGenerations);

		double b1 = BetaCalculator.getB1(generations, lastGenerations);
		double b2 = BetaCalculator.getB2(generations, lastGenerations);
		double beta = BetaCalculator.getBeta(generations, lastGenerations);
		double beta1 = BetaCalculator.getBeta1(generations, lastGenerations);
		double beta2 = BetaCalculator.getBeta2(generations, lastGenerations);

		double c1 = GammaCalculator.getC1(generations, lastGenerations);
		double c2 = GammaCalculator.getC2(generations, lastGenerations);
		double gamma = GammaCalculator.getGamma(generations, lastGenerations);
		double gamma1 = GammaCalculator.getGamma1(generations, lastGenerations);
		double gamma2 = GammaCalculator.getGamma2(generations, lastGenerations);

		double e1 = EpsilonCalculator.getE1(generations, lastGenerations);
		double e2 = EpsilonCalculator.getE2(generations, lastGenerations);
		double e = EpsilonCalculator.getE(generations, lastGenerations);
		double epsilon = EpsilonCalculator.getEpsilon(generations, lastGenerations);
		double upsilon = EpsilonCalculator.getUpsilon(generations, lastGenerations);


		HashSet<CellularAutomataState> excludeStates1 = new HashSet<>();
		excludeStates1.add(TwoDiaseState.R);
		excludeStates1.add(TwoDiaseState.R1);
		excludeStates1.add(TwoDiaseState.R2);
		excludeStates1.add(TwoDiaseState.S);
		excludeStates1.add(TwoDiaseState.V);

		HashSet<CellularAutomataState> excludeStates2 = new HashSet<>();
		excludeStates2.add(TwoDiaseState.I);
		excludeStates2.add(TwoDiaseState.I1);
		excludeStates2.add(TwoDiaseState.I12);
		excludeStates2.add(TwoDiaseState.I21);
		excludeStates2.add(TwoDiaseState.I2);

		File dir = new File(simulationCase.getPath());
		dir.mkdirs();
		String edoValues = new EDOGraphGenerator(alfa1, alfa2, alfa, a, y, beta1, beta2, beta, b1, b2, c1, c2, gamma1, gamma2, gamma, e,
				e1, e2, epsilon, upsilon).generateChart(simulationCase.getPath());

		writeSimulationValues(simulationCase, generations, lastGenerations, a, alfa, alfa1, alfa2, y, b1, b2, beta, beta1, beta2, c1,
				c2, gamma, gamma1, gamma2, e1, e2, e, epsilon, upsilon, edoValues, between);

	}

	private static void writeSimulationValues(SimulationCase simulationCase, List<CellularAutomataState[][]> generations, int lastGenerations, double a,
			double alfa, double alfa1, double alfa2, double y, double b1, double b2, double beta, double beta1,
			double beta2, double c1, double c2, double gamma, double gamma1, double gamma2, double e1, double e2,
			double e, double epsilon, double upsilon, String edoValues, Duration between) {

		File dir = new File(simulationCase.getPath());
		File file = new File(dir, "dados_simulacao.txt");
		try (FileWriter writer = new FileWriter(file); BufferedWriter bw = new BufferedWriter(writer)) {

			bw.write(" ======= " + simulationCase.getName() + " ======= \n");
			bw.write("\n\n ======= Probabilidades ======= \n");
			bw.write("\nB1 = " + simulationCase.getB1());
			bw.write("\nB2 = " + simulationCase.getB2());
			bw.write("\nBETA = " + simulationCase.getBeta());
			bw.write("\nBETA1 = " + simulationCase.getBeta1());
			bw.write("\nBETA2 = " + simulationCase.getBeta2());
			bw.write("\nC1 = " + simulationCase.getC1());
			bw.write("\nC2 = " + simulationCase.getC2());
			bw.write("\nGAMMA = " + simulationCase.getGamma());
			bw.write("\nGAMMA1 = " + simulationCase.getGamma1());
			bw.write("\nGAMMA2 = " + simulationCase.getGamma2());
			bw.write("\nE1 = " + simulationCase.getE1());
			bw.write("\nE2 = " + simulationCase.getE2());
			bw.write("\nE = " + simulationCase.getE());
			bw.write("\nEPSILON = " + simulationCase.getEpsilon());
			bw.write("\nV = " + simulationCase.getV());

			bw.write("\n\n ======= Metrica  AC ======= \n");
			bw.write("\nTempo de simulação: " + DurationFormatUtils.formatDuration(between.toMillis(), "HH:mm:sss"));
			bw.write("\n\n ======= As, ALFAS e Y ======= \n");
			bw.write("\nA = " + a);
			bw.write("\nALFA = " + alfa);
			bw.write("\nALFA1 = " + alfa1);
			bw.write("\nALFA2 = " + alfa2);
			bw.write("\nY = " + y);

			bw.write("\n\n ======= Bs e BETAS ======= \n");
			bw.write("\nB1 = " + b1);
			bw.write("\nB2 = " + b2);
			bw.write("\nBETA = " + beta);
			bw.write("\nBETA1 = " + beta1);
			bw.write("\nBETA2 = " + beta2);

			bw.write("\n\n ======= Cs e GAMMAS ======= \n");
			bw.write("\nC1 = " + c1);
			bw.write("\nC2 = " + c2);
			bw.write("\nGAMMA = " + gamma);
			bw.write("\nGAMMA1 = " + gamma1);
			bw.write("\nGAMMA2 = " + gamma2);

			bw.write("\n\n ======= Es, EPSILON e V ======= \n");
			bw.write("\nE1 = " + e1);
			bw.write("\nE2 = " + e2);
			bw.write("\nE = " + e);
			bw.write("\nEPSILON = " + epsilon);
			bw.write("\nV = " + upsilon);

			bw.write("\n\n ======= VARIAVEIS AC ======= \n");
			bw.write("\nS = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.S));
			bw.write("\nI1 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.I1));
			bw.write("\nI2 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.I2));
			bw.write("\nI12 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.I12));
			bw.write("\nI21 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.I21));
			bw.write("\nI = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.I));
			bw.write("\nR1 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.R1));
			bw.write("\nR2 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.R2));
			bw.write("\nR = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.R));
			bw.write("\nV = " + VariablesCalculator.average(generations, lastGenerations, TwoDiaseState.V));

			bw.write("\n" + edoValues);
			
		} catch (IOException exception) {
			System.err.format("IOException: %s%n", exception);
		}
	}

}
