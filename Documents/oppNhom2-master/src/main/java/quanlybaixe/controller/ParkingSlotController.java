package quanlybaixe.controller;

import quanlybaixe.action.ManagerParkingSlot;
import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ParkingSlotView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ParkingSlotController {
    private ParkingSlotView parkingSlotView;
    private MainView mainView;
    private ManagerParkingSlot managerParkingSlot;
    
    public ParkingSlotController(ParkingSlotView view) {
        this.parkingSlotView = view;
        this.managerParkingSlot = new ManagerParkingSlot();
        
        // Đăng ký các sự kiện tương thích với ParkingSlotView
        view.addUndoListener(new UndoListener());
        view.addAddParkingSlotListener(new AddParkingSlotListener());
        view.addListParkingSlotSelectionListener(new ListParkingSlotsSelectionListener());
        view.addEditParkingSlotListener(new EditParkingSlotListener());
        view.addClearListener(new ClearParkingSlotListener());
        view.addDeleteParkingSlotListener(new DeleteParkingSlotListener());
        view.addSortParkingSlotListener(new SortParkingSlotsListener());
        view.addSearchDialogListener(new SearchParkingSlotListener());
        view.addCancelSearchParkingSlotListener(new CancelSearchParkingSlotListener());
    }
    
    public void showManagerView() {
        List<ParkingSlot> slotsList = managerParkingSlot.getListParkingSlots();
        parkingSlotView.setVisible(true);
        parkingSlotView.showListParkingSlots(slotsList);
        parkingSlotView.showCountListParkingSlots(slotsList);
    }
    
    // Nút "Quay lại"
    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            parkingSlotView.dispose(); // Đóng cửa sổ hiện tại để tránh rác bộ nhớ
        }
    }
    
    // Nút "Thêm"
    class AddParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ParkingSlot slot = parkingSlotView.getParkingSlotInfo();
            if (slot != null) {
                try {
                    if (!managerParkingSlot.isViTriUnique(slot)) {
                        throw new IllegalArgumentException("Lỗi: Tên vị trí đỗ xe này đã tồn tại!");
                    }
                    
                    managerParkingSlot.add(slot);
                    List<ParkingSlot> updatedList = managerParkingSlot.getListParkingSlots();
                    
                    parkingSlotView.showParkingSlot(slot);
                    parkingSlotView.showListParkingSlots(updatedList);
                    parkingSlotView.showCountListParkingSlots(updatedList);
                    parkingSlotView.showMessage("Thêm vị trí đỗ thành công!");
                } catch (IllegalArgumentException ex) {
                    parkingSlotView.showMessage(ex.getMessage());
                }
            }
        }
    }
    
    // Nút "Sửa"
    class EditParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ParkingSlot slot = parkingSlotView.getParkingSlotInfo();
            if (slot != null) {
                if (!managerParkingSlot.isViTriUnique(slot)) {
                    parkingSlotView.showMessage("Lỗi: Tên vị trí đỗ này trùng với một vị trí khác!");
                    return;
                }
                
                managerParkingSlot.edit(slot);
                List<ParkingSlot> updatedList = managerParkingSlot.getListParkingSlots();
                
                parkingSlotView.showParkingSlot(slot);
                parkingSlotView.showListParkingSlots(updatedList);
                parkingSlotView.showCountListParkingSlots(updatedList);
                parkingSlotView.showMessage("Cập nhật vị trí đỗ thành công!");
            }
        }
    }
    
    // Nút "Xóa"
    class DeleteParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ParkingSlot slot = parkingSlotView.getParkingSlotInfo();
            if (slot != null) {
                managerParkingSlot.delete(slot);
                List<ParkingSlot> updatedList = managerParkingSlot.getListParkingSlots();
                
                parkingSlotView.clearParkingSlotInfo();
                parkingSlotView.showListParkingSlots(updatedList);
                parkingSlotView.showCountListParkingSlots(updatedList);
                parkingSlotView.showMessage("Xóa vị trí đỗ thành công!");
            }
        }
    }
    
    // Sự kiện Chọn dòng trên Bảng
    class ListParkingSlotsSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                List<ParkingSlot> slotsList = managerParkingSlot.getListParkingSlots();
                parkingSlotView.fillParkingSlotFromSelectedRow(slotsList);
            }
        }
    }
    
    // Nút "Làm mới / Nhập lại"
    class ClearParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            parkingSlotView.clearParkingSlotInfo();
        }
    }
    
    // Nút "Sắp xếp"
    class SortParkingSlotsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int check = parkingSlotView.getChooseSelectSort();
            if (check == 1) {
                managerParkingSlot.sortSlotsByID();
            } else if (check == 2) {
                managerParkingSlot.sortSlotsByViTri();
            } else if (check == 3) {
                managerParkingSlot.sortSlotsByGiaTien();
            } else {
                parkingSlotView.showMessage("Bạn chưa chọn tiêu chí sắp xếp!");
                return;
            }
            List<ParkingSlot> sortedList = managerParkingSlot.getListParkingSlots();
            parkingSlotView.showListParkingSlots(sortedList);
        }
    }
    
    // Nút "Hủy tìm"
    class CancelSearchParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<ParkingSlot> allSlots = managerParkingSlot.getListParkingSlots();
            parkingSlotView.showListParkingSlots(allSlots);
            parkingSlotView.showCountListParkingSlots(allSlots);
            parkingSlotView.cancelSearchParkingSlot();
        }
    }
    
    // Nút "Tìm kiếm"
    class SearchParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<ParkingSlot> temp = new ArrayList<>();
            int check = parkingSlotView.getChooseSelectSearch();
            String search = parkingSlotView.validateSearch();
            
            if (search == null || search.trim().isEmpty()) {
                parkingSlotView.showMessage("Vui lòng nhập từ khóa tìm kiếm!");
                return;
            }
            
            if (check == 1) {
                temp = managerParkingSlot.searchByTenViTri(search);
            } else if (check == 2) {
                temp = managerParkingSlot.searchByLoaiSlot(search);
            } else {
                parkingSlotView.showMessage("Vui lòng chọn tiêu chí tìm kiếm!");
                return;
            }
            
            parkingSlotView.showListParkingSlots(temp);
            parkingSlotView.showCountListParkingSlots(temp);
            
            if (temp.isEmpty()) {
                parkingSlotView.showMessage("Không tìm thấy vị trí đỗ phù hợp!");
            }
        }
    }
}