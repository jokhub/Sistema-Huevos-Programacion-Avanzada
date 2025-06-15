package eggceptional;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class GestionUsuarios extends JFrame {
    public GestionUsuarios() {
        setTitle("Gestión de Usuarios");
        setSize(400, 250);
        setLayout(null); // Layout absoluto

        JLabel lblUsuario = new JLabel("Nuevo Usuario:");
        lblUsuario.setBounds(30, 20, 100, 25);
        add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 20, 200, 25);
        add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(30, 60, 100, 25);
        add(lblContrasena);

        JTextField txtContrasena = new JTextField();
        txtContrasena.setBounds(150, 60, 200, 25);
        add(txtContrasena);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 100, 100, 25);
        add(lblRol);

        JComboBox<String> comboRol = new JComboBox<>(new String[] { "gerente", "administrador" });
        comboRol.setBounds(150, 100, 200, 25);
        add(comboRol);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 160, 120, 30);
        add(btnGuardar);

        // Botón para volver atrás
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(10, 160, 100, 30);
        add(btnVolver);

        btnGuardar.addActionListener(_ -> {
            try (Connection conn = ConexionDB.conectar()) {
                String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, rol) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtUsuario.getText());
                ps.setString(2, txtContrasena.getText());
                ps.setString(3, comboRol.getSelectedItem().toString());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Usuario creado");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al crear usuario");
            }
        });

        // Acción del botón Volver: cerrar esta ventana
        btnVolver.addActionListener(_ -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}