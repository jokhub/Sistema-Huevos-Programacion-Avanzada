package eggceptional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Inventario extends JFrame {
    public Inventario() {
        setTitle("Inventario");
        setSize(500, 350);
        setResizable(true); // Permite agrandar la ventana
        setLayout(null);    // Layout absoluto: los componentes mantienen su tamaño y posición

        JLabel lblTitulo = new JLabel("Inventario de Huevos");
        lblTitulo.setBounds(20, 10, 200, 25);
        add(lblTitulo);

        // 1. Tabla de presentaciones
        String[] columnas = {"Presentación", "Precio", "Stock"};
        Object[][] datos = {
            {"Pequeño", "400", "100"},
            {"Mediano", "500", "80"},
            {"Grande", "600", "60"}
        };
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 50, 440, 100);
        add(scroll);

        // 2. Botón para agregar stock
        JButton btnAgregarStock = new JButton("Agregar Stock");
        btnAgregarStock.setBounds(20, 170, 130, 30);
        add(btnAgregarStock);

        // 3. Botón para editar precios
        JButton btnEditarPrecio = new JButton("Editar Precio");
        btnEditarPrecio.setBounds(170, 170, 130, 30);
        add(btnEditarPrecio);

        // 4. Campo de búsqueda
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(320, 170, 50, 25);
        add(lblBuscar);

        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(370, 170, 90, 25);
        add(txtBuscar);

        // 5. Botón para volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(370, 220, 90, 30);
        add(btnVolver);

        // 6. Historial de movimientos (simulado)
        JTextArea areaHistorial = new JTextArea("Historial de movimientos:\n");
        areaHistorial.setEditable(false);
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        scrollHistorial.setBounds(20, 220, 330, 70);
        add(scrollHistorial);

        // 7. Alertas de bajo stock
        JLabel lblAlerta = new JLabel();
        lblAlerta.setBounds(20, 300, 400, 25);
        add(lblAlerta);

        // Acción para agregar stock
        btnAgregarStock.addActionListener(_ -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese cantidad a agregar:");
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    int stockActual = Integer.parseInt(modelo.getValueAt(fila, 2).toString());
                    modelo.setValueAt(String.valueOf(stockActual + cantidad), fila, 2);
                    areaHistorial.append("Agregado +" + cantidad + " a " + modelo.getValueAt(fila, 0) + "\n");
                    verificarStock(modelo, lblAlerta);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Cantidad inválida");
                }
            }
        });

        // Acción para editar precio
        btnEditarPrecio.addActionListener(_ -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String precioStr = JOptionPane.showInputDialog(this, "Nuevo precio:");
                try {
                    double precio = Double.parseDouble(precioStr);
                    modelo.setValueAt(String.valueOf((int)precio), fila, 1);
                    areaHistorial.append("Precio de " + modelo.getValueAt(fila, 0) + " cambiado a " + precio + "\n");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Precio inválido");
                }
            }
        });

        // Acción para buscar presentación
        btnVolver.addActionListener(_ -> dispose());

        txtBuscar.addActionListener(_ -> {
            String buscar = txtBuscar.getText().toLowerCase();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String presentacion = modelo.getValueAt(i, 0).toString().toLowerCase();
                if (presentacion.contains(buscar)) {
                    tabla.setRowSelectionInterval(i, i);
                    return;
                }
            }
            tabla.clearSelection();
        });

        verificarStock(modelo, lblAlerta);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para mostrar alerta si hay bajo stock
    private void verificarStock(DefaultTableModel modelo, JLabel lblAlerta) {
        StringBuilder alerta = new StringBuilder();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            int stock = Integer.parseInt(modelo.getValueAt(i, 2).toString());
            if (stock < 20) {
                alerta.append("¡Atención! Bajo stock en: ").append(modelo.getValueAt(i, 0)).append("  ");
            }
        }
        lblAlerta.setText(alerta.toString());
    }
}