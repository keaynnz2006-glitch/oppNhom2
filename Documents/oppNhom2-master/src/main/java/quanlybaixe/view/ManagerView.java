package quanlybaixe.view;

import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.entity.VehicleDetail;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerView extends JFrame {

    private JTextField txtId, txtBienSo, txtMauXe, txtNgayVao, txtGiaTien, txtHinhAnh, txtViTriSelected;
    private JComboBox<String> cbLoaiXe;
    private JButton btnSelectSlot;
    private JLabel lblTotalCount, lblStatistic;
    private JRadioButton rdoSearchBienSo, rdoSearchLoaiXe, rdoSearchMauXe;
    private ButtonGroup bgSearch;
    private JTextField txtSearchInput;

    private JButton btnAdd, btnEdit, btnDelete, btnCheckout, btnClear, btnSortId, btnSortBienSo, btnSortNgay, btnSearch, btnCancelSearch, btnChooseImg, btnUndo, btnStatistic, btnStatisticType, btnStatisticClear;

    private JTable tableVehicle;
    private DefaultTableModel tableModel;
    private JDialog searchDialog, statisticDialog, slotPickerDialog;

    private List<ParkingSlot> currentParkingSlots; // Lưu danh sách ô đỗ hiện tại
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private final DecimalFormat priceFormat = new DecimalFormat("#,###"); // Định dạng tiền ví dụ: 5,000

    public ManagerView() {
        setTitle("Quản Lý Chi Tiết Xe Trong Bãi");
        setSize(1150, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(41, 128, 185));
        JLabel lblTitle = new JLabel("QUẢN LÝ CHI TIẾT XE RA VÀO BÃI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle);
        add(panelTitle, BorderLayout.NORTH);

        // Form Panel (Bên trái)
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin xe"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15); txtId.setEditable(false);
        txtBienSo = new JTextField(15);
        
        String[] loaiXeOptions = {"Xe máy", "Ô tô"};
        cbLoaiXe = new JComboBox<>(loaiXeOptions);

        txtMauXe = new JTextField(15);
        
        txtNgayVao = new JTextField(15);
        txtNgayVao.setEditable(false);
        txtNgayVao.setText(dateTimeFormat.format(new Date()));
        
        // Ô vị trí đỗ: Ô chữ + Nút bấm chọn từ Sơ đồ
        txtViTriSelected = new JTextField(10);
        txtViTriSelected.setEditable(false);
        btnSelectSlot = new JButton("Chọn vị trí");
        btnSelectSlot.addActionListener(e -> showSlotPickerDialog());

        JPanel panelSlotInput = new JPanel(new BorderLayout(5, 0));
        panelSlotInput.add(txtViTriSelected, BorderLayout.CENTER);
        panelSlotInput.add(btnSelectSlot, BorderLayout.EAST);

        txtGiaTien = new JTextField(15);
        txtHinhAnh = new JTextField(10); txtHinhAnh.setEditable(false);

        btnChooseImg = new JButton("Chọn ảnh");
        JPanel panelImgInput = new JPanel(new BorderLayout(5, 0));
        panelImgInput.add(txtHinhAnh, BorderLayout.CENTER);
        panelImgInput.add(btnChooseImg, BorderLayout.EAST);

        addFormField(panelForm, gbc, 0, "ID:", txtId);
        addFormField(panelForm, gbc, 1, "Biển số xe:", txtBienSo);
        addFormField(panelForm, gbc, 2, "Loại xe:", cbLoaiXe);
        addFormField(panelForm, gbc, 3, "Màu xe:", txtMauXe);
        addFormField(panelForm, gbc, 4, "Thời gian vào:", txtNgayVao);
        addFormField(panelForm, gbc, 5, "Vị trí đỗ:", panelSlotInput);
        addFormField(panelForm, gbc, 6, "Giá tiền:", txtGiaTien);
        addFormField(panelForm, gbc, 7, "Hình ảnh:", panelImgInput);

        // Nút chức năng
        JPanel panelBtns = new JPanel(new GridLayout(4, 2, 4, 4));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnCheckout = new JButton("Trả xe");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");
        btnUndo = new JButton("Quay lại");
        btnStatistic = new JButton("Thống kê");

        panelBtns.add(btnAdd); 
        panelBtns.add(btnEdit); 
        panelBtns.add(btnCheckout);
        panelBtns.add(btnDelete);
        panelBtns.add(btnClear); 
        panelBtns.add(btnStatistic); 
        panelBtns.add(btnUndo);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        panelForm.add(panelBtns, gbc);

        add(panelForm, BorderLayout.WEST);

        // Panel bên phải (Công cụ & Bảng dữ liệu)
        JPanel panelRight = new JPanel(new BorderLayout(5, 5));

        JPanel panelTools = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTools.setBorder(BorderFactory.createTitledBorder("Công cụ"));

        btnSortId = new JButton("Xếp ID");
        btnSortBienSo = new JButton("Xếp Biển Số");
        btnSortNgay = new JButton("Xếp Ngày Vào");
        
        btnSearch = new JButton("Tìm kiếm");
        btnCancelSearch = new JButton("Hủy tìm");

        panelTools.add(new JLabel("Sắp xếp:"));
        panelTools.add(btnSortId); panelTools.add(btnSortBienSo); panelTools.add(btnSortNgay);
        panelTools.add(new JSeparator(JSeparator.VERTICAL));
        panelTools.add(btnSearch); panelTools.add(btnCancelSearch);

        panelRight.add(panelTools, BorderLayout.NORTH);

        // Bảng danh sách xe
        String[] columns = {"ID", "Biển Số", "Loại Xe", "Màu Xe", "Thời Gian Vào", "Vị Trí", "Giá Tiền", "Hình Ảnh"};
        tableModel = new DefaultTableModel(columns, 0);
        tableVehicle = new JTable(tableModel);
        
        // Căn phải cột giá tiền trên bảng cho đẹp
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableVehicle.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

        panelRight.add(new JScrollPane(tableVehicle), BorderLayout.CENTER);

        // Thống kê tổng số
        lblTotalCount = new JLabel("Tổng số xe trong bãi: 0");
        lblTotalCount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFooter.add(lblTotalCount);
        panelRight.add(panelFooter, BorderLayout.SOUTH);

        add(panelRight, BorderLayout.CENTER);

        initSearchDialog();
        initStatisticDialog();
    }

    private void addFormField(JPanel p, GridBagConstraints gbc, int row, String label, Component comp) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        p.add(comp, gbc);
    }

    // Hiển thị Dialog sơ đồ bãi xe phân chia theo Tab
    private void showSlotPickerDialog() {
        if (currentParkingSlots == null || currentParkingSlots.isEmpty()) {
            showMessage("Không có dữ liệu vị trí đỗ!");
            return;
        }

        slotPickerDialog = new JDialog(this, "Sơ Đồ Chọn Vị Trí Đỗ Theo Khu", true);
        slotPickerDialog.setSize(600, 450);
        slotPickerDialog.setLocationRelativeTo(this);
        slotPickerDialog.setLayout(new BorderLayout(10, 10));

        // Ghi chú màu sắc
        JPanel panelLegend = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JLabel lblGreen = new JLabel(" GREEN: Vị trí trống ");
        lblGreen.setOpaque(true); 
        lblGreen.setBackground(new Color(46, 204, 113)); 
        lblGreen.setForeground(Color.WHITE);

        JLabel lblRed = new JLabel(" RED: Đã có xe ");
        lblRed.setOpaque(true); 
        lblRed.setBackground(new Color(231, 76, 60)); 
        lblRed.setForeground(Color.WHITE);

        panelLegend.add(lblGreen); 
        panelLegend.add(lblRed);
        slotPickerDialog.add(panelLegend, BorderLayout.NORTH);

        // Phân loại ô đỗ theo Tên Khu (A, B, C...)
        Map<String, List<ParkingSlot>> groupedSlots = currentParkingSlots.stream()
                .collect(Collectors.groupingBy(slot -> {
                    String name = slot.getTenViTri() != null ? slot.getTenViTri().trim() : "Khác";
                    if (name.contains("-")) {
                        return name.split("-")[0].trim();
                    } else if (name.matches("^[a-zA-Z]+.*")) {
                        return "Khu " + name.substring(0, 1).toUpperCase();
                    }
                    return "Khu Khác";
                }));

        JTabbedPane tabbedPane = new JTabbedPane();

        groupedSlots.forEach((zoneName, slotsInZone) -> {
            JPanel panelGrid = new JPanel(new GridLayout(0, 5, 8, 8));
            panelGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            for (ParkingSlot slot : slotsInZone) {
                JButton btnSlot = new JButton(slot.getTenViTri());
                btnSlot.setFont(new Font("Segoe UI", Font.BOLD, 12));
                btnSlot.setFocusPainted(false);
                btnSlot.setOpaque(true);
                btnSlot.setContentAreaFilled(true);
                btnSlot.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

                if (slot.isTrangThai()) {
                    // MÀU ĐỎ: Đã có xe
                    btnSlot.setBackground(new Color(231, 76, 60)); 
                    btnSlot.setForeground(Color.WHITE);
                    btnSlot.addActionListener(e -> showMessage("Vị trí này đã có xe đỗ!"));
                } else {
                    // MÀU XANH: Trống
                    btnSlot.setBackground(new Color(46, 204, 113)); 
                    btnSlot.setForeground(Color.WHITE);
                    btnSlot.addActionListener(e -> {
                        // 1. Gán vị trí đỗ được chọn
                        txtViTriSelected.setText(slot.getTenViTri());
                        
                        // 2. TỰ ĐỘNG CẬP NHẬT GIÁ TIỀN TỪ PARKINGSLOT SANG FORM (Đã format ví dụ 5,000)
                        txtGiaTien.setText(priceFormat.format(slot.getGiaTien()));
                        
                        // 3. Tự động chuyển ComboBox loại xe nếu slot có quy định loại xe
                        if (slot.getLoaiSlot() != null && !slot.getLoaiSlot().isEmpty()) {
                            cbLoaiXe.setSelectedItem(slot.getLoaiSlot());
                        }

                        slotPickerDialog.dispose();
                    });
                }
                panelGrid.add(btnSlot);
            }

            JScrollPane scrollPane = new JScrollPane(panelGrid);
            tabbedPane.addTab(zoneName, scrollPane);
        });

        slotPickerDialog.add(tabbedPane, BorderLayout.CENTER);
        slotPickerDialog.setVisible(true);
    }

    private void initSearchDialog() {
        searchDialog = new JDialog(this, "Tìm Kiếm Xe", true);
        searchDialog.setSize(400, 180);
        searchDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchDialog.setLocationRelativeTo(this);

        rdoSearchBienSo = new JRadioButton("Biển số", true);
        rdoSearchLoaiXe = new JRadioButton("Loại xe");
        rdoSearchMauXe = new JRadioButton("Màu xe");
        bgSearch = new ButtonGroup();
        bgSearch.add(rdoSearchBienSo); bgSearch.add(rdoSearchLoaiXe); bgSearch.add(rdoSearchMauXe);

        txtSearchInput = new JTextField(20);
        JButton btnDoSearch = new JButton("Thực hiện tìm");

        searchDialog.add(rdoSearchBienSo); searchDialog.add(rdoSearchLoaiXe); searchDialog.add(rdoSearchMauXe);
        searchDialog.add(new JLabel("Từ khóa:")); searchDialog.add(txtSearchInput);
        searchDialog.add(btnDoSearch);
    }

    private void initStatisticDialog() {
        statisticDialog = new JDialog(this, "Thống Kê Bãi Xe", true);
        statisticDialog.setSize(350, 250);
        statisticDialog.setLayout(new BorderLayout(10, 10));
        statisticDialog.setLocationRelativeTo(this);

        lblStatistic = new JLabel("<html><b>Bảng Thống Kê:</b><br/>Chưa có thông tin.</html>", SwingConstants.CENTER);
        btnStatisticType = new JButton("Thống kê theo Loại Xe");
        btnStatisticClear = new JButton("Xóa Thống Kê");

        JPanel pSouth = new JPanel();
        pSouth.add(btnStatisticType); pSouth.add(btnStatisticClear);

        statisticDialog.add(lblStatistic, BorderLayout.CENTER);
        statisticDialog.add(pSouth, BorderLayout.SOUTH);
    }

    public void setParkingSlotList(List<ParkingSlot> listSlot) {
        this.currentParkingSlots = listSlot;
    }

    public VehicleDetail getVehicleInfo() {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
            String bienSo = txtBienSo.getText();
            String loaiXe = (String) cbLoaiXe.getSelectedItem();
            String mauXe = txtMauXe.getText();

            Date ngayVao = txtNgayVao.getText().isEmpty() ? new Date() : dateTimeFormat.parse(txtNgayVao.getText());
            String viTri = txtViTriSelected.getText();
            
            // Xử lý đọc giá tiền (loại bỏ dấu phẩy/chấm nếu người dùng gõ vào)
            String rawGiaTien = txtGiaTien.getText().replaceAll("[,.]", "").trim();
            double giaTien = rawGiaTien.isEmpty() ? 0.0 : Double.parseDouble(rawGiaTien);
            
            String hinhAnh = txtHinhAnh.getText();

            return new VehicleDetail(id, bienSo, loaiXe, mauXe, ngayVao, hinhAnh, viTri, giaTien);
        } catch (Exception ex) {
            showMessage("Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
            return null;
        }
    }

    public void showVehicle(VehicleDetail v) {
        txtId.setText(String.valueOf(v.getId()));
        txtBienSo.setText(v.getBienSo());
        if (v.getLoaiXe() != null) cbLoaiXe.setSelectedItem(v.getLoaiXe());
        txtMauXe.setText(v.getMauXe());
        txtNgayVao.setText(v.getNgayVaoBai() != null ? dateTimeFormat.format(v.getNgayVaoBai()) : dateTimeFormat.format(new Date()));
        txtViTriSelected.setText(v.getViTriDo() != null ? v.getViTriDo() : "");
        
        // Format hiển thị giá tiền
        txtGiaTien.setText(priceFormat.format(v.getGiaTien()));
        txtHinhAnh.setText(v.getHinhAnh());
    }

    public void showListVehicles(List<VehicleDetail> list) {
        tableModel.setRowCount(0);
        for (VehicleDetail v : list) {
            tableModel.addRow(new Object[]{
                v.getId(), v.getBienSo(), v.getLoaiXe(), v.getMauXe(),
                v.getNgayVaoBai() != null ? dateTimeFormat.format(v.getNgayVaoBai()) : "",
                v.getViTriDo(), 
                priceFormat.format(v.getGiaTien()), // Định dạng trên bảng
                v.getHinhAnh()
            });
        }
    }

    public void fillVehicleFromSelectedRow() throws ParseException {
        int row = tableVehicle.getSelectedRow();
        if (row >= 0) {
            txtId.setText(tableModel.getValueAt(row, 0).toString());
            txtBienSo.setText(tableModel.getValueAt(row, 1).toString());
            
            Object loaiXeObj = tableModel.getValueAt(row, 2);
            if (loaiXeObj != null) cbLoaiXe.setSelectedItem(loaiXeObj.toString());

            txtMauXe.setText(tableModel.getValueAt(row, 3).toString());
            txtNgayVao.setText(tableModel.getValueAt(row, 4).toString());
            
            Object viTriObj = tableModel.getValueAt(row, 5);
            txtViTriSelected.setText(viTriObj != null ? viTriObj.toString() : "");

            // Lấy giá tiền từ bảng đổ ra ô Giá tiền
            Object giaTienObj = tableModel.getValueAt(row, 6);
            if (giaTienObj != null) {
                txtGiaTien.setText(giaTienObj.toString());
            } else {
                txtGiaTien.setText("0");
            }

            txtHinhAnh.setText(tableModel.getValueAt(row, 7) != null ? tableModel.getValueAt(row, 7).toString() : "");
        }
    }

    public void showCountListVehicles(List<VehicleDetail> list) {
        lblTotalCount.setText("Tổng số xe trong bãi: " + (list != null ? list.size() : 0));
    }

    public void clearVehicleInfo() {
        txtId.setText(""); 
        txtBienSo.setText(""); 
        if (cbLoaiXe.getItemCount() > 0) cbLoaiXe.setSelectedIndex(0);
        txtMauXe.setText(""); 
        
        // Cập nhật lại thời gian theo giờ thực tế hiện tại
        txtNgayVao.setText(dateTimeFormat.format(new Date())); 
        
        txtViTriSelected.setText("");
        txtGiaTien.setText(""); 
        txtHinhAnh.setText("");

        // Bỏ chọn dòng đang Highlight trên bảng (nếu có)
        if (tableVehicle != null) {
            tableVehicle.clearSelection();
        }
    }

    public void chooseVehicleImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtHinhAnh.setText(selectedFile.getAbsolutePath());
        }
    }

    public int getChooseSelectSearch() {
        if (rdoSearchBienSo.isSelected()) return 1;
        if (rdoSearchLoaiXe.isSelected()) return 2;
        if (rdoSearchMauXe.isSelected()) return 3;
        return 0;
    }

    public String validateSearch() { return txtSearchInput.getText().trim(); }
    public void searchVehicleInfo() { searchDialog.setVisible(true); }
    public void cancelDialogSearchVehicleInfo() { searchDialog.setVisible(false); }
    public void cancelSearchVehicle() { txtSearchInput.setText(""); }

    public void displayStatisticView() { statisticDialog.setVisible(true); }
    public void clearStatisticView() { lblStatistic.setText("<html><b>Bảng Thống Kê:</b><br/>Đã xóa dữ liệu hiển thị.</html>"); }

    public void showStatisticTypeVehicles(List<VehicleDetail> list) {
        Map<String, Long> counts = list.stream()
                .collect(Collectors.groupingBy(v -> v.getLoaiXe() == null ? "Khác" : v.getLoaiXe(), Collectors.counting()));
        
        StringBuilder sb = new StringBuilder("<html><b>Thống kê phân loại xe:</b><br/>");
        counts.forEach((k, v) -> sb.append("- ").append(k).append(": ").append(v).append(" chiếc<br/>"));
        sb.append("</html>");
        lblStatistic.setText(sb.toString());
    }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

    public String getSelectedLoaiXe() { return (String) cbLoaiXe.getSelectedItem(); }
    public void addLoaiXeChangeListener(ActionListener l) { cbLoaiXe.addActionListener(l); }

    // --- ADD LISTENERS ---
    public void addAddVehicleListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditVehicleListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addCheckoutVehicleListener(ActionListener l) { btnCheckout.addActionListener(l); }
    public void addDeleteVehicleListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addClearListener(ActionListener l) { btnClear.addActionListener(l); }
    public void addImageVehicleListener(ActionListener l) { btnChooseImg.addActionListener(l); }
    public void addSortByBienSoListener(ActionListener l) { btnSortBienSo.addActionListener(l); }
    public void addSortByNgayVaoBaiListener(ActionListener l) { btnSortNgay.addActionListener(l); }
    public void addSortByIDListener(ActionListener l) { btnSortId.addActionListener(l); }
    public void addSearchListener(ActionListener l) { btnSearch.addActionListener(l); }
    public void addSearchDialogListener(ActionListener l) { ((JButton) searchDialog.getContentPane().getComponent(5)).addActionListener(l); }
    public void addCancelSearchVehicleListener(ActionListener l) { btnCancelSearch.addActionListener(l); }
    public void addCancelDialogListener(ActionListener l) {}
    public void addUndoListener(ActionListener l) { btnUndo.addActionListener(l); }
    public void addStatisticListener(ActionListener l) { btnStatistic.addActionListener(l); }
    public void addStatisticTypeListener(ActionListener l) { btnStatisticType.addActionListener(l); }
    public void addStatisticClearListener(ActionListener l) { btnStatisticClear.addActionListener(l); }

    public void addListVehicleSelectionListener(ListSelectionListener l) {
        tableVehicle.getSelectionModel().addListSelectionListener(l);
    }

    public void addParkingSlotChangeListener(ActionListener l) {}

    public String getSelectedParkingSlot() { return txtViTriSelected.getText(); }

    public void setGiaTienText(String giaTien) { 
        try {
            double val = Double.parseDouble(giaTien);
            txtGiaTien.setText(priceFormat.format(val));
        } catch (Exception e) {
            txtGiaTien.setText(giaTien);
        }
    }
}