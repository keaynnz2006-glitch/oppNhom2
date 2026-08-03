package quanlybaixe.controller;

import quanlybaixe.action.ManagerVehicleDetail;
import quanlybaixe.view.DashboardView;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ManagerView;
import quanlybaixe.view.ParkingSlotView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private ManagerView managerView;
    private ParkingSlotView parkingSlotView;
    private MainView mainView;
    private ManagerVehicleDetail managerVehicleDetail;

    public MainController(MainView view) {
        this.mainView = view;
        this.managerVehicleDetail = new ManagerVehicleDetail(); // Khởi tạo class quản lý dữ liệu xe
        
        this.mainView.addChooseVehicleDetailListener(new ChooseVehicleDetailListener());
        this.mainView.addChooseParkingSlotListener(new ChooseParkingSlotListener());
        this.mainView.addChooseDashboardListener(new ChooseDashboardListener());
    }

    public void showMainView() {
        mainView.setVisible(true);
    }

    // Chuyển sang màn hình Quản lý Xe vào/ra
    class ChooseVehicleDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerView = new ManagerView();
            VehicleDetailController vehicleController = new VehicleDetailController(managerView);
            vehicleController.showManagerView();
            mainView.setVisible(false);
        }
    }
    
    // Chuyển sang màn hình Quản lý Vị trí/Sơ đồ đỗ xe
    class ChooseParkingSlotListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parkingSlotView = new ParkingSlotView();
            ParkingSlotController slotController = new ParkingSlotController(parkingSlotView);
            slotController.showManagerView();
            mainView.setVisible(false);
        }
    }

    // Mở màn hình Báo Cáo & Thống Kê
    class ChooseDashboardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Truyền trực tiếp managerVehicleDetail để Dashboard lấy đủ cả xe trong bãi lẫn lịch sử
            DashboardView dashboard = new DashboardView(managerVehicleDetail);
            dashboard.setVisible(true);
        }
    }
}