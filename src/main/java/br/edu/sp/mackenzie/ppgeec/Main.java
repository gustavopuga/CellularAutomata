package br.edu.sp.mackenzie.ppgeec;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import br.edu.sp.mackenzie.ppgeec.ca.CellularAutomata;
import br.edu.sp.mackenzie.ppgeec.ca.neighborhood.MooreNeighborhood;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.PopulationState;

public class Main {

    public static XYChart getChart(Map<CellularAutomataState, List<Double>> map, int generations) {

	// Create Chart
	XYChart chart = new XYChartBuilder().width(800).height(600).title("População Sarampo").xAxisTitle("Tempo")
		.yAxisTitle("População").theme(ChartTheme.Matlab).build();

	// Customize Chart
	chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
	chart.getStyler().setLegendVisible(true);
	chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

	double[] xTime = IntStream.rangeClosed(1, generations).asDoubleStream().toArray();

	map.keySet().stream().map(key -> map.get(key));
	for (CellularAutomataState state : map.keySet()) {

	    double[] y = map.get(state).stream().mapToDouble(i -> i / Constants.NORMALIZE).toArray();
	    chart.addSeries(state.getDescription(), xTime, y);

	}

	return chart;
    }

    public static void main(String[] args) throws IOException {

	CellularAutomata ca = new CellularAutomata(Constants.COLUMNS, Constants.ROWS, new MooreNeighborhood(1),
		PopulationState.class);
	ca.nextGeneration(Constants.TIME);

	Map<CellularAutomataState, List<Double>> map = ca.generateGenerationsStateMap();
	XYChart chart = getChart(map, Constants.TIME);
	new SwingWrapper<XYChart>(chart).displayChart();

	/* BitmapEncoder.saveBitmap(chart, "./Sarampo_Chart", BitmapFormat.PNG); */

	/*
	 * BitmapEncoder.saveBitmapWithDPI(chart, "./Sarampo_Chart_300_DPI",
	 * BitmapFormat.PNG, 300);
	 */

    }

}
