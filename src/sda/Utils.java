package sda;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.aioobe.cloudconvert.CloudConvertService;
import org.aioobe.cloudconvert.ConvertProcess;
import org.aioobe.cloudconvert.ProcessStatus;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Utils {

    static String convertPagesOnMac(String name, String baseName) throws IOException, InterruptedException {
        File file = new File(name);
        String[] args = {"/usr/bin/osascript", "pagesConvertor.scpt", file.getParent() + "/", file.getName()};
        Process result = Runtime.getRuntime().exec(args);
        result.waitFor();
        return (file.getParent() + "/" + baseName + ".pdf");
    }

    static String startOnlineConvert(String name, String fileType, String path, String baseName) throws IOException, ParseException, InterruptedException {
        CloudConvertService service = new CloudConvertService("ndpcvQGVyeM6eT8cLqqNStOQYa7IC7c08YRsr6Gt7USLXQSdhbA14C2pg1IUmcwx");
        ConvertProcess process = null;
        try {
            process = service.startProcess(fileType, "pdf");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert process != null;
        process.startConversion(new File(name));
        // Wait for result
        ProcessStatus status;
        waitLoop:
        while (true) {
            status = process.getStatus();

            switch (status.step) {
                case FINISHED:
                    break waitLoop;
                case ERROR:
                    throw new RuntimeException(status.message);
            }
            Thread.sleep(200);
        }

        service.download(status.output.url, new File("/" + path + baseName + ".pdf"));
        process.delete();
        return ("/" + path + baseName + ".pdf");
    }

    static String convertDocx(String name, String baseName) throws IOException {
        File inputWord = new File(name);
        String path = inputWord.getParent() + "/"+ baseName + ".pdf";
        File outputFile = new File(path);
       // System.out.println("inputFile:" + name + ",outputFile:"+ outputFile);
        FileInputStream in=new FileInputStream(name);
        XWPFDocument document=new XWPFDocument(in);
        File outFile= outputFile;
        OutputStream out=new FileOutputStream(outFile);
        PdfOptions options=null;
        PdfConverter.getInstance().convert(document,out,options);
        return outputFile.getParent() + "/"+ baseName + ".pdf";
   }

}
