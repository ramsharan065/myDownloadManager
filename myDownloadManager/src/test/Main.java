/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import core.Downloader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author ramsharan
 */
public class Main {
    public static void main(String string[]) throws MalformedURLException{
        URL url = new URL("http://localhost/test/Untitled.wma");
        //URL url = new URL("http://d19vezwu8eufl6.cloudfront.net/images/recoded_videos%2F06_05%20-%20Video%20Level%20Sets%20and%20Curve%20Evolution%20%5Bf3f5e6e2%5D%20.mp4");
        //URL url = new URL("http://photography.nationalgeographic.com/staticfiles/NGS/Shared/StaticFiles/Photography/Images/Content/mountain-hut-584962-sw.jpg");
        Downloader d = new Downloader(url);
        
        //System.out.println(""+url.getFile());
    }
}
