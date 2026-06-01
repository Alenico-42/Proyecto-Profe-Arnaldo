package Vista;
import Modelo.Prestamo;


import java.util.Comparator;


public class ComparadorPrestamoUsuario implements Comparator<Prestamo> {
    @Override
    public int compare(Prestamo p1, Prestamo p2) {
        return p1.getAlumno().getNombreApellido().compareTo(p2.getAlumno().getNombreApellido());
    }
}
