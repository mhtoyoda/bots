package com.fiveware.dsl.pdf.core.detectors;

import com.fiveware.dsl.pdf.core.Page;
import com.fiveware.dsl.pdf.core.Rectangle;

import java.util.List;


/**
 * Created by matt on 2015-12-14.
 */
public interface DetectionAlgorithm {
    List<Rectangle> detect(Page page);
}
