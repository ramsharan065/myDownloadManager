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
        URL url = new URL("http://localhost/test/LFS-BOOK-7.2.pdf");
        Downloader d = new Downloader(url);
        
        //System.out.println(""+url.getFile());
    }
}
