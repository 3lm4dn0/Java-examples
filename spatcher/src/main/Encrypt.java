package main;

import javax.crypto.spec.SecretKeySpec;	
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

/**
 *
 * Code based on bricef from github 
 * @link https://gist.github.com/bricef/2436364/
 *
 */
public class Encrypt {

    /* Change IV and encryptionKey here and on FileUtils. */
    static String IV = "AAAAAAAAAAAAAAAA";
    static String encryptionKey = "passphrase";

    public static void main(String[] args) {
        try {                        
            byte[] read = FileUtils.readFile(args[0]);
            
            if(read == null){
                System.out.println("No hay fichero");
                System.exit(-1);
            }
            
            System.out.print(args[0] + " encoding... ");
            System.out.println(read.length);
            
            byte[] cipher = encrypt(read, encryptionKey);
            
            FileUtils.createFile(args[1], cipher);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(byte[] plainText, String encryptionKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");        
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText);
    }

    public static byte[] decrypt(byte[] cipherText, String encryptionKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(cipherText);
    }
}
