package quanlybaixe.view;

import quanlybaixe.entity.ParkingSlot;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ParkingSlotView extends JFrame {

    private JTextField txtId;
    private JTextField txtBienSo;
    private JTextField txtLoaiXe;
    private JTextField txtMauXe;
    private JTextField txtNgayGui;
    private JTextField txtGiaTien;
    private JTextField txtTenViTri;
    private JTextField txtLoaiSlot;
    private JCheckBox chkTrangThai;
    private JTextField txtGhiChu;

    private JLabel lblTotalCount;

    private JRadioButton rdoSortId, rdoSortViTri, rdoSortGiaTien;
    private ButtonGroup bgSort;

    private JRadioButton rdoSearchBienSo, rdoSearchViTri, rdoSearchLoaiXe;
    private ButtonGroup bgSearch;
    private JTextField txtSearchInput;

    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnSort, btnSearch, btnCancelSearch, btnUndo;

    private JTable tableSlot;
    private DefaultTableModel tableModel;

    public ParkingSlotView() {
        setTitle("Quản Lý Sơ Đồ / Vị Trí Đỗ Xe");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. TIÊU ĐỀ ---
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(33, 150, 243));
        JLabel lblTitle = new JLabel("QUẢN LÝ VỊ TRÍ ĐỖ XE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle);
        add(panelTitle, BorderLayout.NORTH);

        // --- 2. FORM NHẬP THÔNG TIN (LEFT) ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin vị trí & Xe"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15);
        txtId.setEditable(false); // ID tự tăng
        txtBienSo = new JTextField(15);
        txtLoaiXe = new JTextField(15);
        txtMauXe = new JTextField(15);
        txtNgayGui = new JTextField(15);
        txtGiaTien = new JTextField(15);
        txtTenViTri = new JTextField(15);
        txtLoaiSlot = new JTextField(15);
        chkTrangThai = new JCheckBox("Đã có xe đỗ");
        txtGhiChu = new JTextField(15);

        addFormField(panelForm, gbc, 0, "ID Slot:", txtId);
        addFormField(panelForm, gbc, 1, "Tên vị trí (Ví dụ A01):", txtTenViTri);
        addFormField(panelForm, gbc, 2, "Biển số xe:", txtBienSo);
        addFormField(panelForm, gbc, 3, "Loại xe:", txtLoaiXe);
        addFormField(panelForm, gbc, 4, "Màu xe:", txtMauXe);
        addFormField(panelForm, gbc, 5, "Ngày gửi (dd/MM/yyyy):", txtNgayGui);
        addFormField(panelForm, gbc, 6, "Giá tiền:", txtGiaTien);
        addFormField(panelForm, gbc, 7, "Loại Slot:", txtLoaiSlot);
        addFormField(panelForm, gbc, 8, "Trạng thái:", chkTrangThai);
        addFormField(panelForm, gbc, 9, "Ghi chú:", txtGhiChu);

        // Nút chức năng Form
        JPanel panelButtons = new JPanel(new GridLayout(2, 3, 5, 5));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Nhập lại");
        btnUndo = new JButton("Quay lại");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);
        panelButtons.add(btnUndo);

        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- 3. BẢNG DANH SÁCH & TÌM KIẾM / SẮP XẾP (CENTER) ---
        JPanel panelRight = new JPanel(new BorderLayout(5, 5));

        // Panel Tìm kiếm & Sắp xếp
        JPanel panelTools = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTools.setBorder(BorderFactory.createTitledBorder("Công cụ Tìm kiếm & Sắp xếp"));

        // Sắp xếp
        JPanel panelSort = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rdoSortId = new JRadioButton("ID");
        rdoSortViTri = new JRadioButton("Tên vị trí");
        rdoSortGiaTien = new JRadioButton("Giá tiền");
        bgSort = new ButtonGroup();
        bgSort.add(rdoSortId); bgSort.add(rdoSortViTri); bgSort.add(rdoSortGiaTien);
        btnSort = new JButton("Sắp xếp");
        panelSort.add(new JLabel("Sắp xếp theo:"));
        panelSort.add(rdoSortId); panelSort.add(rdoSortViTri); panelSort.add(rdoSortGiaTien);
        panelSort.add(btnSort);

        // Tìm kiếm
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rdoSearchBienSo = new JRadioButton("Biển số");
        rdoSearchViTri = new JRadioButton("Vị trí");
        rdoSearchLoaiXe = new JRadioButton("Loại xe");
        bgSearch = new ButtonGroup();
        bgSearch.add(rdoSearchBienSo); bgSearch.add(rdoSearchViTri); bgSearch.add(rdoSearchLoaiXe);
        txtSearchInput = new JTextField(12);
        btnSearch = new JButton("Tìm kiếm");
        btnCancelSearch = new JButton("Hủy tìm");

        panelSearch.add(new JLabel("Tìm theo:"));
        panelSearch.add(rdoSearchBienSo); panelSearch.add(rdoSearchViTri); panelSearch.add(rdoSearchLoaiXe);
        panelSearch.add(txtSearchInput);
        panelSearch.add(btnSearch);
        panelSearch.add(btnCancelSearch);

        panelTools.add(panelSort);
        panelTools.add(panelSearch);
        panelRight.add(panelTools, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] columnNames = {"ID", "Vị Trí", "Biển Số", "Loại Xe", "Màu Xe", "Ngày Gửi", "Giá Tiền", "Trạng Thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableSlot = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableSlot);
        panelRight.add(scrollPane, BorderLayout.CENTER);

        // Thanh trạng thái đếm số lượng
        lblTotalCount = new JLabel("Tổng số vị trí đỗ: 0");
        lblTotalCount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFooter.add(lblTotalCount);
        panelRight.add(panelFooter, BorderLayout.SOUTH);

        add(panelRight, BorderLayout.CENTER);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, Component comp) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(comp, gbc);
    }

    // --- CÁC HÀM XỬ LÝ DỮ LIỆU ĐƯỢC CONTROLLER GỌI ---
    public ParkingSlot getParkingSlotInfo() {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
            String bienSo = txtBienSo.getText();
            String loaiXe = txtLoaiXe.getText();
            String mauXe = txtMauXe.getText();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayGui = txtNgayGui.getText().isEmpty() ? new Date() : sdf.parse(txtNgayGui.getText());
            
            double giaTien = txtGiaTien.getText().isEmpty() ? 0.0 : Double.parseDouble(txtGiaTien.getText());
            String tenViTri = txtTenViTri.getText();
            String loaiSlot = txtLoaiSlot.getText();
            boolean trangThai = chkTrangThai.isSelected();
            String ghiChu = txtGhiChu.getText();

            return new ParkingSlot(id, bienSo, loaiXe, mauXe, ngayGui, giaTien, tenViTri, loaiSlot, trangThai, ghiChu);
        } catch (Exception ex) {
            showMessage("Dữ liệu nhập vào không hợp lệ (Kiểm tra lại Ngày/Giá tiền)!");
            return null;
        }
    }

    public void showParkingSlot(ParkingSlot slot) {
        txtId.setText(String.valueOf(slot.getId()));
        txtTenViTri.setText(slot.getTenViTri());
        txtBienSo.setText(slot.getBienSo());
        txtLoaiXe.setText(slot.getLoaiXe());
        txtMauXe.setText(slot.getMauXe());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayGui.setText(slot.getNgayGui() != null ? sdf.format(slot.getNgayGui()) : "");
        
        txtGiaTien.setText(String.valueOf(slot.getGiaTien()));
        txtLoaiSlot.setText(slot.getLoaiSlot());
        chkTrangThai.setSelected(slot.isTrangThai());
        txtGhiChu.setText(slot.getGhiChu());
    }

    public void showListParkingSlots(List<ParkingSlot> list) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (ParkingSlot slot : list) {
            tableModel.addRow(new Object[]{
                slot.getId(),
                slot.getTenViTri(),
                slot.getBienSo(),
                slot.getLoaiXe(),
                slot.getMauXe(),
                slot.getNgayGui() != null ? sdf.format(slot.getNgayGui()) : "",
                slot.getGiaTien(),
                slot.isTrangThai() ? "Đã đỗ" : "Còn trống"
            });
        }
    }

    public void fillParkingSlotFromSelectedRow(List<ParkingSlot> list) throws ParseException {
        int row = tableSlot.getSelectedRow();
        if (row >= 0 && row < list.size()) {
            showParkingSlot(list.get(row));
        }
    }

    public void showCountListParkingSlots(List<ParkingSlot> list) {
        lblTotalCount.setText("Tổng số vị trí đỗ: " + (list != null ? list.size() : 0));
    }

    public void clearParkingSlotInfo() {
        txtId.setText("");
        txtTenViTri.setText("");
        txtBienSo.setText("");
        txtLoaiXe.setText("");
        txtMauXe.setText("");
        txtNgayGui.setText("");
        txtGiaTien.setText("");
        txtLoaiSlot.setText("");
        chkTrangThai.setSelected(false);
        txtGhiChu.setText("");
    }

    public int getChooseSelectSort() {
        if (rdoSortId.isSelected()) return 1;
        if (rdoSortViTri.isSelected()) return 2;
        if (rdoSortGiaTien.isSelected()) return 3;
        return 0;
    }

    public int getChooseSelectSearch() {
        if (rdoSearchBienSo.isSelected()) return 1;
        if (rdoSearchViTri.isSelected()) return 2;
        if (rdoSearchLoaiXe.isSelected()) return 3;
        return 0;
    }

    public String validateSearch() {
        return txtSearchInput.getText().trim();
    }

    public void searchParkingSlotInfo() {}
    public void cancelDialogSearchParkingSlotInfo() {}
    public void cancelSearchParkingSlot() { txtSearchInput.setText(""); }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // --- BỘ LẮNG NGHE CHO CONTROLLER ---
    public void addUndoListener(ActionListener l) { btnUndo.addActionListener(l); }
    public void addAddParkingSlotListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditParkingSlotListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteParkingSlotListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addClearListener(ActionListener l) { btnClear.addActionListener(l); }
    public void addSortParkingSlotListener(ActionListener l) { btnSort.addActionListener(l); }
    public void addSearchListener(ActionListener l) {}
    public void addSearchDialogListener(ActionListener l) { btnSearch.addActionListener(l); }
    public void addCancelSearchParkingSlotListener(ActionListener l) { btnCancelSearch.addActionListener(l); }
    public void addCancelDialogListener(ActionListener l) {}
    
    public void addListParkingSlotSelectionListener(ListSelectionListener l) {
        tableSlot.getSelectionModel().addListSelectionListener(l);
    }
}