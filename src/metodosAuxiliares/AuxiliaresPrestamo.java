package metodosAuxiliares;

import Controlador.*;
import Modelo.*;
import Repositorio.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

public class AuxiliaresPrestamo {
    Repositorio<Prestamo> RPrestamos = new RepositorioPrestamo();
    Repositorio<Alumno> RAlumno = new RepositorioAlumno();
    Repositorio<Libro> RLibro =  new RepositorioLibro();
    //Formato de fechas dd/MM/YYYY
    static DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    ControladorPrestamo CPrestamo = new ControladorPrestamo(RPrestamos);
    ControladorAlumno CAlumno = new ControladorAlumno(RAlumno);
    ControladorLibro CLibro = new ControladorLibro(RLibro);

    public static Prestamo crearPrestamo(ControladorLibro controladorLibro, ControladorAlumno controladorAlumno) {
        /*HashMap que gestiona los libros prestados de cada préstamo, en el que la clave es el identificador del libro y
        el valor es la cantidad de libros que presto con ese mismo id.
         */
        HashMap<Integer, Integer> librosPrestadosLocal = new HashMap<Integer, Integer>();

        Alumno alumnoPrestamo = buscarAlumnoPrestamo(controladorAlumno);

        if(alumnoPrestamo == null){
            return null;
        }

        LocalDate fechas[] = ingresarFechasPrestamo(formatoFecha);

        if(fechas[0] == null || fechas[1] == null){
            return null;
        }

        boolean hayLibros = prestarLibros(librosPrestadosLocal, controladorLibro);

        if(!hayLibros){
            return null;
        }

        try{
            return new Prestamo(alumnoPrestamo, librosPrestadosLocal, fechas[0], fechas[1], null);
        }catch(IllegalArgumentException e ){
            return null;
        }
    }

    public static Alumno buscarAlumnoPrestamo(ControladorAlumno CAlumno){
        boolean seguir = true;
        Alumno alumnoPrestamo;

        do{
            JOptionPane.showMessageDialog(null, "Ingrese el id de Alumno que quiere buscar");
            int id = AuxiliaresGeneral.validarNumero();

            if(!AuxiliaresGeneral.isNumValido(id)){
                return null;
            }

            alumnoPrestamo = CAlumno.buscarAlumno(id);

            if(alumnoPrestamo != null){
                return alumnoPrestamo;

            } else{
                JOptionPane.showMessageDialog(null, "Alumno no encontrado");
                String opcion = JOptionPane.showInputDialog(null, "Desea buscar otro alumno?: \n-Si\n-No\nOpción: ");

                if(opcion == null || opcion.equalsIgnoreCase("No")){
                    seguir = false;
                }
            }

        }while(seguir);

        return alumnoPrestamo;
    }

    public static LocalDate[] ingresarFechasPrestamo(DateTimeFormatter formatoFecha){
        LocalDate[] fechas = new LocalDate[2];
        LocalDate LDFechaPrestamo = null;
        LocalDate  LDFechaLimiteDevolucion =  null;
        boolean seguir = true;

        do{
            try{
                String fechaPrestamo = JOptionPane.showInputDialog("Ingrese la fecha del préstamo: ");
                LDFechaPrestamo = LocalDate.parse(fechaPrestamo, formatoFecha);
                String fechaLimiteDevolucion = JOptionPane.showInputDialog("Ingrese la fecha límite de devolución: ");
                LDFechaLimiteDevolucion = LocalDate.parse(fechaLimiteDevolucion, formatoFecha);
                seguir = false;

            }catch(DateTimeParseException e){
                JOptionPane.showMessageDialog(null, "Fecha inválida");
                String opcion = JOptionPane.showInputDialog(null, "¿Desea intentar de nuevo?: \n-Si\n-No\nOpción: ");

                if(opcion == null || opcion.equalsIgnoreCase("No")){
                    seguir  = false;
                }
            }
        }while(seguir);

        fechas[0] = LDFechaPrestamo;
        fechas[1] = LDFechaLimiteDevolucion;
        return fechas;
    }

