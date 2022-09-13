import parser.FileParser;
import parser.XMLParser;

import java.io.File;

public class Main {


    public static void main(String[] args) {

        if (args == null || args[0].trim().isEmpty()) {

            System.out.println("Please specify the path to your XML-file as an argument!");
            return;

        } else {
            String fullPath = args[0];
            if (!XMLParser.parseXML(new File(fullPath))) return;
            String path = fullPath.substring(0, fullPath.length() - fullPath.split("\\.")[fullPath.split("\\.").length - 1].length() - 1);
            if (!FileParser.createFiles(path)) return;
        }

        System.out.println("Done :)\nJust remember that the sourcecode generated might\nnot actually compile due to bad Input/State names\nthis is made with the intent to reproduce already\nworking mealy-machines in java ^^");

    }

}
