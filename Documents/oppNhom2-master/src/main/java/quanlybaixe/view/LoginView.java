package quanlybaixe.view;

import quanlybaixe.entity.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton btnLogin;

    public LoginView() {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;

        // Username
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblUser, gbc);

        userNameField = new JTextField(15);
        userNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(userNameField, gbc);

        // Password
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblPass, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(passwordField, gbc);

        // Button Login
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(33, 150, 243));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        add(panel);
    }

    public User getUser() {
        return new User(userNameField.getText().trim(), String.valueOf(passwordField.getPassword()));
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
        passwordField.addActionListener(listener);
    }
}