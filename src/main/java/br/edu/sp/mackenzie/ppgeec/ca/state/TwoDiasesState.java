package br.edu.sp.mackenzie.ppgeec.ca.state;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import br.edu.sp.mackenzie.ppgeec.Constants;

public enum TwoDiasesState implements CellularAutomataState {
	
	S(0, "Suscetível as doenças", 0.98),
    I1(1, "Infectado por 1 / nunca contraiu 2", 0.01),
    I2(2, "Infectado por 2 / nunca contraiu 1", 0.01),
    I12(3, "Infectado por 1 / imune a 2 porque contraiu 2", 0d),
    I21(4, "Infectado por 2 / imune a 1 porque contraiu 1", 0d),
    I(5, "Infectado por 2 / imune a 1 por ter sido vacinado contra 1", 0d),
    R1(6, "Imune a 1 por ter contraído 1", 0d),
    R2(7, "Imune a 2 por ter contraído 2", 0d),
    R(8, "Imune as duas doenças", 0d),
    V(9, "Imune a 1 por ter se vacinado contra 1", 0d);

	private final Map<TwoDiasesState, Map<TwoDiasesState, Function<Double, Double>>> graph = new HashMap<>();

	private final int value;
	private final String description;
	private final Double populationPercentage;

	private TwoDiasesState(int value, String description, Double populationPercentage) {

		this.value = value;
		this.description = description;
		this.populationPercentage = populationPercentage;
	}

	private void initGraph() {

		if (graph.isEmpty()) {

			// S
			Map<TwoDiasesState, Function<Double, Double>> sVertex = new LinkedHashMap<>();
			sVertex.put(V, n -> Probabilities.V);
			sVertex.put(I1, n -> 1 - Math.pow(Math.E, -1 * Probabilities.K1 * n));
			sVertex.put(I2, n -> 1 - Math.pow(Math.E, -1 * Probabilities.K2 * n));
	
			// I1
			Map<TwoDiasesState, Function<Double, Double>> i1Vertex = new LinkedHashMap<>();
			i1Vertex.put(R1, n -> Probabilities.B1);
			i1Vertex.put(S, n -> Probabilities.C1);
	
			// I12
			Map<TwoDiasesState, Function<Double, Double>> i12Vertex = new LinkedHashMap<>();
			i12Vertex.put(R, n -> Probabilities.BETA1);
			i12Vertex.put(S, n -> Probabilities.R1);
	
			// I21
			Map<TwoDiasesState, Function<Double, Double>> i21Vertex = new LinkedHashMap<>();
			i21Vertex.put(R, n -> Probabilities.BETA2);
			i21Vertex.put(S, n -> Probabilities.R2);
	
			// I2
			Map<TwoDiasesState, Function<Double, Double>> i2Vertex = new LinkedHashMap<>();
			i2Vertex.put(R2, n -> Probabilities.B2);
			i2Vertex.put(S, n -> Probabilities.C2);
	
			// I
			Map<TwoDiasesState, Function<Double,  Double>> iVertex = new LinkedHashMap<>();
			iVertex.put(R, n -> Probabilities.BETA);
			iVertex.put(S, n -> Probabilities.GAMA);
	
			// R1
			Map<TwoDiasesState, Function<Double, Double>> r1Vertex = new LinkedHashMap<>();
			r1Vertex.put(S, n -> Probabilities.E1);
			r1Vertex.put(I21, n -> 1 - Math.pow(Math.E, -1 * Probabilities.K4 * n));
	
			// R2
			Map<TwoDiasesState, Function<Double, Double>> r2Vertex = new LinkedHashMap<>();
			r2Vertex.put(S, n -> Probabilities.E2);
			r2Vertex.put(I12, n -> 1 - Math.pow(Math.E, -1 * Probabilities.K3 * n));
			
			// R
			Map<TwoDiasesState, Function<Double, Double>> rVertex = new LinkedHashMap<>();
			rVertex.put(S, n -> Probabilities.EPSLON);
	
			// V
			Map<TwoDiasesState, Function<Double, Double>> vVertex = new LinkedHashMap<>();
			rVertex.put(S, n -> Probabilities.E);
			rVertex.put(I, n -> 1 - Math.pow(Math.E, -1 * Probabilities.K5 * n));
	
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
		}

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
	public CellularAutomataState applyRule(List<CellularAutomataState> neighborhood) {

		double n = 0;
		
		initGraph();
		Map<TwoDiasesState, Function<Double, Double>> vertexMap = this.graph.get(this);

		Random random = new Random();
		for (TwoDiasesState state : vertexMap.keySet()) {

			Function<Double, Double> rule = vertexMap.get(state);	
			n = countNeighbors(neighborhood, n, state);
			double probability = rule.apply(n);
			if (random.nextDouble() < probability) {
				return state;
			}
		}

		return this;
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
