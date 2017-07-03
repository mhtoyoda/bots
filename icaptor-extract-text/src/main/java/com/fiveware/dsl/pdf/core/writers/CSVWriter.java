package com.fiveware.dsl.pdf.core.writers;

import com.fiveware.dsl.pdf.core.RectangularTextContainer;
import com.fiveware.dsl.pdf.core.Table;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVWriter implements Writer {
    
    CSVPrinter printer;
    private boolean useLineReturns = true;
    
//    public CSVWriter() {
//        super();
//    }
//    
//    public CSVWriter(boolean useLineReturns) {
//        super();
//        this.useLineReturns = useLineReturns;
//    }
    
    void createWriter(Appendable out) {
        try {
            this.printer = new CSVPrinter(out, CSVFormat.EXCEL);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void write(Appendable out, Table table) throws IOException {
        this.createWriter(out);
        for (List<RectangularTextContainer> row: table.getRows()) {
            List<String> cells = new ArrayList<String>(row.size());
            for (RectangularTextContainer tc: row) {
                cells.add(tc.getText());
            }
            this.printer.printRecord(cells);
        }
        printer.flush();
    }

	@Override
	public void write(Appendable out, List<Table> tables) throws IOException {
		for (Table table : tables) {
			write(out, table);
		}
		
	}

}
