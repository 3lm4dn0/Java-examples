package window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import main.Spatcher.IsoVersion;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private static String title;
    private static String version;
    private static String autor;
    private static String translate;
    private static String agradecimientos;
    private static String project;
    private static String website;

    private JMenuBar barra = new JMenuBar();
    private JMenu programa = new JMenu("Programa");
    private JMenu ayuda = new JMenu("Ayuda");
    private JMenuItem open = new JMenuItem("Seleccionar imagen");
    private JMenuItem patchMenu = new JMenuItem("Aplicar parche");
    private JMenuItem salir = new JMenuItem("Salir");
    private JMenuItem acerca = new JMenuItem("Acerca de");

    private JTextField origFile = new JTextField();
    private JTextField saveFile = new JTextField();
    private JTextField textFieldVersion = new JTextField();
    private JTextArea textArea = new JTextArea();

    private JFileChooser fileChooser = new JFileChooser();
    
        
    IsoVersion iv = null;
    String srcPath = null; /* Source file path from iso */
    String dstPath = null; /* Destination file path from new iso */
    boolean removeCensure = true; /* removeCensure game */

    /**
     * Constructor
     * 
     * @param title
     *            title from window
     * @param website
     * @param project
     * @param agradecimientos
     * @param translate
     * @param autor
     */
    public Window(String title, String version, String autor, String translate,
            String agradecimientos, String project, String website)
    {
        super(title + " " + version);

        Window.title = title;
        Window.version = version;
        Window.autor = autor;
        Window.translate = translate;
        Window.agradecimientos = agradecimientos;
        Window.project = project;
        Window.website = website;

        // Window Size
        setSize(550, 400);
        setAlwaysOnTop(isAlwaysOnTop());

        // Not resizable
        setResizable(false);

        // Add bar
        setJMenuBar(barra);
        barra.add(programa);
        barra.add(ayuda);

        programa.add(open);
        programa.add(patchMenu);
        programa.addSeparator();
        programa.add(salir);
        programa.setMnemonic('P'); // ALT + P

        ayuda.add(acerca);

        // Set Accessibility
        ayuda.setMnemonic('A'); // ALT + A

        // Exit on close
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Center
        Dimension pantalla, cuadro;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        cuadro = this.getSize();
        this.setLocation(((pantalla.width - cuadro.width) / 2),
                (pantalla.height - cuadro.height) / 2);

        // Tooltip
        salir.setToolTipText("Salir del Programa.");
        open.setToolTipText("Seleccionar imagen para parchear");
        patchMenu.setToolTipText("Crear nueva imagen parcheada");

        // Action about
        addActionAcercaDe(acerca);

        // Action exit
        addActionSalir(salir);

        // Action Open
        addActionOpen(open);

        // Action patch
        addActionPatch(patchMenu);

        // Add panel
        addPanel();

        // Set visible
        setVisible(true);
    }

    /**
     * Adds a panel in window
     */
    private void addPanel() {
        // Create Buttons
        JButton open = new JButton("Seleccionar imagen");
        open.setToolTipText("Seleccionar imagen original para parchear");
        addActionOpen(open);

        JButton patchButton = new JButton("Patch");
        patchButton.setToolTipText("Parchear la nueva imagen");
        addActionPatch(patchButton);
             
        JButton salir = new JButton("Salir");
        patchButton.setToolTipText("Salir del programa");
        addActionSalir(salir);

        Container cp = getContentPane();

        // Create Panel for Patch button
        JPanel p = new JPanel();
        p.add(patchButton);
        p.add(salir);
        cp.add(p, BorderLayout.SOUTH);

        // Create layer to select iso
        origFile.setEditable(false);
        saveFile.setEditable(false);
        textFieldVersion.setEditable(false);

        p = new JPanel(new BorderLayout());

        int eb = 20;
        p.setBorder(BorderFactory.createEmptyBorder(10, eb, eb, eb));
        p.setLayout(new GridLayout(4, 2, 4, 4));

        p.add(open);
        p.add(origFile);
        
        // Add row censure        
        addRowCensure(p);
        
        // Create new panel for censure
        p.add(new JLabel("Región: "));
        p.add(textFieldVersion);        

        p.add(new JLabel("Imagen destino: "));
        saveFile.setToolTipText("Pulsa en Patch para seleccionar la nueva imagen parcheada.");
        p.add(saveFile);

        cp.add(p, BorderLayout.NORTH);        

        // Add Textarea with scroll
        p = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane(textArea);
        p.add(scroll);       
        
        cp.add(p);
    }   

    /**
     * Add row censure
     * @param p
     */
    private void addRowCensure(JPanel p) {
      JLabel censure = new JLabel("¿Quitar censura? ");
      censure.setToolTipText("Quita la censura respecto a la versión Japonesa.");                
      p.add(censure);

      JRadioButton rbYes = new JRadioButton("Sí", true);  //yes button           
      JRadioButton rbNo = new JRadioButton("No", false);  //no censure button
      
      ButtonGroup group = new ButtonGroup();
      group.add(rbYes);
      group.add(rbNo);
      
      addActionRadio(rbYes);
      addActionRadio(rbNo);
      
      // Create subpanel
      JPanel p2 = new JPanel();
      p2.add(rbYes);
      p2.add(rbNo);
      
      // Add to general panel
      p.add(p2);
    }

    /**
     * Create listeners for JCheckBox
     * @param c1
     * @param foo
     */
    private void addActionRadio(JRadioButton radio) {
      
      radio.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent event) {

          JRadioButton button = (JRadioButton) event.getSource();
          if (button.getText().equals("No"))
          {            
            removeCensure = false;                
          }
          else
          {
            removeCensure = true;
          }
        }
       });
      
    }

    /**
     * Action for patch iso
     * 
     * @param patch
     */
    void addActionPatch(AbstractButton patch) {
        patch.addActionListener(PatchL.getPatchLayer(this, iv, srcPath,
                saveFile, textArea, fileChooser, removeCensure));
    }

    /**
     * Adds action for Open image
     * 
     * @param open
     */
    private void addActionOpen(AbstractButton open) {
        open.addActionListener(new OpenL(this, origFile, textFieldVersion, iv,
                srcPath, textArea, fileChooser));
    }

    /**
     * Adds action to exit
     * 
     * @param salir
     */
    private void addActionSalir(AbstractButton salir) {
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
    }

    /**
     * Adds action to about us
     * 
     * @param acerca
     * @param title
     */
    public static void addActionAcercaDe(AbstractButton acerca) {
        acerca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new Dialog(title, version, autor, translate, agradecimientos,
                        project, website);
            }
        });
    }

}