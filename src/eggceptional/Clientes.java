package eggceptional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Clientes extends JFrame {
    public Clientes() {
        setTitle("Clientes");
        setSize(600, 400);
        setResizable(true); // Permite agrandar la ventana
        setLayout(null);    // Layout absoluto: los componentes mantienen su tamaño y posición

        // 1. Formulario para agregar/editar clientes
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 60, 25);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(90, 20, 120, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(230, 20, 60, 25);
        add(lblApellido);

        JTextField txtApellido = new JTextField();
        txtApellido.setBounds(300, 20, 120, 25);
        add(txtApellido);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 60, 60, 25);
        add(lblTelefono);

        JTextField txtTelefono = new JTextField();
        txtTelefono.setBounds(90, 60, 120, 25);
        add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(230, 60, 60, 25);
        add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(300, 60, 120, 25);
        add(txtEmail);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(20, 100, 60, 25);
        add(lblDireccion);

        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(90, 100, 330, 25);
        add(txtDireccion);

        // 2. Botón "Agregar Cliente"
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(440, 20, 120, 30);
        add(btnAgregar);

        // 5. Campo de búsqueda
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(20, 140, 60, 25);
        add(lblBuscar);

        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(90, 140, 200, 25);
        add(txtBuscar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(300, 140, 80, 25);
        add(btnBuscar);

        // 3. Tabla para listar clientes
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nombre", "Apellido", "Teléfono", "Email", "Dirección"}, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 180, 540, 120);
        add(scroll);

        // 4. Botones "Editar" y "Eliminar"
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(440, 60, 120, 30);
        add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(440, 100, 120, 30);
        add(btnEliminar);

        // 6. Botón "Volver"
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(440, 320, 120, 30);
        add(btnVolver);

        // Acciones simuladas
        btnAgregar.addActionListener(_ -> {
            modelo.addRow(new Object[]{
                txtNombre.getText(),
                txtApellido.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                txtDireccion.getText()
            });
            txtNombre.setText("");
            txtApellido.setText("");
            txtTelefono.setText("");
            txtEmail.setText("");
            txtDireccion.setText("");
        });

        btnBuscar.addActionListener(_ -> {
            String buscar = txtBuscar.getText().toLowerCase();
            tabla.clearSelection();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                boolean match = false;
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    if (modelo.getValueAt(i, j).toString().toLowerCase().contains(buscar)) {
                        match = true;
                        break;
                    }
                }
                if (match) {
                    tabla.addRowSelectionInterval(i, i);
                }
            }
        });

        btnEditar.addActionListener(_ -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                modelo.setValueAt(txtNombre.getText(), fila, 0);
                modelo.setValueAt(txtApellido.getText(), fila, 1);
                modelo.setValueAt(txtTelefono.getText(), fila, 2);
                modelo.setValueAt(txtEmail.getText(), fila, 3);
                modelo.setValueAt(txtDireccion.getText(), fila, 4);
            }
        });

        btnEliminar.addActionListener(_ -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                modelo.removeRow(fila);
            }
        });

        btnVolver.addActionListener(_ -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}