package org.just4fun.voc.util;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PDFReader {
   public static void main(String[] args) throws Exception {
      PDDocument document = PDDocument.load(new File("doc.pdf"));
      System.out.println(document.getNumberOfPages());

      PDFTextStripperByArea stripper = new PDFTextStripperByArea();
      stripper.setSortByPosition(true);
      PDFTextStripper Tstripper = new PDFTextStripper();
      String st = Tstripper.getText(document);
      System.out.println("Text:" + st);
   }

}
