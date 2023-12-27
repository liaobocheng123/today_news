package com.heima;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class Applcation {
    public static void main(String[]args) throws TesseractException {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\ProgrammingStudy");
        tesseract.setLanguage("chi_sim");
        File file = new File("D:\\test.png");
        String result = tesseract.doOCR(file);
        System.out.println("识别的结果为" + result.replaceAll("\\r|\\n","-"));
    }
}
