package Vista;
import Controlador.*;
import Modelo.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import metodosAuxiliares.AuxiliaresGeneral;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelGestionarAlumno extends JPanel {

    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final VentanaPrincipal ventanaPrincipal;

    //Se crean los controladores

    ControladorAlumno CAlumno;

    public PanelGestionarAlumno(VentanaPrincipal ventanaPrincipal, ControladorAlumno CAlumno) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.CAlumno=CAlumno;
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 2, 2));

        JButton agregarAlumno = new JButton("1-Agregar Alumno");
        agregarAlumno.addActionListener(e -> agregarAlumno());

        JButton editarAlumno = new JButton("2-Editar Alumno");
        editarAlumno.addActionListener(e -> editarAlumno());

        JButton eliminarAlumno = new JButton("3-Eliminar Alumno");

        eliminarAlumno.addActionListener(e -> eliminarAlumno());

        JButton listarAlumnos = new JButton("4-Listar Alumnos");
        listarAlumnos.addActionListener(e -> {
            listarAlumnos();


        });

        JButton salir = new JButton("5-Salir");
        salir.addActionListener(e -> ventanaPrincipal.mostrarPanel("Menu Principal"));

        panelBotones.add(agregarAlumno);
        panelBotones.add(editarAlumno);
        panelBotones.add(eliminarAlumno);
        panelBotones.add(listarAlumnos);
        panelBotones.add(salir);

        add(panelBotones, BorderLayout.CENTER);
    }

    private void agregarAlumno() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
            String ci = JOptionPane.showInputDialog("Ingrese su CI");
            String email = JOptionPane.showInputDialog("Ingrese su email");
            String telefono = JOptionPane.showInputDialog("Ingrese su número de teléfono");
            LocalDate fechaNacimiento = AuxiliaresGeneral.ingresarFecha(formatoFecha);
            String facultad = JOptionPane.showInputDialog("Ingrese la facultad a la que pertenece");

            Alumno alumno = new Alumno(nombre, ci, email, telefono, fechaNacimiento, facultad);
            CAlumno.agregarAlumno(alumno);
            JOptionPane.showMessageDialog(null, "Alumno agregado correctamente");
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(null, "Ingrese los datos");
        }
    }

    private void editarAlumno() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del alumno a editar"));

        if (CAlumno.buscarAlumno(id) == null) {
            JOptionPane.showMessageDialog(null, "Alumno no encontrado");
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog("Si quiere modificar el nombre, escríbelo, sino apriete enter");
        if (!nuevoNombre.isEmpty()) {
            CAlumno.buscarAlumno(id).setNombreApellido(nuevoNombre);
        }

        String nuevoCi = JOptionPane.showInputDialog("Si quiere modificar el número de documento, escríbelo, sino apriete enter");
        if (!nuevoCi.isEmpty()) {
            CAlumno.buscarAlumno(id).setNumeroCI(nuevoCi);
        }

        String nuevoEmail = JOptionPane.showInputDialog("Si quiere modificar el email, escríbelo, sino apriete enter");
        if (!nuevoEmail.isEmpty()) {
            CAlumno.buscarAlumno(id).setEmail(nuevoEmail);
        }

        String nuevoTelefono = JOptionPane.showInputDialog("Si quiere modificar el teléfono, escríbelo, sino apriete enter");
        if (!nuevoTelefono.isEmpty()) {
            CAlumno.buscarAlumno(id).setTelefono(nuevoTelefono);
        }

        String fechaStr = JOptionPane.showInputDialog("Si quiere modificar la fecha de nacimiento, escríbelo, sino apriete enter");
        if (fechaStr != null && !fechaStr.isEmpty()) {
            CAlumno.buscarAlumno(id).setFechaNacimiento(LocalDate.parse(fechaStr));
        }

        String nuevaFacultad = JOptionPane.showInputDialog("Si quiere modificar la facultad, escríbelo, sino apriete enter");
        if (!nuevaFacultad.isEmpty()) {
            CAlumno.buscarAlumno(id).setFacultad(nuevaFacultad);
        }
    }

    private void eliminarAlumno() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del alumno a eliminar"));
        if (CAlumno.buscarAlumno(id) == null) {
            JOptionPane.showMessageDialog(null, "Alumno no encontrado");
            return;
        }
        CAlumno.eliminarAlumno(id);
        JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente");
    }

    private void listarAlumnos() {
        List<Alumno> lista = CAlumno.listarAlumnos();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay alumnos registrados.");
            return;
        }

// JComboBox
        String[] opciones = {"Sin filtro", "Por Nombre", "Por Edad"};
        JComboBox<String> comboFiltro = new JComboBox<>(opciones);

// Botón
        JButton btnFiltrar = new JButton("Filtrar");

// Panel filtro
        JPanel panelFiltro = new JPanel();
        panelFiltro.add(new JLabel("Ordenar: "));
        panelFiltro.add(comboFiltro);
        panelFiltro.add(btnFiltrar);

// Columnas
        String[] columnas = {"ID", "Nombre", "CI", "Email", "Teléfono", "Nacimiento", "Facultad"};
//
// Modelo de tabla (esto permite actualizarla)
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

// Tabla con el modelo
        JTable tabla = new JTable(modelo);
        tabla.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(900, 300));

// Método para cargar filas en la tabla
        Runnable cargarTabla = () -> {
            modelo.setRowCount(0); // limpia las filas
            List<Alumno> alumnos = CAlumno.listarAlumnos();

            String seleccion = (String) comboFiltro.getSelectedItem();
            if (seleccion.equals("Por Nombre")) {
                alumnos.sort(new ComparadorAlumnosNombre());
            } else if (seleccion.equals("Por Edad")) {
                alumnos.sort(new ComparadorAlumnosEdad());
            }

            for (Alumno a : alumnos) {
                modelo.addRow(new Object[]{
                        a.getId(),
                        a.getNombreApellido(),
                        a.getNumeroCI(),
                        a.getEmail(),
                        a.getTelefono(),
                        a.getFechaNacimiento(),
                        a.getFacultad()
                });
            }
        };

// Cargar datos iniciales
        cargarTabla.run();

// Acción del botón filtrar
        btnFiltrar.addActionListener(e -> cargarTabla.run());

// Panel principal del diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelFiltro, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

// Mostrar diálogo
        JOptionPane.showMessageDialog(null, panelPrincipal, "Lista de Alumnos", JOptionPane.PLAIN_MESSAGE);

    }

}