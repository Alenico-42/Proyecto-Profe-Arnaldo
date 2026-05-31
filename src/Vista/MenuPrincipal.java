package Vista;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import Controlador.*;
import Modelo.*;
import Repositorio.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que contiene el menú principal del sistema.
 * Interactúa con el usuario por medio de la consola.
 * Contienen métodos auxiliares que hacen más limpio el main.
 */
public class MenuPrincipal {
    static Scanner entrada = new Scanner (System.in);
    //Formato que tendrán las fechas
    static DateTimeFormatter  formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String [] args){
        //Creación de repositorios
        Repositorio<Alumno> RAlumno = new RepositorioAlumno();
        Repositorio<Libro> RLibro =  new RepositorioLibro();
        Repositorio<Prestamo> RPrestamo = new RepositorioPrestamo();
        //Creación de los controladores
        ControladorAlumno CAlumno = new ControladorAlumno(RAlumno);
        ControladorLibro CLibro = new ControladorLibro(RLibro);
        ControladorPrestamo CPrestamo = new ControladorPrestamo(RPrestamo);

        boolean seguir=true;

        do{
            System.out.printf("Introduzca la opción que desea realizar: %n 1-%s%n 2-%s%n 3-%s%n 4-%s%n %s", "Gestionar Libros",
                    "Gestionar Alumnos", "Gestionar Préstamos", "Salir", "Ingrese la Opción:");

            String opcion = entrada.nextLine();

            switch(opcion){

                case "1":
                    mostrarMenuLibros();
                    String opcion2 = entrada.nextLine();

                    switch (opcion2){

                        case "1":
                           CLibro.agregarLibro(crearLibro());
                            break;

                        case "2":
                            System.out.print("Ingrese el id del libro que desea editar: ");
                            int id = validarNumero();

                            if(!isNumValido(id)){
                                return;
                            }

                            editarLibro(id, CLibro);
                            break;

                        case "3":
                            System.out.print("Ingrese el id del libro que desea eliminar: ");
                            int id2 = validarNumero();

                            if(!isNumValido(id2)){
                                return;
                            }
                            eliminarLibro(id2, CLibro);
                            break;

                        case "4":
                            listarLibros(CLibro);
                            break;

                        default:
                            System.out.println("Ha terminado de gestionar los Libros");
                            break;
                    }
                    break;

                case "2":
                    mostrarMenuAlumnos();
                    String  opcion3 = entrada.nextLine();

                    switch (opcion3){

                        case "1":
                            CAlumno.agregarAlumno(crearAlumno());
                            break;

                        case "2":
                            System.out.print("Ingrese el número de id del Alumno que desea modificar: ");
                            int id = validarNumero();

                            if(!isNumValido(id)){
                                return;
                            }
                            editarAlumno(id, CAlumno);
                            break;

                        case "3":
                            System.out.print("Ingrese el numero de id del Alumno que desea eliminar: ");
                            int id2 = validarNumero();

                            if(!isNumValido(id2)){
                                return;
                            }
                            eliminarAlumno(id2, CAlumno);
                            break;

                        case "4":
                            listarAlumnos(CAlumno);
                            break;

                        default:
                            System.out.println("Ha terminado de gestionar los Alumnos");
                            break;
                    }
                    break;

                case "3":
                    mostrarMenuPrestamos();
                    String opcion4 = entrada.nextLine();

                    switch (opcion4){

                        case "1":
                            Prestamo prestamoNuevo = crearPrestamo(CLibro, CAlumno);
                            if(prestamoNuevo != null){
                                CPrestamo.agregarPrestamo(prestamoNuevo);
                            } else{
                                System.out.println("No se a podido crear el préstamo");
                            }
                            break;

                        case "2":
                            System.out.print("Ingrese el id del préstamo que quiere modificar: ");
                            int id = validarNumero();

                            if(!isNumValido(id)){
                                return;
                            }
                            editarPrestamo(id, CLibro, CAlumno, CPrestamo);
                            break;

                        case "3":
                            System.out.print("Ingrese el id del préstamo que desea eliminar: ");
                            int id2 =  validarNumero();

                            if(!isNumValido(id2)){
                                System.out.print("No se ha podido eliminar el préstamo");
                                return;
                            }
                            eliminarPrestamo(id2, CPrestamo);
                            break;

                        case "4":
                            listarPrestamos(CPrestamo, CLibro);
                            break;

                        case "5":
                            listarPrestamosVencidos(CPrestamo, CLibro);
                            break;

                        case "6":
                            devolverPrestamo(CLibro, CPrestamo);
                            break;

                        default:
                            System.out.println("Ha terminado de gestionar los préstamos");
                            break;
                    }
                    break;
                case "4":
                    seguir=false;
                    break;
            }
        }while(seguir);
        System.out.println("Fin del programa");
    }


    //Métodos auxiliares para mostrar los diferentes menús

    /**
     * Muestra un menú con opciones para gestionar los libros
     */
    public static void mostrarMenuLibros(){

        System.out.printf("Gestión de Libros:%n 1-%s%n 2-%s%n 3-%s%n 4-%s%n 5-%s%n %s " , "Crear Libro", "Editar Libro", "Borrar Libro",
                "Listar Libros", "Salir", "Ingrese la Opción: ");
        }

    /**
     * Muestra un menú con opciones para gestionar los alumnos
     */
    public static void mostrarMenuAlumnos(){
        System.out.printf("Gestion de Alumnos: %n 1-%s%n 2-%s%n 3-%s%n 4-%s%n 5-%s%n %s", "Crear Alumno", "Editar Alumno", "Borrar Alumno",
                "Listar Alumnos", "Salir", "Ingrese la Opción: ");
    }

    /**
     * Muestra un menú con opciones para gestionar los préstamos
     */
    public static void mostrarMenuPrestamos(){
        System.out.printf("Gestion de Prestamos: %n 1-%s%n 2-%s%n 3-%s%n 4-%s%n 5-%s%n 6-%s%n 7-%s%n %s", "Crear Préstamo", "Editar Préstamo",
                "Borrar Préstamo", "Listar Prestamos", "Listar Prestamos Vencidos", "Devolver Préstamo", "Salir", "Ingrese la Opción: ");
    }

    //Fin de los métodos para mostrar los menús

    //Métodos auxiliares para la sección libros

    /**
     * Crea y retorna un libro con los datos ingresados.
     * @return libro creado con los datos ingresados.
     */
    public static Libro crearLibro(){
        System.out.println("Ingrese los datos del libro que desea crear: ");
        System.out.print("Ingrese el título del libro: ");
        String titulo = entrada.nextLine();
        System.out.print("Ingrese la editorial del libro: ");
        String editorial = entrada.nextLine();
        System.out.print("Ingrese el año de publicación del libro: ");
        int yearPublicacion  = validarNumero();

        if(!isNumValido(yearPublicacion)){
            return null;
        }

        System.out.print("Ingrese el autor del libro: ");
        String autor = entrada.nextLine();
        System.out.print("Ingrese la cantidad de libros que desea crear: ");
        int cantidad = validarNumero();

        if(!isNumValido(cantidad)){
            return null;
        }
        return new Libro(titulo, editorial, yearPublicacion, autor, cantidad);
    }


    /**
     * Busca el libro que se quiere actualizar, pide los datos actualizados y lo actualiza.
     * @param id id del libro que se desea actualizar.
     * @param controladorLibro Controlador de libros.
     */
    public static void editarLibro(int id, ControladorLibro controladorLibro){

        if(controladorLibro.buscarLibro(id) == null){
            System.out.println("Libro no encontrado");
            return;
        }

        System.out.println("Ingrese los nuevos datos: ");
        System.out.print("Ingrese el nuevo título del libro: ");
        String titulo = entrada.nextLine();
        System.out.print("Ingrese la nueva editorial del libro: ");
        String editorial = entrada.nextLine();
        System.out.print("Ingrese el nuevo año de publicación del libro: ");
        int yearPiublicacion  = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Ingrese el nuevo autor del libro: ");
        String autor = entrada.nextLine();
        System.out.print("Ingrese la nueva cantidad de libros: ");
        int cantidad = validarNumero();

        if(!isNumValido(cantidad)){
            return;
        }

        controladorLibro.editarLibro(id, new Libro(titulo, editorial, yearPiublicacion, autor, cantidad));
    }

    /**
     * Busca un alumno y lo elimina si lo encuentra.
     * Si no lo encuentra, avisa al usuario que no se encontró el libro
     * @param id id del alumno que se desea eliminar
     * @param CLibro controlador de libros
     */
    public static void eliminarLibro(int id, ControladorLibro CLibro){
        try{
            CLibro.eliminarLibro(id);
        }catch(IllegalArgumentException e){
            System.out.println("No se encontró el libro. No se puede eliminar");
        }
    }

    /**
     * Imprime una lista con todos los datos de todos los libros disponibles.
     * @param controladorLibro Controlador de libros.
     */
    public static void listarLibros(ControladorLibro controladorLibro) {
        List<Libro> libros = controladorLibro.listarLibros();

        System.out.printf("%-5s %-30s %-20s %-25s %-22s %-10s%n", "ID", "Título", "Editorial", "Autor", "Año de Publicación", "Cantidad");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for(Libro libro1 : libros){
            System.out.printf("%-5d %-30s %-20s %-25s %-22s %-10d%n", libro1.getId(), libro1.getTitulo(), libro1.getEditorial(), libro1.getAutor(), libro1.getYearPublicacion(), libro1.getStock());
        }
    }

    //Fin de los métodos de la sección libros

    //Métodos de la sección alumnos

    /**
     * Crea y retorna un alumno con los datos ingresados.
     * @return nuevo alumno
     */
    public static Alumno crearAlumno(){
        System.out.println("Ingrese los datos del Alumno: ");
        System.out.print("Ingrese el nombre y apellido del Alumno: ");
        String nombreApellidoAlumno = entrada.nextLine();
        System.out.print("Ingrese el número de CI del Alumno: ");
        String CI = entrada.nextLine();
        System.out.print("Ingrese el email del Alumno: ");
        String emailAlumno = entrada.nextLine();
        System.out.print("Ingrese el teléfono del Alumno: ");
        String telefonoAlumno = entrada.nextLine();
        System.out.print("Ingrese la fecha de nacimiento del alumno: ");
        LocalDate fechaNacimiento= ingresarFecha();

        if(fechaNacimiento == null){
            System.out.println("No se ha ingresado ninguna fecha de nacimiento");
        }

        System.out.print("Ingrese la facultad del Alumno: ");
        String facultadAlumno = entrada.nextLine();
        return new Alumno(nombreApellidoAlumno, CI, emailAlumno, telefonoAlumno, fechaNacimiento, facultadAlumno);
    }


    /**
     * Busca el alumno a actualizar, pide los datos y lo actualiza.
     * @param id id del alumno que se desea actualizar
     * @param controladorAlumno Controlador de Alumnos.
     */
    public static void editarAlumno(int id, ControladorAlumno controladorAlumno){
        if(controladorAlumno.buscarAlumno(id) == null){
            System.out.println("Alumno no encontrado");
            return;
        }

        System.out.println("Ingrese los nuevos datos del Alumno");
        System.out.print("Ingrese el nuevo nombre y apellido del Alumno: ");
        String nombreApellidoAlumno2 = entrada.nextLine();
        System.out.print("Ingrese el nuevo número de CI del Alumno: ");
        String CI2 = entrada.nextLine();
        System.out.print("Ingrese el nuevo email del Alumno: ");
        String emailAlumno2 = entrada.nextLine();
        System.out.print("Ingrese el nuevo teléfono del Alumno: ");
        String telefonoAlumno2 = entrada.nextLine();
        System.out.print("Ingrese la nueva fecha de nacimiento del alumno: ");
        String  fechaNacimientoAlumno2 = entrada.nextLine();
        LocalDate fechaNacimiento2= LocalDate.parse(fechaNacimientoAlumno2, formatoFecha);
        System.out.print("Ingrese la nueva facultad del alumno:");
        String facultadAlumno2 = entrada.nextLine();
        Alumno dato = new Alumno(nombreApellidoAlumno2, CI2, emailAlumno2, telefonoAlumno2, fechaNacimiento2, facultadAlumno2);
        controladorAlumno.editarAlumno(id, dato);
    }

    /**
     * Busca un alumno y lo elmina.
     * Si no lo encuentra avisa al usuario que el alumno no se ha podido encontrar
     * @param id id del alumno que se desea eliminar
     * @param CAlumno controlador se alumnos
     */
    public static void eliminarAlumno(int id, ControladorAlumno CAlumno){
        try{
            CAlumno.eliminarAlumno(id);
        }catch(IllegalArgumentException e){
            System.out.println("No se encontró el alumno. No se puede eliminar");
        }
    }


    /**
     * Imprime una lista con los datos de todos los alumnos disponibles
     * @param controladorAlumno Controlador de Alumnos.
     */
    public static void listarAlumnos(ControladorAlumno controladorAlumno) {
        List<Alumno> lista = controladorAlumno.listarAlumnos();

        System.out.printf("%-5s %-25s %-15s %-30s %-15s %-20s %-20s%n", "ID", "Nombre y Apellido", "Numero de CI", "Email", "Teléfono", "Fecha de Nacimiento", "Facultad");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for(Alumno alumno : lista){
            System.out.printf("%-5d %-25s %-15s %-30s %-15s %-20s %-20s%n", alumno.getId(), alumno.getNombreApellido(), alumno.getNumeroCI(), alumno.getEmail(), alumno.getTelefono(), alumno.getFechaNacimiento(), alumno.getFacultad());
        }
    }

    //Fin de los métodos auxiliares de la sección alumnos

   //Métodos auxiliares sin sección en específico

    /**
     *Busca los libros que el usuario quiere prestar. Si no los encuentra, le pregunta al usuario si quiere seguir buscando
     * @param librosPrestados HashMap en donde se almacenan todos los libros que son prestados.
     * @param controladorLibro Controlador de Libros.
     * @return true si se prestaron libros y false si no se prestaron.
     */
    public static boolean prestarLibros(HashMap<Integer, Integer> librosPrestados, ControladorLibro controladorLibro){
        boolean seguir = true;

        do{

            System.out.print("Ingrese el id del libro que desea prestar:");
            int id2 = validarNumero();

            if(!isNumValido(id2)){
                return false;
            }

            System.out.print("Ingrese la cantidad de libros que desea prestar:");
            int cantidad = entrada.nextInt();
            entrada.nextLine();

            Libro libroPrestado = controladorLibro.buscarLibro(id2);

            // Verificación de que el libro ya no fue agregado
            if(librosPrestados.containsKey(id2)){
                System.out.println("El libro seleccionado ya se encuentra en la lista");
            }
            else{
                if(libroPrestado!=null && libroPrestado.getStock()>0 && libroPrestado.getStock()>=cantidad){

                    librosPrestados.put(id2, cantidad);
                    libroPrestado.setStock(libroPrestado.getStock()-cantidad);

                }else{
                    System.out.println("El libro no está disponible");
                }
            }
            System.out.print("Desea prestar más libros?: \n-Si\n-No\nOpción: ");
            String opcion = entrada.nextLine();

            if(opcion.equalsIgnoreCase("No")){
                seguir = false;
            }

        }while(seguir);
        return !librosPrestados.isEmpty();
    }

    /**
     * Analiza una fecha para ver si es válida.
     * Si no es válida le consulta al usuario si quiere seguir intentando.
     * @return fecha ingresada por el usuario.
     */
    public static LocalDate ingresarFecha(){
        LocalDate fecha = null;
        boolean seguir = true;
        do{

            try{
                System.out.print("(Ingrese la fecha con el siguiente formato DD-MM-YYYY):");
                String fechaPrestamo = entrada.nextLine();
                fecha = LocalDate.parse(fechaPrestamo, formatoFecha);
                seguir = false;

            }catch(DateTimeParseException e){
                System.out.print("La fecha ingresada no es válida. ¿Desea intentar de nuevo?\nSi\nNo\nOpción: ");
                String opcion = entrada.nextLine();

                if(opcion.equalsIgnoreCase("No")){
                    seguir = false;
                }
            }
        }while(seguir);
        return fecha;
    }

    //Fin de los métodos auxiliares sin sección en específico

    //Métodos auxiliares para gestionar la sección de préstamos

    /**
     * Busca el préstamo que se quiere devolver, devuelve todos los libros y asigna la fecha de devolución del préstamo
     * @param CLibro Controlador de Libros.
     * @param CPrestamo Controlador de Préstamos.
     */
    public static void devolverPrestamo(ControladorLibro CLibro, ControladorPrestamo CPrestamo){
        boolean seguir = true;
        Prestamo prestamo;
        do{
            System.out.print("Ingrese el id del préstamo que desea devolver:");
            int id = validarNumero();

            if(!isNumValido(id)){
                return;
            }

            prestamo = CPrestamo.buscarPrestamo(id);

            if(prestamo == null){
                System.out.println("Préstamo no encontrado");
                System.out.print("¿Desea intentar de nuevo?\nSi\nNo\nOpción:");
                String opcion = entrada.nextLine();

                if(opcion.equalsIgnoreCase("No")){
                    seguir = false;
                    System.out.println("No se ha podido devolver el préstamo");
                    return;
                }

            }else{
                seguir =  false;
            }
        }while(seguir);
        LocalDate fechaDevolucion = ingresarFecha();

        try{
            CPrestamo.devolverPrestamo(prestamo, CLibro, fechaDevolucion);
        }
        catch(IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Busca un alumno y lo retorna cuando lo encuentra.
     * Si no lo encuentra, consulta al usuario si quiere seguir buscando.
     * @param controladorAlumno Controlador de Alumnos.
     * @return alumno buscado
     */
    public static Alumno buscarAlumnoPrestamo(ControladorAlumno controladorAlumno){
        boolean seguir = true;
        Alumno alumnoPrestamo;

        do{
            System.out.print("Ingrese el id del alumno que desea realizar el préstamo: ");
            int id = validarNumero();

            if(!isNumValido(id)){
                return null;
            }

            alumnoPrestamo = controladorAlumno.buscarAlumno(id);

            if(alumnoPrestamo != null){
                seguir = false;

            } else{
                System.out.println("Alumno no encontrado");
                System.out.print("Desea buscar otro alumno?: \n-Si\n-No\nOpción: ");
                String opcion =  entrada.nextLine();

                if(opcion.equalsIgnoreCase("No")){

                    seguir = false;
                }
            }

        }while(seguir);
        return alumnoPrestamo;
    }

    /**
     *Analiza las fechas para ver si son válidas. Luego retorna un arreglo con esas fechas.
     * @return arreglo de fechas
     */
    public static LocalDate[] ingresarFechasPrestamo(){
        LocalDate[] fechas = new LocalDate[2];
        LocalDate LDFechaPrestamo = null;
        LocalDate  LDFechaLimiteDevolucion =  null;
        boolean seguir = true;

        do{
            try{
                System.out.print("Ingrese la fecha del préstamo: ");
                String fechaPrestamo = entrada.nextLine();
                LDFechaPrestamo = LocalDate.parse(fechaPrestamo, formatoFecha);
                System.out.print("Ingrese la fecha límite de devolución: ");
                String fechaLimiteDevolucion = entrada.nextLine();
                LDFechaLimiteDevolucion = LocalDate.parse(fechaLimiteDevolucion, formatoFecha);
                seguir = false;

            }catch(DateTimeParseException e){
                System.out.println("Fecha Invalida");
                System.out.print("¿Desea intentar de nuevo?: \n-Si\n-No\nOpción: ");
                String opcion = entrada.nextLine();

                if(opcion.equalsIgnoreCase("No")){
                    seguir  = false;
                }
            }
        }while(seguir);

        fechas[0] = LDFechaPrestamo;
        fechas[1] = LDFechaLimiteDevolucion;
        return fechas;
    }

    /**
     * Busca el préstamo que se quiere actualizar, pide los datos actualizados y actualiza el préstamo.
     * @param id id del préstamo que se quiere actualizar
     * @param controladorLibro Controlador de Libros.
     * @param controladorAlumno Controlador de Alumnos.
     * @param controladorPrestamo Controlador de Préstamos.
     */
    public static void editarPrestamo(int id, ControladorLibro controladorLibro, ControladorAlumno controladorAlumno, ControladorPrestamo controladorPrestamo) {
        HashMap<Integer,Integer> librosPrestadosLocal = new HashMap<Integer, Integer>();

        Prestamo prestamoViejo = controladorPrestamo.buscarPrestamo(id);

        if (prestamoViejo == null){
            System.out.println("Préstamo no encontrado");
            return;
        }

        System.out.println("Ingrese los nuevos datos");

        Alumno alumnoPrestamo = buscarAlumnoPrestamo(controladorAlumno);

        if(alumnoPrestamo == null){
            System.out.println("No se ha podido modificar el préstamo");
            return;
        }

        LocalDate fechas[] = ingresarFechasPrestamo();

        if(fechas[0] == null || fechas[1] == null){
            System.out.println("No se ha podido modificar el préstamo");
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
            System.out.println("No se ha podido modificar el préstamo");
            controladorPrestamo.restaurarLibrosPrestamoViejo(auxiliar, controladorLibro);
            return;
        }

        Prestamo datos = new Prestamo(alumnoPrestamo, librosPrestadosLocal, fechas[0], fechas[1], null);

        controladorPrestamo.editarPrestamo(id, datos);
    }

    /**
     * Crea y retorna un Préstamo con los datos ingresados
     * @param controladorLibro Controlador de Libros.
     * @param controladorAlumno Controlador de Alumnos.
     * @return nuevo préstamo
     */
    public static Prestamo crearPrestamo(ControladorLibro controladorLibro, ControladorAlumno controladorAlumno) {
        /*HashMap que gestiona los libros prestados de cada préstamo, en el que la clave es el identificador del libro y
        el valor es la cantidad de libros que presto con ese mismo id.
         */
        HashMap<Integer, Integer> librosPrestadosLocal = new HashMap<Integer, Integer>();

        Alumno alumnoPrestamo = buscarAlumnoPrestamo(controladorAlumno);

        if(alumnoPrestamo == null){
            return null;
        }

        LocalDate fechas[] = ingresarFechasPrestamo();

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
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Busca un préstamo y lo elimina.
     * Si no lo encuentra, avisa al usuario que no se encontró el préstamo
     */
    public static void eliminarPrestamo(int id, ControladorPrestamo CPrestamo){
       try{
           CPrestamo.eliminarPrestamo(id);
       }catch(IllegalArgumentException e ){
           System.out.println("No se encontró el préstamo. No se puede eliminar");
       }
    }

    /**
     * Imprime una lista con todos los datos de todos los préstamos disponibles.
     * @param CPrestamo Controlador de Préstamos.
     * @param CLibro Controlador de Libros.
     */
    public static void listarPrestamos(ControladorPrestamo CPrestamo, ControladorLibro CLibro) {
        if(CPrestamo.listarPrestamos().isEmpty()){
            System.out.println("No existe ningún préstamo");
            return;
        }
        System.out.printf("%-5s %-25s %-20s %-30s %-20s %n","ID", "Alumno", "Fecha del Préstamo", "Fecha límite de devolución", "Fecha Devolución");

        System.out.println("----------------------------------------------------------------------------------------------");

        for (Prestamo prestamo : CPrestamo.listarPrestamos()){

            System.out.printf("%-5d %-25s %-20s %-30s %-20s%n", prestamo.getId(), prestamo.getAlumno().getNombreApellido(), prestamo.getFechaPrestamo(), prestamo.getFechaLimiteDevolucion(), prestamo.getFechaDevolucion());
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.printf("%-40s %-10s%n", "Libros Prestados", "Cantidad");
            System.out.println("    ------------------------------------------------------");

            for(int id : prestamo.getLibrosPrestados().keySet()){
                System.out.printf("%-40s %-10d%n", CLibro.buscarLibro(id).getTitulo(), prestamo.getLibrosPrestados().get(id));

            }

            System.out.println("----------------------------------------------------------------------------------------------");

        }

    }

    /**
     * Imprime una lista con todos los datos de todos los préstamos vencidos disponibles.
     * @param CPrestamo Controlador de Préstamos.
     * @param CLibro Controlador de Libros.
     */
    public static void listarPrestamosVencidos(ControladorPrestamo CPrestamo, ControladorLibro CLibro) {
        if(CPrestamo.listarPrestamosVencidos().isEmpty()){
            System.out.println("No hay prestamos vencidos");

        }else{
            System.out.println("Prestamos vencidos: ");
            System.out.printf("%-5s %-25s %-25s %-30s %-25s %-10s%n","ID", "Alumno", "Fecha del Préstamo", "Fecha límite de devolución", "Fecha Devolución", "Multa");
            System.out.println("------------------------------------------------------------------------------------------");

            for(Prestamo prestamo :  CPrestamo.listarPrestamosVencidos()){
                System.out.printf("%-5s %-25s %-25s %-30s %-25s %-10s%n", prestamo.getId(), prestamo.getAlumno().getNombreApellido(), prestamo.getFechaPrestamo(), prestamo.getFechaLimiteDevolucion(), prestamo.getFechaDevolucion(), prestamo.getMulta()+" G");

                System.out.printf("    %-35s %-10s%n", "Libros Prestados", "Cantidad");
                System.out.println("    ------------------------------------------------------");

                for(int id : prestamo.getLibrosPrestados().keySet()){
                    System.out.printf("    %-35s %-10d%n", CLibro.buscarLibro(id).getTitulo(), prestamo.getLibrosPrestados().get(id));

                }

                System.out.println("----------------------------------------------------------------------------------------------");
            }
        }
    }

    /**
     * Recibe un número y verifica si es un número válido.
     * Si no es válido, le consulta al usuario si quiere intentar de nuevo.
     */
    public static int validarNumero(){
        boolean seguir = true;
        int numero = 0;

        do{
            try{
                numero = entrada.nextInt();
                entrada.nextLine();
                seguir = false;

            }catch(InputMismatchException e ){
                // nextLine() que limpia el texto ingresado antes.
                entrada.nextLine();
                System.out.print("El número ingresado es inválido. ¿Desea intentar de nuevo?\nSi\nNo\nOpción:");
                String opcion = entrada.nextLine();

                if(opcion.equalsIgnoreCase("No")){
                    seguir = false;
                    return -1;
                }

                System.out.print("Ingrese un número:");
            }
        }while(seguir);
        return numero;
    }

    /**
     * Verifica que el número sea o no igual a -1.
     * Si es igual retorna false y si no lo es retorna true.
     * @param id número que se verifica
     * @return false si es igual a -1 y true si no es igual
     */
    public static boolean isNumValido(int id){
        if(id == -1){
            System.out.println("No ah ingresado ningún número válido, fin de la operación");
            return false;
        }
        return true;
    }

    //Fin de los métodos auxiliares para la sección de préstamos
}
