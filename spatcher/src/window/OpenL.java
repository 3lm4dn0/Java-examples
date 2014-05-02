package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Spatcher;
import main.Spatcher.IsoVersion;

public class OpenL implements ActionListener {

    private JFrame frame;
    private JTextField filename;
    private IsoVersion iv;
    private JTextField version;
    private String srcPath;
    private JTextArea textArea;
    private JFileChooser fileChooser;

    public OpenL(JFrame frame, JTextField filename, JTextField version,
            IsoVersion iv, String srcPath, JTextArea textArea, JFileChooser fileChooser) {
        this.frame = frame;
        this.filename = filename;
        this.version = version;
        this.iv = iv;
        this.srcPath = srcPath;
        this.textArea = textArea;
        this.fileChooser = fileChooser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        // Demonstrate "Open" dialog:
        int rVal = fileChooser.showOpenDialog(frame);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            String dir = fileChooser.getCurrentDirectory().toString();
            String file = fileChooser.getSelectedFile().getName();
            
            try {

                this.srcPath = dir + File.separator + file;
                iv = Spatcher.checkVersion(srcPath);
                filename.setText(file);

                // Set version to fieldText
                version.setText(iv.toString());

                // Set version and srcPath to PatchL
                PatchL.setIsoVersion(iv);
                PatchL.setSrcPath(srcPath);

                // Set text area info
                textArea.setText("Imagen original: "
                        + srcPath + "\r\nRegión: " + iv.toString());

            }
            catch (IOException e1) 
            {
                textArea.setText(file + " is not a Snatcher ISO.");
                filename.setText("");
                version.setText("");
            }
        }
    }

    /**
     * Get srcPath
     * 
     * @return
     */
    public String getSrcPath() {
        return srcPath;
    }

    /**
     * Get IsoVersion
     * 
     * @return
     */
    public IsoVersion getVersion() {
        return iv;
    }

}