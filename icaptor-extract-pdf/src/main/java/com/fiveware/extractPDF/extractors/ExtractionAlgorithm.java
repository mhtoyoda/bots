package com.fiveware.extractPDF.extractors;

import com.fiveware.extractPDF.Page;
import com.fiveware.extractPDF.Table;

import java.util.List;


public interface ExtractionAlgorithm {

    List<? extends Table> extract(Page page);
    String toString();
    
}
