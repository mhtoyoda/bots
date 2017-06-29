package com.fiveware.core.detectors;

import com.fiveware.core.Page;
import com.fiveware.core.Rectangle;

import java.util.List;


/**
 * Created by matt on 2015-12-14.
 */
public interface DetectionAlgorithm {
    List<Rectangle> detect(Page page);
}
