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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
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
        view.addCheckoutVehicleListener(new CheckoutVehicleListener());
        view.addHistoryVehicleListener(new HistoryVehicleListener()); 
        view.addClearListener(new ClearVehicleListener());
        view.addDeleteVehicleListener(new DeleteVehicleListener());
        view.addListVehicleSelectionListener(new ListVehicleSelectionListener());
        view.addSortByBienSoListener(new SortVehicleBienSoListener());
        
        // Gán chung 1 listener cho cả nút Tìm kiếm và Lọc Ngày
        SearchVehicleListener searchListener = new SearchVehicleListener();
        view.addSearchListener(searchListener);
        view.addFilterDateListener(searchListener);

        view.addSortByNgayVaoBaiListener(new SortVehicleNgayVaoBaiListener());
        view.addSortByIDListener(new SortVehicleIDListener());
        view.addCancelSearchVehicleListener(new CancelSearchVehicleListener());
        view.addImageVehicleListener(new ImageVehicleListener());
        view.addUndoListener(new UndoListener());
        view.addStatisticListener(new StatisticViewListener());
        view.addStatisticTypeListener(new StatisticVehicleTypeListener());
        view.addStatisticClearListener(new StatisticClearListener());
    }

    public void showManagerView() {
        List<ParkingSlot> allSlots = managerParkingSlot.getListParkingSlots();
        managerView.setParkingSlotList(allSlots);

        List<VehicleDetail> vehicleList = managerVehicleDetail.getListVehicleDetails();
        managerView.setVisible(true);
        managerView.showListVehicles(vehicleList);
        managerView.showCountListVehicles(vehicleList);
    }

    class CheckoutVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle == null || vehicle.getId() == 0) {
                managerView.showMessage("Vui lòng chọn xe cần trả từ bảng!");
                return;
            }

            double tongTien = vehicle.getGiaTien();

            String confirmMsg = String.format(
                "XÁC NHẬN TRẢ XE (THEO LƯỢT)\n" +
                "-----------------------------------\n" +
                "Biển số: %s\n" +
                "Vị trí đỗ: %s\n" +
                "THÀNH TIỀN (1 LƯỢT): %,.0f VNĐ\n\n" +
                "Xác nhận thanh toán và cho xe xuất bãi?",
                vehicle.getBienSo(), vehicle.getViTriDo(), tongTien
            );

            int confirm = JOptionPane.showConfirmDialog(
                managerView, confirmMsg, "Xác Nhận Trả Xe", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                vehicle.setNgayXuatBai(new java.util.Date());

                if (vehicle.getViTriDo() != null && !vehicle.getViTriDo().isEmpty()) {
                    managerParkingSlot.updateSlotStatus(vehicle.getViTriDo(), false);
                }

                managerVehicleDetail.saveToHistory(vehicle);
                managerVehicleDetail.delete(vehicle);

                managerView.clearVehicleInfo(); // Tự động làm sạch & bật lại nút Thêm
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                
                managerView.setParkingSlotList(managerParkingSlot.getListParkingSlots());

                managerView.showMessage("Trả xe thành công! Đã thu: " + String.format("%,.0f VNĐ", tongTien));
            }
        }
    }

    class HistoryVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            HistoryController historyController = new HistoryController(managerView, managerVehicleDetail);
            historyController.showHistoryView();
        }
    }

    class AddVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                managerVehicleDetail.add(vehicle);
                
                if (vehicle.getViTriDo() != null && !vehicle.getViTriDo().isEmpty()) {
                    managerParkingSlot.updateSlotStatus(vehicle.getViTriDo(), true);
                }

                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.setParkingSlotList(managerParkingSlot.getListParkingSlots());
                managerView.clearVehicleInfo();
                
                managerView.showMessage("Thêm thông tin xe thành công!");
            }
        }
    }
    
    class EditVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                try {
                    VehicleDetail oldVehicle = null;
                    for (VehicleDetail v : managerVehicleDetail.getListVehicleDetails()) {
                        if (v.getId() == vehicle.getId()) {
                            oldVehicle = v;
                            break;
                        }
                    }

                    if (oldVehicle != null && oldVehicle.getViTriDo() != null 
                            && !oldVehicle.getViTriDo().equalsIgnoreCase(vehicle.getViTriDo())) {
                        managerParkingSlot.updateSlotStatus(oldVehicle.getViTriDo(), false);
                        if (vehicle.getViTriDo() != null && !vehicle.getViTriDo().isEmpty()) {
                            managerParkingSlot.updateSlotStatus(vehicle.getViTriDo(), true);
                        }
                    }

                    managerVehicleDetail.edit(vehicle);
                } catch (ParseException ex) {
                    Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.setParkingSlotList(managerParkingSlot.getListParkingSlots());
                managerView.clearVehicleInfo();
                
                managerView.showMessage("Cập nhật thông tin xe thành công!");
            }
        }
    }
    
    class DeleteVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            VehicleDetail vehicle = managerView.getVehicleInfo();
            if (vehicle != null) {
                managerVehicleDetail.delete(vehicle);

                if (vehicle.getViTriDo() != null && !vehicle.getViTriDo().isEmpty()) {
                    managerParkingSlot.updateSlotStatus(vehicle.getViTriDo(), false);
                }

                managerView.clearVehicleInfo();
                managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.showCountListVehicles(managerVehicleDetail.getListVehicleDetails());
                managerView.setParkingSlotList(managerParkingSlot.getListParkingSlots());
                managerView.showMessage("Xóa thông tin xe thành công!");
            }
        }
    }
    
    class ImageVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerView.chooseVehicleImage();
        }
    }

    class ClearVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerView.clearVehicleInfo();
        }
    }

    class SortVehicleBienSoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerVehicleDetail.sortDetailsByBienSo();
            managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SortVehicleNgayVaoBaiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerVehicleDetail.sortDetailsByNgayVaoBai();
            managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class SortVehicleIDListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerVehicleDetail.sortDetailsByID();
            managerView.showListVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }
    
    class StatisticViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerView.displayStatisticView();
        }
    }

    class SearchVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = managerView.validateSearch().toLowerCase().trim();
            boolean isFilterDate = managerView.isDateFilterEnabled();
            Date selectedDate = managerView.getSelectedFilterDate();

            SimpleDateFormat sdfCompare = new SimpleDateFormat("dd/MM/yyyy");
            String selectedDateStr = (selectedDate != null) ? sdfCompare.format(selectedDate) : "";

            List<VehicleDetail> allVehicles = managerVehicleDetail.getListVehicleDetails();

            SimpleDateFormat sdfStandard = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            DecimalFormat dfFormated = new DecimalFormat("#,###");

            List<VehicleDetail> filteredList = allVehicles.stream().filter(v -> {
                if (isFilterDate) {
                    if (v.getNgayVaoBai() == null) return false;
                    String vehicleDateStr = sdfCompare.format(v.getNgayVaoBai());
                    if (!vehicleDateStr.equals(selectedDateStr)) {
                        return false;
                    }
                }

                if (keyword.isEmpty()) {
                    return true;
                }

                String idStr = String.valueOf(v.getId());
                String bienSo = v.getBienSo() != null ? v.getBienSo().toLowerCase() : "";
                String loaiXe = v.getLoaiXe() != null ? v.getLoaiXe().toLowerCase() : "";
                String mauXe = v.getMauXe() != null ? v.getMauXe().toLowerCase() : "";
                String viTri = v.getViTriDo() != null ? v.getViTriDo().toLowerCase() : "";

                String ngayVaoStr = v.getNgayVaoBai() != null ? sdfStandard.format(v.getNgayVaoBai()).toLowerCase() : "";
                String giaTienTho = String.valueOf((long) v.getGiaTien());
                String giaTienFormatted = dfFormated.format(v.getGiaTien()).toLowerCase();

                return idStr.contains(keyword)
                        || bienSo.contains(keyword)
                        || loaiXe.contains(keyword)
                        || mauXe.contains(keyword)
                        || viTri.contains(keyword)
                        || ngayVaoStr.contains(keyword)
                        || giaTienTho.contains(keyword)
                        || giaTienFormatted.contains(keyword);
            }).collect(Collectors.toList());

            managerView.showListVehicles(filteredList);
            managerView.showCountListVehicles(filteredList);

            if (filteredList.isEmpty()) {
                String msg = "Không tìm thấy kết quả!";
                if (isFilterDate && !keyword.isEmpty()) {
                    msg = String.format("Không tìm thấy xe khớp từ khóa '%s' trong ngày %s!", keyword, selectedDateStr);
                } else if (isFilterDate) {
                    msg = "Không có xe nào vào bãi trong ngày: " + selectedDateStr;
                } else if (!keyword.isEmpty()) {
                    msg = "Không tìm thấy xe phù hợp từ khóa: " + keyword;
                }
                managerView.showMessage(msg);
            }
        }
    }

    class CancelSearchVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerView.cancelSearchVehicle();
            List<VehicleDetail> fullList = managerVehicleDetail.getListVehicleDetails();
            managerView.showListVehicles(fullList);
            managerView.showCountListVehicles(fullList);
        }
    }
    
    class UndoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            managerView.dispose();
        }
    }

    class ListVehicleSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                try {
                    managerView.fillVehicleFromSelectedRow();
                    managerView.setAddButtonEnabled(false);
                } catch (ParseException ex) {
                    Logger.getLogger(VehicleDetailController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    class StatisticVehicleTypeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerView.displayStatisticView();
            managerView.showStatisticTypeVehicles(managerVehicleDetail.getListVehicleDetails());
        }
    }

    class StatisticClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            managerView.clearStatisticView();
        }
    }
}   