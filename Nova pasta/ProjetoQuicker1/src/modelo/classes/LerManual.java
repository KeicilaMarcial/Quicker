/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.classes;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
/**
 *
 * @author Adonias
 */
public class LerManual {
    public static void main(String[] args) {

        try{

            PdfReader reader = new PdfReader("/manual/AVISOS-LIED.pdf");
            int n = reader.getNumberOfPages();
            System.out.println("Total de páginas para este pdf: "+n);

            //extraindo o conteúdo de uma página específica
            String str=PdfTextExtractor.getTextFromPage(reader, 1);
            System.out.println("Conteudo: "+str);

        }catch(Exception x){
            x.printStackTrace();
        }
    }
}
