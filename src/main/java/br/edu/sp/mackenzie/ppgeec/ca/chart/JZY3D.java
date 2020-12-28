package br.edu.sp.mackenzie.ppgeec.ca.chart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Rectangle;
import org.jzy3d.maths.algorithms.interpolation.algorithms.BernsteinInterpolator;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;


public class JZY3D extends AbstractAnalysis {

	private String title;
	private List<double[]> values;
	private String path;

	public JZY3D(String title, List<double[]> values, String path) {
		this.title = title;
		this.values = values;
		this.path = path;
	}

	public static void plot(String title, String path, List<double[]> values) throws Exception {
		JZY3D jzy3d = new JZY3D(title, values, path);
		AnalysisLauncher.open(jzy3d, new Rectangle(1024, 820));
		jzy3d.save();
	}

	@Override
	public void init() {
		int size = values.size();
		float a = 0.25f;

		double[] x = new double[size];
		double[] y = new double[size];
		double[] z = new double[size];
		double[][] grid = new double[size][];
		List<Coord3d> controlCoords = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			grid[i] = values.get(i);
			double[] xyz = values.get(i);
//			x[i] = xyz[0];
//			y[i] = xyz[1];
//			z[i] = xyz[2];
		    controlCoords.add(new Coord3d(xyz[0], xyz[1], xyz[2]));
		}

	    BernsteinInterpolator interp = new BernsteinInterpolator();
	    List<Coord3d> interpolatedCoords = interp.interpolate(controlCoords, 30);

	    List<Point> controlPoints = new ArrayList<>();
	    for (Coord3d coord : controlCoords) {
	        controlPoints.add(new Point(coord, Color.RED));
	    }

	    List<Point> interpPoints = new ArrayList<>();
	    for (Coord3d coord : interpolatedCoords) {
	        interpPoints.add(new Point(coord, Color.BLUE));
	    }

	    Shape surface = Builder.buildDelaunay(interpolatedCoords);
	    surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.GRAY);
        
//	    LineStrip line = new LineStrip(interpolatedCoords);
//	    line.setWireframeColor(Color.BLACK);
		chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");
		chart.getScene().add(surface);
		chart.getAxeLayout().setXAxeLabel("taxa de morte");
		chart.getAxeLayout().setYAxeLabel("taxa de cura");
		chart.getAxeLayout().setZAxeLabel("%" + title);
	}
	 
	private void save() throws IOException {
		File dir = new File(path);
		String name = StringUtils.stripAccents(title.toLowerCase().replaceAll("\\s+", "_"));
		chart.screenshot(new File(dir, name + ".png"));
		saveChartData(path, name);
	}
	
	private void saveChartData( String path, String name) {
		File dir = new File(path);
		File file = new File(dir, name + ".txt");
		try (FileWriter writer = new FileWriter(file); BufferedWriter bw = new BufferedWriter(writer)) {
			
			for (double[] v : values) {
				bw.write("\n" + Arrays.toString(v));
			}


		} catch (IOException exception) {
			System.err.format("IOException: %s%n", exception);
		}
		
	}

	public static double[][] zFunction(double[] x, double[] y) {
		double[][] z = new double[y.length][x.length];
		double scalex = 0.5;
		double scalez = 0.5;

		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < y.length; j++) {
				// point a (3D)
				double ax = x[i];
				double az = y[j];

				// assumtions
				double cx = 0; // offset
				double cz = 0; // offset

				// trafo 3D -> 2D
				double bx = scalex * ax + cz;
				double by = scalez * az + cz;

//				z[j][i] = zFunction(x[i], y[j]);
			}
		}
		return z;
	}
}