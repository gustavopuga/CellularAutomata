package br.edu.mackenzie;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public enum PopulationState implements Comparable<PopulationState> {

    SUSCEPTIBLE(0, "Suscetível", 0.6), INFECTIOUS(1, "Infectado", 0.3), RECOVERY(2, "Recuperado", 0.1);

    private final static double K = 1;
    private final Map<PopulationState, Collection<PopulationState>> graph = new HashMap<>();

    private final int value;
    private final String description;
    private final Double probability;

    private PopulationState(int value, String description, double probability) {

	this.value = value;
	this.description = description;
	this.probability = probability;
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

	return (state1, state2) -> Double.compare(state2 == null ? -1 : state2.probability,
		state1 == null ? -1 : state1.probability);
    }

    public int getValue() {
	return value;
    }

    public String getDescription() {
	return description;
    }

    public PopulationState applyRule(int neighborhood) {

	double probability = 1 - Math.pow(Math.E, -1 * K * neighborhood);
	if (graph.isEmpty()) {
	    initGraph();
	}

	PopulationState newState = this;
	for (PopulationState state : this.graph.get(this)) {
	    if (probability < state.probability) {
		newState = state;
	    }
	}

	return newState;
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
