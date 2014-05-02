package window;

import java.awt.event.ActionEvent;	
import java.awt.event.ActionListener;
import java.io.File;
//import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.FileUtils;
import main.Spatcher;
import main.Spatcher.IsoVersion;

public class PatchL implements ActionListener {

    private JFrame frame;
    private IsoVersion iv;
    private JTextField newFile;
    private String srcPath;
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private boolean removeCensure;
    
    private static PatchL _singleton = null;

    /**
     * Get Singleton
     * 
     * @param window
     * @param iv
     * @param newFile
     * @param removeCensure 
     * @return
     */
    public static PatchL getPatchLayer(JFrame window, IsoVersion iv,
            String srcPath, JTextField newFile, JTextArea textArea, JFileChooser fileChooser, boolean removeCensure) {
        if (_singleton == null) {
            _singleton = new PatchL(window, iv, srcPath, newFile, textArea, fileChooser, removeCensure);
        }

        return _singleton;
    }

    /**
     * Private Constructor
     * 
     * @param frame
     * @param iv
     * @param newFile
     * @param removeCensure 
     */
    private PatchL(JFrame frame, IsoVersion iv, String srcPath,
            JTextField newFile, JTextArea textArea, JFileChooser fileChooser, boolean removeCensure) {
        this.frame = frame;
        this.iv = iv;
        this.srcPath = srcPath;
        this.newFile = newFile;
        this.textArea = textArea;
        this.fileChooser = fileChooser;
        this.removeCensure = removeCensure;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {        
        if (iv != null) {
            // Demonstrate "Save" dialog:
            int rVal = fileChooser.showSaveDialog(frame);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getName();
                String dir = fileChooser.getCurrentDirectory().toString();
                String dstPath = dir + File.separator + fileName;

                // Patch ISO
                byte[] fileData;
                try {
                    // Set new file
                    newFile.setText(fileName);

                    // Send textArea
                    Spatcher.setTextArea(textArea);
                    
                    // Patch iso
                    fileData = Spatcher.patchIso(FileUtils.readFile(srcPath),
                            iv, removeCensure);

                    // Create new ISO
                    FileUtils.createFile(dstPath, fileData);

                    // Show data
                    textArea.append("\r\nImagen parcheada: " + dstPath);

                    // Show dialog
                    SuccessfullDialog d = new SuccessfullDialog("");
                    d.setVisible(true);
                    
                    // reset
                    iv = null;

                } catch (Exception e1) {
                    e1.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }

    public static void setIsoVersion(IsoVersion iv) {
        _singleton.iv = iv;
    }

    public static void setSrcPath(String srcPath) {
        _singleton.srcPath = srcPath;
    }

}