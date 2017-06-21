package com.fiveware.tabula.detectors;

import com.fiveware.tabula.Page;
import com.fiveware.tabula.Rectangle;

import java.util.List;


/**
 * Created by matt on 2015-12-14.
 */
public interface DetectionAlgorithm {
    List<Rectangle> detect(Page page);
}
