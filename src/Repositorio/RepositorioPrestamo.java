package Repositorio;
import Modelo.Prestamo;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RepositorioPrestamo implements Repositorio<Prestamo> {
    //HashMap que almacena todos los préstamos que se crean
    private HashMap <Integer, Prestamo> prestamos= new HashMap<Integer, Prestamo>();

    /**
     * Agrega un nuevo préstamo al repositorio.
     * @param prestamo préstamo que es agregado al repositorio
     */
    @Override
    public void agregar(Prestamo prestamo){
        //Se verifica que ya no exista en el repositorio el prestamo que se quiere agregar
        if(prestamos.containsKey(prestamo.getId())){
           return;
        }
        prestamos.put(prestamo.getId(), prestamo);
    }

    /**
     * Actualiza los datos del préstamo por los nuevos datos recibidos
     * @param id id del préstamo que se quiere actualizar
     * @param datos objeto de tipo Préstamo que contiene los datos actualizados
     */
    @Override
    public void editar(int id, Prestamo datos){
        Prestamo aux=prestamos.get(id);

        if(aux == null){
            return;
        }

        aux.setAlumno(datos.getAlumno());
        aux.setFechaPrestamo(datos.getFechaPrestamo());
        aux.setLibros(datos.getLibrosPrestados());
        aux.setFechaLimiteDevolucion(datos.getFechaLimiteDevolucion());
        aux.setFechaDevolucion(datos.getFechaDevolucion());
    }

    /**
     * Elimina un préstamo del repositorio
     * @param id id del préstamo que se quiere eliminar
     */
    @Override
    public void eliminar(int id){
        prestamos.remove(id);
    }

    /**
     * Busca un préstamo por su id y lo retorna
     * @param id id del préstamo que se quiere buscar
     * @return préstamo buscado o null si no lo encuentra
     */
    @Override
    public Prestamo buscar(int id){
        return prestamos.get(id);
    }

    /**
     * Crea una lista con todos los préstamos del repositorio y la retorna
     * @return lista de todos los préstamos del repositorio
     */
    @Override
    public List<Prestamo> listar() {
        return new ArrayList<Prestamo>(prestamos.values());
    }
}
