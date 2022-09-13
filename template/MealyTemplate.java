import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.Map;

public class Template {

    // Yes this is indeed unnecessarily complicated and I don't know why I wanted to do it this way
    public static void main(String[] args) {

        State state = State.; //TODO set the initial state here

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            for (; ; ) {
                String input = in.readLine();

                Transition currentTransition = getTransition(input);

                if (currentTransition == null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("Not a valid input.\nValid inputs are: ");
                    for(Transition transition : Transition.values()){
                        builder.append(transition.getInput()).append(" | ");
                    }
                    builder.append("stop");
                    System.out.println(builder);
                    continue;
                }

                System.out.println(currentTransition.lambda(state));
                state = currentTransition.delta(state);

            }

        } catch (IOException e) {
            System.out.println("There was an error trying to open the input reader!\n");
            e.printStackTrace();
        }

    }

    /* returns either the transition for the given input or null if there is no transition for the given input */
    private static Transition getTransition(String input) {
        for (Transition transition : Transition.values()) {
            if (input.equals(transition.getInput())) return transition;
        }
        return null;
    }

}

enum State { //TODO add all the states here

}

enum Transition { //TODO                    add all the transitions as well as

    T("input", Map.ofEntries( //TODO                                    input
            new AbstractMap.SimpleEntry<>(State., State.), //TODO       delta values
            new AbstractMap.SimpleEntry<>(State., State.) //TODO        delta values
    ), Map.ofEntries(
            new AbstractMap.SimpleEntry<>(State., "output"), //TODO     lambda values
            new AbstractMap.SimpleEntry<>(State., "output") //TODO      lambda values
    )),
    T("input", Map.ofEntries( //TODO                                    input
            new AbstractMap.SimpleEntry<>(State., State.) //TODO        delta values
    ), Map.ofEntries(
            new AbstractMap.SimpleEntry<>(State., "output") //TODO      lambda values
    ));

    private final String input;
    private final Map<State, State> delta;
    private final Map<State, String> lambda;

    Transition(String input, Map<State, State> delta, Map<State, String> lambda) {
        this.input = input;
        this.delta = delta;
        this.lambda = lambda;
    }

    public String getInput() {
        return input;
    }

    public State delta(State state) {
        return delta.get(state);
    }

    public String lambda(State state) {
        return lambda.get(state);
    }

}
