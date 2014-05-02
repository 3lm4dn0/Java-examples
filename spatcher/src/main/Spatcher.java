package main;

import java.io.IOException;	
import java.util.zip.ZipInputStream;

import javax.swing.JTextArea;

import window.Window;

/**
 * @author D.Albela
 * 
 */
public class Spatcher {

    /* for use in window mode */
    // TODO change to standard output
    private static JTextArea textArea;

    private final static String title = "Sega Mega CD Snatcher Patcher";
    private final static String version = "0.1";
    
    public static enum IsoVersion {
        PAL, NTCS
    };

    private static boolean textModeActive = false;

    private static String autor = "Autor name";
    private static String translate = "Translator name";

    private static String thanks = "Staff or thanks";

    private static String website = "Website";
    private static String project = "https://github.com/3lm4dn0/Sega-CD-Snatcher-Language-Patcher";

    /**************************************/

    /**
     * Path iso
     * 
     * @param bs
     * @param version
     * @param censured 
     * @return
     * @throws IOException
     */
    public static byte[] patchIso(byte[] bs, IsoVersion version, boolean removeCensure)
            throws IOException {

        String[] binaryFiles; 
      
        
        /* Replace DATA_P0.BIN without censure 
         * Use same order
         * DATA_D1.BIN: Intro game
         * DATA_G0.BIN: Diary disk PC89
         * DATA_P0.BIN: Uncensured Katrina at bathroom
         * */
        if( removeCensure )
        {
          binaryFiles = new String[] { 
              "DATA_D1.BIN", "DATA_G0.BIN", "DATA_P0.BIN", "SP00.BIN", "SP01.BIN",
              "SP02.BIN", "SP03.BIN", "SP04.BIN", "SP05.BIN", "SP06.BIN",
              "SP07.BIN", "SP08.BIN", "SP09.BIN", "SP10.BIN", "SP11.BIN",
              "SP12.BIN", "SP13.BIN", "SP14.BIN", "SP15.BIN", "SP16.BIN",
              "SP17.BIN", "SP18.BIN", "SP19.BIN", "SP20.BIN", "SP21.BIN",
              "SP22.BIN", "SP23.BIN", "SP24.BIN", "SP25.BIN", "SP26.BIN",
              "SP27.BIN", "SP28.BIN", "SP29.BIN", "SP30.BIN", "SP32.BIN",
              "SP33.BIN", "SP34.BIN", "SP35.BIN", "SP36.BIN", "SP37.BIN",
              "SP38.BIN" };
        }else{
          binaryFiles = new String[] { 
              "DATA_D1.BIN", "DATA_G0.BIN", "SP00.BIN", "SP01.BIN",
              "SP02.BIN", "SP03.BIN", "SP04.BIN", "SP05.BIN", "SP06.BIN",
              "SP07.BIN", "SP08.BIN", "SP09.BIN", "SP10.BIN", "SP11.BIN",
              "SP12.BIN", "SP13.BIN", "SP14.BIN", "SP15.BIN", "SP16.BIN",
              "SP17.BIN", "SP18.BIN", "SP19.BIN", "SP20.BIN", "SP21.BIN",
              "SP22.BIN", "SP23.BIN", "SP24.BIN", "SP25.BIN", "SP26.BIN",
              "SP27.BIN", "SP28.BIN", "SP29.BIN", "SP30.BIN", "SP32.BIN",
              "SP33.BIN", "SP34.BIN", "SP35.BIN", "SP36.BIN", "SP37.BIN",
              "SP38.BIN" };        
        }

        // Files Table from 0xA000 (40960) to 0x0B26A (45674)
        byte[] filesTable = ByteUtils.subByte(bs, 40960, 45674 - 40960);        

        // Read files
        ZipInputStream zip = FileUtils.readZip();
        
        // Replace files
        for (String file : binaryFiles) {
            patchFile(bs, file, file, filesTable, zip);
        }

        // Replace subcode
        if (version.equals(IsoVersion.PAL)) {
            patchFile(bs, "SUBCODE_E.BIN", "SUBCODE.BIN", filesTable, zip);
        } else if (version.equals(IsoVersion.NTCS)) {
            patchFile(bs, "SUBCODE_U.BIN", "SUBCODE.BIN", filesTable, zip);
        }        

        // Replace files table in bs
        ByteUtils.replaceFile(bs, filesTable, 40960, 45674 - 40960);

        return bs;
    }

