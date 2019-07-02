import java.nio.file.*;
import java.io.*;

public class Exp2 {
    public static void main(String[] args) {
        String input;
        PrintWriter output;

        /** Calls the 'readFile' and 'writeToaFile' methods to
         * read input file into a String and open the given
         * output file(creates if there is no output file.).*/
        try {
            String inputFile = args[0];
            String outputFile = args[1];

            input = readFile(inputFile);
            output = writeToaFile(outputFile);

            OperationHandler.handleOperation(input, output);
            if (output != null) {
                output.close();
            }
        } catch (Exception err) {
            System.out.println("File couldn't be found.");
        }
    }

    /**
     * Reads the given file into a String.
     */
    private static String readFile(String fileName) throws Exception {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    /**
     * Opens the given output file. If there is no file
     * with the given file name then creates a new one.
     */
    private static PrintWriter writeToaFile(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter buffWriter = new BufferedWriter(fw);

            return new PrintWriter(buffWriter);
        } catch (IOException e) {
            return null;
        }
    }


}
