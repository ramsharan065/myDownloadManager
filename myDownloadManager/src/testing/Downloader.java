/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

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
    
    //for partition download
    protected int totalSize;
    protected int Psize[];
    protected int Pdownloaded[];
    
    public Downloader(URL url){
        this.url = url;
        this.downloaded = 0;
        this.size = -1;
        
        download();
    }
    public Downloader(URL url,int dummy){
        this.url = url;
        Psize = new int[2];
        Pdownloaded = new int[2];
        Pdownload();
    }

    
    private void download() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes="+downloaded+"-");
            connection.connect();
            System.out.println(""+connection.getResponseCode());
            Object o = connection.getContent();
            System.out.println("headers \n" + connection.getHeaderFields());
            System.out.println("type "+ connection.getContentType());
            //System.out.println(" "+ connection.);
            System.out.println(""+(size=connection.getContentLength())/(1024.0*1024)+"MB");
            RandomAccessFile file = new RandomAccessFile("abcabc.pdf", "rw");
            file.seek(downloaded);
            InputStream stream = connection.getInputStream();
            while(true){
                byte b[] = new byte[1024*40];
                int read = stream.read(b);
                if (read ==-1) break;
                downloaded+=read;
                System.out.println("downloaded = "+downloaded/(1024.0) +"KB");
                System.out.println("remaining = "+(size - downloaded)/(1024.0) + "KB");
                file.write(b, 0, read);
//                try {
//                    Thread.sleep(1000);
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

    private void Pdownload() {
        HttpURLConnection connection[] = new HttpURLConnection[2];
        InputStream stream[] = new InputStream[2];
        try {
            connection[0] = (HttpURLConnection) url.openConnection();
            connection[0].setRequestProperty("Range", "bytes="+Pdownloaded[0]+"-");
            connection[0].connect();
            System.out.println(""+Pdownloaded[0]);
            totalSize = connection[0].getContentLength();
            System.out.println("total size = "+(totalSize/1024.0)+"KB");
            Psize[0] = totalSize/2;
            Psize[1] = totalSize - Psize[0];
            connection[1] = (HttpURLConnection) url.openConnection();
            connection[1].setRequestProperty("Range", "bytes="+(Psize[0])+"-");
            connection[1].connect();
            
            stream[0] = connection[0].getInputStream();
            stream[1] = connection[1].getInputStream();
            
            RandomAccessFile file0 = new RandomAccessFile("jpt.wma", "rw");
            RandomAccessFile file1 = new RandomAccessFile("jpt.wma", "rw");
            file0.seek(Pdownloaded[0]);
            file1.seek(Psize[0]);
            
            while(true){
                byte b0[] = new byte[1024*4];
                byte b1[] = new byte[1024*4];
                int read0 = stream[0].read(b0);
                int read1 = stream[1].read(b1);
                if (read0 ==-1 && read1==-1) break;
                if(read0!=-1){
                    Pdownloaded[0]+=read0;
                    file0.write(b0, 0, read0);
                }
                if(read1!=-1){
                    Pdownloaded[1]+=read1;
                    file1.write(b1, 0, read1);
                }
                downloaded = Pdownloaded[0]+Pdownloaded[1];
                if(downloaded>=totalSize+1) break;
                System.out.println("downloaded = "+(downloaded/1024.0)+"KB");
                System.out.println("remaining = "+((totalSize - downloaded)/1024.0)+"KB");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