    public static boolean prestarLibros(HashMap<Integer, Integer> librosPrestados, ControladorLibro controladorLibro){
        boolean seguir = true;

        do{
            JOptionPane.showMessageDialog(null, "Ingrese el id del libro que desea prestar");
            int id = AuxiliaresGeneral.validarNumero();

            if(!AuxiliaresGeneral.isNumValido(id)){
                return false;
            }

            JOptionPane.showMessageDialog(null,"Ingrese la cantidad de libros que desea prestar");

            int cantidad = AuxiliaresGeneral.validarNumero();
            if(!AuxiliaresGeneral.isNumValido(cantidad)){
                return false;
            }

            Libro libroPrestado = controladorLibro.buscarLibro(id);

            // Verificación de que el libro ya no fue agregado
            if(librosPrestados.containsKey(id)){
                JOptionPane.showMessageDialog(null, "El libro seleccionado ya se encuentra en la lista");
            }
            else{
                if(libroPrestado!=null && libroPrestado.getStock()>0 && libroPrestado.getStock()>=cantidad){

                    librosPrestados.put(id, cantidad);
                    libroPrestado.setStock(libroPrestado.getStock()-cantidad);

                }else{
                    JOptionPane.showMessageDialog(null, "El libro no esta disponible");
                }
            }

            String opcion = JOptionPane.showInputDialog("Desea prestar más libros?: \n-Si\n-No\nOpción: ");

            if(opcion == null || opcion.equalsIgnoreCase("No")){
                seguir = false;
            }

        }while(seguir);
        return !librosPrestados.isEmpty();
    }

    public void editarPrestamo(int id, ControladorLibro controladorLibro, ControladorAlumno controladorAlumno, ControladorPrestamo controladorPrestamo) {
        HashMap<Integer,Integer> librosPrestadosLocal = new HashMap<Integer, Integer>();

        Prestamo prestamoViejo = controladorPrestamo.buscarPrestamo(id);

        if (prestamoViejo == null){
            JOptionPane.showMessageDialog(null, "Prestamo no encontrado. No se puedo editar el préstamo");
            return;
        }

        JOptionPane.showMessageDialog(null, "Ingrese los nuevos datos");

        Alumno alumnoPrestamo = buscarAlumnoPrestamo(controladorAlumno);

        if(alumnoPrestamo == null){
            JOptionPane.showInputDialog(null, "No se encontró el Alumno. No se puedo editar el préstamo");
            return;
        }

        LocalDate fechas[] = ingresarFechasPrestamo(formatoFecha);

        if(fechas[0] == null || fechas[1] == null){
            JOptionPane.showMessageDialog(null, "Fechas del préstamo no encontradas. No se pudo editar el préstamo");
            return;
        }

        //Guardo los antiguos valores de libros del prestamo viejo, por si pasa algo y debo restaurar nuevamente el viejo prestamo
        HashMap<Integer ,Integer> auxiliar = controladorPrestamo.crearMapProvisional(prestamoViejo);

        /*Se devuelven los libros que ya se habían prestado para evitar perder libros.
        Lo hice aquí para que vuelvan a haber libros disponibles para la nueva cantidad que ingresara el usuario a continuación.
        Si algo sale mal, se restaurará el stock viejo y el prestamo viejo seguirá existiendo
        */
        controladorPrestamo.devolverLibrosPrestamo(prestamoViejo, controladorLibro);

        boolean hayLibros = prestarLibros(librosPrestadosLocal, controladorLibro);

        if(!hayLibros){
            JOptionPane.showMessageDialog(null, "No se a seleccionado ningún libro para el préstamo. No se pudo editar el préstamo");
            controladorPrestamo.restaurarLibrosPrestamoViejo(auxiliar, controladorLibro);
            return;
        }

        Prestamo datos = new Prestamo(alumnoPrestamo, librosPrestadosLocal, fechas[0], fechas[1], null);

        controladorPrestamo.editarPrestamo(id, datos);
    }

