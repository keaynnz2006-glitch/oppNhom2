package quanlybaixe.controller;

import quanlybaixe.action.ManagerParkingSlot;
import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.view.LoginView;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ManagerView;
import quanlybaixe.view.ResidentView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author PC
 */
public class ParkingSlotController 
{
    private LoginView loginView;
    private ManagerView managerView;
    private ResidentView residentView;
    private MainView mainView;
    private ManagerParkingSlot managerParkingSlot;
    
    public ParkingSlotController(ResidentView view)
    {
        this.residentView = view;
        this.managerParkingSlot = new ManagerParkingSlot();
        view.addUndoListener(new UndoListener());
        view.addAddResidentListener(new AddResidentListener());
        view.addListResidentSelectionListener(new ListResidentsSelectionListener());
        view.addEditResidentListener(new EditResidentListener());
        view.addClearListener(new ClearResidentListener());
        view.addDeleteSpecialPersonListener(new DeleteSpecialPersonListener());
        view.addSortSpecialPersonListener(new SortResidentsListener());
        view.addSearchListener(new SearchResidentViewListener());
        view.addSearchDialogListener(new SearchResidentListener());
        view.addCancelSearchResidentListener(new CancelSearchResidentListener());
        view.addCancelDialogListener(new CancelDialogSearchResidentListener());
    }
    
    public void showManagerView() 
    {
        List<ParkingSlot> slotsList = managerParkingSlot.getListParkingSlots();
        residentView.setVisible(true);
        residentView.showListResidents(slotsList);
        residentView.showCountListResidents(slotsList);
        residentView.showStatisticTypeCMT(slotsList);
        residentView.showStatisticIDFamily(slotsList);
    }
    
    class UndoListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            residentView.setVisible(false);
        }
    }
    
    class AddResidentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ParkingSlot slot = residentView.getResidentInfo();
            if (slot != null) {
                try {
                    if (!managerParkingSlot.isViTriUnique(slot)) {
                        throw new IllegalArgumentException("Lỗi: Tên vị trí đỗ xe này đã tồn tại trong bãi!");
                    }
                    
                    managerParkingSlot.add(slot);
                    residentView.showResidents(slot);
                    residentView.showListResidents(managerParkingSlot.getListParkingSlots());
                    residentView.showCountListResidents(managerParkingSlot.getListParkingSlots());
                    residentView.showStatisticTypeCMT(managerParkingSlot.getListParkingSlots());
                    residentView.showStatisticIDFamily(managerParkingSlot.getListParkingSlots());
                    residentView.showMessage("Thêm vị trí đỗ thành công!");
                } catch (IllegalArgumentException ex) {
                    residentView.showMessage(ex.getMessage());
                }
            }
        }
    }
    
    class EditResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            ParkingSlot slot = residentView.getResidentInfo();
            if (slot != null) 
            {
                try {
                    managerParkingSlot.edit(slot);
                } catch (ParseException ex) {
                    Logger.getLogger(ParkingSlotController.class.getName()).log(Level.SEVERE, null, ex);
                }
                residentView.showResidents(slot);
                residentView.showListResidents(managerParkingSlot.getListParkingSlots());
                residentView.showCountListResidents(managerParkingSlot.getListParkingSlots());
                residentView.showStatisticTypeCMT(managerParkingSlot.getListParkingSlots());
                residentView.showStatisticIDFamily(managerParkingSlot.getListParkingSlots());
                residentView.showMessage("Cập nhật vị trí đỗ thành công!");
            }
        }
    }
    
    class DeleteSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            ParkingSlot slot = residentView.getResidentInfo();
            if (slot != null) 
            {
                managerParkingSlot.delete(slot);
                residentView.clearResidentInfo();
                residentView.showListResidents(managerParkingSlot.getListParkingSlots());
                residentView.showCountListResidents(managerParkingSlot.getListParkingSlots());
                residentView.showStatisticTypeCMT(managerParkingSlot.getListParkingSlots());
                residentView.showStatisticIDFamily(managerParkingSlot.getListParkingSlots());
                residentView.showMessage("Xóa vị trí đỗ thành công!");
            }
        }
    }
    
    class ListResidentsSelectionListener implements ListSelectionListener 
    {
        public void valueChanged(ListSelectionEvent e) 
        {
            List<ParkingSlot> slotsList = managerParkingSlot.getListParkingSlots();
            try {
                residentView.fillResidentFromSelectedRow(slotsList);
            } catch (ParseException ex) {
                Logger.getLogger(ParkingSlotController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class ClearResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.clearResidentInfo();
        }
    }
    
    class SortResidentsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int check = residentView.getChooseSelectSort();
            if(check == 1){
                managerParkingSlot.sortSlotsByID();
                residentView.showListResidents(managerParkingSlot.getListParkingSlots());
            }else if(check == 2){
                managerParkingSlot.sortSlotsByViTri();
                residentView.showListResidents(managerParkingSlot.getListParkingSlots());
            }else if(check == 3){
                managerParkingSlot.sortSlotsByGiaTien();
                residentView.showListResidents(managerParkingSlot.getListParkingSlots());
            } else
                residentView.showMessage("Bạn chưa chọn tiêu chí sắp xếp");
        }
    }
    
    class SearchResidentViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.searchResidentInfo();
        }
    }
    
    class CancelDialogSearchResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.cancelDialogSearchResidentInfo();
        }
    }
    
    class CancelSearchResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.showListResidents(managerParkingSlot.getListParkingSlots());
            residentView.cancelSearchResident();
        }
    }
    
    class SearchResidentListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            List<ParkingSlot> temp = new ArrayList<>();
            int check = residentView.getChooseSelectSearch();
            String search = residentView.validateSearch();
            if(check == 1){
                // Tìm kiếm theo biển số xe
                temp = managerParkingSlot.searchByBienSo(search);
            }else if(check == 2){
                // Tìm kiếm theo tên vị trí đỗ
                temp = managerParkingSlot.searchByTenViTri(search);
            }else if(check == 3){
                // Tìm kiếm theo loại xe
                temp = managerParkingSlot.searchByLoaiXe(search);
            }
            if(!temp.isEmpty()) residentView.showListResidents(temp);
            else residentView.showMessage("Không tìm thấy vị trí đỗ phù hợp!");
        }
    }
}