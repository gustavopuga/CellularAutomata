package br.edu.sp.mackenzie.ppgeec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import br.edu.sp.mackenzie.ppgeec.ca.CellularAutomata;
import br.edu.sp.mackenzie.ppgeec.ca.neighborhood.MooreNeighborhood;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;
import br.edu.sp.mackenzie.ppgeec.edo.EDOGraphGenerator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.AlfaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.BetaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.EpsilonCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.GammaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.VariablesCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.YCalculator;

public class SimulationCase {

	private double infectados1;
	private double infectados2;
	private double mortosDoenca1;
	private double mortosDoenca2;
	private double taxaCura;
	private double taxaMorte;

	private String name;

	// K
	private double k1;
	private double k2;
	private double k3;
	private double k4;
	private double k5;

	// cura
	private double beta1;
	private double beta2;
	private double beta;

	private double b1;
	private double b2;

	// morte doenca
	private double c1;
	private double c2;

	private double gamma1;
	private double gamma2;

	private double gamma;

	// morte outras causas
	private double e;
	private double e1;
	private double e2;

	private double epsilon;

	// VACINA
	private double v;

	public double getK1() {
		return k1;
	}

	public void setK1(double k1) {
		this.k1 = k1;
	}

	public double getK2() {
		return k2;
	}

	public void setK2(double k2) {
		this.k2 = k2;
	}

	public double getK3() {
		return k3;
	}

	public void setK3(double k3) {
		this.k3 = k3;
	}

	public double getK4() {
		return k4;
	}

	public void setK4(double k4) {
		this.k4 = k4;
	}

	public double getK5() {
		return k5;
	}

	public void setK5(double k5) {
		this.k5 = k5;
	}

	public double getBeta1() {
		return beta1;
	}

	public void setBeta1(double beta1) {
		this.beta1 = beta1;
	}

	public double getBeta2() {
		return beta2;
	}

