package br.edu.sp.mackenzie.ppgeec.ca.state;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import br.edu.sp.mackenzie.ppgeec.Constants;
import br.edu.sp.mackenzie.ppgeec.SimulationCase;
import br.edu.sp.mackenzie.ppgeec.ca.util.KCalculator;

public enum TwoDiasesState implements CellularAutomataState {

	S(0, "Suscetível as doenças", "S", 0.98),
	I1(1, "Infectado por 1 / nunca contraiu 2", "I\u2081", 0.01),
	I2(2, "Infectado por 2 / nunca contraiu 1", "I\u2082", 0.01), 
	I12(3, "Infectado por 1 / imune a 2 porque contraiu 2", "I\u2081\u2082", 0d),
	I21(4, "Infectado por 2 / imune a 1 porque contraiu 1", "I\u2082\u2081", 0d),
	I(5, "Infectado por 2 / imune a 1 por ter sido vacinado contra 1", "I", 0d), 
	R1(6, "Imune a 1 por ter contraído 1", "R\u2081", 0d),
	R2(7, "Imune a 2 por ter contraído 2", "R\u2082", 0d), 
	R(8, "Imune as duas doenças", "R", 0d),
	V(9, "Imune a 1 por ter se vacinado contra 1", "V", 0d);

	private static Map<TwoDiasesState, Map<TwoDiasesState, Function<Double, Double>>> graph;

	private final int value;
	private final String description;
	private final String symbol;
	private final Double populationPercentage;

	private TwoDiasesState(int value, String description, String symbol, Double populationPercentage) {

		this.value = value;
		this.description = description;
		this.symbol = symbol;
		this.populationPercentage = populationPercentage;
	}

