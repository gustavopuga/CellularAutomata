package br.edu.sp.mackenzie.ppgeec;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import br.edu.sp.mackenzie.ppgeec.ca.CellularAutomata;
import br.edu.sp.mackenzie.ppgeec.ca.neighborhood.MooreNeighborhood;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;
import br.edu.sp.mackenzie.ppgeec.csv.CSVChart1;
import br.edu.sp.mackenzie.ppgeec.csv.CSVUtils;
import br.edu.sp.mackenzie.ppgeec.edo.EDOGraphGenerator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.AlfaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.BetaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.EpslonCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.GammaCalculator;
import br.edu.sp.mackenzie.ppgeec.edo.calculator.VariablesCalculator;

public class Main {

	public static XYChart getChart(Map<CellularAutomataState, List<Double>> map, int generations, Set<CellularAutomataState> excludeStates) {

		// Create Chart
		XYChart chart = new XYChartBuilder().width(800).height(600).title("Evolução da população no tempo").xAxisTitle("Tempo")
				.yAxisTitle("População").theme(ChartTheme.GGPlot2).build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.OutsideS);
		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
//		chart.getStyler().setYAxisMax(1d);
		
		double[] xTime = IntStream.rangeClosed(1, generations).asDoubleStream().toArray();

		map.keySet().stream().map(key -> map.get(key));
		for (CellularAutomataState state : map.keySet()) {
			if (!excludeStates.contains(state)) {				
				double[] y = map.get(state).stream().mapToDouble(i -> i / Constants.NORMALIZE).toArray();
				chart.addSeries(state.getDescription(), xTime, y).setMarker(SeriesMarkers.NONE);
			}

		}

		return chart;
	}

	public static void toCSV(Map<CellularAutomataState, List<Double>> map) throws IOException {
		
		List<CSVChart1> list = new ArrayList<>();
		for (CellularAutomataState state : map.keySet()) {
			
			int count = 0;
			for (Double value : map.get(state)) {
				CSVChart1 csv = new CSVChart1();
				csv.setNome(state.getDescription());
				csv.setPopulacao(value/Constants.NORMALIZE);
				csv.setTempo(++count);	
				list.add(csv);
			}			
			
		}
		String[] header = { "name", "n", "year"};
		CSVUtils.createCSVFile(header, list, "view/grafico1");

		Map<Long, List<Double>> mapCSV = new HashMap<>();
		List<String> header2 = new ArrayList<>();
		header2.add("year");
		for (CellularAutomataState state : map.keySet()) {
			long count = 1;
			for (Double value : map.get(state)) {
				List<Double> population = mapCSV.getOrDefault(count, new ArrayList<>());
				population.add(value/Constants.NORMALIZE);
				mapCSV.put(count, population);
				count++;
			}
			header2.add(state.getDescription());
		}
		CSVUtils.createCSVFile(header2.toArray(new String[header2.size()]), mapCSV, "view/grafico2");
		
	}

	public static void main(String[] args) throws IOException {

		Instant start = Instant.now();
		CellularAutomata ca = new CellularAutomata(Constants.COLUMNS, Constants.ROWS, new MooreNeighborhood(Constants.RADIUS),
				TwoDiasesState.class);
		ca.nextGeneration(Constants.TIME);
		Instant end = Instant.now();
		Duration between = Duration.between(start, end);
		System.out.println("Tempo de simulação: " + DurationFormatUtils.formatDuration(between.toMillis(), "HH:mm:sss"));

		Map<CellularAutomataState, List<Double>> map = ca.generateGenerationsStateMap();
		
		List<CellularAutomataState[][]> generations = ca.getGenerations();
		int lastGenerations = Constants.TIME;
		toCSV(map);
		
		double a1 = AlfaCalculator.getA1(generations, lastGenerations);
		double a2 = AlfaCalculator.getA2(generations, lastGenerations);
		double alfa = AlfaCalculator.getAlfa(generations, lastGenerations);
		double alfa1 = AlfaCalculator.getAlfa1(generations, lastGenerations);
		double alfa2 = AlfaCalculator.getAlfa2(generations, lastGenerations);

		System.out.println("A1 = " + a1);
		System.out.println("A2 = " + a2);
		System.out.println("Alfa = " + alfa);
		System.out.println("Alfa1 = " + alfa1);
		System.out.println("Alfa2 = " + alfa2);

		System.out.println("B1 = " + BetaCalculator.getB1(generations, lastGenerations));
		System.out.println("B2 = " + BetaCalculator.getB2(generations, lastGenerations));
		System.out.println("Beta = " + BetaCalculator.getBeta(generations, lastGenerations));
		System.out.println("Beta1 = " + BetaCalculator.getBeta1(generations, lastGenerations));
		System.out.println("Beta2 = " + BetaCalculator.getBeta2(generations, lastGenerations));
		
		System.out.println("C1 = " + GammaCalculator.getC1(generations, lastGenerations));
		System.out.println("C2 = " + GammaCalculator.getC2(generations, lastGenerations));
		System.out.println("Gama = " + GammaCalculator.getGamma(generations, lastGenerations));
		System.out.println("Gama1 = " + GammaCalculator.getGamma1(generations, lastGenerations));
		System.out.println("Gama2 = " + GammaCalculator.getGamma2(generations, lastGenerations));

		System.out.println("E1 = " + EpslonCalculator.getE1(generations, lastGenerations));
		System.out.println("E2 = " + EpslonCalculator.getE2(generations, lastGenerations));
		System.out.println("E = " + EpslonCalculator.getE(generations, lastGenerations));
		System.out.println("Epslon = " + EpslonCalculator.getEpslon(generations, lastGenerations));
		System.out.println("V = " + EpslonCalculator.getUpslon(generations, lastGenerations));

		System.out.println("");
		System.out.println(" ================= VARIAVEIS AC ========================== ");
		System.out.println("");
		System.out.println("S = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.S));
		System.out.println("I1 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I1));
		System.out.println("I2 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I2));
		System.out.println("I12 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I12));
		System.out.println("I21 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I21));
		System.out.println("I = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.I));
		System.out.println("R1 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.R1));
		System.out.println("R2 = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.R2));
		System.out.println("R = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.R));
		System.out.println("V = " + VariablesCalculator.average(generations, lastGenerations, TwoDiasesState.V));

		
//		XYChart chart = getChart(map, Constants.TIME);
//		new SwingWrapper<XYChart>(chart).displayChart();

		
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
		
		XYChart chart1 = getChart(map, Constants.TIME, excludeStates1);
		XYChart chart2 = getChart(map, Constants.TIME, excludeStates2);
//		XYChart chart3 = getChart(map, Constants.TIME, new HashSet<CellularAutomataState>());
		
		new SwingWrapper<XYChart>(chart1).displayChart();
		new SwingWrapper<XYChart>(chart2).displayChart();
//		new SwingWrapper<XYChart>(chart3).displayChart();

		BitmapEncoder.saveBitmap(chart1, "./grafico1", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(chart2, "./grafico2", BitmapFormat.PNG);
//		BitmapEncoder.saveBitmap(chart3, "./grafico3", BitmapFormat.PNG);

		
		new EDOGraphGenerator(alfa1, alfa2, alfa, a1, a2).generateChart();
		/*
		 * BitmapEncoder.saveBitmapWithDPI(chart, "./Sarampo_Chart_300_DPI",
		 * BitmapFormat.PNG, 300);
		 */

	}

}
