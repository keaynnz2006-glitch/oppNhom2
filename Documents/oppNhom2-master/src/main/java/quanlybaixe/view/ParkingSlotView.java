package quanlybaixe.view;

import quanlybaixe.entity.ParkingSlot;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class ParkingSlotView extends JFrame {

    private JTextField txtId;
    private JTextField txtTenViTri;
    private JComboBox<String> cbLoaiSlot;
    private JTextField txtGiaTien;
    private JCheckBox chkTrangThai;
    private JTextField txtGhiChu;

    private JLabel lblTotalCount;

    private JRadioButton rdoSortId, rdoSortViTri, rdoSortGiaTien;
    private ButtonGroup bgSort;

    private JRadioButton rdoSearchViTri, rdoSearchLoaiSlot;
    private ButtonGroup bgSearch;
    private JTextField txtSearchInput;

    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnSort, btnSearch, btnCancelSearch, btnUndo;

    private JTable tableSlot;
    private DefaultTableModel tableModel;

    public ParkingSlotView() {
        setTitle("Quản Lý Sơ Đồ / Vị Trí Đỗ Xe");
        setSize(980, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. TIÊU ĐỀ ---
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(33, 150, 243));
        panelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ VỊ TRÍ ĐỖ XE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle);
        add(panelTitle, BorderLayout.NORTH);

        // --- 2. FORM NHẬP THÔNG TIN (BÊN TRÁI) ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin vị trí đỗ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15);
        txtId.setEditable(false); // ID tự động sinh
        txtTenViTri = new JTextField(15);

        String[] loaiSlotOptions = {"Xe máy", "Ô tô"};
        cbLoaiSlot = new JComboBox<>(loaiSlotOptions);

        txtGiaTien = new JTextField(15);
        chkTrangThai = new JCheckBox("Đã có xe đỗ");
        txtGhiChu = new JTextField(15);

        addFormField(panelForm, gbc, 0, "ID Slot:", txtId);
        addFormField(panelForm, gbc, 1, "Tên vị trí (Ví dụ: A01):", txtTenViTri);
        addFormField(panelForm, gbc, 2, "Loại Slot:", cbLoaiSlot);
        addFormField(panelForm, gbc, 3, "Giá tiền đỗ (VNĐ):", txtGiaTien);
        addFormField(panelForm, gbc, 4, "Trạng thái:", chkTrangThai);
        addFormField(panelForm, gbc, 5, "Ghi chú:", txtGhiChu);

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

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- 3. BẢNG DANH SÁCH & CÔNG CỤ (BÊN PHẢI) ---
        JPanel panelRight = new JPanel(new BorderLayout(5, 5));
        panelRight.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        // Panel Tìm kiếm & Sắp xếp
        JPanel panelTools = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTools.setBorder(BorderFactory.createTitledBorder("Công cụ Tìm kiếm & Sắp xếp"));

        // Khu vực Sắp xếp
        JPanel panelSort = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        rdoSortId = new JRadioButton("ID");
        rdoSortViTri = new JRadioButton("Tên vị trí");
        rdoSortGiaTien = new JRadioButton("Giá tiền");
        bgSort = new ButtonGroup();
        bgSort.add(rdoSortId); bgSort.add(rdoSortViTri); bgSort.add(rdoSortGiaTien);
        rdoSortId.setSelected(true);

        btnSort = new JButton("Sắp xếp");
        panelSort.add(new JLabel("Sắp xếp theo:"));
        panelSort.add(rdoSortId); 
        panelSort.add(rdoSortViTri); 
        panelSort.add(rdoSortGiaTien);
        panelSort.add(btnSort);

        // Khu vực Tìm kiếm
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        rdoSearchViTri = new JRadioButton("Vị trí");
        rdoSearchLoaiSlot = new JRadioButton("Loại Slot");
        bgSearch = new ButtonGroup();
        bgSearch.add(rdoSearchViTri); bgSearch.add(rdoSearchLoaiSlot);
        rdoSearchViTri.setSelected(true);

        txtSearchInput = new JTextField(12);
        btnSearch = new JButton("Tìm kiếm");
        btnCancelSearch = new JButton("Hủy tìm");

        panelSearch.add(new JLabel("Tìm theo:"));
        panelSearch.add(rdoSearchViTri); 
        panelSearch.add(rdoSearchLoaiSlot);
        panelSearch.add(txtSearchInput);
        panelSearch.add(btnSearch);
        panelSearch.add(btnCancelSearch);

        panelTools.add(panelSort);
        panelTools.add(panelSearch);
        panelRight.add(panelTools, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] columnNames = {"ID", "Tên Vị Trí", "Loại Slot", "Giá Tiền (VNĐ)", "Trạng Thái", "Ghi Chú"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSlot = new JTable(tableModel);
        tableSlot.setRowHeight(25);
        tableSlot.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Căn giữa dữ liệu các cột ID, Loại Slot, Trạng thái
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableSlot.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
        tableSlot.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); 
        tableSlot.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); 

        JScrollPane scrollPane = new JScrollPane(tableSlot);
        panelRight.add(scrollPane, BorderLayout.CENTER);

        // Thanh trạng thái đếm số lượng
        lblTotalCount = new JLabel("Tổng số vị trí đỗ: 0");
        lblTotalCount.setFont(new Font("Segoe UI", Font.BOLD, 13));
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

    // --- CÁC HÀM XỬ LÝ DỮ LIỆU ---

    public ParkingSlot getParkingSlotInfo() {
        try {
            int id = txtId.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtId.getText().trim());
            String tenViTri = txtTenViTri.getText().trim();
            String loaiSlot = (String) cbLoaiSlot.getSelectedItem();
            double giaTien = txtGiaTien.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtGiaTien.getText().trim());
            boolean trangThai = chkTrangThai.isSelected();
            String ghiChu = txtGhiChu.getText().trim();

            if (tenViTri.isEmpty()) {
                showMessage("Vui lòng nhập tên vị trí đỗ!");
                return null;
            }

            return new ParkingSlot(id, tenViTri, loaiSlot, giaTien, trangThai, ghiChu);
        } catch (NumberFormatException ex) {
            showMessage("Giá tiền nhập vào không hợp lệ!");
            return null;
        }
    }

    public void showParkingSlot(ParkingSlot slot) {
        if (slot == null) return;
        txtId.setText(String.valueOf(slot.getId()));
        txtTenViTri.setText(slot.getTenViTri());
        if (slot.getLoaiSlot() != null) {
            cbLoaiSlot.setSelectedItem(slot.getLoaiSlot());
        }
        txtGiaTien.setText(String.format("%.0f", slot.getGiaTien()));
        chkTrangThai.setSelected(slot.isTrangThai());
        txtGhiChu.setText(slot.getGhiChu());
    }

    public void showListParkingSlots(List<ParkingSlot> list) {
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        if (list != null) {
            for (ParkingSlot slot : list) {
                tableModel.addRow(new Object[]{
                    slot.getId(),
                    slot.getTenViTri(),
                    slot.getLoaiSlot(),
                    df.format(slot.getGiaTien()),
                    slot.isTrangThai() ? "Đã đỗ" : "Còn trống",
                    slot.getGhiChu()
                });
            }
        }
    }

    public void fillParkingSlotFromSelectedRow(List<ParkingSlot> list) {
        int row = tableSlot.getSelectedRow();
        if (row >= 0 && list != null) {
            Object idValue = tableModel.getValueAt(row, 0);
            if (idValue != null) {
                int selectedId = Integer.parseInt(idValue.toString());
                for (ParkingSlot slot : list) {
                    if (slot.getId() == selectedId) {
                        showParkingSlot(slot);
                        break;
                    }
                }
            }
        }
    }

    public void showCountListParkingSlots(List<ParkingSlot> list) {
        lblTotalCount.setText("Tổng số vị trí đỗ: " + (list != null ? list.size() : 0));
    }

    public void clearParkingSlotInfo() {
        txtId.setText("");
        txtTenViTri.setText("");
        cbLoaiSlot.setSelectedIndex(0);
        txtGiaTien.setText("");
        chkTrangThai.setSelected(false);
        txtGhiChu.setText("");
        tableSlot.clearSelection();
    }

    public int getChooseSelectSort() {
        if (rdoSortId.isSelected()) return 1;
        if (rdoSortViTri.isSelected()) return 2;
        if (rdoSortGiaTien.isSelected()) return 3;
        return 0;
    }

    public int getChooseSelectSearch() {
        if (rdoSearchViTri.isSelected()) return 1;
        if (rdoSearchLoaiSlot.isSelected()) return 2;
        return 0;
    }

    public String validateSearch() {
        return txtSearchInput.getText().trim();
    }

    public void cancelSearchParkingSlot() {
        txtSearchInput.setText("");
    }

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
    public void addSearchDialogListener(ActionListener l) { btnSearch.addActionListener(l); }
    public void addCancelSearchParkingSlotListener(ActionListener l) { btnCancelSearch.addActionListener(l); }

    public void addListParkingSlotSelectionListener(ListSelectionListener l) {
        tableSlot.getSelectionModel().addListSelectionListener(l);
    }
}