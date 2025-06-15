package eggceptional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ventas extends JFrame {
    public Ventas() {
        setTitle("Ventas");
        setSize(600, 450);
        setResizable(true); // Permite agrandar la ventana, pero los componentes no cambian de tamaño
        setLayout(null);    // Layout absoluto

        // 1. Campos para ingresar datos de la venta
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(30, 20, 80, 25);
        add(lblProducto);

        JTextField txtProducto = new JTextField();
        txtProducto.setBounds(120, 20, 150, 25);
        add(txtProducto);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(30, 60, 80, 25);
        add(lblCantidad);

        JTextField txtCantidad = new JTextField();
        txtCantidad.setBounds(120, 60, 60, 25);
        add(txtCantidad);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(200, 60, 50, 25);
        add(lblPrecio);

        JTextField txtPrecio = new JTextField();
        txtPrecio.setBounds(250, 60, 80, 25);
        add(txtPrecio);

        // 6. Campo para seleccionar cliente
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(350, 20, 60, 25);
        add(lblCliente);

        JTextField txtCliente = new JTextField();
        txtCliente.setBounds(410, 20, 150, 25);
        add(txtCliente);

        // 2. Botón "Agregar a la venta"
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(350, 60, 100, 25);
        add(btnAgregar);

        // 3. Tabla de detalle de venta
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Precio", "Subtotal"}, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 100, 530, 150);
        add(scroll);

        // 7. Mostrar total general de la venta
        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(400, 270, 50, 25);
        add(lblTotal);

        JTextField txtTotal = new JTextField("0.00");
        txtTotal.setBounds(450, 270, 110, 25);
        txtTotal.setEditable(false);
        add(txtTotal);

        // 4. Botón "Finalizar venta"
        JButton btnFinalizar = new JButton("Finalizar venta");
        btnFinalizar.setBounds(120, 320, 140, 30);
        add(btnFinalizar);

        // 5. Botón "Cancelar"
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, 320, 120, 30);
        add(btnCancelar);

        // Acción para agregar producto a la tabla
        btnAgregar.addActionListener(_ -> {
            try {
                String producto = txtProducto.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precio = Double.parseDouble(txtPrecio.getText());
                double subtotal = cantidad * precio;
                modelo.addRow(new Object[]{producto, cantidad, precio, subtotal});
                // Actualizar total
                double total = 0;
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    total += (double) modelo.getValueAt(i, 3);
                }
                txtTotal.setText(String.format("%.2f", total));
                // Limpiar campos producto/cantidad/precio
                txtProducto.setText("");
                txtCantidad.setText("");
                txtPrecio.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        // Acción para finalizar venta (simulada)
        btnFinalizar.addActionListener(_ -> {
            JOptionPane.showMessageDialog(this, "Venta registrada (simulado)");
            modelo.setRowCount(0);
            txtTotal.setText("0.00");
        });

        // Acción para cancelar
        btnCancelar.addActionListener(_ -> {
            modelo.setRowCount(0);
            txtTotal.setText("0.00");
            txtProducto.setText("");
            txtCantidad.setText("");
            txtPrecio.setText("");
            txtCliente.setText("");
        });

        setLocationRelativeTo(null);
        setVisible(true);

        // Botón para volver atrás
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(430, 320, 100, 30);
        add(btnVolver);

        btnVolver.addActionListener(_ -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}