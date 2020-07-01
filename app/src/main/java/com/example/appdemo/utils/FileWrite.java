package com.example.appdemo.utils;


import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileWrite {



    FileWrite()
    {
        try {
            File newFolder = new File(Environment.getExternalStorageDirectory(), "DemoProject");
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            try {
                File file = new File(newFolder, "LastPayment" + ".txt");
                file.createNewFile();
            } catch (Exception ex) {
                System.out.println("ex: " + ex);
            }
        } catch (Exception e) {
            System.out.println("e: " + e);
        }
    }

    public static void Deletefile()
    {
        File newFolder = new File(Environment.getExternalStorageDirectory(), "DemoProject");
        if (!newFolder.exists()) {
            newFolder.mkdir();
        }
        try {
            File file = new File(newFolder, "LastPayment" + ".txt");
            if (file.exists()) {

                file.delete();
                file.deleteOnExit();
            }
        }
        catch (Exception e)
        {

        }
    }

    public static String readUsingFileInputStream() {

        File newFolder = new File(Environment.getExternalStorageDirectory(), "DemoProject");
        if (!newFolder.exists()) {
            newFolder.mkdir();
        }

        String fileName=Environment.getExternalStorageDirectory().toString()+"/DemoProject/LastPayment.txt";
        FileInputStream fis = null;
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        try {
            fis = new FileInputStream(fileName);

            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return sb.toString();
    }

    public static void writedata(String infomation)
    { try {



        File newFolder = new File(Environment.getExternalStorageDirectory(), "DemoProject");
        if (!newFolder.exists()) {
            newFolder.mkdir();
        }
        try {
            File file = new File(newFolder, "LastPayment" + ".txt");
            if(file.exists())
            {
                try
                {

                    OutputStreamWriter writer = new OutputStreamWriter(
                            new FileOutputStream(file, true), "UTF-8");
                    BufferedWriter fbw = new BufferedWriter(writer);
                    fbw.write("\n"+infomation );
                    fbw.newLine();
                    fbw.close();
                } catch(Exception e)
                {
                    System.out.println("ex: " + e);
                }
            }
            else
            {
                FileOutputStream fos=  new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString()+"/DemoProject/LastPayment.txt"));
                fos.flush();
                fos.close();
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                Date date = new Date();
                String formattedDateTime = dateFormat.format(date);
                OutputStreamWriter writer = new OutputStreamWriter(
                        new FileOutputStream(file, true), "UTF-8");
                BufferedWriter fbw = new BufferedWriter(writer);
                fbw.write("\n"+infomation);
                fbw.newLine();
                fbw.close();
            }
        } catch (Exception ex) {
            System.out.println("ex: " + ex);
        }
    } catch (Exception e) {
        System.out.println("e: " + e);
    }

    }


}