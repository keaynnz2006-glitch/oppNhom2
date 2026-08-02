package quanlybaixe.view;

import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.entity.VehicleDetail;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerView extends JFrame {

    private JTextField txtId, txtBienSo, txtMauXe, txtNgayVao, txtGiaTien, txtHinhAnh;
    private JComboBox<String> cbLoaiXe, cbViTri;
    private JLabel lblTotalCount, lblImagePreview, lblStatistic;
    private JRadioButton rdoSearchBienSo, rdoSearchLoaiXe, rdoSearchMauXe;
    private ButtonGroup bgSearch;
    private JTextField txtSearchInput;

    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnSortId, btnSortBienSo, btnSortNgay, btnSearch, btnCancelSearch, btnChooseImg, btnUndo, btnStatistic, btnStatisticType, btnStatisticClear;

    private JTable tableVehicle;
    private DefaultTableModel tableModel;
    private JDialog searchDialog, statisticDialog;

    public ManagerView() {
        setTitle("Quản Lý Chi Tiết Xe Trong Bãi");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(41, 128, 185));
        JLabel lblTitle = new JLabel("QUẢN LÝ CHI TIẾT XE RA VÀO BÃI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle);
        add(panelTitle, BorderLayout.NORTH);

        // Form Panel (Left)
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin xe"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15); txtId.setEditable(false);
        txtBienSo = new JTextField(15);
        
        String[] loaiXeOptions = {"Ô tô", "Xe máy"};
        cbLoaiXe = new JComboBox<>(loaiXeOptions);

        txtMauXe = new JTextField(15);
        txtNgayVao = new JTextField(15);
        
        cbViTri = new JComboBox<>();
        
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
        addFormField(panelForm, gbc, 4, "Ngày vào (dd/MM/yyyy):", txtNgayVao);
        addFormField(panelForm, gbc, 5, "Vị trí đỗ:", cbViTri);
        addFormField(panelForm, gbc, 6, "Giá tiền:", txtGiaTien);
        addFormField(panelForm, gbc, 7, "Hình ảnh:", panelImgInput);

        // Buttons
        JPanel panelBtns = new JPanel(new GridLayout(3, 3, 4, 4));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");
        btnUndo = new JButton("Quay lại");
        btnStatistic = new JButton("Thống kê");

        panelBtns.add(btnAdd); panelBtns.add(btnEdit); panelBtns.add(btnDelete);
        panelBtns.add(btnClear); panelBtns.add(btnStatistic); panelBtns.add(btnUndo);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        panelForm.add(panelBtns, gbc);

        add(panelForm, BorderLayout.WEST);

        // Right Panel
        JPanel panelRight = new JPanel(new BorderLayout(5, 5));

        // Tools (Sort & Search)
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

        // Table
        String[] columns = {"ID", "Biển Số", "Loại Xe", "Màu Xe", "Ngày Vào", "Vị Trí", "Giá Tiền", "Hình Ảnh"};
        tableModel = new DefaultTableModel(columns, 0);
        tableVehicle = new JTable(tableModel);
        panelRight.add(new JScrollPane(tableVehicle), BorderLayout.CENTER);

        // Status
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
        cbViTri.removeAllItems();
        if (listSlot != null) {
            for (ParkingSlot slot : listSlot) {
                cbViTri.addItem(slot.getTenViTri());
            }
        }
    }

    public VehicleDetail getVehicleInfo() {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
            String bienSo = txtBienSo.getText();
            String loaiXe = (String) cbLoaiXe.getSelectedItem();
            String mauXe = txtMauXe.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayVao = txtNgayVao.getText().isEmpty() ? new Date() : sdf.parse(txtNgayVao.getText());

            String viTri = (String) cbViTri.getSelectedItem();
            
            double giaTien = txtGiaTien.getText().isEmpty() ? 0.0 : Double.parseDouble(txtGiaTien.getText());
            String hinhAnh = txtHinhAnh.getText();

            return new VehicleDetail(id, bienSo, loaiXe, mauXe, ngayVao, hinhAnh, viTri, giaTien);
        } catch (Exception ex) {
            showMessage("Dữ liệu không hợp lệ! Vui lòng kiểm tra lại Ngày/Giá tiền.");
            return null;
        }
    }

    public void showVehicle(VehicleDetail v) {
        txtId.setText(String.valueOf(v.getId()));
        txtBienSo.setText(v.getBienSo());
        
        if (v.getLoaiXe() != null) {
            cbLoaiXe.setSelectedItem(v.getLoaiXe());
        }
        
        txtMauXe.setText(v.getMauXe());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayVao.setText(v.getNgayVaoBai() != null ? sdf.format(v.getNgayVaoBai()) : "");

        if (v.getViTriDo() != null) {
            boolean exists = false;
            for (int i = 0; i < cbViTri.getItemCount(); i++) {
                if (v.getViTriDo().equalsIgnoreCase(cbViTri.getItemAt(i))) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                cbViTri.addItem(v.getViTriDo());
            }
            cbViTri.setSelectedItem(v.getViTriDo());
        }
        
        txtGiaTien.setText(String.valueOf(v.getGiaTien()));
        txtHinhAnh.setText(v.getHinhAnh());
    }

    public void showListVehicles(List<VehicleDetail> list) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (VehicleDetail v : list) {
            tableModel.addRow(new Object[]{
                v.getId(), v.getBienSo(), v.getLoaiXe(), v.getMauXe(),
                v.getNgayVaoBai() != null ? sdf.format(v.getNgayVaoBai()) : "",
                v.getViTriDo(), v.getGiaTien(), v.getHinhAnh()
            });
        }
    }

    // Cập nhật quan trọng: Đảm bảo khi chọn dòng trên bảng, vị trí đỗ được nạp chính xác vào ComboBox
    public void fillVehicleFromSelectedRow() throws ParseException {
        int row = tableVehicle.getSelectedRow();
        if (row >= 0) {
            txtId.setText(tableModel.getValueAt(row, 0).toString());
            txtBienSo.setText(tableModel.getValueAt(row, 1).toString());
            
            Object loaiXeObj = tableModel.getValueAt(row, 2);
            if (loaiXeObj != null) {
                cbLoaiXe.setSelectedItem(loaiXeObj.toString());
            }

            txtMauXe.setText(tableModel.getValueAt(row, 3).toString());
            txtNgayVao.setText(tableModel.getValueAt(row, 4).toString());
            
            Object viTriObj = tableModel.getValueAt(row, 5);
            if (viTriObj != null) {
                String viTriStr = viTriObj.toString();
                
                // Kiểm tra nếu vị trí này chưa có trong ComboBox thì thêm tạm thời vào
                boolean exists = false;
                for (int i = 0; i < cbViTri.getItemCount(); i++) {
                    if (viTriStr.equalsIgnoreCase(cbViTri.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    cbViTri.addItem(viTriStr);
                }
                cbViTri.setSelectedItem(viTriStr);
            }

            txtGiaTien.setText(tableModel.getValueAt(row, 6).toString());
            txtHinhAnh.setText(tableModel.getValueAt(row, 7) != null ? tableModel.getValueAt(row, 7).toString() : "");
        }
    }

    public void showCountListVehicles(List<VehicleDetail> list) {
        lblTotalCount.setText("Tổng số xe trong bãi: " + (list != null ? list.size() : 0));
    }

    public void clearVehicleInfo() {
        txtId.setText(""); txtBienSo.setText(""); 
        if (cbLoaiXe.getItemCount() > 0) cbLoaiXe.setSelectedIndex(0);
        txtMauXe.setText(""); txtNgayVao.setText(""); 
        if (cbViTri.getItemCount() > 0) cbViTri.setSelectedIndex(0);
        txtGiaTien.setText(""); txtHinhAnh.setText("");
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

    // --- ADD LISTENERS ---
    public void addAddVehicleListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditVehicleListener(ActionListener l) { btnEdit.addActionListener(l); }
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

    public void addParkingSlotChangeListener(ActionListener l) {
        cbViTri.addActionListener(l);
    }

    public String getSelectedParkingSlot() {
        return (String) cbViTri.getSelectedItem();
    }

    public void setGiaTienText(String giaTien) {
        txtGiaTien.setText(giaTien);
    }
}