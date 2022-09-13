package parser;

import util.Input;
import util.Util;

import java.io.*;
import java.util.List;

public class FileParser {

    public static boolean createFiles(String path) {

        if (new File(path).mkdir()) System.out.println("Created directory");

        File mainFile = new File(path + "\\Main.java");
        File stateFile = new File(path + "\\State.java");
        File transFile = new File(path + "\\Transition.java"); //heh

        try {

            if (mainFile.createNewFile()) System.out.println("Created Main.java");
            if (stateFile.createNewFile()) System.out.println("Created State.java");
            if (transFile.createNewFile()) System.out.println("Created Transition.java");

        } catch (IOException e) {
            System.out.println("Something went wrong when trying to create a file");
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mainFile.getPath()))) {

            StringBuilder mainBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader("ressources/first_part"))) {
                String line;
                while ((line = reader.readLine()) != null) mainBuilder.append(line).append("\n");
                mainBuilder.delete(mainBuilder.length()-1, mainBuilder.length());
            } catch (IOException e) {
                System.out.println("Something went wrong trying to fetch the internal ressources, welp :(");
                e.printStackTrace();
                return false;
            }

            mainBuilder.append(XMLParser.getInitialState());

            try (BufferedReader reader = new BufferedReader(new FileReader("ressources/second_part"))) {
                String line;
                while ((line = reader.readLine()) != null) mainBuilder.append(line).append("\n");
            } catch (IOException e) {
                System.out.println("Something went wrong trying to fetch the internal ressources, welp :(");
                e.printStackTrace();
                return false;
            }

            writer.write(mainBuilder.toString());


        } catch (IOException e) {
            System.out.println("Something went wrong trying to write to the Main.java file!");
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stateFile.getPath()))) {

            StringBuilder stateBuilder = new StringBuilder();

            stateBuilder.append("enum State {\n\n\t");

            List<String> states = XMLParser.getStates();
            for(String state : states) {
                stateBuilder.append(state);
                if(!state.equals(states.get(states.size()-1))) stateBuilder.append(", ");
            }
            stateBuilder.append("\n\n}");

            writer.write(stateBuilder.toString());

        } catch (IOException e) {
            System.out.println("Something went wrong trying to write to the State.java file!");
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transFile.getPath()))) {

            StringBuilder transBuilder = new StringBuilder(); //heh

            transBuilder.append("import java.util.AbstractMap;\nimport java.util.Map;\n\nenum Transition {\n");

            List<Input> inputList = XMLParser.getInputs();

            for(Input input : inputList) {

                transBuilder.append("\n\tT").append(Util.strip(input.getInput())).append("(\"").append(input.getInput()).append("\", Map.ofEntries(");

                for(String state : input.getDelta().keySet()) {
                    transBuilder.append("\n\t\tnew AbstractMap.SimpleEntry<>(State.").append(state).append(", State.").append(input.getDelta().get(state)).append("),");
                }
                transBuilder.delete(transBuilder.length()-1, transBuilder.length()); //deletes the last comma
                transBuilder.append("\n\t), Map.ofEntries(");

                for(String state : input.getLambda().keySet()) {
                    transBuilder.append("\n\t\tnew AbstractMap.SimpleEntry<>(State.").append(state).append(", \"").append(input.getLambda().get(state)).append("\"),");
                }
                transBuilder.delete(transBuilder.length()-1, transBuilder.length()); //deletes the last comma
                transBuilder.append("\n\t)),");

            }
            transBuilder.delete(transBuilder.length()-1, transBuilder.length()); //deletes the last comma
            transBuilder.append(";\n\n");

            try (BufferedReader reader = new BufferedReader(new FileReader("ressources/transition"))) {
                String line;
                while ((line = reader.readLine()) != null) transBuilder.append(line).append("\n");
                transBuilder.delete(transBuilder.length()-1, transBuilder.length());
            } catch (IOException e) {
                System.out.println("Something went wrong trying to fetch the internal ressources, welp :(");
                e.printStackTrace();
                return false;
            }

            writer.write(transBuilder.toString());

        } catch (IOException e) {
            System.out.println("Something went wrong trying to write to the Transition.java file!");
            e.printStackTrace();
            return false;
        }

        return true;

    }

}

//just realized that it might have been better to use streams and not this approach, but now it's too late welp