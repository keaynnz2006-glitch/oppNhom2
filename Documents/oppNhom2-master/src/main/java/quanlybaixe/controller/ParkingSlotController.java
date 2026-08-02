package quanlybaixe.controller;

import quanlybaixe.action.ManagerParkingSlot;
import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ParkingSlotView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        view.addSearchListener(new SearchParkingSlotViewListener());
        view.addSearchDialogListener(new SearchParkingSlotListener());
        view.addCancelSearchParkingSlotListener(new CancelSearchParkingSlotListener());
        view.addCancelDialogListener(new CancelDialogSearchParkingSlotListener());
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
            parkingSlotView.setVisible(false);
        }
    }
    
    class AddParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ParkingSlot slot = parkingSlotView.getParkingSlotInfo();
            if (slot != null) {
                try {
                    if (!managerParkingSlot.isViTriUnique(slot)) {
                        throw new IllegalArgumentException("Lỗi: Tên vị trí đỗ xe này đã tồn tại trong bãi!");
                    }
                    
                    managerParkingSlot.add(slot);
                    parkingSlotView.showParkingSlot(slot);
                    parkingSlotView.showListParkingSlots(managerParkingSlot.getListParkingSlots());
                    parkingSlotView.showCountListParkingSlots(managerParkingSlot.getListParkingSlots());
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
                try {
                    managerParkingSlot.edit(slot);
                } catch (ParseException ex) {
                    Logger.getLogger(ParkingSlotController.class.getName()).log(Level.SEVERE, null, ex);
                }
                parkingSlotView.showParkingSlot(slot);
                parkingSlotView.showListParkingSlots(managerParkingSlot.getListParkingSlots());
                parkingSlotView.showCountListParkingSlots(managerParkingSlot.getListParkingSlots());
                parkingSlotView.showMessage("Cập nhật vị trí đỗ thành công!");
            }
        }
    }
    
    class DeleteParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ParkingSlot slot = parkingSlotView.getParkingSlotInfo();
            if (slot != null) {
                managerParkingSlot.delete(slot);
                parkingSlotView.clearParkingSlotInfo();
                parkingSlotView.showListParkingSlots(managerParkingSlot.getListParkingSlots());
                parkingSlotView.showCountListParkingSlots(managerParkingSlot.getListParkingSlots());
                parkingSlotView.showMessage("Xóa vị trí đỗ thành công!");
            }
        }
    }
    
    class ListParkingSlotsSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            List<ParkingSlot> slotsList = managerParkingSlot.getListParkingSlots();
            try {
                parkingSlotView.fillParkingSlotFromSelectedRow(slotsList);
            } catch (ParseException ex) {
                Logger.getLogger(ParkingSlotController.class.getName()).log(Level.SEVERE, null, ex);
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
                parkingSlotView.showListParkingSlots(managerParkingSlot.getListParkingSlots());
            } else if (check == 2) {
                managerParkingSlot.sortSlotsByViTri();
                parkingSlotView.showListParkingSlots(managerParkingSlot.getListParkingSlots());
            } else if (check == 3) {
                managerParkingSlot.sortSlotsByGiaTien();
                parkingSlotView.showListParkingSlots(managerParkingSlot.getListParkingSlots());
            } else {
                parkingSlotView.showMessage("Bạn chưa chọn tiêu chí sắp xếp");
            }
        }
    }
    
    class SearchParkingSlotViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            parkingSlotView.searchParkingSlotInfo();
        }
    }
    
    class CancelDialogSearchParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            parkingSlotView.cancelDialogSearchParkingSlotInfo();
        }
    }
    
    class CancelSearchParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            parkingSlotView.showListParkingSlots(managerParkingSlot.getListParkingSlots());
            parkingSlotView.cancelSearchParkingSlot();
        }
    }
    
    class SearchParkingSlotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<ParkingSlot> temp = new ArrayList<>();
            int check = parkingSlotView.getChooseSelectSearch();
            String search = parkingSlotView.validateSearch();
            if (check == 1) {
                temp = managerParkingSlot.searchByBienSo(search);
            } else if (check == 2) {
                temp = managerParkingSlot.searchByTenViTri(search);
            } else if (check == 3) {
                temp = managerParkingSlot.searchByLoaiXe(search);
            }
            if (!temp.isEmpty()) {
                parkingSlotView.showListParkingSlots(temp);
            } else {
                parkingSlotView.showMessage("Không tìm thấy vị trí đỗ phù hợp!");
            }
        }
    }
}