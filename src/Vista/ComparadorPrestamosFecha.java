package Vista;
import Modelo.Prestamo;


import java.util.Comparator;

public class ComparadorPrestamosFecha implements Comparator<Prestamo> {


    @Override
    public int compare(Prestamo p1, Prestamo p2) {
        return p1.getFechaPrestamo().compareTo(p2.getFechaPrestamo());
    }
}
