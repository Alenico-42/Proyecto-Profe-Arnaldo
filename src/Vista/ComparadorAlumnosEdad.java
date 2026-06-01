package Vista;

import Modelo.Alumno;

import java.util.Comparator;

public class ComparadorAlumnosEdad implements Comparator<Alumno> {
    @Override
    public int compare(Alumno a1, Alumno a2) {
        return Integer.compare(a1.getFechaNacimiento().getYear(), a2.getFechaNacimiento().getYear()); // ordena menor→mayor
    }
}