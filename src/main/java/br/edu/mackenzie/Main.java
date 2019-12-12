package br.edu.mackenzie;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.ChartColor;

public class Main {

    public static XYChart getChart(Map<PopulationState, List<Integer>> map) {

	// Create Chart	
	XYChart chart = new XYChartBuilder().width(800).height(600).title("População Sarampo").xAxisTitle("Tempo")
		.yAxisTitle("População").build();

	// Customize Chart
	chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
	chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
	chart.getStyler().setYAxisDecimalPattern("#,###.##");
	chart.getStyler().setPlotMargin(0);
	chart.getStyler().setPlotContentSize(.95);
	
	int[] ySusceptible = null;
	int[] yInfectious = null;
	int[] yRecovery = null;
	int[] xAges = IntStream.rangeClosed(1, 200).toArray();

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
	
	String susceptibleDescription = PopulationState.SUSCEPTIBLE.getDescription();
	XYSeries susceptibleSeries = chart.addSeries(susceptibleDescription, xAges, ySusceptible);
	susceptibleSeries.setShowInLegend(true);
	susceptibleSeries.setLabel(susceptibleDescription);
	
	String infectiousDescription = PopulationState.INFECTIOUS.getDescription();
	XYSeries infectiousSeries = chart.addSeries(infectiousDescription, xAges, yInfectious);
	infectiousSeries.setShowInLegend(true);
	infectiousSeries.setLabel(infectiousDescription);
	
	String recoveryDescription = PopulationState.RECOVERY.getDescription();
	XYSeries recoverySeries = chart.addSeries(recoveryDescription, xAges, yRecovery);
	recoverySeries.setShowInLegend(true);
	recoverySeries.setLabel(recoveryDescription);
	
	return chart;
    }

    public static void main(String[] args) {

	SarampoCellularAutomata ca = new SarampoCellularAutomata(200, 200);
	ca.nextGeneration(200);

	Map<PopulationState, List<Integer>> map = ca.generateMap();
	XYChart chart = getChart(map);
	new SwingWrapper<XYChart>(chart).displayChart();
    }

}
