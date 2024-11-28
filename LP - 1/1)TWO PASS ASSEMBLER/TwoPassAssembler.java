import java.io.*;
import java.util.*;

public class TwoPassAssembler {

    // Data structures for symbol table, literal table, and intermediate code
    private static Map<String, Integer> symbolTable = new HashMap<>(); // Symbol Table
    private static List<String> literalTable = new ArrayList<>(); // Literal Table
    private static List<String> intermediateCode = new ArrayList<>(); // Intermediate Code
    private static Map<String, String> opcodeTable = new HashMap<>(); // Opcode table

    // Static block to initialize the opcode table
    static {
        opcodeTable.put("LOAD", "01");
        opcodeTable.put("ADD", "02");
        opcodeTable.put("STORE", "03");
        opcodeTable.put("END", "04");
    }

    public static void main(String[] args) {
        String sourceFilename = "source.asm"; // The source file containing assembly code

        // Pass I
        passI(sourceFilename);
    }

    // Pass I: Process the assembly source code and generate intermediate code
    private static void passI(String filename) {
        try (BufferedReader sourceFile = new BufferedReader(new FileReader(filename))) {
            String line;
            int locationCounter = 0;

            // Read each line from the source file
            while ((line = sourceFile.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String label = parts.length > 0 ? parts[0] : "";
                String opcode = parts.length > 1 ? parts[1] : "";
                String operand = parts.length > 2 ? parts[2] : "";

                // Process START directive
                if (opcode.equals("START")) {
                    locationCounter = Integer.parseInt(operand);
                    continue; // Skip the rest for START
                }

                // Process the label (add to symbol table)
                if (!label.isEmpty() && !opcode.equals("END")) {
                    symbolTable.put(label, locationCounter);
                }

                // Process the opcode and add to intermediate code
                if (opcode.equals("END")) {
                    intermediateCode.add(locationCounter + "\t" + opcode);
                    break; // End of processing
                } else {
                    intermediateCode.add(locationCounter + "\t" + opcode + "\t" + operand);
                    locationCounter += 1; // Increment for each instruction
                }

                // Identify literals and add to the literal table
                if (!operand.isEmpty() && operand.startsWith("=")) {
                    String literal = operand.substring(1); // Remove '='
                    if (!literalTable.contains(literal)) {
                        literalTable.add(literal);
                    }
                }
            }

            generateFiles(); // Generate files after Pass I
        } catch (IOException e) {
            System.err.println("Error opening source file: " + e.getMessage());
        }
    }

    // Method to generate the symbol table, literal table, and intermediate code files after Pass I
    private static void generateFiles() {
        // Write symbol table to a file
        try (BufferedWriter symbolFile = new BufferedWriter(new FileWriter("symbol_table.txt"))) {
            symbolFile.write("Symbol Table:\n");
            for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
                symbolFile.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing symbol table file: " + e.getMessage());
        }

        // Write literal table to a file
        try (BufferedWriter literalFile = new BufferedWriter(new FileWriter("literal_table.txt"))) {
            literalFile.write("Literal Table:\n");
            for (String literal : literalTable) {
                literalFile.write(literal + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing literal table file: " + e.getMessage());
        }

        // Write intermediate code to a file
        try (BufferedWriter intermediateFile = new BufferedWriter(new FileWriter("intermediate_code.txt"))) {
            intermediateFile.write("Intermediate Code:\n");
            for (String code : intermediateCode) {
                intermediateFile.write(code + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing intermediate code file: " + e.getMessage());
        }

        System.out.println("Output files generated: symbol_table.txt, literal_table.txt, intermediate_code.txt");
    }
}