    public void eliminarPrestamo(int id, ControladorPrestamo CPrestamo){
        try{
            CPrestamo.eliminarPrestamo(id);
        }catch(IllegalArgumentException e ){
            JOptionPane.showMessageDialog(null, "No se encontró el préstamo. No se puede eliminar");
        }
    }

    private JScrollPane listarPrestamos(ControladorPrestamo CPrestamo){

        List<Prestamo> lista = CPrestamo.listarPrestamos();

        String[] columnas = {"ID", "Alumno", "Fecha del Préstamo", "Fecha límite de devolución", "Fecha Devolución"};

        Object[][] filas = new Object[CPrestamo.listarPrestamos().size()][5];

        for (int i = 0; i < CPrestamo.listarPrestamos().size(); i++) {
            Prestamo prestamo = CPrestamo.listarPrestamos().get(i);
            filas[i][0] = prestamo.getId();
            filas[i][1] = prestamo.getAlumno().getNombreApellido();
            filas[i][2] = prestamo.getFechaPrestamo();
            filas[i][3] = prestamo.getFechaLimiteDevolucion();
            filas[i][4] = prestamo.getFechaDevolucion();
        }

        JTable tabla = new JTable(filas, columnas);
        tabla.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(900, 300));

        return scrollPane;
    }

    private JScrollPane listarPrestamosVencidos(ControladorPrestamo CPrestamo){

        List<Prestamo> lista = CPrestamo.listarPrestamosVencidos();

        String[] columnas = {"ID", "Alumno", "Fecha del Préstamo", "Fecha límite de devolución", "Fecha Devolución", "Multa"};

        Object[][] filas = new Object[CPrestamo.listarPrestamosVencidos().size()][6];

        for (int i = 0; i < CPrestamo.listarPrestamosVencidos().size(); i++) {
            Prestamo prestamo = CPrestamo.listarPrestamos().get(i);
            filas[i][0] = prestamo.getId();
            filas[i][1] = prestamo.getAlumno().getNombreApellido();
            filas[i][2] = prestamo.getFechaPrestamo();
            filas[i][3] = prestamo.getFechaLimiteDevolucion();
            filas[i][4] = prestamo.getFechaDevolucion();
        }

        JTable tabla = new JTable(filas, columnas);
        tabla.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(900, 300));

        return scrollPane;
    }

    public void devolverPrestamo(ControladorLibro CLibro, ControladorPrestamo CPrestamo){
        boolean seguir = true;
        Prestamo prestamo;
        do{
            JOptionPane.showMessageDialog(null, "Ingrese el id del préstamo que desea devolver");
            int id = AuxiliaresGeneral.validarNumero();

            if(!AuxiliaresGeneral.isNumValido(id)){
                JOptionPane.showMessageDialog(null, "No se a podido devolver el préstamo");
                return;
            }

            prestamo = CPrestamo.buscarPrestamo(id);

            if(prestamo == null){
                JOptionPane.showMessageDialog(null, "Préstamo no encontrado");
                String opcion = JOptionPane.showInputDialog("¿Desea intentar de nuevo?\nSi\nNo\nOpción:");

                if( opcion==null || opcion.equalsIgnoreCase("No")){
                    JOptionPane.showMessageDialog(null, "No se a podido devolver el préstamo");
                    return;
                }

            }else{
                seguir =  false;
            }
        }while(seguir);
        LocalDate fechaDevolucion = AuxiliaresGeneral.ingresarFecha(formatoFecha);
        if(fechaDevolucion == null){
            JOptionPane.showMessageDialog(null, "No se a podido devolver el préstamo");
            return;
        }

        try{
            CPrestamo.devolverPrestamo(prestamo, CLibro, fechaDevolucion);
        }
        catch(IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }



}
