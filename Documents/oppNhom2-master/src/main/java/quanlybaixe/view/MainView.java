package quanlybaixe.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JButton btnVehicleDetail;
    private JButton btnParkingSlot;
    private JButton btnDashboard; 

    public MainView() {
        setTitle("Màn Hình Chính - Quản Lý Bãi Xe");
        setSize(500, 450); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        // 1. Nạp 3 icon từ đường dẫn Maven (resources/icon/...)
        ImageIcon iconManager = createScaledIcon("icon/manager.png", 32, 32);
        ImageIcon iconParking = createScaledIcon("icon/parking.png", 32, 32);
        ImageIcon iconRevenue = createScaledIcon("icon/revenue.png", 32, 32);

        // 2. Nút Quản Lý Xe
        btnVehicleDetail = new JButton("Quản Lý Xe Vào / Ra", iconManager);
        btnVehicleDetail.setPreferredSize(new Dimension(280, 70));
        btnVehicleDetail.setFocusPainted(false); 
        btnVehicleDetail.setBackground(Color.WHITE);
        btnVehicleDetail.setIconTextGap(15);

        // 3. Nút Sơ Đồ Vị Trí
        btnParkingSlot = new JButton("Sơ Đồ / Vị Trí Đỗ Xe", iconParking);
        btnParkingSlot.setPreferredSize(new Dimension(280, 70));
       
        btnParkingSlot.setBackground(Color.WHITE);
        btnParkingSlot.setIconTextGap(15);

        // 4. Nút Báo Cáo Thống Kê
        btnDashboard = new JButton("Báo Cáo & Thống Kê", iconRevenue);
        btnDashboard.setPreferredSize(new Dimension(280, 70));
     
        btnDashboard.setBackground(Color.WHITE);
        btnDashboard.setIconTextGap(15);

        add(btnVehicleDetail);
        add(btnParkingSlot);
        add(btnDashboard);
    }

   
    private ImageIcon createScaledIcon(String path, int width, int height) {
        try {
            
            java.net.URL imgURL = getClass().getClassLoader().getResource(path);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImg = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            } else {
                System.err.println("Không tìm thấy icon: " + path);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void addChooseVehicleDetailListener(ActionListener listener) {
        btnVehicleDetail.addActionListener(listener);
    }

    public void addChooseParkingSlotListener(ActionListener listener) {
        btnParkingSlot.addActionListener(listener);
    }

    public void addChooseDashboardListener(ActionListener listener) {
        btnDashboard.addActionListener(listener);
    }
}