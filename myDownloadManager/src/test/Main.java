/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import core.Downloader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author ramsharan
 */
public class Main {
    public static void main(String string[]) throws MalformedURLException{

        //URL url = new URL("http://localhost/test/LFS-BOOK-7.2.pdf");
        //URL url = new URL("http://d19vezwu8eufl6.cloudfront.net/images/recoded_videos%2F06_05%20-%20Video%20Level%20Sets%20and%20Curve%20Evolution%20%5Bf3f5e6e2%5D%20.mp4");
        //URL url = new URL("http://photography.nationalgeographic.com/staticfiles/NGS/Shared/StaticFiles/Photography/Images/Content/mountain-hut-584962-sw.jpg");
        //Downloader d = new Downloader(url);

        
        if(string.length!=0){
            URL url = new URL(string[0]);
            String file = url.getFile();
            System.out.println(""+file);
            Downloader d = new Downloader(url);
        }
        else {
            System.out.println("Enter the link of the file to download");
        }
        
        //System.out.println(""+file.substring(file.lastIndexOf('/')+1));
        //System.out.println(""+url.getFile());
    }
}
