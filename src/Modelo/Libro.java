package Modelo;

public class Libro {

    //Atributos de la clase

    private static int contadorId = 1;
    private  final int id;
    private String titulo;
    private String editorial;
    private int yearPublicacion;
    private String autor;
    public int stock;

    public Libro(String titulo, String editorial, int yearPublicacion, String autor, int stock) {
        setTitulo(titulo);
        setEditorial(editorial);
        setYearPublicacion(yearPublicacion);
        setAutor(autor);
        setStock(stock);
        this.id = contadorId++;
    }

    //Métodos getters

    public String getTitulo() {
        return titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getYearPublicacion() {
        return yearPublicacion;
    }

    public String getAutor() {
        return autor;
    }

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    //Fin de los métodos getters

    //Métodos setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setYearPublicacion(int yearPublicacion) {
        this.yearPublicacion = yearPublicacion;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setStock(int stock) {
        this.stock = Math.abs(stock);
    }

    //Final de los métodos setters

    @Override
    public String toString() {
        return "Título: "+titulo+"\nAño de publicacion: "+yearPublicacion+"\nAutor: "+autor+"Editorial: "+editorial;
    }

}
