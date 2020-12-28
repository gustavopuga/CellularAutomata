package br.edu.sp.mackenzie.ppgeec.edo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import br.edu.sp.mackenzie.ppgeec.Constants;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiaseState;

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

	private double A;
	private double Y;

	// CURA
	private double BETA1;
	private double BETA2;
	private double BETA;

	private double B1;
	private double B2;

	// MORTE DOENCA
	private double C1;
	private double C2;

	private double GAMMA1;
	private double GAMMA2;

	private double GAMMA;

	// MORTE OUTRAS CAUSAS
	private double E;
	private double E1;
	private double E2;

	private double EPSILON;

	// VACINA
	private double V;



	public EDOGraphGenerator(double alfa1, double alfa2, double alfa, double a, double y,
			double beta1, double beta2, double beta, double b1, double b2, double c1, double c2, double gamma1,
			double gamma2, double gamma, double e, double e1, double e2, double epsilon, double v) {
		
		ALFA1 = alfa1;
		ALFA2 = alfa2;
		ALFA = alfa;
		A = a;
		Y = y;
		BETA1 = beta1;
		BETA2 = beta2;
		BETA = beta;
		B1 = b1;
		B2 = b2;
		C1 = c1;
		C2 = c2;
		GAMMA1 = gamma1;
		GAMMA2 = gamma2;
		GAMMA = gamma;
		E = e;
		E1 = e1;
		E2 = e2;
		EPSILON = epsilon;
		V = v;
	}

	private void getEquations() {

		sValues.add(TwoDiaseState.S.getPercentage());
		i1Values.add(TwoDiaseState.I1.getPercentage());
		i2Values.add(TwoDiaseState.I2.getPercentage());
		i12Values.add(TwoDiaseState.I12.getPercentage());
		i21Values.add(TwoDiaseState.I21.getPercentage());
		iValues.add(TwoDiaseState.I.getPercentage());
		r1Values.add(TwoDiaseState.R1.getPercentage());
		r2Values.add(TwoDiaseState.R2.getPercentage());
		rValues.add(TwoDiaseState.R.getPercentage());
		vValues.add(TwoDiaseState.V.getPercentage());
		tValues.add(0d);

		int time = (int) (Constants.TIME / STEP);

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

			double newS = s + (STEP * ((-A * s * (i1 + i12 + i2 + i21 + i)) + (C1 * i1) + (C2 * i2) + (GAMMA1 * i12)
					+ (GAMMA2 * i21) + (GAMMA * i) + (E1 * r1) + (E2 * r2) + (E * v) + (EPSILON * r) - (V * s)));
			sValues.add(newS);

			
			double newI1 = i1 + (STEP * ((Y * A * s * (i1 + i12 + i2 + i21 + i)) - (B1 * i1) - (C1 * i1)));
			i1Values.add(newI1);

			double newI2 = i2 + (STEP * (((1 - Y) * A * s * (i1 + i12 + i2 + i21 + i)) - (B2 * i2) - (C2 * i2)));
			i2Values.add(newI2);

			double newI12 = i12 + (STEP * ((ALFA1 * r2 * (i1 + i12)) - (BETA1 * i12) - (GAMMA1 * i12)));
			i12Values.add(newI12);

			double newI21 = i21 + (STEP * ((ALFA2 * r1 * (i2 + i21 + i)) - (BETA2 * i21) - (GAMMA2 * i21)));
			i21Values.add(newI21);

			double newI = i + (STEP * ((ALFA * v * (i2 + i21 + i)) - (BETA * i) - (GAMMA * i)));
			iValues.add(newI);

			double newR1 = r1 + (STEP * ((-ALFA2 * r1 * (i2 + i21 + i)) + (B1 * i1) - (E1 * r1)));
			r1Values.add(newR1);

			double newR2 = r2 + (STEP * ((-ALFA1 * r2 * (i1 + i12)) + (B2 * i2) - (E2 * r2)));
			r2Values.add(newR2);

			double newR = r + (STEP * ((BETA1 * i12) + (BETA2 * i21) + (BETA * i) - (EPSILON * r)));
			rValues.add(newR);

			double newV = v + (STEP * ((-ALFA * v * (i2 + i21 + i)) - (E * v) + (V * s)));
			vValues.add(newV);

			tValues.add(z * STEP);

		}

	}

	public String generateChart(String path) throws IOException {

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

		String sLabel =  TwoDiaseState.S.getSymbol() + "    " + TwoDiaseState.S.getDescription();
		String i1Label = TwoDiaseState.I1.getSymbol() + "  " + TwoDiaseState.I1.getDescription();
		String i2Label = TwoDiaseState.I2.getSymbol() + "  " +TwoDiaseState.I2.getDescription();
		String i12Label = TwoDiaseState.I12.getSymbol() + " " + TwoDiaseState.I12.getDescription();
		String i21Label = TwoDiaseState.I21.getSymbol() + " " +TwoDiaseState.I21.getDescription();
		String iLabel = TwoDiaseState.I.getSymbol() + "    " + TwoDiaseState.I.getDescription();
		String r1Label = TwoDiaseState.R1.getSymbol() + "  " + TwoDiaseState.R1.getDescription();
		String r2Label = TwoDiaseState.R2.getSymbol() + "  " + TwoDiaseState.R2.getDescription();
		String rLabel = TwoDiaseState.R.getSymbol() + "   " + TwoDiaseState.R.getDescription();
		String vLabel = TwoDiaseState.V.getSymbol() + "   " + TwoDiaseState.V.getDescription();

		XYChart chart1 = buildXYChart();
		chart1.addSeries(i1Label, xTime, arrayI1).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(i2Label, xTime, arrayI2).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(i12Label, xTime, arrayI12).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(i21Label, xTime, arrayI21).setMarker(SeriesMarkers.NONE);
		chart1.addSeries(iLabel, xTime, arrayI).setMarker(SeriesMarkers.NONE);

		XYChart chart2 = buildXYChart();
		chart2.addSeries(sLabel, xTime, arrayS).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(r1Label, xTime, arrayR1).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(r2Label, xTime, arrayR2).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(rLabel, xTime, arrayR).setMarker(SeriesMarkers.NONE);
		chart2.addSeries(vLabel, xTime, arrayV).setMarker(SeriesMarkers.NONE);

		StringBuilder builder = new StringBuilder("\n ======= VARIAVEIS EDO ======= \n");

		builder.append("\nS = " + sValues.get(sValues.size() - 1));
		builder.append("\nI1 = " + i1Values.get(i1Values.size() - 1));
		builder.append("\nI2 = " + +i2Values.get(i2Values.size() - 1));
		builder.append("\nI12 = " + +i12Values.get(i12Values.size() - 1));
		builder.append("\nI21 = " + +i21Values.get(i21Values.size() - 1));
		builder.append("\nI = " + +iValues.get(iValues.size() - 1));
		builder.append("\nR1 = " + +r1Values.get(r1Values.size() - 1));
		builder.append("\nR2 = " + +r2Values.get(r2Values.size() - 1));
		builder.append("\nR = " + +rValues.get(rValues.size() - 1));
		builder.append("\nV = " + +vValues.get(vValues.size() - 1));
		builder.append("\nt = " + +tValues.get(tValues.size() - 1));

//		new SwingWrapper<XYChart>(chart1).displayChart();
//		new SwingWrapper<XYChart>(chart2).displayChart();

		BitmapEncoder.saveBitmap(chart1, "./" + path + "/edo_grafico1", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(chart2, "./" + path + "/edo_grafico2", BitmapFormat.PNG);
		
		return builder.toString();

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
