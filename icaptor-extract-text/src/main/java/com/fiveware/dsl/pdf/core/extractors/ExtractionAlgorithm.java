package com.fiveware.dsl.pdf.core.extractors;

import com.fiveware.dsl.pdf.core.Page;
import com.fiveware.dsl.pdf.core.Table;

import java.util.List;


public interface ExtractionAlgorithm {

    List<? extends Table> extract(Page page);
    String toString();
    
}
