package com.fiveware.dsl.pdf.core;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;

public class ObjectExtractor {

    private PDDocument pdfDocument;

    public ObjectExtractor(PDDocument pdfDocument) throws IOException {
        this.pdfDocument = pdfDocument;
    }


    protected Page extractPage(Integer pageNumber) throws IOException {

        if (pageNumber > this.pdfDocument.getNumberOfPages() || pageNumber < 1) {
            throw new IndexOutOfBoundsException(
                    "Page number does not exist");
        }

        PDPage p = this.pdfDocument.getPage(pageNumber - 1);

        ObjectExtractorStreamEngine se = new ObjectExtractorStreamEngine(p);
        se.processPage(p);

        Utils.sort(se.characters);

        float w, h;
        int pageRotation = p.getRotation();
        if (Math.abs(pageRotation) == 90 || Math.abs(pageRotation) == 270) {
            w = p.getCropBox().getHeight();
            h = p.getCropBox().getWidth();
        } else {
            w = p.getCropBox().getWidth();
            h = p.getCropBox().getHeight();
        }

        return new Page(0, 0, w, h, pageRotation, pageNumber, p, se.characters,
                se.rulings, se.minCharWidth, se.minCharHeight, se.spatialIndex);
    }

    public PageIterator extract(Iterable<Integer> pages) {
        return new PageIterator(this, pages);
    }

    public PageIterator extract() {
        return extract(Utils.range(1, this.pdfDocument.getNumberOfPages() + 1));
    }

    public Page extract(int pageNumber) {
        return extract(Utils.range(pageNumber, pageNumber + 1)).next();
    }

    public void close() throws IOException {
        this.pdfDocument.close();
    }

}
