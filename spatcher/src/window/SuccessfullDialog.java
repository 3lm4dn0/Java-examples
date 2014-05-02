package window;

import java.awt.BorderLayout;		
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SuccessfullDialog extends JDialog{

    JLabel msg = new JLabel("Imagen parcheada");            
    JButton aceptar = new JButton("Aceptar");    
    
    public SuccessfullDialog(String title){
        super(new Frame(), "", true);
        
        Container cp = getContentPane();
        
        JPanel p = new JPanel();
        p.add(aceptar);       
        cp.add(p, BorderLayout.SOUTH);
        
        p = new JPanel(new BorderLayout());
        int eb = 20;
        p.setBorder(BorderFactory.createEmptyBorder(10, eb, eb, eb));
        p.add(msg);
        cp.add(p, BorderLayout.NORTH);        
                
        pack();
        
        // Fixed
        setResizable(false);
        
        // Center
        Dimension pantalla, cuadro;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        cuadro = this.getSize();
        this.setLocation(((pantalla.width - cuadro.width)/2), (pantalla.height - cuadro.height)/2);

        // Accept        
        aceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                dispose();
            }
        });

    }
    
}