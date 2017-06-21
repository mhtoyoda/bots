package com.fiveware.tabula.extractors;

import com.fiveware.tabula.Page;
import com.fiveware.tabula.Table;

import java.util.List;


public interface ExtractionAlgorithm {

    List<? extends Table> extract(Page page);
    String toString();
    
}
