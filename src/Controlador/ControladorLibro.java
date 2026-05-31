package Controlador;

import Modelo.Libro;
import Repositorio.Repositorio;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de los objetos de tipo Libro.
 * Comunica a la vista con el RepositorioLibro
 */
public class ControladorLibro {
    private Repositorio<Libro> repositorio;

    public ControladorLibro( Repositorio<Libro> repositorio){
        this.repositorio = repositorio;
    }

    public void agregarLibro(Libro libro){
        repositorio.agregar(libro);
    }

    public void eliminarLibro(int id){
        if(repositorio.buscar(id) != null){
            repositorio.eliminar(id);
        }
        else{
            throw new IllegalArgumentException("No se encontro el libro. No se puede eliminar");
        }

    }

    public Libro buscarLibro(int id){
        return repositorio.buscar(id);
    }

    public void editarLibro(int id, Libro datos){
        repositorio.editar(id, datos);
    }

    public List<Libro> listarLibros(){
        return repositorio.listar();
    }

}