	public static void initGraph(SimulationCase simulationCase) {

		graph = new HashMap<>();
//		if (graph.isEmpty()) {
			// S
			Map<TwoDiasesState, Function<Double, Double>> sVertex = new LinkedHashMap<>();
			sVertex.put(V, n -> Probabilities.V);
			sVertex.put(I1, n -> 1 - Math.pow(Math.E, -1 * simulationCase.getK1() * n));
			sVertex.put(I2, n -> 1 - Math.pow(Math.E, -1 * simulationCase.getK2() * n));

			// I1
			Map<TwoDiasesState, Function<Double, Double>> i1Vertex = new LinkedHashMap<>();
			i1Vertex.put(R1, n -> simulationCase.getB1());
			i1Vertex.put(S, n -> simulationCase.getC1());

			// I12
			Map<TwoDiasesState, Function<Double, Double>> i12Vertex = new LinkedHashMap<>();
			i12Vertex.put(R, n -> simulationCase.getBeta1());
			i12Vertex.put(S, n -> simulationCase.getGamma1());

			// I21
			Map<TwoDiasesState, Function<Double, Double>> i21Vertex = new LinkedHashMap<>();
			i21Vertex.put(R, n -> simulationCase.getBeta2());
			i21Vertex.put(S, n -> simulationCase.getGamma2());

			// I2
			Map<TwoDiasesState, Function<Double, Double>> i2Vertex = new LinkedHashMap<>();
			i2Vertex.put(R2, n -> simulationCase.getB2());
			i2Vertex.put(S, n -> simulationCase.getC2());

			// I
			Map<TwoDiasesState, Function<Double, Double>> iVertex = new LinkedHashMap<>();
			iVertex.put(R, n -> simulationCase.getBeta());
			iVertex.put(S, n -> simulationCase.getGamma());

			// R1
			Map<TwoDiasesState, Function<Double, Double>> r1Vertex = new LinkedHashMap<>();
			r1Vertex.put(S, n -> simulationCase.getE1());
			r1Vertex.put(I21, n -> 1 - Math.pow(Math.E, -1 * simulationCase.getK4() * n));

			// R2
			Map<TwoDiasesState, Function<Double, Double>> r2Vertex = new LinkedHashMap<>();
			r2Vertex.put(S, n -> simulationCase.getE2());
			r2Vertex.put(I12, n -> 1 - Math.pow(Math.E, -1 * simulationCase.getK3() * n));

			// R
			Map<TwoDiasesState, Function<Double, Double>> rVertex = new LinkedHashMap<>();
			rVertex.put(S, n -> simulationCase.getEpsilon());

			// V
			Map<TwoDiasesState, Function<Double, Double>> vVertex = new LinkedHashMap<>();
			vVertex.put(S, n -> simulationCase.getE());
			vVertex.put(I, n -> 1 - Math.pow(Math.E, -1 * simulationCase.getK5() * n));

			// GRAPH
			graph.put(S, sVertex);
			graph.put(I1, i1Vertex);
			graph.put(I12, i12Vertex);
			graph.put(I21, i21Vertex);
			graph.put(I2, i2Vertex);
			graph.put(I, iVertex);
			graph.put(R1, r1Vertex);
			graph.put(R2, r2Vertex);
			graph.put(R, rVertex);
			graph.put(V, vVertex);
//		}

	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public CellularAutomataState applyRule(List<CellularAutomataState> neighborhood) {

		double n = 0;

//		initGraph();
		Map<TwoDiasesState, Function<Double, Double>> vertexMap = graph.get(this);

		Random random = new Random();

		double diaseNeighbors = countNeighborsDiase(neighborhood, n);
		double diaseProbability = diaseProbability(diaseNeighbors);
		if (TwoDiasesState.S.equals(this)) {
			
			Function<Double, Double> rule = vertexMap.get(V);
			n = countNeighbors(neighborhood, n, V);
			double probability = rule.apply(n);
			if (random.nextDouble() < probability) {
				return V;
			}
			
			if (random.nextDouble() < diaseProbability) {
				double pI1 = countNeighborByState(neighborhood, I1, I12) / diaseNeighbors;

				double pRandom = random.nextDouble();
				if (pRandom < pI1) {
					return I1;
				} else {
					return I2;
				}
			} 
		} else {
			for (TwoDiasesState state : vertexMap.keySet()) {

				Function<Double, Double> rule = vertexMap.get(state);
				n = countNeighbors(neighborhood, n, state);
				double probability = rule.apply(n);
				if (random.nextDouble() < probability) {
					return state;
				}
			}
		}

		return this;
	}

	private double countNeighborsDiase(List<CellularAutomataState> neighborhood, double n) {
		return countNeighborByState(neighborhood, I1, I12, I2, I21, I);
	}

	private double diaseProbability(double n) {
		return 1 - Math.pow(Math.E, -1 * Constants.K * n);
	}

	private double countNeighbors(List<CellularAutomataState> neighborhood, double n, TwoDiasesState state) {
		switch (state) {

		case I1:

			if (TwoDiasesState.S.equals(this)) {
				n = countNeighborByState(neighborhood, I1, I12);
			}
			break;

		case I2:

			if (TwoDiasesState.S.equals(this)) {
				n = countNeighborByState(neighborhood, I2, I21, I);
			}
			break;

		case I21:

			if (TwoDiasesState.R1.equals(this)) {
				n = countNeighborByState(neighborhood, I2, I21, I);
			}
			break;

		case I12:

			if (TwoDiasesState.R2.equals(this)) {
				n = countNeighborByState(neighborhood, I1, I12);
			}
			break;

		case I:

			if (TwoDiasesState.V.equals(this)) {
				n = countNeighborByState(neighborhood, I2, I21, I);
			}
			break;

		default:
			break;

		}
		return n;
	}

	private long countNeighborByState(List<CellularAutomataState> neighborhood, TwoDiasesState... diasesState) {
		long n = 0;
		for (TwoDiasesState diaseState : diasesState) {
			n += countNeighborByState(neighborhood, diaseState);
		}
		return n;
	}

	private long countNeighborByState(List<CellularAutomataState> neighborhood, TwoDiasesState diaseState) {
		return neighborhood.stream().filter(n -> diaseState.equals(n)).count();
	}

	@Override
	public Double getPercentage() {
		return populationPercentage;
	}

	public static CellularAutomataState get(int value) {
		for (TwoDiasesState populationState : values()) {
			if (populationState.value == value) {
				return populationState;
			}
		}
		return null;
	}
	
}
