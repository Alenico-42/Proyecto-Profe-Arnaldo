package Vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    CardLayout controladorPaneles = new CardLayout();
    JPanel contenedorPaneles =  new JPanel(controladorPaneles);

    public VentanaPrincipal(){
        setTitle("Practica Java");
        setSize(1920,1000);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(contenedorPaneles,BorderLayout.CENTER);

    }

    public void agregarPanel(JPanel panel, String nombre){
        contenedorPaneles.add(panel, nombre);
    }

    public void mostrarPanel(String nombre){
        controladorPaneles.show(contenedorPaneles, nombre);
    }

    public static void main(String [] args){
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

        PanelMenuPrincipal panelMenuPrincipal = new PanelMenuPrincipal(ventanaPrincipal);
        PanelGestionarLibros  panelGestionarLibros = new PanelGestionarLibros(ventanaPrincipal);

        ventanaPrincipal.agregarPanel(panelMenuPrincipal, "Menu Principal");
        ventanaPrincipal.agregarPanel(panelGestionarLibros,"Gestionar Libros");

        ventanaPrincipal.mostrarPanel("Menu Principal");

        ventanaPrincipal.setVisible(true);
    }
}
