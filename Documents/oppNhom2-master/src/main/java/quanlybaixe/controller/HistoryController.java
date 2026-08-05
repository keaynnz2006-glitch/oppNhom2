package quanlybaixe.controller;

import quanlybaixe.action.ManagerVehicleDetail;
import quanlybaixe.entity.VehicleDetail;
import quanlybaixe.view.HistoryView;
import quanlybaixe.view.ManagerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryController {
    private HistoryView historyView;
    private ManagerVehicleDetail managerVehicleDetail;
    private List<VehicleDetail> originalHistoryList;

    public HistoryController(ManagerView managerView, ManagerVehicleDetail managerVehicleDetail) {
        this.managerVehicleDetail = managerVehicleDetail;
        this.originalHistoryList = managerVehicleDetail.getHistoryVehicleDetails();

        // Khởi tạo View
        this.historyView = new HistoryView(managerView);

        // Gán sự kiện
        this.historyView.addFilterListener(new FilterListener());
        this.historyView.addResetListener(new ResetListener());

        // Đổ dữ liệu ban đầu
        this.historyView.updateTableData(originalHistoryList);
    }

    public void showHistoryView() {
        historyView.setVisible(true);
    }

    // --- LỚP XỬ LÝ LỌC DỮ LIỆU TỜI GIAN VÀ TỪ KHÓA ---
    class FilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (originalHistoryList == null) return;

            String keyword = historyView.getSearchKeyword().toLowerCase();
            boolean useDate = historyView.isDateFilterSelected();
            Date fromDate = historyView.getFromDate();
            Date toDate = historyView.getToDate();

            // Lấy chính xác Ngày & Giờ & Phút từ Spinner Từ
            Calendar calFrom = Calendar.getInstance();
            calFrom.setTime(fromDate);
            calFrom.set(Calendar.SECOND, 0);
            calFrom.set(Calendar.MILLISECOND, 0);

            // Lấy chính xác Ngày & Giờ & Phút từ Spinner Đến (lấy trọn phút đó)
            Calendar calTo = Calendar.getInstance();
            calTo.setTime(toDate);
            calTo.set(Calendar.SECOND, 59);
            calTo.set(Calendar.MILLISECOND, 999);

            List<VehicleDetail> filtered = originalHistoryList.stream().filter(v -> {
                // Lọc theo từ khóa (ID, Biển số, Loại xe, Màu xe, Vị trí đỗ)
                boolean matchKey = keyword.isEmpty()
                        || String.valueOf(v.getId()).contains(keyword)
                        || (v.getBienSo() != null && v.getBienSo().toLowerCase().contains(keyword))
                        || (v.getLoaiXe() != null && v.getLoaiXe().toLowerCase().contains(keyword))
                        || (v.getMauXe() != null && v.getMauXe().toLowerCase().contains(keyword))
                        || (v.getViTriDo() != null && v.getViTriDo().toLowerCase().contains(keyword));

                // Lọc theo khoảng thời gian xuất bãi (chính xác đến từng phút)
                boolean matchDate = true;
                if (useDate) {
                    if (v.getNgayXuatBai() != null) {
                        Date outDate = v.getNgayXuatBai();
                        matchDate = !outDate.before(calFrom.getTime()) && !outDate.after(calTo.getTime());
                    } else {
                        matchDate = false;
                    }
                }
                return matchKey && matchDate;
            }).collect(Collectors.toList());

            historyView.updateTableData(filtered);
        }
    }

    // --- LỚP XỬ LÝ REFRESH/RESET ---
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            historyView.clearFilterInputs();
            originalHistoryList = managerVehicleDetail.getHistoryVehicleDetails();
            historyView.updateTableData(originalHistoryList);
        }
    }
}