package quanlybaixe.controller;

import quanlybaixe.action.ManagerVehicleDetail;
import quanlybaixe.entity.VehicleDetail;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ManagerView;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VehicleDetailController {
    private ManagerVehicleDetail managerVehicleDetail;
    private ManagerView managerView;
    private MainView mainView;

    public VehicleDetailController(ManagerView view) {
        this.managerView = view;
        this.managerVehicleDetail = new ManagerVehicleDetail();
        
        view.addAddVehicleListener(new AddVehicleListener());
        view.addEditVehicleListener(new EditVehicleListener());
        view.addClearListener(new ClearVehicleListener());
        view.addDeleteVehicleListener(new DeleteVehicleListener());
        view.addListVehicleSelectionListener(new ListVehicleSelectionListener());
        view.addSortByBienSoListener(new SortVehicleBienSoListener());
        view.addSearchListener(new SearchVehicleViewListener());
        view.addSearchDialogListener(new SearchVehicleListener());
        view.addSortByNgayVaoBaiListener(new SortVehicleNgayVaoBaiListener());
        view.addSortByIDListener(new SortVehicleIDListener());
        view.addCancelSearchVehicleListener(new CancelSearchVehicleListener());
        view.addImageVehicleListener(new ImageVehicleListener());
        view.addCancelDialogListener(new CancelDialogSearchVehicleListener());
        view.addUndoListener(new UndoListener());
        view.addStatisticListener(new StatisticViewListener());
        view.addStatisticTypeListener(new StatisticVehicleTypeListener());
        view.addStatisticClearListener(new StatisticClearListener());
    }

    public void showManagerView() {
        List<VehicleDetail> vehicleList = managerVehicleDetail.getListVehicleDetails();
        managerView.setVisible(true);
        managerView.showListVehicles(vehicleList);
        managerView.showCountListVehicles(vehicleList);
    }

    class AddVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                managerVehicleDetail.add(vehicle);
                managerView.showVehicle(vehicle);
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showMessage("Thêm thông tin xe thành công!");
            }
        }
    }
    
    class EditVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                try {
                    managerVehicleDetail.edit(vehicle);
                } catch (ParseException ex) {
                    Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
                }
                managerView.showVehicle(vehicle);
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showMessage("Cập nhật thông tin xe thành công!");
            }
        }
    }
    
    class DeleteVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                managerVehicleDetail.delete(vehicle);
                managerView.clearVehicleInfo();
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showMessage("Xóa thông tin xe thành công!");
            }
        }
    }
    
    class ImageVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.chooseVehicleImage();
        }
    }

    class ClearVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.clearVehicleInfo();
        }
    }

    class SortVehicleBienSoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerVehicleDetail.sortDetailsByBienSo();
            managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SortVehicleNgayVaoBaiListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerVehicleDetail.sortDetailsByNgayVaoBai();
            managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SortVehicleIDListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerVehicleDetail.sortDetailsByID();
            managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SearchVehicleViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.searchVehicleInfo();
        }
    }
    
    class StatisticViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.displayStatisticView();
        }
    }
    
    class SearchVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<VehicleDetail> temp = new ArrayList<>();
            int check = managerView.getChooseSelectSearch();
            String search = managerView.validateSearch();
            if (check == 1) {
                temp = managerVehicleDetail.searchByBienSo(search);
            } else if (check == 2) {
                temp = managerVehicleDetail.searchByLoaiXe(search);
            } else if (check == 3) {
                temp = managerVehicleDetail.searchByMauXe(search);
            }
            if (!temp.isEmpty()) {
                managerView.showListVehicles(temp);
            } else {
                managerView.showMessage("Không tìm thấy kết quả!");
            }
        }
    }
    
    class CancelDialogSearchVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.cancelDialogSearchVehicleInfo();
        }
    }
    
    class CancelSearchVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
            managerView.cancelSearchVehicle();
        }
    }
    
    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            managerView.setVisible(false);
        }
    }

    class ListVehicleSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            try {
                managerView.fillVehicleFromSelectedRow();
            } catch (ParseException ex) {
                Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class StatisticVehicleTypeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.displayStatisticView();
            managerView.showStatisticTypeVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }

    class StatisticClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView.clearStatisticView();
        }
    }
}