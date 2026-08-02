package quanlybaixe.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JButton btnVehicleDetail;
    private JButton btnParkingSlot;

    public MainView() {
        setTitle("Màn Hình Chính - Quản Lý Bãi Xe");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        // Nút 1: Xe Vào/Ra
        btnVehicleDetail = new JButton("Quản Lý Xe Vào / Ra");
        btnVehicleDetail.setPreferredSize(new Dimension(200, 80));

        // Nút 2: Vị Trí Đỗ Xe
        btnParkingSlot = new JButton("Sơ Đồ / Vị Trí Đỗ Xe");
        btnParkingSlot.setPreferredSize(new Dimension(200, 80));

        add(btnVehicleDetail);
        add(btnParkingSlot);
    }

    // Hai hàm này đặt đúng tên để MainController gọi không bị đỏ:
    public void addChooseVehicleDetailListener(ActionListener listener) {
        btnVehicleDetail.addActionListener(listener);
    }

    public void addChooseParkingSlotListener(ActionListener listener) {
        btnParkingSlot.addActionListener(listener);
    }
}