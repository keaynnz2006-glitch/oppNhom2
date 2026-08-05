package quanlybaixe.view;

import quanlybaixe.entity.VehicleDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryView extends JDialog {

    private JTable tableHistory;
    private DefaultTableModel tableModel;
    private JLabel lblTotalRevenue;
    private JLabel lblTotalCount;

    private JTextField txtSearch;
    private JCheckBox chkEnableDate;
    private JSpinner spinFromDate;
    private JSpinner spinToDate;
    private JButton btnFilter;
    private JButton btnReset;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private DecimalFormat priceFormat = new DecimalFormat("#,###");

    public HistoryView(Frame parent) {
        super(parent, "Lịch Sử Trả Xe & Doanh Thu", true);

        setSize(1020, 580);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // --- PANEL TIÊU ĐỀ ---
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(41, 128, 185));
        JLabel lblTitle = new JLabel("DANH SÁCH XE ĐÃ TRẢ BÃI & LỊCH SỬ DOANH THU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle);

        // --- PANEL LỌC TÌM KIẾM ---
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelFilter.setBackground(new Color(236, 240, 241));

        txtSearch = new JTextField(12);
        chkEnableDate = new JCheckBox("Lọc Thời Gian Xuất");
        chkEnableDate.setOpaque(false);

        // --- KHỞI TẠO THỜI GIẢN MẶC ĐỊNH (HÔM QUA -> HÔM NAY) ---
        Date now = new Date();
        
        // Thời gian "Đến": Thời điểm hiện tại
        spinToDate = new JSpinner(new SpinnerDateModel(now, null, null, Calendar.MINUTE));
        JSpinner.DateEditor deTo = new JSpinner.DateEditor(spinToDate, "HH:mm dd/MM/yyyy");
        spinToDate.setEditor(deTo);

        // Thời gian "Từ": Lùi lại 1 ngày so với hiện tại
        Calendar calYesterday = Calendar.getInstance();
        calYesterday.setTime(now);
        calYesterday.add(Calendar.DAY_OF_MONTH, -1); // Lùi 1 ngày
        
        spinFromDate = new JSpinner(new SpinnerDateModel(calYesterday.getTime(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor deFrom = new JSpinner.DateEditor(spinFromDate, "HH:mm dd/MM/yyyy");
        spinFromDate.setEditor(deFrom);

        // Mặc định vô hiệu hóa 2 ô thời gian
        spinFromDate.setEnabled(false);
        spinToDate.setEnabled(false);

        // Bắt sự kiện khi tích/bỏ tích Checkbox
        chkEnableDate.addActionListener(e -> toggleDateSpinners());

        btnFilter = new JButton("Lọc");
        btnFilter.setBackground(new Color(52, 152, 219));
        btnFilter.setForeground(Color.WHITE);

        btnReset = new JButton("Tải Lại");

        panelFilter.add(new JLabel("Từ khóa:"));
        panelFilter.add(txtSearch);
        panelFilter.add(chkEnableDate);
        panelFilter.add(new JLabel("Từ:"));
        panelFilter.add(spinFromDate);
        panelFilter.add(new JLabel("Đến:"));
        panelFilter.add(spinToDate);
        panelFilter.add(btnFilter);
        panelFilter.add(btnReset);

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.add(panelTitle, BorderLayout.NORTH);
        panelNorth.add(panelFilter, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // --- BẢNG DỮ LIỆU ---
        String[] columns = {"ID", "Biển Số", "Loại Xe", "Màu Xe", "Thời Gian Vào", "Thời Gian Ra", "Vị Trí Đỗ", "Tiền Đã Thu"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableHistory = new JTable(tableModel);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableHistory.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

        add(new JScrollPane(tableHistory), BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel panelFooter = new JPanel(new BorderLayout());
        panelFooter.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        lblTotalCount = new JLabel("Tổng số lượt trả: 0");
        lblTotalCount.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        lblTotalRevenue = new JLabel("TỔNG DOANH THU: 0 VNĐ");
        lblTotalRevenue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalRevenue.setForeground(new Color(39, 174, 96));

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());

        JPanel panelStats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        panelStats.add(lblTotalCount);
        panelStats.add(lblTotalRevenue);

        panelFooter.add(panelStats, BorderLayout.CENTER);
        panelFooter.add(btnClose, BorderLayout.EAST);
        add(panelFooter, BorderLayout.SOUTH);
    }

    // --- BẬT/TẮT SPINNER THEO CHECKBOX ---
    public void toggleDateSpinners() {
        boolean enable = chkEnableDate.isSelected();
        spinFromDate.setEnabled(enable);
        spinToDate.setEnabled(enable);
    }

    // --- CÁC HÀM HIỂN THỊ DỮ LIỆU ---
    public void updateTableData(List<VehicleDetail> list) {
        tableModel.setRowCount(0);
        double totalRevenue = 0;

        if (list != null && !list.isEmpty()) {
            for (VehicleDetail v : list) {
                totalRevenue += v.getGiaTien();
                tableModel.addRow(new Object[]{
                        v.getId(),
                        v.getBienSo(),
                        v.getLoaiXe(),
                        v.getMauXe(),
                        v.getNgayVaoBai() != null ? dateFormat.format(v.getNgayVaoBai()) : "",
                        v.getNgayXuatBai() != null ? dateFormat.format(v.getNgayXuatBai()) : "",
                        v.getViTriDo(),
                        priceFormat.format(v.getGiaTien()) + " VNĐ"
                });
            }
        }

        lblTotalCount.setText("Tổng số lượt trả: " + (list != null ? list.size() : 0));
        lblTotalRevenue.setText("TỔNG DOANH THU: " + priceFormat.format(totalRevenue) + " VNĐ");
    }

    // --- GETTERS DÙNG CHO CONTROLLER ---
    public String getSearchKeyword() { return txtSearch.getText().trim(); }
    public boolean isDateFilterSelected() { return chkEnableDate.isSelected(); }
    public Date getFromDate() { return (Date) spinFromDate.getValue(); }
    public Date getToDate() { return (Date) spinToDate.getValue(); }

    public void clearFilterInputs() {
        txtSearch.setText("");
        chkEnableDate.setSelected(false);
        
        // Reset thời gian về lại Mặc định (Hôm qua -> Hôm nay) khi bấm Tải Lại
        Date now = new Date();
        spinToDate.setValue(now);
        
        Calendar calYesterday = Calendar.getInstance();
        calYesterday.setTime(now);
        calYesterday.add(Calendar.DAY_OF_MONTH, -1);
        spinFromDate.setValue(calYesterday.getTime());

        toggleDateSpinners();
    }

    public void addFilterListener(ActionListener listener) { btnFilter.addActionListener(listener); }
    public void addResetListener(ActionListener listener) { btnReset.addActionListener(listener); }
}