	public void setBeta2(double beta2) {
		this.beta2 = beta2;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public double getB1() {
		return b1;
	}

	public void setB1(double b1) {
		this.b1 = b1;
	}

	public double getB2() {
		return b2;
	}

	public void setB2(double b2) {
		this.b2 = b2;
	}

	public double getC1() {
		return c1;
	}

	public void setC1(double c1) {
		this.c1 = c1;
	}

	public double getC2() {
		return c2;
	}

	public void setC2(double c2) {
		this.c2 = c2;
	}

	public double getGamma1() {
		return gamma1;
	}

	public void setGamma1(double gamma1) {
		this.gamma1 = gamma1;
	}

	public double getGamma2() {
		return gamma2;
	}

	public void setGamma2(double gamma2) {
		this.gamma2 = gamma2;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getE() {
		return e;
	}

	public void setE(double e) {
		this.e = e;
	}

	public double getE1() {
		return e1;
	}

	public void setE1(double e1) {
		this.e1 = e1;
	}

	public double getE2() {
		return e2;
	}

	public void setE2(double e2) {
		this.e2 = e2;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		StringBuilder builderPath = new StringBuilder("taxa_vacinacao_");
		builderPath.append(this.v);
		builderPath.append("/");
		builderPath.append(StringUtils.stripAccents(name.toLowerCase().replaceAll("\\s+", "_")));
		return builderPath.toString();
	}

	public double getInfectados1() {
		return infectados1;
	}

	public double getInfectados2() {
		return infectados2;
	}

	public double getMortosDoenca1() {
		return mortosDoenca1;
	}

	public double getMortosDoenca2() {
		return mortosDoenca2;
	}

	public double getTaxaCura() {
		return taxaCura;
	}

	public double getTaxaMorte() {
		return taxaMorte;
	}

	public void execute() throws IOException {

		Instant start = Instant.now();
		TwoDiasesState.initGraph(this);

		CellularAutomata ca = new CellularAutomata(Constants.COLUMNS, Constants.ROWS,
				new MooreNeighborhood(Constants.RADIUS), TwoDiasesState.class);
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
		excludeStates1.add(TwoDiasesState.R);
		excludeStates1.add(TwoDiasesState.R1);
		excludeStates1.add(TwoDiasesState.R2);
		excludeStates1.add(TwoDiasesState.S);
		excludeStates1.add(TwoDiasesState.V);

		HashSet<CellularAutomataState> excludeStates2 = new HashSet<>();
		excludeStates2.add(TwoDiasesState.I);
		excludeStates2.add(TwoDiasesState.I1);
		excludeStates2.add(TwoDiasesState.I12);
		excludeStates2.add(TwoDiasesState.I21);
		excludeStates2.add(TwoDiasesState.I2);

		File dir = new File(getPath());
		dir.mkdirs();
		String edoValues = new EDOGraphGenerator(alfa1, alfa2, alfa, a, y, beta1, beta2, beta, b1, b2, c1, c2, gamma1,
				gamma2, gamma, e, e1, e2, epsilon, upsilon).generateChart(getPath());

		writeSimulationValues(generations, lastGenerations, a, alfa, alfa1, alfa2, y, b1, b2, beta, beta1, beta2, c1,
				c2, gamma, gamma1, gamma2, e1, e2, e, epsilon, upsilon, edoValues, between);

		infectados1 = YCalculator.getInfectados1(generations, lastGenerations);
		infectados2 = YCalculator.getInfectados2(generations, lastGenerations);
		mortosDoenca1 = YCalculator.getMorteDoenca1(generations, lastGenerations);
		mortosDoenca2 = YCalculator.getMorteDoenca2(generations, lastGenerations);
		taxaCura = getBeta() / getBeta2();
		taxaMorte = getGamma2() / getGamma();
		
		System.out.println(" ================= ");
		System.out.println("infectados1 " + infectados1);
		System.out.println("infectados2 " + infectados2);
		System.out.println("mortosDoenca1 " + mortosDoenca1);
		System.out.println("mortosDoenca2 " + mortosDoenca2);
		System.out.println("taxaCura " + taxaCura);
		System.out.println("taxaMorte " + taxaMorte);
		System.out.println(" ================= ");

	}

	private void writeSimulationValues(List<CellularAutomataState[][]> generations, int lastGenerations, double a,
			double alfa, double alfa1, double alfa2, double y, double b1, double b2, double beta, double beta1,
			double beta2, double c1, double c2, double gamma, double gamma1, double gamma2, double e1, double e2,
			double e, double epsilon, double upsilon, String edoValues, Duration between) {

		File dir = new File(getPath());
		File file = new File(dir, "dados_simulacao.txt");
		try (FileWriter writer = new FileWriter(file); BufferedWriter bw = new BufferedWriter(writer)) {

			bw.write(" ======= " + getName() + " ======= \n");
			bw.write("\n\n ======= Probabilidades ======= \n");
			bw.write("\nB1 = " + getB1());
			bw.write("\nB2 = " + getB2());
			bw.write("\nBETA = " + getBeta());
			bw.write("\nBETA1 = " + getBeta1());
			bw.write("\nBETA2 = " + getBeta2());
			bw.write("\nC1 = " + getC1());
			bw.write("\nC2 = " + getC2());
			bw.write("\nGAMMA = " + getGamma());
			bw.write("\nGAMMA1 = " + getGamma1());
			bw.write("\nGAMMA2 = " + getGamma2());
			bw.write("\nE1 = " + getE1());
			bw.write("\nE2 = " + getE2());
			bw.write("\nE = " + getE());
			bw.write("\nEPSILON = " + getEpsilon());
			bw.write("\nV = " + getV());

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
			bw.write("\nS = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.S));
			bw.write("\nI1 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I1));
			bw.write("\nI2 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I2));
			bw.write("\nI12 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I12));
			bw.write("\nI21 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I21));
			bw.write("\nI = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I));
			bw.write("\nR1 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.R1));
			bw.write("\nR2 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.R2));
			bw.write("\nR = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.R));
			bw.write("\nV = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.V));

			bw.write("\n" + edoValues);

		} catch (IOException exception) {
			System.err.format("IOException: %s%n", exception);
		}
	}
}
