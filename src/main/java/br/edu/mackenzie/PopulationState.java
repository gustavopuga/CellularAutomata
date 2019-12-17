package br.edu.mackenzie;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public enum PopulationState {

    SUSCEPTIBLE(0, "Suscetível", 0.6, 0.99), INFECTIOUS(1, "Infectado", 0.3, 0.01), RECOVERY(2, "Recuperado", 0.1, 0d);

    private final static double K = 1;
    private final Map<PopulationState, Collection<PopulationState>> graph = new HashMap<>();

    private final int value;
    private final String description;
    private final Double probability;
    private final Double populationPercentage;

    private PopulationState(int value, String description, double probability, Double populationPercentage) {

	this.value = value;
	this.description = description;
	this.probability = probability;
	this.populationPercentage = populationPercentage;
    }

    private void initGraph() {

	Comparator<PopulationState> probabilityComparator = generateProbabilityComparator();

	Collection<PopulationState> susceptibleVertex = new PriorityQueue<>(probabilityComparator);
	susceptibleVertex.add(INFECTIOUS);

	Collection<PopulationState> infectiousVertex = new PriorityQueue<>(probabilityComparator);
	infectiousVertex.add(RECOVERY);
	infectiousVertex.add(SUSCEPTIBLE);

	Collection<PopulationState> recoveryVertex = new PriorityQueue<>(probabilityComparator);
	recoveryVertex.add(SUSCEPTIBLE);

	graph.put(SUSCEPTIBLE, susceptibleVertex);
	graph.put(INFECTIOUS, infectiousVertex);
	graph.put(RECOVERY, recoveryVertex);

    }

    private Comparator<PopulationState> generateProbabilityComparator() {

	return (state1, state2) -> Double.compare(state1 == null ? -1 : state1.probability,
		state2 == null ? -1 : state2.probability);
    }

    public int getValue() {
	return value;
    }

    public String getDescription() {
	return description;
    }

    public PopulationState applyRule(List<PopulationState> neighborhood) {

	long k = neighborhood.stream().filter(n -> this.equals(n)).count();
	return applyRule(k);
    }

    public PopulationState applyRule(long v) {
	
	double probability = 1 - Math.pow(Math.E, -1 * K * v );
	
	if (graph.isEmpty()) {
	    initGraph();
	}

	PopulationState newState = this;
	for (PopulationState state : this.graph.get(this)) {
	    if (probability < state.probability) {
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
