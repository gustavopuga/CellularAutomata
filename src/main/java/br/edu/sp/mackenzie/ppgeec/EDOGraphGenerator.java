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

	private List<Double> tValues = new ArrayList<>();
	private List<Double> sValues = new ArrayList<>();
	private List<Double> i1Values = new ArrayList<>();
	private List<Double> i2Values = new ArrayList<>();
	private List<Double> i12Values = new ArrayList<>();
	private List<Double> i21Values = new ArrayList<>();
	private List<Double> iValues = new ArrayList<>();
	private List<Double> r1Values = new ArrayList<>();
	private List<Double> r2Values = new ArrayList<>();
	private List<Double> rValues = new ArrayList<>();
	private List<Double> vValues = new ArrayList<>();

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

		sValues.add(TwoDiasesState.S.getPercentage());
		i1Values.add(TwoDiasesState.I1.getPercentage());
		i2Values.add(TwoDiasesState.I2.getPercentage());
		i12Values.add(TwoDiasesState.I12.getPercentage());
		i21Values.add(TwoDiasesState.I21.getPercentage());
		iValues.add(TwoDiasesState.I.getPercentage());
		r1Values.add(TwoDiasesState.R1.getPercentage());
		r2Values.add(TwoDiasesState.R2.getPercentage());
		rValues.add(TwoDiasesState.R.getPercentage());
		vValues.add(TwoDiasesState.V.getPercentage());
		tValues.add(0d);

		int time = Constants.TIME*100;
		for (int j = 1; j < time; j++) {

			int z = j - 1;
			double s = sValues.get(z);
			double i1 = i1Values.get(z);
			double i12 = i12Values.get(z);
			double i2 = i2Values.get(z);
			double i21 = i21Values.get(z);
			double i = iValues.get(z);
			double r1 = r1Values.get(z);
			double r2 = r2Values.get(z);
			double r = rValues.get(z);
			double v = vValues.get(z);

			sValues.add(s + (STEP * ((-A1 * (i1 + i12)) - (A2 * (i2 + i21 + i)) + (C1 * i1) + (C2 * i2) + (GAMMA1 * i12)
					+ (GAMMA2 * i21) + (GAMMA * i) + (E * v) + (EPSLON * r) - (V * s))));

			i1Values.add(i1 + (STEP * ((A1 * s * (i1 + i12)) - (B1 * i1) - (C1 * i1))));

			i2Values.add(i2 + (STEP * ((A2 * s * (i2 + i21 + i)) - (B2 * i2) - (C2 * i2))));

			i12Values.add(i12 + (STEP * ((ALFA1 * r2 * (i1 + i12)) - (BETA1 * i12) - (GAMMA1 * i12))));

			i21Values.add(i21 + (STEP * ((ALFA2 * r1 * (i2 + i21 + i)) - (BETA2 * i21) - (GAMMA2 * i21))));

			iValues.add(i + (STEP * ((ALFA * v * (i2 + i21 + i)) - (BETA * i) - (GAMMA * i))));

			r1Values.add(r1 + (STEP * ((-ALFA2 * r1 * (i2 + i21 + i)) + (B1 * i1) - (E1 * r1))));

			r2Values.add(r2 + (STEP * ((-ALFA1 * r2 * (i1 + i12)) + (B2 * i2) - (E2 * r2))));

			rValues.add(r + (STEP * ((BETA1 * i12) + (BETA2 * i21) + (BETA * i) - (EPSLON * r))));

			vValues.add(v + (STEP * ((-ALFA * v * (i2 + i21 + i)) - (E * v) + (V * s))));

			tValues.add(z * STEP);

		}

	}

	public void generateChart() throws IOException {

		getEquations();
		double[] xTime = tValues.stream().mapToDouble(x -> x).toArray();

		double[] arrayS = sValues.stream().mapToDouble(x -> x).toArray();
		double[] arrayI1 = i1Values.stream().mapToDouble(x -> x).toArray();
		double[] arrayI2 = i2Values.stream().mapToDouble(x -> x).toArray();
		double[] arrayI12 = i12Values.stream().mapToDouble(x -> x).toArray();
		double[] arrayI21 = i21Values.stream().mapToDouble(x -> x).toArray();
		double[] arrayI = iValues.stream().mapToDouble(x -> x).toArray();
		double[] arrayR1 = r1Values.stream().mapToDouble(x -> x).toArray();
		double[] arrayR2 = r2Values.stream().mapToDouble(x -> x).toArray();
		double[] arrayR = rValues.stream().mapToDouble(x -> x).toArray();
		double[] arrayV = vValues.stream().mapToDouble(x -> x).toArray();

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
