package main;

import java.io.ByteArrayInputStream;	
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    public static byte[] readFile(String filename) {
        File file = new File(filename);
        byte[] fileData = new byte[(int) file.length()];

        DataInputStream dis;
        try {
            dis = new DataInputStream(new FileInputStream(file));

            dis.readFully(fileData);

            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

    public static ZipInputStream readZip() throws IOException 
    {                
	/* Change Encryption passphrase from your '.file.dat' */
        String pass = "passphrase";
        
        try {
            
            // In Resources File.separator is always '/'
            InputStream input = FileUtils.class.getResourceAsStream('/'
                    + "resources" + '/' + ".file.dat");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();       

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = input.read(data, 0, data.length)) != -1) {
              buffer.write(data, 0, nRead);
            }
            buffer.flush();                  
            
            byte[] descriptedData = Encrypt.decrypt(buffer.toByteArray(), pass);
            
            InputStream newData = new ByteArrayInputStream(descriptedData);
            
            return new ZipInputStream(newData);   
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        throw new IOException("File dont exists.");
    }

    public static byte[] readResourceZip(String resourceName,
            ZipInputStream stream) throws IOException 
    {      
        InputStream input = FileUtils.class.getResourceAsStream('/'
                + "resources" + '/' + ".file.dat");
        ZipEntry entry;

        try {
            byte[] aux = new byte[2048];
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            while ((entry = stream.getNextEntry()) != null) {
                if (entry.toString().equals(resourceName)) {
                    int len = 0;
                    while ((len = stream.read(aux)) > 0) {
                        output.write(aux, 0, len);
                    }

                    byte[] buffer = output.toByteArray();

                    input.close();
                    output.close();

                    return buffer;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IOException(resourceName + " not found.");
    }

    public static byte[] readResource(String resourceName) throws IOException {
        InputStream input = FileUtils.class.getResourceAsStream('/'
                + "resources" + '/' + resourceName);

        if (input == null) {
            throw new IOException("File not found.");
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (int read = input.read(); read >= 0; read = input.read()) {
            output.write(read);
        }

        byte[] buffer = output.toByteArray();

        input.close();
        output.close();

        return buffer;
    }

    public static byte[] subStr(String filename, int offset, int len) {
        File file = new File(filename);
        byte[] fileData = new byte[len];

        DataInputStream dis;
        try {
            dis = new DataInputStream(new FileInputStream(file));

            dis.read(fileData, 0, len);

            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

    public static void createFile(String filename, byte[] fileData) {
        FileOutputStream fop = null;
        File file;

        try {

            file = new File(filename);
            fop = new FileOutputStream(file);

            // if file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            fop.write(fileData);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
