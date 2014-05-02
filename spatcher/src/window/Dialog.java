package window;

import java.awt.BorderLayout;	
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Dialog extends JDialog {

    private String title;
    private String autor;
    private String version;
    private String translate;
    private String agradecimientos;
    private String project;
    private String website;

    public Dialog(String title, String version, String autor, String translate,
            String agradecimientos, String project, String website) {
        super(new Frame(), "Acerca de...", true);

        this.title = title;
        this.version = version;
        this.autor = autor;
        this.translate = translate;
        this.agradecimientos = agradecimientos;
        this.project = project;
        this.website = website;

        // Configure dialog
        addLabel();
        setSize(250, 250);
        setResizable(true);        
        pack();

        // Center
        Dimension pantalla, cuadro;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        cuadro = this.getSize();
        this.setLocation(((pantalla.width - cuadro.width) / 2),
                (pantalla.height - cuadro.height) / 2);
        
        setVisible(true);
    }

    public void addLabel() {
        Container cp = getContentPane();

        JPanel p = new JPanel();
        JButton aceptar = new JButton("Aceptar");
        p.add(aceptar);
        // Action event
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dispose();
            }
        });
        cp.add(p, BorderLayout.SOUTH);

        // Title, version and autor
//        p = new JPanel();
//        p.setLayout(new GridLayout(1, 2, 4, 4));
//        int eb = 10;
//        p.setBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb));                
//
//        p.add(new JLabel(title));
//        p.add(new JLabel(version));
//        
//        cp.add(p, BorderLayout.NORTH);        

        p = new JPanel();
        int eb = 10;
        p.setBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb));
        
        String SEPARATOR = "\r\n"; 
        
        String data = 
                title + " versión " + version +
                SEPARATOR + SEPARATOR + "Autor: " + autor +
                SEPARATOR + SEPARATOR + "Equipo de traducción:" + SEPARATOR + convertToMultiline(translate) +
                SEPARATOR + SEPARATOR + "Agradecimientos a: " + SEPARATOR + convertToMultiline(agradecimientos);

        p.add(new JLabel(convertToMultilineHtmlWidthLimit(data)));
        cp.add(p, BorderLayout.NORTH);
        
        // Data links
        p = new JPanel(new BorderLayout());
        p.setLayout(new GridLayout(4, 1, 5, 5));
        eb = 20;
        p.setBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb));
        
        p.add(new JLabel("Foro del proyecto: "));
        p.add(goWebsite(website));
        
        p.add(new JLabel("Herramienta de traducción: "));
        p.add(goWebsite(project));

        cp.add(p);
    }

    public static String convertToMultiline(String orig) {
        return orig.replaceAll("\n", "\r\n");
    }
    
    public static String convertToMultilineHtmlWidthLimit(String orig) {
        int width = 350;
        return String.format("<html><div style=\"width:%dpx;\">%s</div><html>",
                width, orig.replaceAll("\t", "  - ").replaceAll("\r\n", "<br />"));
    }
    
    public static String convertToMultilineHtml(String orig) {
        return String.format("<html>%s<html>",
                orig.replaceAll("\t", "  - ").replaceAll("\r\n", "<br />"));
    }
    
    public static String addLink(String href)
    {
        return new String("<a href=\"" + href + "\">" + href + "</a>");
    }
    
    private static JLabel goWebsite(final String url)
    {        
        JLabel website = new JLabel();
        
        website.setText("<html><a href=\"\">"+url+"</a></html>");
        website.setCursor(new Cursor(Cursor.HAND_CURSOR));
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI(url));
                    } catch (URISyntaxException | IOException ex) {
                            //It looks like there's a problem
                    }
            }
        });
        
        return website;
    }    

}