    /**
     * Replace one file
     * 
     * @param bs
     *            original ISO code
     * @param srcFile
     *            source from ISO file
     * @param dstFile
     *            destination ISO
     * @param filesTable
     *            table from files in ISO
     * @param zip
     *            file zipped
     * @throws IOException
     */
    private static void patchFile(byte[] bs, String srcFile, String dstFile,
            byte[] filesTable, ZipInputStream zip) throws IOException {
        int pos, oriFileSize, offset;
        byte[] sector_LE, size_LE, bfile;

        // Get the position from file
        pos = ByteUtils.indexOf(filesTable, dstFile.getBytes());

        // Get data from table file LittleEndian
        sector_LE = ByteUtils.subByte(filesTable, pos - 27, 4);
        size_LE = ByteUtils.subByte(filesTable, pos - 19, 4);

        // Get original filesize from Files Table
        oriFileSize = (int) Long.parseLong(ByteUtils.toHexString(size_LE), 16);

        // Show data
        String infoFile = new String("Patching " + dstFile + "...");
        if (textModeActive) {
            System.out.print(infoFile);
        } else {
            textArea.append("\r\n" + infoFile);
        }

        // Get original offset from binary file
        offset = (int) Long.parseLong(ByteUtils.toHexString(sector_LE), 16) * 2048;

        // Read file from resources
        bfile = FileUtils.readResourceZip(srcFile, zip);

        // Show file size
        String infoSize = " (original filesize " + oriFileSize + " / new filesize " + bfile.length + ")";
        if (textModeActive) {
            System.out.println(infoSize);
        } else {
            textArea.append("\r\n" + infoSize);
        }

        // Replaces data from bs with bfile in sector_LE and length oriFileSize
        ByteUtils.replaceFile(bs, bfile, offset, oriFileSize);

        // Replace table
        if (bfile.length != oriFileSize) {
            byte[] newFileSize = ByteUtils.toByteArray(String.format("%08X",
                    ByteUtils.little2big(bfile.length) & 0xFFFFFFFF)
                    + String.format("%08X", bfile.length & 0xFFFFFFFF));

            ByteUtils.replaceFile(filesTable, newFileSize, pos - 23, 8);
        }
    }

    /**
     * Get Version
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public static IsoVersion checkVersion(String filename) throws IOException {
        if (textModeActive) {
            System.out.print("Checking iso " + filename + "... ");
        }

        byte[] fileData = FileUtils.subStr(filename, 0, 256);

        String pal = "534547414449534353595354454D20205345474149504D454E552000010000014B4F4E414D492053303032000001000000000800000060000000000000000000000068000000180000000000000000003130313231393934202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020";
        String usa = "534547414449534353595354454D20205345474149504D454E552000010000014B4F4E414D492052303032000001000000000800000060000000000000000000000068000000180000000000000000003130313031393934202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020";

        if (ByteUtils.toHexString(fileData).equalsIgnoreCase(pal)) {
            return IsoVersion.PAL;
        }

        if (ByteUtils.toHexString(fileData).equalsIgnoreCase(usa)) {
            return IsoVersion.NTCS;
        }

        String error = filename + " is not a sega cd snatcher iso.";
        throw new IOException(error);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Text mode
        if( (args.length > 0) && (args[0].equals("-T")) ){
            textModeActive = true;
            textMode(args);
            return;
        }

        // Window mode
        new Window(title, version, autor, translate, thanks, project,
                website);
    }

    private static void textMode(String[] args)
    {
        boolean removeCensure = false;
        String removeCensureText = "--removeCensure";
      
        if (args.length < 3) {
            System.out.println(title);
            System.out.println("Version " + version);
            System.out.println();
            System.out.println("Usage: java main.spatcher [-T] original.iso patched.iso ["+removeCensureText+"]");

            System.out.println();

            System.out.println("Author: " + autor);
            System.out.println();
            System.out.println("Translate team:\n" + translate);
            System.out.println();
            System.out.println("Thanks to:\n" + thanks);
            System.out.println();
            System.out.println("Website:" + website);
            System.out.println("Translator tool: " + project);  

            return;
        }

        // Patch ISO
        byte[] fileData;
        try {
            String src = args[1];
            String dst = args[2];
            
            if( (args.length > 3) && (args[3].equals(removeCensureText)) )
            {
              removeCensure = true;
            }

            IsoVersion iv = checkVersion(src);

            System.out.println(iv + " version.");

            // Patch ISO
            fileData = patchIso(FileUtils.readFile(src), iv, removeCensure);

            // Create new ISO
            FileUtils.createFile(dst, fileData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set JTextComponent
     * @param jText
     */
    public static void setTextArea(JTextArea jText) {
        textArea = jText;
    }

}
