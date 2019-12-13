package br.edu.mackenzie;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

public class Main {

    public static XYChart getChart(Map<PopulationState, List<Integer>> map, int generations) {

	// Create Chart
	XYChart chart = new XYChartBuilder().width(800).height(600).title("População Sarampo").xAxisTitle("Tempo")
		.yAxisTitle("População").theme(ChartTheme.Matlab).build();

	// Customize Chart
	chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
	chart.getStyler().setLegendVisible(true);
	chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

	int[] ySusceptible = null;
	int[] yInfectious = null;
	int[] yRecovery = null;
	int[] xTime = IntStream.rangeClosed(1, generations).toArray();

	for (PopulationState state : map.keySet()) {
	    switch (state) {

	    case SUSCEPTIBLE:
		ySusceptible = map.get(state).stream().mapToInt(i -> (int) i).toArray();
		break;

	    case INFECTIOUS:
		yInfectious = map.get(state).stream().mapToInt(i -> (int) i).toArray();
		break;

	    case RECOVERY:
		yRecovery = map.get(state).stream().mapToInt(i -> (int) i).toArray();
		break;

	    }

	}

	Color blue = new Color(52, 152, 219);
	Color red = new Color(231, 76, 60);
	Color green = new Color(46, 204, 113);

	chart.addSeries(PopulationState.SUSCEPTIBLE.getDescription(), xTime, ySusceptible).setMarkerColor(blue).setLineColor(blue);
	chart.addSeries(PopulationState.INFECTIOUS.getDescription(), xTime, yInfectious).setMarkerColor(red).setLineColor(red);
	chart.addSeries(PopulationState.RECOVERY.getDescription(), xTime, yRecovery).setMarkerColor(green).setLineColor(green);

	return chart;
    }

    public static void main(String[] args) throws IOException {

	int time = 200;
	SarampoCellularAutomata ca = new SarampoCellularAutomata(200, 200);
	ca.nextGeneration(time);

	Map<PopulationState, List<Integer>> map = ca.generateMap();
	XYChart chart = getChart(map, time);
	new SwingWrapper<XYChart>(chart).displayChart();
	
	
	/* BitmapEncoder.saveBitmap(chart, "./Sarampo_Chart", BitmapFormat.PNG); */
	/*
	 * BitmapEncoder.saveBitmapWithDPI(chart, "./Sarampo_Chart_300_DPI",
	 * BitmapFormat.PNG, 300);
	 */
	 
    }

}
