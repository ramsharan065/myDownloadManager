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
            System.out.println(""+connection.getResponseCode());
            System.out.println(""+connection.getContentLength()+"B");
            RandomAccessFile file = new RandomAccessFile("abcabc.pdf", "rw");
            file.seek(downloaded);
            InputStream stream = connection.getInputStream();
            while(true){
                byte b[] = new byte[1024];
                int read = stream.read(b);
                if (read ==-1) break;
                downloaded+=read;
                System.out.println(""+downloaded);
                file.write(b, 0, read);
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
            System.out.println("file downloaded");
            connection.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
