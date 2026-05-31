package Controlador;
import Modelo.Alumno;
import Repositorio.Repositorio;

import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de los objetos de tipo Alumno.
 * Comunica a la vista con el RepositorioAlumno
 */
public class ControladorAlumno {
    private Repositorio<Alumno> repositorio;

    public ControladorAlumno(Repositorio<Alumno> repositorio){
        this.repositorio = repositorio;
    }

    public void agregarAlumno(Alumno alumno){
        repositorio.agregar(alumno);
    }

    public void eliminarAlumno(int id){
        if(repositorio.buscar(id) != null){
            repositorio.eliminar(id);
        }
        else{
            throw new IllegalArgumentException("No se encontro el Alumno. No se puede eliminar");
        }
    }

    public Alumno buscarAlumno(int id){
        return repositorio.buscar(id);
    }

    public void editarAlumno(int id, Alumno datos){
        repositorio.editar(id, datos);
    }

    public List<Alumno> listarAlumnos(){
        return repositorio.listar();
    }

}
