package com.snksport.Utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

public class XImage {
    public static Image getAppIcon(){
        URL url = XImage.class.getResource("/icon/sneakers.png");
        return new ImageIcon(url).getImage();
    }
    
    public static void save(File src){
        File dst = new File("image", src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs(); //Tao thu muc image neu chua ton tai
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING); //Copy file vao thu muc image
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ImageIcon read(String fileName){
        File path = new File("image", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
}
