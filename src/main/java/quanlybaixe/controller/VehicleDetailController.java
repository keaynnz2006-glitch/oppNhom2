package quanlybaixe.controller;

import quanlybaixe.action.ManagerVehicleDetail;
import quanlybaixe.entity.VehicleDetail;
import quanlybaixe.view.LoginView;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ManagerView;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author PC
 */
public class VehicleDetailController 
{
    private SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
    private ManagerVehicleDetail managerVehicleDetail;
    private ManagerView specialPersonView;
    private LoginView loginView;
    private MainView mainView;

    public VehicleDetailController(ManagerView view) 
    {
        this.specialPersonView = view;
        managerVehicleDetail = new ManagerVehicleDetail();
        view.addAddSpecialPersonListener(new AddSpecialPersonListener());
        view.addEditSpecialPersonListener(new EditSpecialPersonListener());
        view.addClearListener(new ClearSpecialPersonListener());
        view.addDeleteSpecialPersonListener(new DeleteSpecialPersonListener());
        view.addListSpecialPersonSelectionListener(new ListSpecialPersonSelectionListener());
        view.addSortByNameListener(new SortSpecialPersonNameListener());
        view.addSearchListener(new SearchSpecialPersonViewListener());
        view.addSearchDialogListener(new SearchSpecialPersonListener());
        view.addSortByYearListener(new SortSpecialPersonYearListener());
        view.addSortByIDListener(new SortSpecialPersonIDListener());
        view.addSortByOpeningDateListener(new SortSpecialPersonOpeningDateListener());
        view.addCancelSearchSpecialPersonListener(new CancelSearchSpecialPersonListener());
        view.addImageSpecialPersonListener(new ImageSpecialPersonListener());
        view.addCancelDialogListener(new CancelDialogSearchSpecialPersonListener());
        view.addUndoListener(new UndoListener());
        view.addStatisticListener(new StatisticViewListener());
        view.addStatisticTypeListener(new StatisticSpecialPersonTypeListener());
        view.addStatisticAgeListener(new StatisticSpecialPersonAgeListener());
        view.addStatisticUnderListener(new StatisticClearListener());
    }

    public void showManagerView() 
    {
        List<VehicleDetail> vehicleList = managerVehicleDetail.getListVehicleDetails();
        specialPersonView.setVisible(true);
        specialPersonView.showListSpecialPersons(vehicleList);
        specialPersonView.showCountListSpecialPersons(vehicleList);
    }

    class AddSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            VehicleDetail vehicle = specialPersonView.getSpecialPersonInfo();
            if (vehicle != null) 
            {
                managerVehicleDetail.add(vehicle);
                specialPersonView.showSpecialPerson(vehicle);
                specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
                specialPersonView.showCountListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
                specialPersonView.showMessage("Thêm thông tin xe thành công!");
            }
        }
    }
    
    class EditSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            VehicleDetail vehicle = specialPersonView.getSpecialPersonInfo();
            if (vehicle != null) 
            {
                try {
                    managerVehicleDetail.edit(vehicle);
                } catch (ParseException ex) {
                    Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
                }
                specialPersonView.showSpecialPerson(vehicle);
                specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
                specialPersonView.showCountListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
                specialPersonView.showMessage("Cập nhật thông tin xe thành công!");
            }
        }
    }
    
    class DeleteSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            VehicleDetail vehicle = specialPersonView.getSpecialPersonInfo();
            if (vehicle != null) 
            {
                managerVehicleDetail.delete(vehicle);
                specialPersonView.clearSpecialPersonInfo();
                specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
                specialPersonView.showCountListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
                specialPersonView.showMessage("Xóa thông tin xe thành công!");
            }
        }
    }
    
    class ImageSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.SpecialPersonImage();
        }
    }

    class ClearSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.clearSpecialPersonInfo();
        }
    }

    class SortSpecialPersonNameListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerVehicleDetail.sortDetailsByBienSo();
            specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SortSpecialPersonYearListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerVehicleDetail.sortDetailsByNgayVaoBai();
            specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SortSpecialPersonIDListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerVehicleDetail.sortDetailsByID();
            specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SortSpecialPersonOpeningDateListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerVehicleDetail.sortDetailsByNgayVaoBai();
            specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SearchSpecialPersonViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.searchNameSpecialPersonInfo();
        }
    }
    
    class StatisticViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.displayStatisticView();
        }
    }
    
    class SearchSpecialPersonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            List<VehicleDetail> temp = new ArrayList<>();
            int check = specialPersonView.getChooseSelectSearch();
            String search = specialPersonView.validateSearch();
            if(check == 1){
                temp = managerVehicleDetail.searchByBienSo(search);
            }else if(check == 2){
                temp = managerVehicleDetail.searchByLoaiXe(search);
            }else if(check == 3){
                temp = managerVehicleDetail.searchByMauXe(search);
            }
            if(!temp.isEmpty()) specialPersonView.showListSpecialPersons(temp);
            else specialPersonView.showMessage("Không tìm thấy kết quả!");
        }
    }
    
    class CancelDialogSearchSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.cancelDialogSearchSpecialPersonInfo();
        }
    }
    
    class CancelSearchSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.showListSpecialPersons(managerVehicleDetail.getListVehicleDetails());
            specialPersonView.cancelSearchSpecialPerson();
        }
    }
    
    class UndoListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            specialPersonView.setVisible(false);
        }
    }

    class ListSpecialPersonSelectionListener implements ListSelectionListener 
    {
        public void valueChanged(ListSelectionEvent e) 
        {
            try {
                specialPersonView.fillSpecialPersonFromSelectedRow();
            } catch (ParseException ex) {
                Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class StatisticSpecialPersonTypeListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.displayStatisticView();
            specialPersonView.showStatisticTypeSpecialPersons(managerVehicleDetail.getListVehicleDetails());
        }
    }

    class StatisticSpecialPersonAgeListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.displayStatisticView();
            specialPersonView.showStatisticAgeSpecialPersons(managerVehicleDetail.getListVehicleDetails());
        }
    }

    class StatisticClearListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.UnderViewSpecialPerson();
        }
    }
}