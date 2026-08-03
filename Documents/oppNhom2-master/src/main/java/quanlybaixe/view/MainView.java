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


        btnVehicleDetail = new JButton("Quản Lý Xe Vào / Ra");
        btnVehicleDetail.setPreferredSize(new Dimension(250, 70));

     
        btnParkingSlot = new JButton("Sơ Đồ / Vị Trí Đỗ Xe");
        btnParkingSlot.setPreferredSize(new Dimension(250, 70));

       
        btnDashboard = new JButton("Báo Cáo & Thống Kê");
        btnDashboard.setPreferredSize(new Dimension(250, 70));

        add(btnVehicleDetail);
        add(btnParkingSlot);
        add(btnDashboard);
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