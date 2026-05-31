package Repositorio;
import java.util.HashMap;
import Modelo.Alumno;
import java.util.List;
import java.util.ArrayList;

public class RepositorioAlumno implements Repositorio<Alumno> {
    //HashMap que almacena todos los alumnos que se crean
    private HashMap<Integer, Alumno> alumnos = new  HashMap<Integer, Alumno>();

    /**
     * Agrega un nuevo alumno al HashMap de alumnos
     * @param alumno objeto de tipo Alumno
     */
    @Override
    public void agregar(Alumno alumno){
        if(alumnos.containsKey(alumno.getId())){
           return;
        }
        alumnos.put(alumno.getId(), alumno);
    }

    /**
     * Se actualiza los datos del alumno por los nuevos datos recibidos
     * @param datos objeto de tipo Alumno que contiene los datos actualizados
     * @param id id del alumno que se quiere modificar
     */
    @Override
    public void editar(int id, Alumno datos){
        Alumno aux = alumnos.get(id);

        if(aux == null){
            return;
        }

        aux.setNumeroCI(datos.getNumeroCI());
        aux.setNombreApellido(datos.getNombreApellido());
        aux.setEmail(datos.getEmail());
        aux.setTelefono(datos.getTelefono());
        aux.setFechaNacimiento(datos.getFechaNacimiento());
        aux.setFacultad(datos.getFacultad());
    }

    /**
     * Elimina un alumno del HashMap de alumnos por su número de id.
     * @param id id del Alumno que se desea eliminar.
     */
    @Override
    public void eliminar(int id){
        alumnos.remove(id);
    }

    /**
     * Busca al alumno por el número de id.
     * @param id id del alumno que se desea buscar
     * @return Alumno encontrado o null si no lo encuentra
     */
    @Override
    public Alumno buscar(int id){
        return  alumnos.get(id);
    }

    /**
     * Crea una lista con todos alumnos y luego la retorna
     * @return lista de todos los alumnos del repositorio
     */
    @Override
    public List<Alumno> listar(){
        return new ArrayList<Alumno> (alumnos.values());
    }
}
