package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiaseState;

public class BetaCalculator {

	public static double getB1(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i1 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiaseState.I1.equals(statesTime1[j][k]) && TwoDiaseState.R1.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiaseState.I1.equals(statesTime2[j][k])) {
						i1++;
					}
				}
			}

			
			double d = i1;
			double b1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(b1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;
	}

	public static double getB2(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i2 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiaseState.I2.equals(statesTime1[j][k]) && TwoDiaseState.R2.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiaseState.I2.equals(statesTime2[j][k])) {
						i2++;
					}
				}
			}

			double d = i2;
			double b2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(b2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getBeta(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiaseState.I.equals(statesTime1[j][k]) && TwoDiaseState.R.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiaseState.I.equals(statesTime2[j][k])) {
						i++;
					}

				}
			}

			double d = i;
			double beta = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(beta);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getBeta1(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i12 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiaseState.I12.equals(statesTime1[j][k]) && TwoDiaseState.R.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiaseState.I12.equals(statesTime2[j][k])) {
						i12++;
					}

				}
			}

			double d = i12;
			double beta1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(beta1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getBeta2(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiaseState.I21.equals(statesTime1[j][k]) && TwoDiaseState.R.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiaseState.I21.equals(statesTime2[j][k])) {
						i21++;
					}
				}
			}

			double d = i21;
			double beta2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(beta2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;
	}

}
