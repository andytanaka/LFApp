package com.ufla.lfapp.core.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by juventino on 26/08/15.
 */
public class AcademicSupport {

    private String comments;
    private boolean situation;
    private Map<Integer, String> foundProblems;
    private String result;
    private String solutionDescription;
    private Set<Rule> insertedRules;
    private Set<Rule> irregularRules;
    private List<Set<String>> firstSet;
    private List<Set<String>> secondSet;
    private List<Set<String>> thirdSet;
    private Grammar grammar;


    public AcademicSupport() {
        this("", false, new HashMap<Integer, String>(), "", "",
                new HashSet<Rule>(), new HashSet<Rule>(),
                new ArrayList<Set<String>>(), new ArrayList<Set<String>>(),
                new ArrayList<Set<String>>(), new Grammar());
    }

    public AcademicSupport(String comments, boolean situation,
                           Map<Integer, String> foundProblems,
                           Grammar g, String solutionDescription,
                           Set<Rule> insertedRules,
                           Set<Rule> irregularRules, List<Set<String>> firtSet,
                           List<Set<String>> secondSet,
                           List<Set<String>> thirdSet,
                           Grammar grammar) {
        this.comments = comments;
        this.situation = situation;
        this.foundProblems = foundProblems;
        setResult(g);
        this.solutionDescription = solutionDescription;
        this.insertedRules = insertedRules;
        this.irregularRules = irregularRules;
        this.firstSet = firtSet;
        this.secondSet = secondSet;
        this.thirdSet = thirdSet;
        this.grammar = grammar;
    }

    //Construtor base
    public AcademicSupport(String comments, boolean situation,
                           Map<Integer, String> foundProblems,
                           String result, String solutionDescription,
                           Set<Rule> insertedRules,
                           Set<Rule> irregularRules, List<Set<String>> firtSet,
                           List<Set<String>> secondSet,
                           List<Set<String>> thirdSet,
                           Grammar grammar) {
        super();
        this.comments = comments;
        this.situation = situation;
        this.foundProblems = foundProblems;
        this.result = result;
        this.solutionDescription = solutionDescription;
        this.insertedRules = insertedRules;
        this.irregularRules = irregularRules;
        this.firstSet = firtSet;
        this.secondSet = secondSet;
        this.thirdSet = thirdSet;
        this.grammar = grammar;

    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean getSituation() {
        return situation;
    }

    public void setSituation(boolean situation) {
        this.situation = situation;
    }

    public Map<Integer, String> getFoundProblems() {
        return foundProblems;
    }

    public void setFoundProblems(Map<Integer, String> foundProblems) {
        this.foundProblems = foundProblems;
    }


    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = (Grammar) grammar.clone();
    }

    public String getResult() {
        return result;
    }

    public void setResult(Grammar g) {
        this.result = formatResultGrammar(g);
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }

    public Set<Rule> getInsertedRules() {
        return insertedRules;
    }

    public void setInsertedRules(Set<Rule> insertedRules) {
        this.insertedRules = insertedRules;
    }

    private Set<String> getRuleLeftSides(final Grammar g) {
        Set<String> lefSides = new HashSet<>();
        for (Rule rule : g.getRules()) {
            lefSides.add(rule.getLeftSide());
        }
        return lefSides;
    }

    public String formatResultGrammar(final Grammar g) {
        List<String> listOfVariables = new ArrayList<>(getRuleLeftSides(g));
        Collections.sort(listOfVariables);
        StringBuilder txtGrammar = new StringBuilder(g.getInitialSymbol()
                + " →");
        for (Rule element : g.getRules(g.getInitialSymbol())) {
            txtGrammar.append(" ").append(putSentenceOnSubstring(element
                    .getRightSide())).append(" |");
        }
        txtGrammar.deleteCharAt(txtGrammar.length() - 1);
        txtGrammar.append("<br>");
        for (String variable : listOfVariables) {
            if (variable.equals(g.getInitialSymbol())) {
                continue;
            }
            if (variable.length() > 1) {
                for (int i = 0; i < variable.length(); i++) {
                    if (Character.isDigit(variable.charAt(i))) {
                        int j = i+1;
                        while (j != variable.length() && Character.isDigit(variable.charAt(j))) {
                            j++;
                        }
                        i = j-1;
                        txtGrammar.append("<sub>").append(variable.substring(i, j)).append("</sub>");
                    } else {
                        txtGrammar.append(variable.charAt(i));
                    }
                }
            } else {
                txtGrammar.append(variable);
            }
            txtGrammar.append(" →");
            for (Rule element : g.getRules(variable)) {
                    txtGrammar.append(" ").append(putSentenceOnSubstring
                            (element.getRightSide()));
                    txtGrammar.append(" |");
            }
            txtGrammar.deleteCharAt(txtGrammar.length() - 1);
            txtGrammar.append("<br>");
        }
        return txtGrammar.toString();
    }

