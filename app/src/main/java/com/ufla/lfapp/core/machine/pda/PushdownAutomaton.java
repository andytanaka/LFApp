package com.ufla.lfapp.core.machine.pda;

import android.support.v4.util.Pair;

import com.ufla.lfapp.core.machine.MachineType;
import com.ufla.lfapp.core.machine.TransitionFunction;
import com.ufla.lfapp.core.machine.fsa.FSATransitionFunction;
import com.ufla.lfapp.utils.Symbols;
import com.ufla.lfapp.core.machine.Machine;
import com.ufla.lfapp.core.machine.State;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class PushdownAutomaton extends Machine implements Serializable {

    private Set<PDATransitionFunction> transitionFunction;


    @Override
    public Set<? extends TransitionFunction> getTransitionFunctionsGen() {
        return transitionFunction;
    }

    @Override
    public MachineType getMachineType() {
        return MachineType.PDA;
    }

    public PushdownAutomaton() {
        this(new TreeSet<String>(), null, new TreeSet<String>(), new TreeSet<PDATransitionFunction>());
    }

    @Override
    public SortedSet<String> getAlphabet() {
        SortedSet<String> alphabet = new TreeSet<>();
        for (PDATransitionFunction tFPA : transitionFunction) {
            alphabet.add(tFPA.getSymbol());
        }
        return alphabet;
    }

    public SortedSet<String> getStackAlphabet() {
        SortedSet<String> stackAlphabet = new TreeSet<>();
        for (PDATransitionFunction tFPA : transitionFunction) {
            stackAlphabet.add(tFPA.getPops());
            stackAlphabet.add(tFPA.getStacking());
        }
        stackAlphabet.remove(Symbols.LAMBDA);
        return stackAlphabet;
    }

    //Construtor base
    public PushdownAutomaton(SortedSet<String> states, String initialState,
                             SortedSet<String> finalStates,
                             Set<PDATransitionFunction> transitionFunction) {
        super(states, initialState, finalStates);
        this.transitionFunction = transitionFunction;
    }

    public PushdownAutomaton(SortedSet<State> states, State initialState,
                             SortedSet<State> finalStates,
                             Set<PDATransitionFunction> transitionFunction) {
        super(states, initialState, finalStates);
        this.transitionFunction = transitionFunction;
    }

    public Map<Pair<State, State>, SortedSet<String>> getTransitionsPDA() {
        Map<Pair<State, State>, SortedSet<String>> transitionsPDA = new HashMap<>();
        for (PDATransitionFunction t : transitionFunction) {
            Pair<State, State> states = Pair.create(t.getCurrentState(), t.getFutureState());
            if (!transitionsPDA.containsKey(states)) {
                transitionsPDA.put(states, new TreeSet<String>());
            }
            transitionsPDA.get(states).add(t.getSymbol() + " " + t.getPops() + "/" +
                    t.getStacking());
        }
        return transitionsPDA;
    }

    public Set<PDATransitionFunction> getTransitionFunctions() {
        return transitionFunction;
    }

    public Set<PDATransitionFunction> getTransitions(State state, String symbol, String topStack) {
        Set<PDATransitionFunction> transitions = new HashSet<>();
        for (PDATransitionFunction t : transitionFunction) {
            if (t.getCurrentState().equals(state) &&
                    t.getSymbol().equals(symbol)
                    && (t.getPops().equals(Symbols.LAMBDA)
                    || t.getPops().equals(topStack))) {
                transitions.add(t);
            }
        }
        return transitions;
    }
}
