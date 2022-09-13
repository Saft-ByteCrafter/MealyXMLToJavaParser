package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Input;
import util.Util;

public class XMLParser {

    private static String initialState;
    private static List<String> states;
    private static List<Input> inputs;

    public static boolean parseXML(File xmlFile) {

        try {

            states = new ArrayList<>();
            inputs = new ArrayList<>();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = builder.parse(xmlFile);
            xmlDocument.getDocumentElement().normalize();

            initialState = Util.strip(xmlDocument.getElementsByTagName("INITIALSTATE").item(0).getAttributes().item(0).getNodeValue());

            NodeList alphabet = xmlDocument.getElementsByTagName("ALPHABET").item(0).getChildNodes();
            int m = alphabet.getLength();

            for (int i = 0; ++i < m; i++) {
                String input = alphabet.item(i).getAttributes().item(0).getNodeValue();
                inputs.add(new Input(input));
            }

            NodeList allStates = xmlDocument.getElementsByTagName("STATE");
            m = allStates.getLength();

            for (int i = 0; i < m; i++) {
                Node stateNode = allStates.item(i);
                String state = Util.strip(stateNode.getAttributes().item(1).getNodeValue());
                states.add(state);

                NodeList stateTransitions = stateNode.getChildNodes();
                int n = stateTransitions.getLength();

                for (int j = 0; ++j < n; j++) {
                    Node transitionNode = stateTransitions.item(j);
                    String targetState = Util.strip(transitionNode.getAttributes().item(0).getNodeValue());

                    NodeList transitionLabels = transitionNode.getChildNodes();
                    int o = transitionLabels.getLength();

                    for (int k = 0; ++k < o; k++) {
                        Node labelNode = transitionLabels.item(k);
                        String input = labelNode.getAttributes().item(1).getNodeValue();
                        String output = labelNode.getAttributes().item(0).getNodeValue();

                        for (Input in : inputs) {
                            if (in.getInput().equals(input)) {
                                in.addToDelta(state, targetState);
                                in.addToLambda(state, output);
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | IOException e) {
            System.out.println("There was an error, are you certain you specified the correct path?");
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            System.out.println("Your xml file is malformed, please check it!");
            return false;
        }

        return true;

    }

    static String getInitialState(){
        return initialState;
    }

    static List<String> getStates(){
        return states;
    }

    static List<Input> getInputs() {
        return inputs;
    }
}
