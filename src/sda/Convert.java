package sda;

import com.sun.javafx.PlatformUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static sda.Utils.*;

class Convert {


   void  convertFile(String name) throws IOException, ParseException, InterruptedException {
    String fileType = identFileType(name);
    String baseName = FilenameUtils.getBaseName(name);
    String path = FilenameUtils.getPath(name);
    if (fileType.equals("pdf")) {
       convertPDFFiles(name);
    }
   else if ((PlatformUtil.isMac()) && (fileType.equals("pages"))) {
        name = convertPagesOnMac(name, baseName);
        convertPDFFiles(name);
    }
   else if (fileType.equals("docx")|| fileType.equals("doc")){
       name = convertDocx(name, baseName);
        convertPDFFiles(name);
    }
    else if ((fileType.equals("doc") )|| (fileType.equals("docx")) || (fileType.equals("rtf")) || (fileType.equals("pages"))||(fileType.equals("odt"))) {
        String fileName = startOnlineConvert(name, fileType, path, baseName);
        convertPDFFiles(fileName);
    }


   }
   String identFileType(String name){
       return FilenameUtils.getExtension(name);
   }


   void convertPDFFiles (String name) throws IOException {
      PDDocument document = PDDocument.load(new File(name));
      PDFRenderer renderer = new PDFRenderer(document);
      String pathWithoutName = FilenameUtils.getPath(name);
      String fileName = FilenameUtils.getBaseName(name);
      StringBuilder r = new StringBuilder();
      for (int page = 0; page < document.getNumberOfPages(); ++page){

         BufferedImage src;
            src = renderer.renderImageWithDPI(page, 300, ImageType.RGB);

         // suffix in filename will be used as the file format
         ImageIOUtil.writeImage(src,   "/" +pathWithoutName + (page + 1) + " - " + fileName  + ".jpg", 300);
      }
      document.close();
   }

}
