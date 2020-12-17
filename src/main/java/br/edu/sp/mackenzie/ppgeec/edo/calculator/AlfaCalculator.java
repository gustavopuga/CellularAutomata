package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.Constants;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class AlfaCalculator {

	public static double getA(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double s = 0;
			double i1 = 0;
			double i12 = 0;
			double i2 = 0;
			double i21 = 0;
			double i = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];

					if (TwoDiasesState.S.equals(stateTime1)
							&& (TwoDiasesState.I1.equals(stateTime2) || TwoDiasesState.I2.equals(stateTime2))) {
						delta++;
					}

					if (TwoDiasesState.S.equals(stateTime2)) {
						s++;
					}

					if (TwoDiasesState.I1.equals(stateTime2)) {
						i1++;
					}

					if (TwoDiasesState.I12.equals(stateTime2)) {
						i12++;
					}
					
					if (TwoDiasesState.I2.equals(stateTime2)) {
						i2++;
					}

					if (TwoDiasesState.I21.equals(stateTime2)) {
						i21++;
					}
					if (TwoDiasesState.I.equals(stateTime2)) {
						i++;
					}
				}
			}

			double d = (i1 + i12 + i2 + i21 + i) * s;
			double a = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(a);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;
	}

	public static double getA1(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double s = 0;
			double i1 = 0;
			double i12 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];
					
					if (TwoDiasesState.S.equals(stateTime1) && TwoDiasesState.I1.equals(stateTime2)) {
						delta++;
					}

					if (TwoDiasesState.S.equals(stateTime2)) {
						s++;
					}

					if (TwoDiasesState.I1.equals(stateTime2)) {
						i1++;
					}

					if (TwoDiasesState.I12.equals(stateTime2)) {
						i12++;
					}
				}
			}

			double d = (i1 + i12) * s;
			double a1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(a1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;
	}

	public static double getA2(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double s = 0;
			double i = 0;
			double i2 = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];
					
					if (TwoDiasesState.S.equals(stateTime1) && TwoDiasesState.I2.equals(stateTime2)) {
						delta++;
					}

					if (TwoDiasesState.S.equals(stateTime2)) {
						s++;
					}

					if (TwoDiasesState.I.equals(stateTime2)) {
						i++;
					}

					if (TwoDiasesState.I2.equals(stateTime2)) {
						i2++;
					}
					if (TwoDiasesState.I21.equals(stateTime2)) {
						i21++;
					}
				}
			}

			double d = (i + i21 + i2) * s;
			double a2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(a2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;

	}

	public static double getAlfa(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double v = 0;
			double i = 0;
			double i2 = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];
					
					if (TwoDiasesState.V.equals(stateTime1) && TwoDiasesState.I.equals(stateTime2)) {
						delta++;
					}

					if (TwoDiasesState.V.equals(stateTime2)) {
						v++;
					}

					if (TwoDiasesState.I.equals(stateTime2)) {
						i++;
					}

					if (TwoDiasesState.I2.equals(stateTime2)) {
						i2++;
					}
					if (TwoDiasesState.I21.equals(stateTime2)) {
						i21++;
					}
				}
			}

			double d = (i + i21 + i2) * v;
			double alfa = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;

	}

	public static double getAlfa1(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double r2 = 0;
			double i1 = 0;
			double i12 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];
					
					if (TwoDiasesState.R2.equals(stateTime1) && TwoDiasesState.I12.equals(stateTime2)) {
						delta++;
					}

					if (TwoDiasesState.R2.equals(stateTime2)) {
						r2++;
					}

					if (TwoDiasesState.I1.equals(stateTime2)) {
						i1++;
					}

					if (TwoDiasesState.I12.equals(stateTime2)) {
						i12++;
					}

				}
			}

			double d = (i1 + i12) * r2;
			double alfa1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;

	}

	public static double getAlfa2(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double r1 = 0;
			double i = 0;
			double i2 = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];
					
					if (TwoDiasesState.R1.equals(stateTime1) && TwoDiasesState.I21.equals(stateTime2)) {
						delta++;
					}

					if (TwoDiasesState.R1.equals(stateTime2)) {
						r1++;
					}

					if (TwoDiasesState.I.equals(stateTime2)) {
						i++;
					}

					if (TwoDiasesState.I2.equals(stateTime2)) {
						i2++;
					}
					if (TwoDiasesState.I21.equals(stateTime2)) {
						i21++;
					}
				}
			}

			double d = (i + i21 + i2) * r1;
			double alfa2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;
	}

	
	public static double getY(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta_S_I1 = 0;
			double deltaS_I1_I2 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];
					
					if (TwoDiasesState.S.equals(stateTime1) && TwoDiasesState.I1.equals(stateTime2)) {
						delta_S_I1++;
					}

					if (TwoDiasesState.S.equals(stateTime1) && (TwoDiasesState.I1.equals(stateTime2) || TwoDiasesState.I2.equals(stateTime2))) {
						deltaS_I1_I2++;
					}
				}
			}

			double d = deltaS_I1_I2;
			double y = (delta_S_I1 == 0 && d == 0) ? 0 : (delta_S_I1/d);
			values.add(y);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble();
	}

	public static double getYc(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}
		
		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);
			
			double delta_S_I1 = 0;
			double deltaS_I1_I2 = 0;
			
			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];
					
					if (TwoDiasesState.S.equals(stateTime1) && TwoDiasesState.I2.equals(stateTime2)) {
						delta_S_I1++;
					}
					
					if (TwoDiasesState.S.equals(stateTime1) && (TwoDiasesState.I1.equals(stateTime2) || TwoDiasesState.I2.equals(stateTime2))) {
						deltaS_I1_I2++;
					}
				}
			}
			
			double d = deltaS_I1_I2;
			double y = (delta_S_I1 == 0 && d == 0) ? 0 : (delta_S_I1/d);
			values.add(y);
			
		}
		
		return values.stream().mapToDouble(a -> a).average().getAsDouble();
	}
	
}
