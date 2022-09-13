package util;

import java.util.HashMap;
import java.util.Map;

public class Input {
    
    private final String input;
    private final Map<String, String> delta;
    private final Map<String, String> lambda;

    public Input(String input){
        this.input = input;
        delta = new HashMap<>();
        lambda = new HashMap<>();
    }

    public String getInput() {
        return input;
    }

    public Map<String, String> getDelta(){
        return delta;
    }

    public Map<String, String> getLambda() {
        return lambda;
    }

    public void addToDelta(String k, String v){
        delta.put(k, v);
    }

    public void addToLambda(String k, String v){
        lambda.put(k, v);
    }

}
