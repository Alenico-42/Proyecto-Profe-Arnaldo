package Repositorio;
import java.util.HashMap;
import Modelo.Libro;
import java.util.List;
import java.util.ArrayList;

public class RepositorioLibro implements Repositorio<Libro> {
    //HashMap que almacena todos los Libros que se crean
    private HashMap<Integer, Libro> libros = new HashMap<Integer, Libro>();

    /**
     * Agrega un nuevo libro al repositorio
     * @param libro objeto de tipo Libro
     */
    @Override
    public void agregar(Libro libro){
        if(libros.containsKey(libro.getId())){
            return;
        }
        libros.put(libro.getId(), libro);
    }

    /**
     * Se actualizan los datos del libro existente por los nuevos datos recibidos
     * @param id id del Libro que se desea editar
     * @param datos objeto de tipo Libro que contiene todos los datos que se van a cambiar
     */
    @Override
    public void editar(int id, Libro datos){
        Libro aux= libros.get(id);

        if(aux == null){
            return;
        }

        aux.setTitulo(datos.getTitulo());
        aux.setEditorial(datos.getEditorial());
        aux.setYearPublicacion(datos.getYearPublicacion());
        aux.setAutor(datos.getAutor());
        aux.setStock(datos.getStock());
    }

    /**
     * Elimina un libro del repositorio
     * @param id id del libro que se desea eliminar
     */
    @Override
    public void eliminar(int id){
        libros.remove(id);
    }

    /**
     * Busca un libro por su id y lo retorna
     * @param id id del libro que se desea buscar
     * @return Libro encontrado o null si no lo encuentra
     */
    @Override
    public Libro buscar(int id){

        return libros.get(id);
    }

    /**
     * Crea una lista de todos los libros y la retorna
     * @return lista de todos los libros dle repositorio
     */
    @Override
    public List<Libro> listar(){
        return new ArrayList<Libro> (libros.values());
    }
}
