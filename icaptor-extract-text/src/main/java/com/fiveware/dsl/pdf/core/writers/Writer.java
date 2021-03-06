package com.fiveware.dsl.pdf.core.writers;

import com.fiveware.dsl.pdf.core.Table;

import java.io.IOException;
import java.util.List;


public interface Writer {
    void write(Appendable out, Table table) throws IOException;
    void write(Appendable out, List<Table> tables) throws IOException;
}
