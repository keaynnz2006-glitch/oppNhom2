package quanlybaixe.controller;

import quanlybaixe.view.MainView;
import quanlybaixe.view.ManagerView;
import quanlybaixe.view.ParkingSlotView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private ManagerView managerView;
    private ParkingSlotView parkingSlotView;
    private MainView mainView;
    
    public MainController(MainView view) {
        this.mainView = view;
        view.addChooseVehicleDetailListener(new ChooseVehicleDetailListener());
        view.addChooseParkingSlotListener(new ChooseParkingSlotListener());
    }

    public void showMainView() {
        mainView.setVisible(true);
    }

    // Chuyển sang màn hình Quản lý Xe vào/ra
    class ChooseVehicleDetailListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView = new ManagerView();
            VehicleDetailController vehicleController = new VehicleDetailController(managerView);
            vehicleController.showManagerView();
            mainView.setVisible(false);
        }
    }
    
    // Chuyển sang màn hình Quản lý Vị trí/Sơ đồ đỗ xe
    class ChooseParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            parkingSlotView = new ParkingSlotView();
            ParkingSlotController slotController = new ParkingSlotController(parkingSlotView);
            slotController.showManagerView();
            mainView.setVisible(false);
        }
    }
}