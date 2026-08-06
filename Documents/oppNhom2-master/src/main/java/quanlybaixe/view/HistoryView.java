package quanlybaixe.view;

import quanlybaixe.entity.VehicleDetail;
import com.toedter.calendar.JDateChooser; // Import thư viện JCalendar

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
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
    
    // Đổi JSpinner sang JDateChooser của JCalendar
    private JDateChooser dateChooserFrom;
    private JDateChooser dateChooserTo;
    
    private JButton btnFilter;
    private JButton btnReset;

    // Component hiển thị và xem ảnh
    private JLabel lblImagePreview;
    private JButton btnZoomImage;
    private String currentImagePath = "";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private DecimalFormat priceFormat = new DecimalFormat("#,###");

    public HistoryView(Frame parent) {
        super(parent, "Lịch Sử Trả Xe & Doanh Thu", true);

        setSize(1200, 620); // Mở rộng chiều rộng cửa sổ để chứa khung xem ảnh
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

        // Khởi tạo JDateChooser cho Từ ngày & Đến ngày
        Date now = new Date();
        Calendar calYesterday = Calendar.getInstance();
        calYesterday.setTime(now);
        calYesterday.add(Calendar.DAY_OF_MONTH, -1);

        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setDateFormatString("dd/MM/yyyy");
        dateChooserFrom.setDate(calYesterday.getTime()); // Mặc định từ hôm qua
        dateChooserFrom.setPreferredSize(new Dimension(120, 24));
        dateChooserFrom.setEnabled(false);

        dateChooserTo = new JDateChooser();
        dateChooserTo.setDateFormatString("dd/MM/yyyy");
        dateChooserTo.setDate(now); // Mặc định đến hôm nay
        dateChooserTo.setPreferredSize(new Dimension(120, 24));
        dateChooserTo.setEnabled(false);

        chkEnableDate.addActionListener(e -> toggleDateChoosers());

        btnFilter = new JButton("Lọc");
        btnFilter.setBackground(new Color(52, 152, 219));
        btnFilter.setForeground(Color.WHITE);

        btnReset = new JButton("Tải Lại");

        panelFilter.add(new JLabel("Từ khóa:"));
        panelFilter.add(txtSearch);
        panelFilter.add(chkEnableDate);
        panelFilter.add(new JLabel("Từ:"));
        panelFilter.add(dateChooserFrom);
        panelFilter.add(new JLabel("Đến:"));
        panelFilter.add(dateChooserTo);
        panelFilter.add(btnFilter);
        panelFilter.add(btnReset);

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.add(panelTitle, BorderLayout.NORTH);
        panelNorth.add(panelFilter, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // --- BẢNG DỮ LIỆU ---
        String[] columns = {"ID", "Biển Số", "Loại Xe", "Màu Xe", "Thời Gian Vào", "Thời Gian Ra", "Vị Trí Đỗ", "Tiền Đã Thu", "Hình Ảnh"};
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

        // --- PANEL BÊN PHẢI: HIỂN THỊ KHUNG XEM ẢNH TRỰC QUAN ---
        JPanel panelImageRight = new JPanel(new BorderLayout(5, 5));
        panelImageRight.setBorder(BorderFactory.createTitledBorder("Ảnh Xe Khi Trả / Check-in"));
        panelImageRight.setPreferredSize(new Dimension(240, 0));

        lblImagePreview = new JLabel("Chưa chọn xe", SwingConstants.CENTER);
        lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagePreview.setPreferredSize(new Dimension(220, 260));

        btnZoomImage = new JButton("Phóng To Ảnh");
        btnZoomImage.setEnabled(false);
        btnZoomImage.addActionListener(e -> zoomImage());

        panelImageRight.add(lblImagePreview, BorderLayout.CENTER);
        panelImageRight.add(btnZoomImage, BorderLayout.SOUTH);

        // Sự kiện khi click dòng trên JTable -> Load ảnh tương ứng lên JLabel
        tableHistory.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableHistory.getSelectedRow();
                if (selectedRow != -1) {
                    Object imgObj = tableModel.getValueAt(selectedRow, 8); // Cột 8 là Hình Ảnh
                    if (imgObj != null && !imgObj.toString().trim().isEmpty()) {
                        displayImage(imgObj.toString().trim());
                    } else {
                        clearImage();
                    }
                }
            }
        });

        // Ghép Bảng và Panel Ảnh vào trung tâm (Center)
        JPanel panelCenter = new JPanel(new BorderLayout(10, 10));
        panelCenter.add(new JScrollPane(tableHistory), BorderLayout.CENTER);
        panelCenter.add(panelImageRight, BorderLayout.EAST);

        add(panelCenter, BorderLayout.CENTER);

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

    // --- HÀM TẢI VÀ SCALE ẢNH LÊN KHUNG PREVIEW ---
    private void displayImage(String imagePath) {
        this.currentImagePath = imagePath;
        File file = new File(imagePath);
        if (file.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(220, 260, Image.SCALE_SMOOTH);
            lblImagePreview.setIcon(new ImageIcon(img));
            lblImagePreview.setText("");
            btnZoomImage.setEnabled(true);
        } else {
            lblImagePreview.setIcon(null);
            lblImagePreview.setText("Không tìm thấy tệp ảnh");
            btnZoomImage.setEnabled(false);
        }
    }

    private void clearImage() {
        this.currentImagePath = "";
        lblImagePreview.setIcon(null);
        lblImagePreview.setText("Chưa có ảnh");
        btnZoomImage.setEnabled(false);
    }

    // --- HÀM PHÓNG TO ẢNH TRONG DIALOG RIÊNG ---
    private void zoomImage() {
        if (!currentImagePath.isEmpty() && new File(currentImagePath).exists()) {
            JDialog dialog = new JDialog(this, "Xem Ảnh Kích Thước Gốc", true);
            JLabel lblFull = new JLabel(new ImageIcon(currentImagePath));
            dialog.add(new JScrollPane(lblFull));
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    public void toggleDateChoosers() {
        boolean enable = chkEnableDate.isSelected();
        dateChooserFrom.setEnabled(enable);
        dateChooserTo.setEnabled(enable);
    }

    // --- CẬP NHẬT DỮ LIỆU BẢNG ---
    public void updateTableData(List<VehicleDetail> list) {
        tableModel.setRowCount(0);
        clearImage(); // Reset ảnh preview
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
                        priceFormat.format(v.getGiaTien()) + " VNĐ",
                        v.getHinhAnh() != null ? v.getHinhAnh() : ""
                });
            }
        }

        lblTotalCount.setText("Tổng số lượt trả: " + (list != null ? list.size() : 0));
        lblTotalRevenue.setText("TỔNG DOANH THU: " + priceFormat.format(totalRevenue) + " VNĐ");
    }

    public String getSearchKeyword() { return txtSearch.getText().trim(); }
    public boolean isDateFilterSelected() { return chkEnableDate.isSelected(); }
    
    // Lấy Date trực tiếp từ JDateChooser
    public Date getFromDate() { return dateChooserFrom.getDate(); }
    public Date getToDate() { return dateChooserTo.getDate(); }

    public void clearFilterInputs() {
        txtSearch.setText("");
        chkEnableDate.setSelected(false);

        Date now = new Date();
        dateChooserTo.setDate(now);

        Calendar calYesterday = Calendar.getInstance();
        calYesterday.setTime(now);
        calYesterday.add(Calendar.DAY_OF_MONTH, -1);
        dateChooserFrom.setDate(calYesterday.getTime());

        toggleDateChoosers();
        clearImage();
    }

    public void addFilterListener(ActionListener listener) { btnFilter.addActionListener(listener); }
    public void addResetListener(ActionListener listener) { btnReset.addActionListener(listener); }
}