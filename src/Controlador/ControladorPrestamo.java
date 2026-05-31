package Controlador;

import Modelo.Libro;
import Modelo.Prestamo;
import Repositorio.Repositorio;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de los objetos de tipo Prestamo.
 * Comunica a la vista con el RepositorioPrestamo.
 */
public class ControladorPrestamo {
    private Repositorio<Prestamo> repositorio;

    public ControladorPrestamo(Repositorio<Prestamo> repositorio) {

        this.repositorio = repositorio;
    }

    public void agregarPrestamo(Prestamo prestamo) {

        this.repositorio.agregar(prestamo);
    }

    public void eliminarPrestamo(int id) {
        if(repositorio.buscar(id) != null){
            this.repositorio.eliminar(id);
        }
        else{
            throw new IllegalArgumentException("No se encontro el prestamo. No se puede eliminar");
        }

    }

    public Prestamo buscarPrestamo(int id) {
        return this.repositorio.buscar(id);
    }

    public void editarPrestamo(int id, Prestamo datos) {
        this.repositorio.editar(id, datos);
    }

    public List<Prestamo> listarPrestamos() {
        return this.repositorio.listar();
    }

    /**
     * Crea una lista con todos los préstamos que hayan vencido dentro del repositorio y la retorna.
     * @return lista de todos los préstamos vencidos.
     */
    public List<Prestamo> listarPrestamosVencidos(){
        ArrayList<Prestamo> listaVencidos = new ArrayList<Prestamo>();
        //For para agregar prestamos devueltos y que vencieron
        for(Prestamo prestamo : repositorio.listar()) {
            LocalDate fechaDevolucion = prestamo.getFechaDevolucion();
            LocalDate fechaLimite = prestamo.getFechaLimiteDevolucion();

            if (fechaDevolucion != null && fechaDevolucion.isAfter(prestamo.getFechaLimiteDevolucion())) {
                listaVencidos.add(prestamo);
            }

            //El ! delante de la segunda condicion es para que incluya que vencio si hoy es el dia de vecimiento
            else if(fechaDevolucion == null && !fechaLimite.isAfter(LocalDate.now())) {
                listaVencidos.add(prestamo);
            }
        }
        return listaVencidos;
    }

    /**
     * Verifica si algún préstamo fue devuelto antes de tiempo y aplica una multa de acuerdo a los días de retraso.
     * @param prestamo préstamo del cual se quiere saber si tiene multa o no.
     */
    public void calcularMulta(Prestamo prestamo){
        if(prestamo.getFechaDevolucion() != null && prestamo.getFechaDevolucion().isAfter(prestamo.getFechaLimiteDevolucion())){
            long diasMulta = ChronoUnit.DAYS.between(prestamo.getFechaLimiteDevolucion(), prestamo.getFechaDevolucion());
            long multa = diasMulta * 5000;
            prestamo.setMulta(multa);

        }else{
            prestamo.setMulta(0);
        }
    }

    /**
     * Busca un préstamo dentro del repositorio, devuelve todos los libros que ese préstamo presto y le asigna la fecha
     * en la que fue devuelto el mismo.
     * @param prestamo préstamo que se quiere devolver.
     * @param CLibro controlador que gestiona el repositorio de libros.
     * @param FechaDevolucion fecha en la que fue devuelto el préstamo.
     */
    public void devolverPrestamo(Prestamo prestamo, ControladorLibro CLibro, LocalDate FechaDevolucion){
        if(prestamo==null){
            throw new IllegalArgumentException("Préstamo no encontrado");
        }

        if(prestamo.getFechaDevolucion() == null){
            devolverLibrosPrestamo(prestamo, CLibro);
            prestamo.setFechaDevolucion(FechaDevolucion);
            calcularMulta(prestamo);

        }else{
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }
    }

    /**
     * Método auxiliar que devuelve todos los libros prestados en un préstamo. Se creo para usarlo en el método
     * devolverPrestamo y hacerlo más limpio.
     * @param prestamo préstamo del cual se quieren devolver todos los libros.
     * @param CLibro controlador que gestiona el repositorio de libros.
     */
    public void devolverLibrosPrestamo(Prestamo prestamo, ControladorLibro CLibro){
        for(int id : prestamo.getLibrosPrestados().keySet()){
            Libro libro = CLibro.buscarLibro(id);

            if(libro!=null){
                libro.setStock(libro.getStock() + prestamo.getLibrosPrestados().get(id) );
            }
        }
    }

    /**
     * Crea un backup de los libros prestados en un préstamo y la retorna en forma de HashMap
     * @param prestamo préstamo del cual se quiere realizar el backup
     * @return HashMap con el backup de los libros prestados del préstamo
     */
    public HashMap<Integer, Integer> crearMapProvisional(Prestamo prestamo){
        HashMap<Integer, Integer> mapProvisional =  new HashMap<Integer, Integer>();

        for(int id : prestamo.getLibrosPrestados().keySet() ){
            mapProvisional.put(id, prestamo.getLibrosPrestados().get(id));
        }
        return  mapProvisional;
    }

    /**
     * Restaura el stock de libros con la información de los libros prestados en un préstamo.
     * @param aux HashMap con los id de los libros prestados y las cantidades prestadas de esos libros
     * @param CLibro controlador del repositorio de libros
     */
    public void restaurarLibrosPrestamoViejo(HashMap<Integer, Integer> aux, ControladorLibro CLibro){
        for(int id : aux.keySet()){
            Libro libro  = CLibro.buscarLibro(id);

            if(libro!=null){
                libro.setStock(libro.getStock() + aux.get(id) );
            }
        }
    }
}
