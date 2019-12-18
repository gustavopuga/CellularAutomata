package br.edu.mackenzie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum PopulationState {

    SUSCEPTIBLE(0, "Suscetível", 0.99),
    INFECTIOUS(1, "Infectado", 0.01), 
    RECOVERY(2, "Recuperado", 0d);

    private final Map<PopulationState, Map<PopulationState, Function<Double, Function<Double, Double>>>> graph = new HashMap<>();

    private final int value;
    private final String description;
    private final Double populationPercentage;

    private PopulationState(int value, String description, Double populationPercentage) {

	this.value = value;
	this.description = description;
	this.populationPercentage = populationPercentage;
    }

    private void initGraph() {

	Map<PopulationState, Function<Double, Function<Double, Double>>> susceptibleVertex = new LinkedHashMap<>();
	susceptibleVertex.put(INFECTIOUS, v -> k -> 1 - Math.pow(Math.E, -1 * k * v));

	Map<PopulationState, Function<Double, Function<Double, Double>>> infectiousVertex = new LinkedHashMap<>();
	infectiousVertex.put(RECOVERY, v -> k -> 0.6);
	infectiousVertex.put(SUSCEPTIBLE, v -> k -> 0.3);

	Map<PopulationState, Function<Double, Function<Double, Double>>> recoveryVertex = new LinkedHashMap<>();
	recoveryVertex.put(SUSCEPTIBLE, v -> k -> 0.1);

	graph.put(SUSCEPTIBLE, susceptibleVertex);
	graph.put(INFECTIOUS, infectiousVertex);
	graph.put(RECOVERY, recoveryVertex);

    }

    public int getValue() {
	return value;
    }

    public String getDescription() {
	return description;
    }

    public PopulationState applyRule(List<PopulationState> neighborhood) {

	double v = neighborhood.stream().filter(n -> this.equals(n)).count();
	
	if (graph.isEmpty()) {
	    initGraph();
	}

	PopulationState newState = this;
	Map<PopulationState, Function<Double, Function<Double, Double>>> vertexMap = this.graph.get(this);
	
	for (PopulationState state : vertexMap.keySet()) {
	    
	    double neighborhoodProbability = neighborhood.stream().filter(n -> state.equals(n)).count()/neighborhood.size();
	    double probability = vertexMap.get(state).apply(v).apply(Constants.K);
	    
	    if (probability < neighborhoodProbability) {
		newState = state;
		break;
	    }
	}

	return newState;
    }

    public Double getPopulationPercentage() {
	return populationPercentage;
    }

    public static PopulationState get(int value) {
	for (PopulationState populationState : values()) {
	    if (populationState.value == value) {
		return populationState;
	    }
	}
	return null;
    }

}
