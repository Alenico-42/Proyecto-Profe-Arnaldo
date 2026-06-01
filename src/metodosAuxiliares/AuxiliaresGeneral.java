package metodosAuxiliares;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AuxiliaresGeneral {

    private AuxiliaresGeneral(){}

    public static int validarNumero(){

        while(true){
            try{
                String numero1 = JOptionPane.showInputDialog("Ingrese un número:");

                if (numero1 == null) {
                    return -1;
                }

                return Integer.parseInt(numero1);

            }catch(NumberFormatException e ){
                String opcion = JOptionPane.showInputDialog("El número ingresado es inválido. ¿Desea intentar de nuevo?\nSi\nNo\nOpción:");

                if(opcion==null || opcion.equalsIgnoreCase("No")){
                    return -1;
                }
            }
        }
    }

    public static boolean isNumValido(int id){
        if(id == -1){
            JOptionPane.showMessageDialog(null, "No a ingresado ningún número válido, fin de la operación");
            return false;
        }
        return true;
    }

    public static LocalDate ingresarFecha(DateTimeFormatter formatoFecha){
        LocalDate fecha = null;
        boolean seguir = true;
        do{

            try{

                String fechaPrestamo = JOptionPane.showInputDialog("(Ingrese la fecha con el siguiente formato DD-MM-YYYY):");
                fecha = LocalDate.parse(fechaPrestamo, formatoFecha);
                seguir = false;

            }catch(DateTimeParseException e){
                String opcion = JOptionPane.showInputDialog("La fecha ingresada no es válida. ¿Desea intentar de nuevo?\nSi\nNo\nOpción: ");

                if(opcion.equalsIgnoreCase("No") || opcion==null){
                    seguir = false;
                }
            }
        }while(seguir);
        return fecha;
    }
}
