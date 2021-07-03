/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.Utils;


import java.io.*;

/**
 *
 * @author Haiiinvv
 */
public class FileWriterExample {
    public static void main(String[] args) {
        try{
        // b1 Tạo đối tượng luồng và liên kết nguồn dữ liệu
   
            File f = new File("FileWriter_UD.txt");
            FileWriter fw = new FileWriter(f);
        //b2  ghi dữ liệu
            fw.write("Ngô văn Hải");
            fw.write("\n");
            fw.write("Nhiều tiền");
            
        // b3 Đóng 
            fw.close();
        }catch(IOException ex){
            System.out.println("Loi"+ex);
        }
    }
}