    String putSentenceOnSubstring(String element) {
        StringBuilder newSentence = new StringBuilder();
        boolean isDigit = false;
        for (int i = 0; i < element.length(); i++) {
            if (Character.isDigit(element.charAt(i)) && !isDigit) {
                newSentence.append("<sub>").append(element.charAt(i));
                isDigit = true;
            } else if (Character.isDigit(element.charAt(i)) && isDigit) {
                newSentence.append(element.charAt(i));
            } else if (!Character.isDigit(element.charAt(i)) && isDigit) {
                newSentence.append("</sub>").append(element.charAt(i));
                isDigit = false;
            } else {
                newSentence.append(element.charAt(i));
            }
        }
        if (isDigit) {
            newSentence.append("</sub>");
        }
        return newSentence.toString();
    }

    void insertNewRule(Rule newRule) {
        this.insertedRules.add(newRule);
    }

    void insertIrregularRule(Rule irregularRule) {
        this.irregularRules.add(irregularRule);
    }

    public Set<Rule> getIrregularRules() {
        return irregularRules;
    }

    public void setIrregularRules(Set<Rule> irregularRules) {
        this.irregularRules = irregularRules;
    }

    public List<Set<String>> getFirstSet() {
        return firstSet;
    }

    public void setFirstSet(List<Set<String>> firstSet) {
        this.firstSet = firstSet;
    }

    public List<Set<String>> getSecondSet() {
        return secondSet;
    }

    public void setSecondSet(List<Set<String>> secondSet) {
        this.secondSet = secondSet;
    }


    public List<Set<String>> getThirdSet() {
        return thirdSet;
    }

    public void setThirdSet(List<Set<String>> thirdSet) {
        this.thirdSet = thirdSet;
    }

    public void insertOnFirstSet(Set<String>  currentSet, String decision) {
        if (decision.equals("Lambda") || decision.equals("TERM")
                || decision.equals("REACH")) {
            if (!verifySet(firstSet, currentSet)) {
                Set<String> aux = new LinkedHashSet<>();
                aux.addAll(currentSet);
                firstSet.add(aux);
            }
        } else if (decision.equals("Chain")) {
            Set<String> aux = new LinkedHashSet<>();
            aux.addAll(currentSet);
           firstSet.add(aux);
        }
    }

    public void insertOnSecondSet(Set<String> currentSet, String decision) {
        if (decision.equals("Lambda") || decision.equals("TERM")
                || decision.equals("REACH")) {
            if (!verifySet(secondSet, currentSet)) {
                Set<String> aux = new LinkedHashSet<>();
                aux.addAll(currentSet);
                secondSet.add(aux);
            }
        } else if (decision.equals("Chain")) {
            Set<String> aux = new LinkedHashSet<>();
            aux.addAll(currentSet);
            secondSet.add(aux);
        }
    }

    public void insertOnThirdSet(Set<String> currentSet, String decision) {
        if (decision.equals("Lambda") || decision.equals("TERM")
                || decision.equals("REACH")) {
            if (!verifySet(thirdSet, currentSet)) {
                Set<String> aux = new LinkedHashSet<>();
                aux.addAll(currentSet);
                thirdSet.add(aux);
            }
        } else if (decision.equals("Chain")) {
            Set<String> aux = new LinkedHashSet<>();
            aux.addAll(currentSet);
            thirdSet.add(aux);
        }
    }

    private boolean verifySet(List<Set<String>> setOfVariables,
                              Set<String> currentSet) {
        /*if (setOfVariables.size() != 0) {
            for (int i = 0; i < setOfVariables.size(); i++) {
                Set<String> auxSet = setOfVariables
                .get(setOfVariables.size() - 1);
                if (auxSet.equals(currentSet)) {
                }
            }
        }*/
        return false;
    }
}

