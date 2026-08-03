package quanlybaixe.controller;

import quanlybaixe.action.ManagerParkingSlot;
import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ParkingSlotView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ParkingSlotController {
    private ParkingSlotView parkingSlotView;
    private MainView mainView;
    private ManagerParkingSlot managerParkingSlot;
    
    public ParkingSlotController(ParkingSlotView view) {
        this.parkingSlotView = view;
        this.managerParkingSlot = new ManagerParkingSlot();
        
        view.addUndoListener(new UndoListener());
        view.addAddParkingSlotListener(new AddParkingSlotListener());
        view.addListParkingSlotSelectionListener(new ListParkingSlotsSelectionListener());
        view.addEditParkingSlotListener(new EditParkingSlotListener());
        view.addClearListener(new ClearParkingSlotListener());
        view.addDeleteParkingSlotListener(new DeleteParkingSlotListener());
        view.addSortParkingSlotListener(new SortParkingSlotsListener());
        view.addSearchDialogListener(new SearchParkingSlotListener());
        view.addCancelSearchParkingSlotListener(new CancelSearchParkingSlotListener());
        view.addFilterTrangThaiListener(new FilterTrangThaiListener());
    }
    
    public void showManagerView() {
        List<ParkingSlot> slotsList = managerParkingSlot.getListParkingSlots();
        parkingSlotView.setVisible(true);
        parkingSlotView.showListParkingSlots(slotsList);
        parkingSlotView.showCountListParkingSlots(slotsList);
    }
    
    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            parkingSlotView.dispose();
        }
    }
    
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
    
    class ListParkingSlotsSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                List<ParkingSlot> slotsList = managerParkingSlot.getListParkingSlots();
                parkingSlotView.fillParkingSlotFromSelectedRow(slotsList);
            }
        }
    }
    
    class ClearParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            parkingSlotView.clearParkingSlotInfo();
        }
    }
    
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

    class FilterTrangThaiListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox<?> cb = (JComboBox<?>) e.getSource();
            int selectedIndex = cb.getSelectedIndex();
            List<ParkingSlot> allSlots = managerParkingSlot.getListParkingSlots();
            
            if (selectedIndex == 1) {
                List<ParkingSlot> availableList = allSlots.stream()
                        .filter(slot -> !slot.isTrangThai())
                        .collect(Collectors.toList());
                parkingSlotView.showListParkingSlots(availableList);
                parkingSlotView.showCountListParkingSlots(availableList);
            } else if (selectedIndex == 2) {
                List<ParkingSlot> occupiedList = allSlots.stream()
                        .filter(ParkingSlot::isTrangThai)
                        .collect(Collectors.toList());
                parkingSlotView.showListParkingSlots(occupiedList);
                parkingSlotView.showCountListParkingSlots(occupiedList);
            } else {
                parkingSlotView.showListParkingSlots(allSlots);
                parkingSlotView.showCountListParkingSlots(allSlots);
            }
        }
    }
    
    class CancelSearchParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<ParkingSlot> allSlots = managerParkingSlot.getListParkingSlots();
            parkingSlotView.showListParkingSlots(allSlots);
            parkingSlotView.showCountListParkingSlots(allSlots);
            parkingSlotView.cancelSearchParkingSlot();
        }
    }
    
    class SearchParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String keyword = parkingSlotView.validateSearch().toLowerCase();
            
            if (keyword.isEmpty()) {
                parkingSlotView.showMessage("Vui lòng nhập từ khóa tìm kiếm!");
                return;
            }
            
            List<ParkingSlot> allSlots = managerParkingSlot.getListParkingSlots();
            
            List<ParkingSlot> filteredList = allSlots.stream().filter(slot -> 
                String.valueOf(slot.getId()).contains(keyword) ||
                (slot.getTenViTri() != null && slot.getTenViTri().toLowerCase().contains(keyword)) ||
                (slot.getLoaiSlot() != null && slot.getLoaiSlot().toLowerCase().contains(keyword)) ||
                String.valueOf(slot.getGiaTien()).contains(keyword) ||
                (slot.getGhiChu() != null && slot.getGhiChu().toLowerCase().contains(keyword))
            ).collect(Collectors.toList());
            
            parkingSlotView.showListParkingSlots(filteredList);
            parkingSlotView.showCountListParkingSlots(filteredList);
            
            if (filteredList.isEmpty()) {
                parkingSlotView.showMessage("Không tìm thấy kết quả phù hợp với từ khóa: " + keyword);
            }
        }
    }
}