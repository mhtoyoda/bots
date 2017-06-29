package com.fiveware.core.writers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public class TSVWriter extends CSVWriter {
    
    @Override
    void createWriter(Appendable out) {
        try {
            this.printer = new CSVPrinter(out, CSVFormat.TDF);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
