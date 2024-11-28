import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class MacroProcessorPass1 {
    public static void main(String[] args) {
        try (
            BufferedReader br = new BufferedReader(new FileReader("source2.asm"));
            FileWriter mnt = new FileWriter("mnt.txt");
            FileWriter mdt = new FileWriter("mdt.txt");
            FileWriter kpdt = new FileWriter("kpdt.txt");
            FileWriter pnt = new FileWriter("pntab.txt");
            FileWriter ir = new FileWriter("intermediate.txt")
        ) {
            LinkedHashMap<String, Integer> pntab = new LinkedHashMap<>();
            String line, macroname = null;
            int mdtp = 1, kpdtp = 0, paramNo = 1, pp = 0, kp = 0, ag = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");

                // Detect macro start
                if (parts[0].equalsIgnoreCase("MACRO")) {
                    ag = 1;
                    line = br.readLine();
                    parts = line.split("\\s+");
                    macroname = parts[0];
                    pp = kp = 0;
                    paramNo = 1;

                    // Process parameters
                    if (parts.length > 1) {
                        for (int i = 1; i < parts.length; i++) {
                            parts[i] = parts[i].replaceAll("[&,]", "");
                            if (parts[i].contains("=")) { // Keyword parameter
                                kp++;
                                String[] keywordParam = parts[i].split("=");
                                pntab.put(keywordParam[0], paramNo++);
                                kpdt.write(keywordParam[0] + "\t" + (keywordParam.length > 1 ? keywordParam[1] : "-") + "\n");
                            } else { // Positional parameter
                                pp++;
                                pntab.put(parts[i], paramNo++);
                            }
                        }
                    }

                    // Write macro name and parameter details to MNT
                    mnt.write(macroname + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + (kp == 0 ? kpdtp : (kpdtp + 1)) + "\n");
                    kpdtp += kp;
                } else if (parts[0].equalsIgnoreCase("MEND")) {
                    mdt.write("MEND\n");
                    ag = pp = kp = 0;
                    mdtp++;

                    // Write PNTAB entries for this macro
                    pnt.write(macroname + ":\t");
                    for (String param : pntab.keySet()) {
                        pnt.write(param + "\t");
                    }
                    pnt.write("\n");
                    pntab.clear();
                } else if (ag == 1) {
                    // Inside macro body
                    for (String part : parts) {
                        if (part.contains("&")) { // Replace parameters with PNTAB entries
                            part = part.replaceAll("[&,]", "");
                            mdt.write("(P," + pntab.get(part) + ")\t");
                        } else {
                            mdt.write(part + "\t");
                        }
                    }
                    mdt.write("\n");
                    mdtp++;
                } else {
                    // Outside any macro definition, write to intermediate file
                    ir.write(line + "\n");
                }
            }

            System.out.println("Macro Pass 1 Processing done.");
        } catch (IOException e) {
            System.err.println("File processing error: " + e.getMessage());
        }
    }
}
