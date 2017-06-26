package com.fiveware.extractPDF.detectors;

import com.fiveware.extractPDF.Page;
import com.fiveware.extractPDF.Rectangle;

import java.util.List;


/**
 * Created by matt on 2015-12-14.
 */
public interface DetectionAlgorithm {
    List<Rectangle> detect(Page page);
}
