package com.fiveware.core.extractors;

import com.fiveware.core.Page;
import com.fiveware.core.Table;

import java.util.List;


public interface ExtractionAlgorithm {

    List<? extends Table> extract(Page page);
    String toString();
    
}
