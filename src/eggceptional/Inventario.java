package eggceptional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class Inventario extends JFrame {
    public Inventario() {
        setTitle("Inventario");
        setSize(550, 400);
        setResizable(true);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Inventario de Huevos");
        lblTitulo.setBounds(20, 10, 200, 25);
        add(lblTitulo);

        // Alerta de bajo stock, visible y enmarcada debajo del título
        JLabel lblAlerta = new JLabel();
        lblAlerta.setBounds(20, 35, 500, 30); // Debajo del título, ancho mayor
        lblAlerta.setOpaque(true); // Permite fondo de color
        lblAlerta.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblAlerta);

        // 1. Tabla de presentaciones (no editable directamente)
        String[] columnas = {"Presentación", "Precio", "Stock"};
        Object[][] datos = {
            {"Pequeño", "400", "100"},
            {"Mediano", "500", "80"},
            {"Grande", "600", "60"}
        };
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda editable directamente
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 75, 440, 100);
        add(scroll);

        // 2. Botón para agregar stock
        JButton btnAgregarStock = new JButton("Agregar Stock");
        btnAgregarStock.setBounds(20, 190, 130, 30);
        add(btnAgregarStock);

        // 3. Botón para editar precios
        JButton btnEditarPrecio = new JButton("Editar Precio");
        btnEditarPrecio.setBounds(170, 190, 130, 30);
        add(btnEditarPrecio);

        // 4. Campo de búsqueda
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(320, 190, 50, 25);
        add(lblBuscar);

        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(370, 190, 90, 25);
        add(txtBuscar);

        // 5. Botón para volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(370, 240, 90, 30);
        add(btnVolver);

        // 6. Historial de movimientos (simulado)
        JTextArea areaHistorial = new JTextArea("Historial de movimientos:\n");
        areaHistorial.setEditable(false);
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        scrollHistorial.setBounds(20, 240, 330, 70);
        add(scrollHistorial);

        // 7. Botones para exportar e importar CSV
        JButton btnExportar = new JButton("Exportar CSV");
        btnExportar.setBounds(370, 280, 130, 30);
        add(btnExportar);

        JButton btnImportar = new JButton("Importar CSV");
        btnImportar.setBounds(370, 320, 130, 30);
        add(btnImportar);

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

        // Acción para editar precio (solo por el botón)
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

        // Acción para exportar CSV
        btnExportar.addActionListener(_ -> exportarCSV(modelo));

        // Acción para importar CSV
        btnImportar.addActionListener(_ -> importarCSV(modelo, lblAlerta));

        verificarStock(modelo, lblAlerta);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para mostrar alerta si hay bajo stock (<100) y notificación visual
    private void verificarStock(DefaultTableModel modelo, JLabel lblAlerta) {
        StringBuilder alerta = new StringBuilder();
        boolean hayBajoStock = false;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            int stock = Integer.parseInt(modelo.getValueAt(i, 2).toString());
            if (stock < 100) {
                alerta.append("¡Atención! Bajo stock en: ").append(modelo.getValueAt(i, 0)).append("  ");
                hayBajoStock = true;
            }
        }
        lblAlerta.setText(alerta.toString());
        if (hayBajoStock) {
            lblAlerta.setForeground(Color.RED);
            lblAlerta.setBackground(new Color(255, 230, 230));
            lblAlerta.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            JOptionPane.showMessageDialog(this, "¡Alerta! Hay presentaciones con menos de 100 unidades.", "Alerta de Stock", JOptionPane.WARNING_MESSAGE);
        } else {
            lblAlerta.setForeground(Color.BLACK);
            lblAlerta.setBackground(getBackground());
            lblAlerta.setBorder(null);
        }
    }

    // Método para exportar inventario a CSV
    private void exportarCSV(DefaultTableModel modelo) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (java.io.PrintWriter pw = new java.io.PrintWriter(fileChooser.getSelectedFile())) {
                // Escribir encabezados
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    pw.print(modelo.getColumnName(i));
                    if (i < modelo.getColumnCount() - 1) pw.print(",");
                }
                pw.println();
                // Escribir filas
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        pw.print(modelo.getValueAt(i, j));
                        if (j < modelo.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();
                }
                JOptionPane.showMessageDialog(this, "Inventario exportado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
            }
        }
    }

    // Método para importar inventario desde CSV y verificar stock
    private void importarCSV(DefaultTableModel modelo, JLabel lblAlerta) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fileChooser.getSelectedFile()))) {
                String linea = br.readLine(); // Saltar encabezado
                modelo.setRowCount(0); // Limpiar tabla
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length == modelo.getColumnCount()) {
                        modelo.addRow(datos);
                    }
                }
                JOptionPane.showMessageDialog(this, "Inventario importado correctamente.");
                verificarStock(modelo, lblAlerta);
            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Error al importar: " + ex.getMessage());
                            }
                        }
                    }
                }
