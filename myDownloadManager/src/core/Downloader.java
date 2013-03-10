/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ramsharan
 */
public class Downloader {
    protected URL url;
    protected int size;
    protected int downloaded;

    
    public Downloader(URL url){
        this.url = url;
        this.downloaded = 0;
        this.size = -1;
        
        download();
    }

    
    private void download() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes="+downloaded+"-");
            connection.connect();

            int response = connection.getResponseCode();
            System.out.println("type : " + connection.getContentType());
            System.out.println(""+response);
            if((response/100)!=2) {
                System.out.println("Cannot download the file");
                return;
            }
            System.out.println(""+(size=connection.getContentLength())/(1024.0)+"KB");
            String fileAddress = url.getFile();
            String filename = fileAddress.substring(fileAddress.lastIndexOf('/')+1);
            if(filename.length()==0){
                filename = "Untitled";
                String extension = connection.getContentType();
                extension = extension.substring(extension.lastIndexOf('/')+1);
                filename = filename + "." + extension;
            }
            System.out.println("file name : "+ filename );
            //filename = "abbbbbbba.flv";
            RandomAccessFile file = new RandomAccessFile(filename, "rw");

            file.seek(downloaded);
            InputStream stream = connection.getInputStream();
            byte b[] = new byte[1024*120];
            while(true){

                int read = stream.read(b);
                if (read ==-1) break;
                downloaded+=read;

                //System.out.println("downloaded = "+downloaded/(1024.0) +"KB");
                //System.out.println("remaining = "+(size - downloaded)/(1024.0) + "KB");

                file.write(b, 0, read);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            System.out.println("file downloaded");
            connection.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(testing.Downloader.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
