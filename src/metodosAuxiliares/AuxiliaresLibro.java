package metodosAuxiliares;

import Controlador.ControladorLibro;
import Modelo.Libro;
import Repositorio.Repositorio;
import Repositorio.RepositorioLibro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AuxiliaresLibro {
    Repositorio<Libro> RLibro = new RepositorioLibro();
    ControladorLibro CLibro =  new ControladorLibro(RLibro);

    public static Libro crearLibro(){
        JOptionPane.showMessageDialog(null,"Ingrese los datos del libro que desea crear");
        String titulo = JOptionPane.showInputDialog(null,"Ingrese el nombre del libro");
        String editorial = JOptionPane.showInputDialog(null,"Ingrese el editorial del libro");
        JOptionPane.showMessageDialog(null,"Ingrese el año de publicación del libro");
        int yearPublicacion = AuxiliaresGeneral.validarNumero();

        if(!AuxiliaresGeneral.isNumValido(yearPublicacion)){
            return null;
        }

        String autor =  JOptionPane.showInputDialog(null,"Ingrese el autor del libro");
        JOptionPane.showMessageDialog(null,"Ingrese la cantidad de libros que quiere crear");
        int cantidad = AuxiliaresGeneral.validarNumero();
        if(!AuxiliaresGeneral.isNumValido(cantidad)){
            return null;
        }

        return new Libro(titulo, editorial, yearPublicacion, autor, cantidad);
    }

    public static void editarLibro(int id, ControladorLibro controladorLibro){

        if(controladorLibro.buscarLibro(id) == null){
            JOptionPane.showMessageDialog(null, "No existe el libro con el id " + id);
            return;
        }

        String titulo  = JOptionPane.showInputDialog(null,"Ingrese el nuevo título del libro: ");
        String editorial =  JOptionPane.showInputDialog(null,"Ingrese la nueva editorial del libro: ");
        JOptionPane.showMessageDialog(null, "Ingrese el nuevo año de publicación del libro: ");

        int yearPublicacion = AuxiliaresGeneral.validarNumero();

        if(!AuxiliaresGeneral.isNumValido(yearPublicacion)){
            JOptionPane.showMessageDialog(null, "El año ingresado es inválido. No se puedo editar el Libro");
            return;
        }

        String autor =   JOptionPane.showInputDialog(null,"Ingrese el nuevo autor del libro: ");
        JOptionPane.showMessageDialog(null, "Ingrese la nueva cantidad de libros: ");
        int cantidad = AuxiliaresGeneral.validarNumero();

        if(!AuxiliaresGeneral.isNumValido(cantidad)){
            JOptionPane.showMessageDialog(null, "La cantidad que a ingresado es inválida. No se puedo editar el Libro");
            return;
        }

        controladorLibro.editarLibro(id, new Libro(titulo, editorial, yearPublicacion, autor, cantidad));
    }

    public static void eliminarLibro(int id, ControladorLibro CLibro){
        try{
            CLibro.eliminarLibro(id);
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "No existe el libro con el id " + id);
        }
    }

    public JScrollPane listarLibros(ControladorLibro CLibro){

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
