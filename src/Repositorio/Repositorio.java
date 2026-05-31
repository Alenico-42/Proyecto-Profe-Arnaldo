package Repositorio;
import java.util.List;


/**
 * Interfaz genérica para operaciones básica que debe realizar cada modelo (Alumno, Libro y Préstamo).
 * @param <M> tipo genérico del modelo
 */
public interface Repositorio<M>{
    void agregar(M elemento);

    void editar(int id, M datos);

    void eliminar(int id);

    M buscar(int id);

    List<M> listar();
}
