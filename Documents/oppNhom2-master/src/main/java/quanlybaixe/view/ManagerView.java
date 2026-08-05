package quanlybaixe.view;

import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.entity.VehicleDetail;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ManagerView extends JFrame {

    private JTextField txtId, txtBienSo, txtMauXe, txtNgayVao, txtGiaTien, txtHinhAnh, txtViTriSelected;
    private JComboBox<String> cbLoaiXe;
    private JButton btnSelectSlot;
    private JLabel lblTotalCount, lblStatistic;
    private JLabel lblImagePreview; // Khung hiển thị ảnh

    private JButton btnZoomImg, btnDeleteImg; // Nút Phóng to & Xóa ảnh

    private JTextField txtSearchInput;

    private JButton btnAdd, btnEdit, btnDelete, btnCheckout, btnClear, btnSortId, btnSortBienSo, btnSortNgay, btnSearch, btnCancelSearch, btnChooseImg, btnUndo, btnStatistic, btnStatisticType, btnStatisticClear, btnHistory, btnFilterDate;

    private JSpinner spinnerFilterDate;
    private JCheckBox chkEnableDateFilter;

    private JTable tableVehicle;
    private DefaultTableModel tableModel;
    private JDialog statisticDialog, slotPickerDialog;

    private List<ParkingSlot> currentParkingSlots; 
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private final DecimalFormat priceFormat = new DecimalFormat("#,###"); 
    
    private MainView mainView;

    public ManagerView() {
        this(null);
    }

    public ManagerView(MainView mainView) {
        this.mainView = mainView;
        setTitle("Quản Lý Chi Tiết Xe Trong Bãi");
        
        setSize(1450, 850);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeAndReturnToMain();
            }
        });

        JPanel panelTitle = new JPanel(new BorderLayout());
        panelTitle.setBackground(new Color(41, 128, 185));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ CHI TIẾT XE RA VÀO BÃI", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelTitle.add(lblTitle, BorderLayout.CENTER);

        add(panelTitle, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin xe"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15); txtId.setEditable(false);
        txtBienSo = new JTextField(15);
        
        String[] loaiXeOptions = {"Xe máy", "Ô tô"};
        cbLoaiXe = new JComboBox<>(loaiXeOptions);

        txtMauXe = new JTextField(15);
        
        txtNgayVao = new JTextField(15);
        txtNgayVao.setEditable(false);
        
        Calendar now = Calendar.getInstance();
        txtNgayVao.setText(dateTimeFormat.format(now.getTime()));
        
        txtViTriSelected = new JTextField(10);
        txtViTriSelected.setEditable(false);
        btnSelectSlot = new JButton("Chọn vị trí");
        btnSelectSlot.addActionListener(e -> showSlotPickerDialog());

        JPanel panelSlotInput = new JPanel(new BorderLayout(5, 0));
        panelSlotInput.add(txtViTriSelected, BorderLayout.CENTER);
        panelSlotInput.add(btnSelectSlot, BorderLayout.EAST);

        txtGiaTien = new JTextField(15);
        txtHinhAnh = new JTextField(10); txtHinhAnh.setEditable(false);

        btnChooseImg = new JButton("Chọn...");
        JPanel panelImgInput = new JPanel(new BorderLayout(5, 0));
        panelImgInput.add(txtHinhAnh, BorderLayout.CENTER);
        panelImgInput.add(btnChooseImg, BorderLayout.EAST);

        // Frame hiển thị ảnh preview
        lblImagePreview = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(180, 120));
        lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        lblImagePreview.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblImagePreview.setToolTipText("Bấm vào để phóng to ảnh");
        lblImagePreview.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zoomImage();
            }
        });

        // Nút thao tác ảnh
        btnZoomImg = new JButton("Phóng to");
        btnDeleteImg = new JButton("Xóa ảnh");
        btnZoomImg.addActionListener(e -> zoomImage());
        btnDeleteImg.addActionListener(e -> deleteImageFile());

        JPanel panelImgActionBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panelImgActionBtns.add(btnZoomImg);
        panelImgActionBtns.add(btnDeleteImg);

        JPanel panelPreviewContainer = new JPanel(new BorderLayout(0, 5));
        panelPreviewContainer.add(lblImagePreview, BorderLayout.CENTER);
        panelPreviewContainer.add(panelImgActionBtns, BorderLayout.SOUTH);

        addFormField(panelForm, gbc, 0, "ID:", txtId);
        addFormField(panelForm, gbc, 1, "Biển số xe:", txtBienSo);
        addFormField(panelForm, gbc, 2, "Loại xe:", cbLoaiXe);
        addFormField(panelForm, gbc, 3, "Màu xe:", txtMauXe);
        addFormField(panelForm, gbc, 4, "Thời gian vào:", txtNgayVao);
        addFormField(panelForm, gbc, 5, "Vị trí đỗ:", panelSlotInput);
        addFormField(panelForm, gbc, 6, "Giá tiền:", txtGiaTien);
        addFormField(panelForm, gbc, 7, "Hình ảnh:", panelImgInput);
        addFormField(panelForm, gbc, 8, "Xem trước:", panelPreviewContainer);

        JPanel panelBtns = new JPanel(new GridLayout(4, 2, 6, 6));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton(" Xác NhậnSửa");
        btnCheckout = new JButton("Trả xe");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");
        btnUndo = new JButton("Quay lại");
        btnStatistic = new JButton("Thống kê");
        btnHistory = new JButton("Lịch sử");

        panelBtns.add(btnAdd); 
        panelBtns.add(btnEdit); 
        panelBtns.add(btnCheckout);
        panelBtns.add(btnDelete);
        panelBtns.add(btnClear); 
        panelBtns.add(btnStatistic); 
        panelBtns.add(btnHistory);
        panelBtns.add(btnUndo);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        panelForm.add(panelBtns, gbc);

        add(panelForm, BorderLayout.WEST);

        JPanel panelRight = new JPanel(new BorderLayout(5, 5));

        JPanel panelTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelTools.setBorder(BorderFactory.createTitledBorder("Thanh công cụ lọc & tìm kiếm"));

        btnSortId = new JButton("Xếp ID");
        btnSortBienSo = new JButton("Xếp Biển Số");
        btnSortNgay = new JButton("Xếp Ngày Vào");

        panelTools.add(new JLabel("Sắp xếp:"));
        panelTools.add(btnSortId); 
        panelTools.add(btnSortBienSo); 
        panelTools.add(btnSortNgay);

        panelTools.add(new JSeparator(JSeparator.VERTICAL));

        chkEnableDateFilter = new JCheckBox("Lọc Ngày:");
        chkEnableDateFilter.setSelected(false);
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerFilterDate = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFilterDate, "dd/MM/yyyy");
        spinnerFilterDate.setEditor(dateEditor);
        spinnerFilterDate.setEnabled(false);

        chkEnableDateFilter.addActionListener(e -> spinnerFilterDate.setEnabled(chkEnableDateFilter.isSelected()));

        btnFilterDate = new JButton("Lọc Ngày");

        panelTools.add(chkEnableDateFilter);
        panelTools.add(spinnerFilterDate);
        panelTools.add(btnFilterDate);

        panelTools.add(new JSeparator(JSeparator.VERTICAL));

        txtSearchInput = new JTextField(12);
        btnSearch = new JButton("Tìm kiếm");
        btnCancelSearch = new JButton("Hủy Lọc");

        panelTools.add(new JLabel("Từ khóa:"));
        panelTools.add(txtSearchInput);
        panelTools.add(btnSearch);
        panelTools.add(btnCancelSearch);

        panelRight.add(panelTools, BorderLayout.NORTH);

        String[] columns = {"ID", "Biển Số", "Loại Xe", "Màu Xe", "Thời Gian Vào", "Vị Trí", "Giá Tiền", "Hình Ảnh"};
        tableModel = new DefaultTableModel(columns, 0);
        tableVehicle = new JTable(tableModel);
        tableVehicle.setRowHeight(24);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableVehicle.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

        panelRight.add(new JScrollPane(tableVehicle), BorderLayout.CENTER);

        lblTotalCount = new JLabel("Tổng số xe trong bãi: 0");
        lblTotalCount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFooter.add(lblTotalCount);
        panelRight.add(panelFooter, BorderLayout.SOUTH);

        add(panelRight, BorderLayout.CENTER);

        initStatisticDialog();
    }

    private void closeAndReturnToMain() {
        this.dispose();
        if (mainView != null) {
            mainView.setVisible(true);
        }
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    private void addFormField(JPanel p, GridBagConstraints gbc, int row, String label, Component comp) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        p.add(comp, gbc);
    }

    // Load và scale ảnh vừa khung hiển thị
    public void displayImage(String imagePath) {
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                lblImagePreview.setIcon(new ImageIcon(img));
                lblImagePreview.setText("");
                return;
            }
        }
        lblImagePreview.setIcon(null);
        lblImagePreview.setText("Chưa có ảnh / Lỗi ảnh");
    }

    // Phóng to ảnh trong Dialog riêng
    public void zoomImage() {
        String path = txtHinhAnh.getText().trim();
        if (path.isEmpty()) {
            showMessage("Chưa chọn hình ảnh để phóng to!");
            return;
        }

        File imgFile = new File(path);
        if (!imgFile.exists()) {
            showMessage("File ảnh không tồn tại!");
            return;
        }

        JDialog zoomDialog = new JDialog(this, "Xem Ảnh Kích Thước Lớn", true);
        zoomDialog.setSize(700, 550);
        zoomDialog.setLocationRelativeTo(this);

        ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
        Image img = icon.getImage().getScaledInstance(650, 450, Image.SCALE_SMOOTH);
        JLabel lblFullImage = new JLabel(new ImageIcon(img), SwingConstants.CENTER);

        zoomDialog.add(new JScrollPane(lblFullImage), BorderLayout.CENTER);
        zoomDialog.setVisible(true);
    }

    // Xóa file ảnh vật lý và xóa đường dẫn
    public void deleteImageFile() {
        String path = txtHinhAnh.getText().trim();
        if (path.isEmpty()) {
            showMessage("Chưa có file ảnh nào được chọn!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc muốn xóa file ảnh này khỏi hệ thống?", 
            "Xác nhận xóa ảnh", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                boolean deleted = imgFile.delete();
                if (deleted) {
                    showMessage("Đã xóa file ảnh thành công!");
                } else {
                    showMessage("Không thể xóa file ảnh (file có thể đang được ứng dụng khác sử dụng)!");
                }
            } else {
                showMessage("File ảnh không tồn tại trên đĩa!");
            }

            // Dọn dẹp ô text và preview
            txtHinhAnh.setText("");
            displayImage("");
        }
    }

    // Chọn ảnh và tự động copy vào thư mục 'image_vehicles/'
    public void chooseVehicleImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn hình ảnh xe");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh (JPG, PNG, JPEG)", "jpg", "jpeg", "png"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            try {
                File dir = new File("image_vehicles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = selectedFile.getName();
                String extension = "";
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    extension = fileName.substring(i);
                }

                String newFileName = "xe_" + System.currentTimeMillis() + extension;
                File destFile = new File(dir, newFileName);

                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                String savedPath = destFile.getPath();
                txtHinhAnh.setText(savedPath);
                displayImage(savedPath);

            } catch (IOException ex) {
                showMessage("Không thể lưu ảnh vào thư mục dự án: " + ex.getMessage());
            }
        }
    }

    private void showSlotPickerDialog() {
        if (currentParkingSlots == null || currentParkingSlots.isEmpty()) {
            showMessage("Không có dữ liệu vị trí đỗ!");
            return;
        }

        slotPickerDialog = new JDialog(this, "Sơ Đồ Chọn Vị Trí Đỗ Theo Khu", true);
        slotPickerDialog.setSize(800, 550);
        slotPickerDialog.setLocationRelativeTo(this);
        slotPickerDialog.setLayout(new BorderLayout(10, 10));

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

        Map<String, List<ParkingSlot>> groupedSlots = currentParkingSlots.stream()
                .collect(Collectors.groupingBy(
                    slot -> {
                        String name = slot.getTenViTri() != null ? slot.getTenViTri().trim() : "Khác";
                        if (name.contains("-")) {
                            return name.split("-")[0].trim();
                        } else if (name.matches("^[a-zA-Z]+.*")) {
                            return "Khu " + name.substring(0, 1).toUpperCase();
                        }
                        return "Khu Khác";
                    },
                    TreeMap::new,
                    Collectors.toList()
                ));

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
                    btnSlot.setBackground(new Color(231, 76, 60)); 
                    btnSlot.setForeground(Color.WHITE);
                    btnSlot.addActionListener(e -> showMessage("Vị trí này đã có xe đỗ!"));
                } else {
                    btnSlot.setBackground(new Color(46, 204, 113)); 
                    btnSlot.setForeground(Color.WHITE);
                    btnSlot.addActionListener(e -> {
                        txtViTriSelected.setText(slot.getTenViTri());
                        txtGiaTien.setText(priceFormat.format(slot.getGiaTien()));
                        
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

    private void initStatisticDialog() {
        statisticDialog = new JDialog(this, "Thống Kê Bãi Xe", true);
        statisticDialog.setSize(450, 300);
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

            Date ngayVao = txtNgayVao.getText().isEmpty() ? Calendar.getInstance().getTime() : dateTimeFormat.parse(txtNgayVao.getText());
            String viTri = txtViTriSelected.getText();
            
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
        txtNgayVao.setText(v.getNgayVaoBai() != null ? dateTimeFormat.format(v.getNgayVaoBai()) : dateTimeFormat.format(Calendar.getInstance().getTime()));
        txtViTriSelected.setText(v.getViTriDo() != null ? v.getViTriDo() : "");
        txtGiaTien.setText(priceFormat.format(v.getGiaTien()));
        txtHinhAnh.setText(v.getHinhAnh());
        displayImage(v.getHinhAnh());
    }

    public void showListVehicles(List<VehicleDetail> list) {
        tableModel.setRowCount(0);
        if (list != null) {
            for (VehicleDetail v : list) {
                tableModel.addRow(new Object[]{
                    v.getId(), 
                    v.getBienSo(), 
                    v.getLoaiXe(), 
                    v.getMauXe(),
                    v.getNgayVaoBai() != null ? dateTimeFormat.format(v.getNgayVaoBai()) : "",
                    v.getViTriDo() != null ? v.getViTriDo() : "", 
                    priceFormat.format(v.getGiaTien()),
                    v.getHinhAnh() != null ? v.getHinhAnh() : ""
                });
            }
        }
    }

    public void fillVehicleFromSelectedRow() throws ParseException {
        int row = tableVehicle.getSelectedRow();
        if (row >= 0) {
            txtId.setText(tableModel.getValueAt(row, 0) != null ? tableModel.getValueAt(row, 0).toString() : "");
            txtBienSo.setText(tableModel.getValueAt(row, 1) != null ? tableModel.getValueAt(row, 1).toString() : "");
            
            Object loaiXeObj = tableModel.getValueAt(row, 2);
            if (loaiXeObj != null) cbLoaiXe.setSelectedItem(loaiXeObj.toString());

            txtMauXe.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
            txtNgayVao.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
            
            Object viTriObj = tableModel.getValueAt(row, 5);
            txtViTriSelected.setText(viTriObj != null ? viTriObj.toString() : "");

            Object giaTienObj = tableModel.getValueAt(row, 6);
            txtGiaTien.setText(giaTienObj != null ? giaTienObj.toString() : "0");

            String imgPath = tableModel.getValueAt(row, 7) != null ? tableModel.getValueAt(row, 7).toString() : "";
            txtHinhAnh.setText(imgPath);
            displayImage(imgPath);
        }
    }

    public void showCountListVehicles(List<VehicleDetail> list) {
        lblTotalCount.setText("Tổng số xe trong bãi: " + (list != null ? list.size() : 0));
    }

    public void setAddButtonEnabled(boolean enabled) {
        btnAdd.setEnabled(enabled);
    }

    public void clearVehicleInfo() {
        txtId.setText(""); 
        txtBienSo.setText(""); 
        if (cbLoaiXe.getItemCount() > 0) cbLoaiXe.setSelectedIndex(0);
        txtMauXe.setText(""); 
        
        Calendar now = Calendar.getInstance();
        txtNgayVao.setText(dateTimeFormat.format(now.getTime())); 
        
        txtViTriSelected.setText("");
        txtGiaTien.setText(""); 
        txtHinhAnh.setText("");
        displayImage("");

        if (tableVehicle != null) {
            tableVehicle.clearSelection();
        }

        setAddButtonEnabled(true);
    }

    public String validateSearch() { return txtSearchInput.getText().trim(); }
    
    public void cancelSearchVehicle() { 
        txtSearchInput.setText(""); 
        chkEnableDateFilter.setSelected(false);
        spinnerFilterDate.setEnabled(false);
    }

    public boolean isDateFilterEnabled() {
        return chkEnableDateFilter.isSelected();
    }

    public Date getSelectedFilterDate() {
        return (Date) spinnerFilterDate.getValue();
    }

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

    public void addAddVehicleListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditVehicleListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addCheckoutVehicleListener(ActionListener l) { btnCheckout.addActionListener(l); }
    public void addDeleteVehicleListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addClearListener(ActionListener l) { btnClear.addActionListener(l); }
    public void addHistoryVehicleListener(ActionListener l) { btnHistory.addActionListener(l); }
    public void addImageVehicleListener(ActionListener l) { btnChooseImg.addActionListener(l); }
    public void addSortByBienSoListener(ActionListener l) { btnSortBienSo.addActionListener(l); }
    public void addSortByNgayVaoBaiListener(ActionListener l) { btnSortNgay.addActionListener(l); }
    public void addSortByIDListener(ActionListener l) { btnSortId.addActionListener(l); }
    
    public void addFilterDateListener(ActionListener l) { btnFilterDate.addActionListener(l); }
    public void addSearchListener(ActionListener l) { btnSearch.addActionListener(l); }
    public void addCancelSearchVehicleListener(ActionListener l) { btnCancelSearch.addActionListener(l); }
    public void addUndoListener(ActionListener l) { btnUndo.addActionListener(l); }
    public void addStatisticListener(ActionListener l) { btnStatistic.addActionListener(l); }
    public void addStatisticTypeListener(ActionListener l) { btnStatisticType.addActionListener(l); }
    public void addStatisticClearListener(ActionListener l) { btnStatisticClear.addActionListener(l); }

    public void addListVehicleSelectionListener(ListSelectionListener l) {
        tableVehicle.getSelectionModel().addListSelectionListener(l);
    }

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