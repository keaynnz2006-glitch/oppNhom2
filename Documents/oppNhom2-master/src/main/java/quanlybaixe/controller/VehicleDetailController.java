package quanlybaixe.controller;

import quanlybaixe.action.ManagerParkingSlot;
import quanlybaixe.action.ManagerVehicleDetail;
import quanlybaixe.entity.ParkingSlot;
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
    private ManagerParkingSlot managerParkingSlot;
    private ManagerView managerView;
    private MainView mainView;

    public VehicleDetailController(ManagerView view) {
        this.managerView = view;
        this.managerVehicleDetail = new ManagerVehicleDetail();
        this.managerParkingSlot = new ManagerParkingSlot();
        
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

        // Lắng nghe đổi vị trí đỗ để hiện giá tiền
        view.addParkingSlotChangeListener(new ParkingSlotChangeListener());
    }

    public void showManagerView() {
        // ĐÚNG: Nạp CHỈ danh sách vị trí đỗ CÒN TRỐNG vào ComboBox
        List<ParkingSlot> availableSlots = managerParkingSlot.getAvailableParkingSlots();
        managerView.setParkingSlotList(availableSlots);

        // Nạp danh sách xe
        List<VehicleDetail> vehicleList = managerVehicleDetail.getListVehicleDetails();
        managerView.setVisible(true);
        managerView.showListVehicles(vehicleList);
        managerView.showCountListVehicles(vehicleList);
    }

    class ParkingSlotChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedSlotName = managerView.getSelectedParkingSlot();
            if (selectedSlotName != null) {
                List<ParkingSlot> slots = managerParkingSlot.getListParkingSlots();
                for (ParkingSlot slot : slots) {
                    if (selectedSlotName.equalsIgnoreCase(slot.getTenViTri())) {
                        managerView.setGiaTienText(String.valueOf(slot.getGiaTien()));
                        break;
                    }
                }
            }
        }
    }

    class AddVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                managerVehicleDetail.add(vehicle);
                
                // Cập nhật vị trí đỗ thành ĐÃ CÓ XE (true)
                if (vehicle.getViTriDo() != null && !vehicle.getViTriDo().isEmpty()) {
                    managerParkingSlot.updateSlotStatus(vehicle.getViTriDo(), true);
                }

                managerView.showVehicle(vehicle);
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                
                // ĐÚNG: Cập nhật lại ComboBox CHỈ NẠP CÁC Ô CÒN TRỐNG
                managerView.setParkingSlotList(managerParkingSlot.getAvailableParkingSlots());
                managerView.showMessage("Thêm thông tin xe thành công!");
            }
        }
    }
    
    class EditVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                try {
                    // Lấy thông tin cũ trước khi sửa để kiểm tra vị trí đỗ
                    VehicleDetail oldVehicle = null;
                    for (VehicleDetail v : managerVehicleDetail.getListVehicleDetails()) {
                        if (v.getId() == vehicle.getId()) {
                            oldVehicle = v;
                            break;
                        }
                    }

                    // Nếu có sự thay đổi vị trí đỗ
                    if (oldVehicle != null && oldVehicle.getViTriDo() != null 
                            && !oldVehicle.getViTriDo().equalsIgnoreCase(vehicle.getViTriDo())) {
                        // Trả vị trí cũ về TRỐNG (false)
                        managerParkingSlot.updateSlotStatus(oldVehicle.getViTriDo(), false);
                        // Đánh dấu vị trí mới là ĐÃ CÓ XE (true)
                        if (vehicle.getViTriDo() != null && !vehicle.getViTriDo().isEmpty()) {
                            managerParkingSlot.updateSlotStatus(vehicle.getViTriDo(), true);
                        }
                    }

                    managerVehicleDetail.edit(vehicle);
                } catch (ParseException ex) {
                    Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                managerView.showVehicle(vehicle);
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                
                // ĐÚNG: Cập nhật lại ComboBox CHỈ NẠP CÁC Ô CÒN TRỐNG
                managerView.setParkingSlotList(managerParkingSlot.getAvailableParkingSlots());
                managerView.showMessage("Cập nhật thông tin xe thành công!");
            }
        }
    }
    
    class DeleteVehicleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                managerVehicleDetail.delete(vehicle);

                // Trả vị trí đỗ về TRỐNG (false)
                if (vehicle.getViTriDo() != null && !vehicle.getViTriDo().isEmpty()) {
                    managerParkingSlot.updateSlotStatus(vehicle.getViTriDo(), false);
                }

                managerView.clearVehicleInfo();
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                
                // ĐÚNG: Cập nhật lại ComboBox CHỈ NẠP CÁC Ô CÒN TRỐNG (ô vừa giải phóng sẽ xuất hiện lại)
                managerView.setParkingSlotList(managerParkingSlot.getAvailableParkingSlots());
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
            managerView.dispose(); // Đóng cửa sổ hiện tại giải phóng bộ nhớ
        }
    }

    class ListVehicleSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                try {
                    managerView.fillVehicleFromSelectedRow();
                } catch (ParseException ex) {
                    Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
                }
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