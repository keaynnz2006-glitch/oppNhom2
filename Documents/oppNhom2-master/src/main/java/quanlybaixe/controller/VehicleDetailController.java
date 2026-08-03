package quanlybaixe.controller;

import quanlybaixe.action.ManagerParkingSlot;
import quanlybaixe.action.ManagerVehicleDetail;
import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.entity.VehicleDetail;
import quanlybaixe.view.MainView;
import quanlybaixe.view.ManagerView;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
        view.addSearchListener(new SearchVehicleListener());
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

        view.addParkingSlotChangeListener(new ParkingSlotChangeListener());
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

                managerView.clearVehicleInfo();
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
            List<VehicleDetail> historyList = managerVehicleDetail.getHistoryVehicleDetails();
            
            JDialog historyDialog = new JDialog(managerView, "Lịch Sử Xe Đã Trả & Doanh Thu", true);
            historyDialog.setSize(950, 480);
            historyDialog.setLocationRelativeTo(managerView);
            historyDialog.setLayout(new BorderLayout(10, 10));

            JLabel lblTitle = new JLabel("DANH SÁCH XE ĐÃ TRẢ / XUẤT BÃI", JLabel.CENTER);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
            historyDialog.add(lblTitle, BorderLayout.NORTH);

            String[] columns = {"ID", "Biển Số", "Loại Xe", "Màu Xe", "Thời Gian Vào", "Thời Gian Xuất", "Vị Trí", "Giá Tiền"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            DecimalFormat df = new DecimalFormat("#,### VNĐ");

            double tongDoanhThu = 0.0;

            if (historyList != null && !historyList.isEmpty()) {
                for (VehicleDetail v : historyList) {
                    tongDoanhThu += v.getGiaTien();
                    model.addRow(new Object[]{
                        v.getId(),
                        v.getBienSo(),
                        v.getLoaiXe(),
                        v.getMauXe(),
                        v.getNgayVaoBai() != null ? sdf.format(v.getNgayVaoBai()) : "",
                        v.getNgayXuatBai() != null ? sdf.format(v.getNgayXuatBai()) : "",
                        v.getViTriDo(),
                        df.format(v.getGiaTien())
                    });
                }
            }

            JTable historyTable = new JTable(model);
            
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
            historyTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

            historyDialog.add(new JScrollPane(historyTable), BorderLayout.CENTER);
            
            JPanel panelFooter = new JPanel(new BorderLayout());
            panelFooter.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel lblTotalCount = new JLabel("Tổng số lượt trả: " + (historyList != null ? historyList.size() : 0));
            lblTotalCount.setFont(new Font("Segoe UI", Font.ITALIC, 13));

            JLabel lblRevenue = new JLabel("TỔNG DOANH THU: " + df.format(tongDoanhThu));
            lblRevenue.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblRevenue.setForeground(new Color(39, 174, 96));

            panelFooter.add(lblTotalCount, BorderLayout.WEST);
            panelFooter.add(lblRevenue, BorderLayout.EAST);

            historyDialog.add(panelFooter, BorderLayout.SOUTH);

            historyDialog.setVisible(true);
        }
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
    
    // Đã cập nhật xử lý logic Tìm kiếm chuẩn theo lựa chọn ComboBox
    class SearchVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<VehicleDetail> temp = new ArrayList<>();
            int check = managerView.getChooseSelectSearch();
            String search = managerView.validateSearch();

            if (search.isEmpty()) {
                managerView.showMessage("Vui lòng nhập từ khóa tìm kiếm!");
                return;
            }

            switch (check) {
                case 1: // Biển số (Chữ)
                    temp = managerVehicleDetail.searchByBienSo(search);
                    break;

                case 2: // ID (Số)
                    try {
                        int searchId = Integer.parseInt(search);
                        for (VehicleDetail v : managerVehicleDetail.getListVehicleDetails()) {
                            if (v.getId() == searchId) {
                                temp.add(v);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        managerView.showMessage("ID phải là số nguyên hợp lệ!");
                        return;
                    }
                    break;

                case 3: // Loại xe
                    temp = managerVehicleDetail.searchByLoaiXe(search);
                    break;

                case 4: // Màu xe
                    temp = managerVehicleDetail.searchByMauXe(search);
                    break;
            }

            if (!temp.isEmpty()) {
                managerView.showListVehicles(temp);
                managerView.showCountListVehicles(temp);
            } else {
                managerView.showMessage("Không tìm thấy kết quả phù hợp!");
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
            List<VehicleDetail> fullList = managerVehicleDetail.getListVehicleDetails();
            managerView.showListVehicles(fullList);
            managerView.showCountListVehicles(fullList);
            managerView.cancelSearchVehicle();
        }
    }
    
    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            managerView.dispose();
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