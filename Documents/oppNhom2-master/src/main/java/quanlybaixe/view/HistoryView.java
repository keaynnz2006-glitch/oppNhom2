package quanlybaixe.view;

import quanlybaixe.entity.VehicleDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryView extends JDialog {

    private JTable tableHistory;
    private DefaultTableModel tableModel;
    private JLabel lblTotalRevenue;

    public HistoryView(Frame parent, List<VehicleDetail> historyList) {
        super(parent, "Lịch Sử Trả Xe & Doanh Thu", true);
        setSize(950, 500); // Nới rộng kích thước dialog để chứa đủ cột mới
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel Tiêu đề
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(41, 128, 185));
        JLabel lblTitle = new JLabel("DANH SÁCH XE ĐÃ TRẢ BÃI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle);
        add(panelTitle, BorderLayout.NORTH);

        // Bảng dữ liệu: Đã thêm cột "Thời Gian Ra"
        String[] columns = {"ID", "Biển Số", "Loại Xe", "Màu Xe", "Thời Gian Vào", "Thời Gian Ra", "Vị Trí Đỗ", "Tiền Đã Thu"};
        tableModel = new DefaultTableModel(columns, 0);
        tableHistory = new JTable(tableModel);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        DecimalFormat priceFormat = new DecimalFormat("#,###");

        double totalRevenue = 0;
        if (historyList != null) {
            for (VehicleDetail v : historyList) {
                totalRevenue += v.getGiaTien();
                tableModel.addRow(new Object[]{
                    v.getId(),
                    v.getBienSo(),
                    v.getLoaiXe(),
                    v.getMauXe(),
                    v.getNgayVaoBai() != null ? dateFormat.format(v.getNgayVaoBai()) : "",
                    v.getNgayXuatBai() != null ? dateFormat.format(v.getNgayXuatBai()) : "", // Hiển thị giờ xuất bãi
                    v.getViTriDo(),
                    priceFormat.format(v.getGiaTien()) + " VNĐ"
                });
            }
        }

        add(new JScrollPane(tableHistory), BorderLayout.CENTER);

        // Panel Tổng doanh thu & Nút đóng
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        lblTotalRevenue = new JLabel("Tổng Doanh Thu: " + priceFormat.format(totalRevenue) + " VNĐ");
        lblTotalRevenue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalRevenue.setForeground(new Color(39, 174, 96));

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());

        panelFooter.add(lblTotalRevenue);
        panelFooter.add(btnClose);
        add(panelFooter, BorderLayout.SOUTH);
    }
}