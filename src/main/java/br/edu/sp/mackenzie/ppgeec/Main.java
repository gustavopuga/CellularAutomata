package br.edu.sp.mackenzie.ppgeec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import br.edu.sp.mackenzie.ppgeec.ca.CellularAutomata;
import br.edu.sp.mackenzie.ppgeec.ca.neighborhood.MooreNeighborhood;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;
import br.edu.sp.mackenzie.ppgeec.csv.CSVChart1;
import br.edu.sp.mackenzie.ppgeec.csv.CSVUtils;

public class Main {

	public static XYChart getChart(Map<CellularAutomataState, List<Double>> map, int generations, Set<CellularAutomataState> excludeStates) {

		// Create Chart
		XYChart chart = new XYChartBuilder().width(800).height(600).title("Evolução da população no tempo").xAxisTitle("Tempo")
				.yAxisTitle("População").theme(ChartTheme.Matlab).build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

		double[] xTime = IntStream.rangeClosed(1, generations).asDoubleStream().toArray();

		map.keySet().stream().map(key -> map.get(key));
		for (CellularAutomataState state : map.keySet()) {
			if (!excludeStates.contains(state)) {				
				double[] y = map.get(state).stream().mapToDouble(i -> i / Constants.NORMALIZE).toArray();
				chart.addSeries(state.getDescription(), xTime, y);
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

		CellularAutomata ca = new CellularAutomata(Constants.COLUMNS, Constants.ROWS, new MooreNeighborhood(1),
				TwoDiasesState.class);
		ca.nextGeneration(Constants.TIME);

		Map<CellularAutomataState, List<Double>> map = ca.generateGenerationsStateMap();
		
		List<CellularAutomataState[][]> generations = ca.getGenerations();
		int lastGenerations = 20;
		toCSV(map);
		System.out.println("A1 = " + AlfaCalculator.getA1(generations, lastGenerations));
		System.out.println("A2 = " + AlfaCalculator.getA2(generations, lastGenerations));
		System.out.println("Alfa = " + AlfaCalculator.getAlfa(generations, lastGenerations));
		System.out.println("Alfa1 = " + AlfaCalculator.getAlfa1(generations, lastGenerations));
		System.out.println("Alfa2 = " + AlfaCalculator.getAlfa2(generations, lastGenerations));

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
		
		new SwingWrapper<XYChart>(chart1).displayChart();
		new SwingWrapper<XYChart>(chart2).displayChart();

		BitmapEncoder.saveBitmap(chart1, "./grafico1", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(chart2, "./grafico2", BitmapFormat.PNG);

		/*
		 * BitmapEncoder.saveBitmapWithDPI(chart, "./Sarampo_Chart_300_DPI",
		 * BitmapFormat.PNG, 300);
		 */

	}

}
