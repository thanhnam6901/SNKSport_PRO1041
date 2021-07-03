/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Haiiinvv
 */
public class FIleReaderExample {
        public static void main(String[] args) {
        try {
            // bươc 1 : tạo đối tượng luồng và liên kết nguồn dữ liệu
             File f = new File("FileWriter_UD.txt");
            FileReader fr = new FileReader(f);
            //bước 2: Đọc dữ liệu
             BufferedReader br = new BufferedReader(fr);
             
//             String s;
//             int i;
//             while((s=br.readLine())!=null){
////            nếu đọc đc null nghĩa là kết thúc Sream
//                i++;
//                 System.out.println(i+":"+s);
//        }
int ch = br.read();
while(ch != -1 ){
            //read till the end ò the stream
            System.out.println((char) ch);
            ch = br.read();
        }
//bước 3: đóng luồng

fr.close();
br.close();
        } catch (IOException e) {
            System.out.println("Loi"+e);
        }
    }
}
