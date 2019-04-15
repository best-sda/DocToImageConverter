package sda;

import org.aioobe.cloudconvert.CloudConvertService;
import org.aioobe.cloudconvert.ConvertProcess;
import org.aioobe.cloudconvert.ProcessStatus;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public class Utils{

   static String convertPagesOnMac(String name, String baseName) throws IOException, InterruptedException {
       File file = new File(name);
        String[] args =  {"/usr/bin/osascript", "pagesConvertor.scpt",  file.getParent() + "/", file.getName()};
        Process result = Runtime.getRuntime().exec(args);
       // Wait for result
      result.waitFor();
        return ( file.getParent() +"/" + baseName + ".pdf");
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

        service.download(status.output.url, new File("/"+path + baseName +".pdf"));
        process.delete();
        return ("/" + path + baseName +".pdf");
    }
}
