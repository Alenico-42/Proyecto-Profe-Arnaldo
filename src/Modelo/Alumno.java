package Modelo;

import java.time.LocalDate;

public class Alumno {

    //Atributos de la clase

    private static int contadorId = 1;
    private final int id;
    private String nombreApellido;
    private String numeroCI;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String facultad;


    public Alumno(String nombreApellido, String numeroCI, String email, String telefono, LocalDate fechaNacimiento,  String facultad) {
        setNombreApellido(nombreApellido);
        setNumeroCI(numeroCI);
        setEmail(email);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
        setFacultad(facultad);
        this.id =  contadorId++;
    }

    //Métodos getters

    public String getNombreApellido() {
        return nombreApellido;
    }

    public String getNumeroCI() {
        return numeroCI;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFacultad() {
        return facultad;
    }

    public int getId() {
        return id;
    }

    //Fin métodos getters

    //Métodos setters

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public void setNumeroCI(String numeroCI) {
        this.numeroCI = numeroCI;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    //Fin de métodos setters

    @Override
    public String toString(){
       // System.out.printf("Nombre y apellido: %s%n Número de CI: %s%n Email: %s%n Telefono: %s%n Fecha de Nacimiento: %s%n Facultad: %s",this.nombreApellido, this.numeroCI, this.email, this.telefono, this.fechaNacimiento.toString(), this.facultad);
        return "Nombre y Apellido: "+nombreApellido+"\nCI: "+numeroCI+"\nEmail: "+email+"\nTeléfono: "+telefono+"\nFecha de Nacimiento: "+fechaNacimiento.toString()+"\nFacultad: "+facultad;
    }

}
