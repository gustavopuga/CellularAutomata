package br.edu.sp.mackenzie.ppgeec.ca.state;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import br.edu.sp.mackenzie.ppgeec.Constants;

public enum TwoDiasesStateG implements CellularAutomataState {
	
	S(0, "Suscet�vel as doen�as", 0.98),
    I1(1, "Infectado por 1 / nunca contraiu 2", 0.01),
    I2(2, "Infectado por 2 / nunca contraiu 1", 0.01),
    I12(3, "Infectado por 1 / imune a 2 porque contraiu 2", 0d),
    I21(4, "Infectado por 2 / imune a 1 porque contraiu 1", 0d),
    I(5, "Infectado por 2 / imune a 1 por vacina��o", 0d),
    R1(6, "Imune a 1 por ter contra�do 1", 0d),
    R2(7, "Imune a 2 por ter contra�do 2", 0d),
    R(8, "Imune", 0d),
    V(9, "Imune a 1 por ter se vacinado contra 1", 0d);

	private final Map<TwoDiasesStateG, Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>>> graph = new HashMap<>();

	private final int value;
	private final String description;
	private final Double populationPercentage;

	private TwoDiasesStateG(int value, String description, Double populationPercentage) {

		this.value = value;
		this.description = description;
		this.populationPercentage = populationPercentage;
	}

	private void initGraph() {

		if (graph.isEmpty()) {

			// S
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> sVertex = new LinkedHashMap<>();
			sVertex.put(I2, n -> k -> 1 - Math.pow(Math.E, -1 * k * n));
			sVertex.put(I1, n -> k -> 1 - Math.pow(Math.E, -1 * k * n ));
			sVertex.put(V, n -> k -> Probabilities.V);
	
			// I1
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> i1Vertex = new LinkedHashMap<>();
			i1Vertex.put(R1, n -> k -> Probabilities.B1);
			i1Vertex.put(S, n -> k -> Probabilities.C1);
	
			// I12
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> i12Vertex = new LinkedHashMap<>();
			i12Vertex.put(R, n -> k -> Probabilities.BETA1);
			i12Vertex.put(S, n -> k -> Probabilities.GAMMA1);
	
			// I21
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> i21Vertex = new LinkedHashMap<>();
			i21Vertex.put(R, n -> k -> Probabilities.BETA2);
			i21Vertex.put(S, n -> k -> Probabilities.GAMMA2);
	
			// I2
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> i2Vertex = new LinkedHashMap<>();
			i2Vertex.put(R2, n -> k -> Probabilities.B2);
			i2Vertex.put(S, n -> k -> Probabilities.C2);
	
			// I
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> iVertex = new LinkedHashMap<>();
			i2Vertex.put(R, n -> k -> Probabilities.BETA);
			i2Vertex.put(S, n -> k -> Probabilities.GAMMA);
	
			// R1
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> r1Vertex = new LinkedHashMap<>();
			r1Vertex.put(S, n -> k -> Probabilities.E1);
			r1Vertex.put(I21, n -> k -> 1 - Math.pow(Math.E, -1 * k * n));
	
			// R2
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> r2Vertex = new LinkedHashMap<>();
			r2Vertex.put(S, n -> k -> Probabilities.E2);
			r2Vertex.put(I12, n -> k -> 1 - Math.pow(Math.E, -1 * k * n));
			
			// R
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> rVertex = new LinkedHashMap<>();
			rVertex.put(S, n -> k -> Probabilities.EPSLON);
	
			// V
			Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> vVertex = new LinkedHashMap<>();
			rVertex.put(I, n -> k -> 1 - Math.pow(Math.E, -1 * k * n));
			rVertex.put(S, n -> k -> Probabilities.E);
	
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
		double k = 0;
		
		initGraph();
		Map<TwoDiasesStateG, Function<Double, Function<Double, Double>>> vertexMap = this.graph.get(this);

		Random random = new Random();
		for (TwoDiasesStateG state : vertexMap.keySet()) {

			Function<Double, Function<Double, Double>> rule = vertexMap.get(state);	
			n = countNeighbors(neighborhood, n, state);
			double probability = rule.apply(n).apply(Constants.K);
			if (random.nextDouble() < probability) {
				return state;
			}
		}

		return this;
	}

	private double countNeighbors(List<CellularAutomataState> neighborhood, double n, TwoDiasesStateG state) {
		switch (state) {
		
		case I1:
			
			n = countNeighborByState(neighborhood, I1, I12);
			break;
			
		case I2:
			
			n = countNeighborByState(neighborhood, I12, I21, I);
			break;
			
		case I21:
			
			n = countNeighborByState(neighborhood, I2, I21, I);
			break;
			
		case I12:
			
			n = countNeighborByState(neighborhood, I1, I12);
			break;
		
		case I:
			
			n = countNeighborByState(neighborhood, I12, I21, I);
			break;
			
		default:
			break;
			
		}
		return n;
	}

	private long countNeighborByState(List<CellularAutomataState> neighborhood, TwoDiasesStateG... diasesState) {
		long n = 0;
		for (TwoDiasesStateG diaseState : diasesState) {
			n += countNeighborByState(neighborhood, diaseState);
		}
		return n;
	}

	private long countNeighborByState(List<CellularAutomataState> neighborhood, TwoDiasesStateG diaseState) {
		return neighborhood.stream().filter(n -> diaseState.equals(n)).count();
	}

	@Override
	public Double getPercentage() {
		return populationPercentage;
	}

	public static CellularAutomataState get(int value) {
		for (TwoDiasesStateG populationState : values()) {
			if (populationState.value == value) {
				return populationState;
			}
		}
		return null;
	}
	
}
