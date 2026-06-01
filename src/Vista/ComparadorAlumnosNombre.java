package Vista;

import Modelo.Alumno;

import java.util.Comparator;

// ComparadorAlumnosNombre.java
public class ComparadorAlumnosNombre implements Comparator<Alumno> {
    @Override
    public int compare(Alumno a1, Alumno a2) {
        return a1.getNombreApellido().compareTo(a2.getNombreApellido()); // ordena A→Z
    }
}