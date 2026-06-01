package Vista;

import Modelo.Libro;
import Repositorio.Repositorio;
import Repositorio.RepositorioLibro;
import Controlador.ControladorLibro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelGestionarLibros extends JPanel {
    Repositorio<Libro> RLibro = new RepositorioLibro();
    ControladorLibro CLibro =  new ControladorLibro(RLibro);
    VentanaPrincipal ventanaPrincipal;

    public PanelGestionarLibros(VentanaPrincipal ventanaPrincipal) {
        setLayout(new BorderLayout());
        JPanel panelOpciones = panelOpciones();
        add(panelOpciones, BorderLayout.CENTER);
        add(panelLogo(), BorderLayout.EAST);
        add(panelLogo(),  BorderLayout.WEST);
        this.ventanaPrincipal = ventanaPrincipal;


    }

    public JPanel panelOpciones(){
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(5,1,2,2));
        JButton crearLibro = new JButton("Crear Libro");
        JButton editarLibro  = new JButton("Editar Libro");
        JButton eliminarLibro = new JButton("Eliminar Libro");
        JButton listarLibros = new JButton("Listar Libros");
        JButton salir = new JButton("Salir");

        crearLibro.addActionListener(e ->{
            Libro libro = crearLibro();
            if(libro != null){
                CLibro.agregarLibro(libro);
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo crear el Libro");
            }
        });

        editarLibro.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"Ingrese el id del libro que desea editar");
            int id = validarNumero();

            if(!isNumValido(id)){
                JOptionPane.showMessageDialog(null,"El número ingresado es inválido. No se pudo editar el libro");
                return;
            }

            editarLibro(id, CLibro);
        });

        eliminarLibro.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Ingrese el id del libro que desea eliminar: ");
            int id2 = validarNumero();

            if(!isNumValido(id2)){
                return;
            }
            eliminarLibro(id2, CLibro);

        });

        listarLibros.addActionListener(e -> {
            List<Libro> listaLibros = CLibro.listarLibros();

            if (listaLibros.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay libros registrados.");
                return;
            }

            JScrollPane tabla = listarLibros(CLibro);
            JOptionPane.showMessageDialog(null, tabla, "Lista de Libros", JOptionPane.PLAIN_MESSAGE);

        });

        salir.addActionListener(e -> ventanaPrincipal.mostrarPanel("Menu Principal"));

        panelOpciones.add(crearLibro);
        panelOpciones.add(editarLibro);
        panelOpciones.add(eliminarLibro);
        panelOpciones.add(listarLibros);
        panelOpciones.add(salir);
        return panelOpciones;
    }

    public JPanel panelLogo(){
        JPanel panelLogo = new JPanel();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/imagen1.png"));
        JLabel imagenLogo = new JLabel(imagen);
        panelLogo.add(imagenLogo);
        return  panelLogo;
    }

    private Libro crearLibro(){
        JOptionPane.showMessageDialog(null,"Ingrese los datos del libro que desea crear");
        String titulo = JOptionPane.showInputDialog(null,"Ingrese el nombre del libro");
        String editorial = JOptionPane.showInputDialog(null,"Ingrese el editorial del libro");
        JOptionPane.showMessageDialog(null,"Ingrese el año de publicación del libro");
        int yearPublicacion = validarNumero();

        if(!isNumValido(yearPublicacion)){
            return null;
        }

        String autor =  JOptionPane.showInputDialog(null,"Ingrese el autor del libro");
        JOptionPane.showMessageDialog(null,"Ingrese la cantidad de libros que quiere crear");
        int cantidad = validarNumero();
        if(!isNumValido(cantidad)){
            return null;
        }

        return new Libro(titulo, editorial, yearPublicacion, autor, cantidad);
    }



    private static int validarNumero(){

        while(true){
            try{
                String numero1 = JOptionPane.showInputDialog("Ingrese un número:");

                if (numero1 == null) {
                    return -1;
                }

                return Integer.parseInt(numero1);

            }catch(NumberFormatException e ){
                String opcion = JOptionPane.showInputDialog("El número ingresado es inválido. ¿Desea intentar de nuevo?\nSi\nNo\nOpción:");

                if(opcion==null || opcion.equalsIgnoreCase("No")){
                    return -1;
                }
            }
        }
    }

    private static boolean isNumValido(int id){
        if(id == -1){
            JOptionPane.showMessageDialog(null, "No a ingresado ningún número válido, fin de la operación");
            return false;
        }
        return true;
    }


    private static void editarLibro(int id, ControladorLibro controladorLibro){

        if(controladorLibro.buscarLibro(id) == null){
           JOptionPane.showMessageDialog(null, "No existe el libro con el id " + id);
            return;
        }

        String titulo  = JOptionPane.showInputDialog(null,"Ingrese el nuevo título del libro: ");
        String editorial =  JOptionPane.showInputDialog(null,"Ingrese la nueva editorial del libro: ");
        JOptionPane.showMessageDialog(null, "Ingrese el nuevo año de publicación del libro: ");

        int yearPublicacion = validarNumero();

        if(!isNumValido(yearPublicacion)){
            JOptionPane.showMessageDialog(null, "El año ingresado es inválido. No se puedo editar el Libro");
            return;
        }

        String autor =   JOptionPane.showInputDialog(null,"Ingrese el nuevo autor del libro: ");
        JOptionPane.showMessageDialog(null, "Ingrese la nueva cantidad de libros: ");
        int cantidad = validarNumero();

        if(!isNumValido(cantidad)){
            JOptionPane.showMessageDialog(null, "La cantidad que a ingresado es inválida. No se puedo editar el Libro");
            return;
        }

        controladorLibro.editarLibro(id, new Libro(titulo, editorial, yearPublicacion, autor, cantidad));
    }

    private static void eliminarLibro(int id, ControladorLibro CLibro){
        try{
            CLibro.eliminarLibro(id);
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "No existe el libro con el id " + id);
        }
    }

    private JScrollPane listarLibros(ControladorLibro CLibro){

        List<Libro> lista = CLibro.listarLibros();

        String[] columnas = {"ID", "Título", "Editorial", "Autor", "Año de Publicación", "Cantidad"};

        Object[][] filas = new Object[CLibro.listarLibros().size()][6];

        for (int i = 0; i < CLibro.listarLibros().size(); i++) {
            Libro libro = CLibro.listarLibros().get(i);
            filas[i][0] = libro.getId();
            filas[i][1] = libro.getTitulo();
            filas[i][2] = libro.getEditorial();
            filas[i][3] = libro.getAutor();
            filas[i][4] = libro.getYearPublicacion();
            filas[i][5] = libro.getStock();
        }

        JTable tabla = new JTable(filas, columnas);
        tabla.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(900, 300));

        return scrollPane;
    }
}
