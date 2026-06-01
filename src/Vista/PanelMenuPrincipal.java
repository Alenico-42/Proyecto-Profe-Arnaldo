package Vista;

import Repositorio.*;
import Modelo.*;
import Controlador.*;

import javax.swing.*;
import java.awt.*;

public class PanelMenuPrincipal extends JPanel {
    VentanaPrincipal ventanaPrincipal;
    Repositorio<Prestamo> RPrestamos = new RepositorioPrestamo();
    Repositorio<Alumno> RAlumno = new RepositorioAlumno();
    Repositorio<Libro> RLibro =  new RepositorioLibro();
    ControladorPrestamo CPrestamo = new ControladorPrestamo(RPrestamos);
    ControladorAlumno CAlumno = new ControladorAlumno(RAlumno);
    ControladorLibro CLibro = new ControladorLibro(RLibro);

    public PanelMenuPrincipal(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        setLayout(new BorderLayout());
        JPanel panelOpciones = panelOpciones();
        add(panelOpciones, BorderLayout.CENTER);
        add(panelLogo(), BorderLayout.EAST);
        add(panelLogo(), BorderLayout.WEST);
    }
    public JPanel panelOpciones(){
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(4,1,2,2));
        JButton gestionarAlumno = new JButton("1-Gestionar Alumno");
        JButton gestionarLibro =  new JButton("2-Gestionar Libro");
        JButton gestionarPrestamo =   new JButton("3-Gestionar Prestamo");
        JButton salir =   new JButton("4-Salir");

        gestionarAlumno.addActionListener(e->{
            PanelGestionarAlumno panelGestionarAlumno= new PanelGestionarAlumno(ventanaPrincipal,CAlumno );
            ventanaPrincipal.agregarPanel(panelGestionarAlumno,"Gestionar Alumno");
            ventanaPrincipal.mostrarPanel("Gestionar Alumno");
        });

        gestionarLibro.addActionListener( e-> {
            PanelGestionarLibros  panelGestionarLibros = new PanelGestionarLibros(ventanaPrincipal);
            ventanaPrincipal.agregarPanel(panelGestionarLibros, "Gestionar Libros");
            ventanaPrincipal.mostrarPanel("Gestionar Libros");
        });

        gestionarPrestamo.addActionListener( e-> {
            PanelGestionarPrestamos panelGestionarPrestamos =  new PanelGestionarPrestamos(ventanaPrincipal);
            ventanaPrincipal.agregarPanel(panelGestionarPrestamos, "Gestionar Prestamos");
            ventanaPrincipal.mostrarPanel("Gestionar Prestamos");
        });

        panelOpciones.add(gestionarAlumno);
        panelOpciones.add(gestionarLibro);
        panelOpciones.add(gestionarPrestamo);
        panelOpciones.add(salir);
        return panelOpciones;
    }

    private JPanel panelLogo(){
        JPanel panelLogo = new JPanel();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/imagen1.png"));
        JLabel imagenLogo = new JLabel(imagen);
        panelLogo.add(imagenLogo);
        return panelLogo;
    }

}
