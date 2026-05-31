package Modelo;

import java.time.LocalDate;
import java.util.HashMap;

public class Prestamo {

    //Atributos de la clase

    private Alumno alumno;
    private HashMap<Integer, Integer> librosPrestados;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimiteDevolucion;
    private LocalDate fechaDevolucion;
    private final int id;
    private static int contadorId = 1;
    private long multa;

    public Prestamo(Alumno alumno, HashMap<Integer, Integer> libros, LocalDate fechaPrestamo, LocalDate fechaLimiteDevolucion, LocalDate fechaDevolucion) {
        setAlumno(alumno);
        setLibros(libros);
        setFechaPrestamo(fechaPrestamo);
        setFechaLimiteDevolucion(fechaLimiteDevolucion);
        setFechaDevolucion(fechaDevolucion);
        this.id = contadorId++;
    }

    //Métodos getters

    public Alumno getAlumno() {
        return alumno;
    }

    public HashMap<Integer,Integer> getLibrosPrestados() {
        return librosPrestados;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaLimiteDevolucion() {
        return fechaLimiteDevolucion;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public int getId() {
        return id;
    }

    public long getMulta() {
        return multa;
    }

    //Fin de los métodos getters

    //Métodos setters

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setLibros(HashMap<Integer, Integer> libros) {
        this.librosPrestados = libros;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * Establece la fecha limite que tiene el usuario para devolver el libro.
     * El if dentro del método, verifica que el LocalDate pasado por parámetro no sea null, evitando así un nullPointerException;
     * también verifica que la fecha pasada por parámetro no sea antes de la fecha del préstamo, si la fecha es inválida,
     * se lanza una excepción.
     * @param fechaLimiteDevolucion fecha límite que tiene el usuario para devolver el préstamo
     */

    public void setFechaLimiteDevolucion(LocalDate fechaLimiteDevolucion) {
        if(fechaLimiteDevolucion!=null){
            if(!fechaLimiteDevolucion.isBefore(fechaPrestamo)){
                this.fechaLimiteDevolucion = fechaLimiteDevolucion;
            } else{
                throw new IllegalArgumentException("La fecha Limite no puede ser antes de la fecha del préstamo");
            }
        }
    }

    /**
     * Establece la fecha de devolución del libro.
     * El if dentro del método, verifica que el LocalDate pasado por parámetro no sea null, evitando así un nullPointerException;
     * también tiene otro if que verifica que la fecha pasada por parámetro no sea antes de la fecha del préstamo, si la
     * fecha es inválida se lanza una excepción.
     * @param fechaDevolucion fecha en la que el libro fue devuelto.
     */

    public void setFechaDevolucion(LocalDate fechaDevolucion){
        if(fechaDevolucion!=null){
            if(!fechaDevolucion.isBefore(fechaPrestamo)){
                this.fechaDevolucion = fechaDevolucion;
            } else{
                throw new IllegalArgumentException("La fecha de devolución no puede ser antes de la fecha del préstamo");
            }
        }
    }

    public void setMulta(long multa) {
        this.multa = Math.abs(multa);
    }

    //Fin de los métodos setters

    @Override
    public String toString() {
       return "Nombre y Apellido del Alumno: "+alumno.getNombreApellido()+"\nFecha del préstamo: "+fechaPrestamo+"\nFecha Límite para la devolución: "+fechaLimiteDevolucion+"\nFecha de la devolución: "+fechaDevolucion+"\nLibros prestados: ";
    }
}
