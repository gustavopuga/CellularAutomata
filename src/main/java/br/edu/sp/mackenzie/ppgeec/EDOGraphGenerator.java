package br.edu.sp.mackenzie.ppgeec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import br.edu.sp.mackenzie.ppgeec.ca.state.Probabilities;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class EDOGraphGenerator {

	private final double STEP = 0.01;

	private List<Double> t = new ArrayList<>();
	private List<Double> s = new ArrayList<>();
	private List<Double> i1 = new ArrayList<>();
	private List<Double> i2 = new ArrayList<>();
	private List<Double> i12 = new ArrayList<>();
	private List<Double> i21 = new ArrayList<>();
	private List<Double> i = new ArrayList<>();
	private List<Double> r1 = new ArrayList<>();
	private List<Double> r2 = new ArrayList<>();
	private List<Double> r = new ArrayList<>();
	private List<Double> v = new ArrayList<>();

	private double ALFA1;
	private double ALFA2;
	private double ALFA;

	private double A1;
	private double A2;

	// CURA
	private double BETA1 = Probabilities.BETA1;
	private double BETA2 = Probabilities.BETA2;
	private double BETA = Probabilities.BETA;

	private double B1 = Probabilities.B1;
	private double B2 = Probabilities.B2;

	// MORTE DOENCA
	private double C1 = Probabilities.C1;
	private double C2 = Probabilities.C2;

	private double GAMMA1 = Probabilities.GAMMA1;
	private double GAMMA2 = Probabilities.GAMMA2;

	private double GAMMA = Probabilities.GAMMA;

	// MORTE OUTRAS CAUSAS
	private double E = Probabilities.E;
	private double E1 = Probabilities.E1;
	private double E2 = Probabilities.E2;

	private double EPSLON = Probabilities.EPSLON;

	// VACINA
	private double V = Probabilities.V;

	public EDOGraphGenerator(double alfa1, double alfa2, double alfa, double a1, double a2) {
		ALFA1 = alfa1;
		ALFA2 = alfa2;
		ALFA = alfa;
		A1 = a1;
		A2 = a2;
	}

	private void getEquations() {

		s.add(TwoDiasesState.S.getPercentage());
		i1.add(TwoDiasesState.I1.getPercentage());
		i2.add(TwoDiasesState.I2.getPercentage());
		i12.add(TwoDiasesState.I12.getPercentage());
		i21.add(TwoDiasesState.I21.getPercentage());
		i.add(TwoDiasesState.I.getPercentage());
		r1.add(TwoDiasesState.R1.getPercentage());
		r2.add(TwoDiasesState.R2.getPercentage());
		r.add(TwoDiasesState.R.getPercentage());
		v.add(TwoDiasesState.V.getPercentage());

		for (int j = 1; j < Constants.TIME; j++) {

			int z = j - 1;
			s.add((s.get(z) + STEP) * ((-A1 * (i1.get(z) + i12.get(z))) - (A2 * (i2.get(z) + i21.get(z) + i.get(z)))
					+ (C1 * i1.get(z)) + (C2 * i2.get(z)) + (GAMMA1 * i12.get(z)) + (GAMMA2 * i21.get(z))
					+ (GAMMA * i.get(z)) + (E * v.get(z)) + (EPSLON * r.get(z) - (V * s.get(z)))));

			i1.add((i1.get(z) + STEP) * ((A1 * s.get(z) * (i1.get(z) + i12.get(z))) - (B1 * i1.get(z)) - (C1 * i1.get(z))));

			i2.add((i2.get(z) + STEP) * ((A2 * s.get(z) * (i2.get(z) + i21.get(z) + i.get(z))) - (B2 * i2.get(z)) - (C2 * i2.get(z))));

			i12.add((i12.get(z) + STEP) * ((ALFA1 * r2.get(z) * (i1.get(z) + i12.get(z))) - (BETA1 * i12.get(z)) - (GAMMA1 * i12.get(z))));

			i21.add((i21.get(z) + STEP) * ((ALFA2 * r1.get(z) * (i2.get(z) + i21.get(z) + i.get(z))) - (BETA2 * i21.get(z)) - (GAMMA2 * i21.get(z))));

			i.add((i.get(z) + STEP) * ((ALFA * v.get(z) * (i2.get(z) + i21.get(z) + i.get(z))) - (BETA * i.get(z)) - (GAMMA * i.get(z))));

			r1.add((r1.get(z) + STEP) * ((-ALFA2 * r1.get(z) * (i2.get(z) + i21.get(z) + i.get(z))) + (B1 * i1.get(z)) - (E1 * r1.get(z))));

			r2.add((r2.get(z) + STEP) * ((-ALFA1 * r2.get(z) * (i1.get(z) + i12.get(z))) + (B2 * i2.get(z)) - (E2 * r2.get(z))));

			r.add((r.get(z) + STEP) * ((BETA1 * i12.get(z)) + (BETA2 * i21.get(z)) + (BETA * i.get(z)) - (EPSLON * r.get(z))));

			v.add((v.get(z) + STEP) * ((-ALFA * v.get(z)* (i2.get(z) + i21.get(z) + i.get(z))) - (E * v.get(z)) + (V * s.get(z))));
			
			t.add(z * STEP);

		}

	}

	public void generateChart() throws IOException {

		getEquations();
		double[] xTime = s.stream().mapToDouble(x -> x).toArray();

		double[] arrayS = s.stream().mapToDouble(x -> x).toArray();
		double[] arrayI1 = i1.stream().mapToDouble(x -> x).toArray();
		double[] arrayI2 = i2.stream().mapToDouble(x -> x).toArray();
		double[] arrayI12 = i12.stream().mapToDouble(x -> x).toArray();
		double[] arrayI21 = i21.stream().mapToDouble(x -> x).toArray();
		double[] arrayI = i.stream().mapToDouble(x -> x).toArray();
		double[] arrayR1 = r1.stream().mapToDouble(x -> x).toArray();
		double[] arrayR2 = r2.stream().mapToDouble(x -> x).toArray();
		double[] arrayR = r.stream().mapToDouble(x -> x).toArray();
		double[] arrayV = v.stream().mapToDouble(x -> x).toArray();

		XYChart chart1 = buildXYChart();
		chart1.addSeries(TwoDiasesState.I1.getDescription(), xTime, arrayI1).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(TwoDiasesState.I2.getDescription(), xTime, arrayI2).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(TwoDiasesState.I12.getDescription(), xTime, arrayI12).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(TwoDiasesState.I21.getDescription(), xTime, arrayI21).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(TwoDiasesState.I.getDescription(), xTime, arrayI).setMarker(SeriesMarkers.NONE);
		
		XYChart chart2 = buildXYChart();
		chart2.addSeries(TwoDiasesState.S.getDescription(), xTime, arrayS).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(TwoDiasesState.R1.getDescription(), xTime, arrayR1).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(TwoDiasesState.R2.getDescription(), xTime, arrayR2).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(TwoDiasesState.R.getDescription(), xTime, arrayR).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(TwoDiasesState.V.getDescription(), xTime, arrayV).setMarker(SeriesMarkers.NONE);
		
		new SwingWrapper<XYChart>(chart1).displayChart();
		new SwingWrapper<XYChart>(chart2).displayChart();
		
		BitmapEncoder.saveBitmap(chart1, "./edo_grafico1", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(chart2, "./edo_grafico2", BitmapFormat.PNG);
		
	}

	private XYChart buildXYChart() {
		XYChart xyChart = new XYChartBuilder().width(800).height(600).title("Evolução da população no tempo")
				.xAxisTitle("Tempo").yAxisTitle("População").theme(ChartTheme.GGPlot2).build();

		// Customize Chart
		xyChart.getStyler().setLegendPosition(LegendPosition.OutsideS);
		xyChart.getStyler().setLegendVisible(true);
		xyChart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

		return xyChart;
	}

}
