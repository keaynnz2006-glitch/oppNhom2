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

    private JTextField txtSearchGeneral;
    private JComboBox<String> cbFilterTrangThai;

    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnSort, btnSearch, btnCancelSearch, btnUndo;

    private JTable tableSlot;
    private DefaultTableModel tableModel;

    public ParkingSlotView() {
        setTitle("Quản Lý Sơ Đồ / Vị Trí Đỗ Xe");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(33, 150, 243));
        panelTitle.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ VỊ TRÍ ĐỖ XE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle);
        add(panelTitle, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin vị trí đỗ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15);
        txtId.setEditable(false);
        txtTenViTri = new JTextField(15);

        String[] loaiSlotOptions = {"Xe máy", "Ô tô"};
        cbLoaiSlot = new JComboBox<>(loaiSlotOptions);
        cbLoaiSlot.addActionListener(e -> autoFillPrice());

        txtGiaTien = new JTextField(15);
        chkTrangThai = new JCheckBox("Đã có xe đỗ");
        txtGhiChu = new JTextField(15);

        addFormField(panelForm, gbc, 0, "ID Slot:", txtId);
        addFormField(panelForm, gbc, 1, "Tên vị trí (Ví dụ: A01):", txtTenViTri);
        addFormField(panelForm, gbc, 2, "Loại Slot:", cbLoaiSlot);
        addFormField(panelForm, gbc, 3, "Giá tiền đỗ (VNĐ):", txtGiaTien);
        addFormField(panelForm, gbc, 4, "Trạng thái:", chkTrangThai);
        addFormField(panelForm, gbc, 5, "Ghi chú:", txtGhiChu);

        autoFillPrice();

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

        JPanel panelRight = new JPanel(new BorderLayout(5, 5));
        panelRight.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        JPanel panelTools = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTools.setBorder(BorderFactory.createTitledBorder("Công cụ Tìm kiếm, Lọc & Sắp xếp"));

        JPanel panelSortFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        rdoSortId = new JRadioButton("ID");
        rdoSortViTri = new JRadioButton("Tên vị trí");
        rdoSortGiaTien = new JRadioButton("Giá tiền");
        bgSort = new ButtonGroup();
        bgSort.add(rdoSortId); bgSort.add(rdoSortViTri); bgSort.add(rdoSortGiaTien);
        rdoSortId.setSelected(true);
        btnSort = new JButton("Sắp xếp");

        panelSortFilter.add(new JLabel("Sắp xếp:"));
        panelSortFilter.add(rdoSortId); 
        panelSortFilter.add(rdoSortViTri); 
        panelSortFilter.add(rdoSortGiaTien);
        panelSortFilter.add(btnSort);

        panelSortFilter.add(new JSeparator(JSeparator.VERTICAL));
        panelSortFilter.add(new JLabel("Lọc trạng thái:"));
        cbFilterTrangThai = new JComboBox<>(new String[]{"Tất cả", " Còn trống", " Đã đỗ"});
        panelSortFilter.add(cbFilterTrangThai);

        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        txtSearchGeneral = new JTextField(20);

        btnSearch = new JButton("Tìm kiếm");
        btnCancelSearch = new JButton("Hủy tìm");

        panelSearch.add(new JLabel("Từ khóa tìm kiếm:"));
        panelSearch.add(txtSearchGeneral);
        panelSearch.add(btnSearch);
        panelSearch.add(btnCancelSearch);

        panelTools.add(panelSortFilter);
        panelTools.add(panelSearch);
        panelRight.add(panelTools, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Tên Vị Trí", "Loại Slot", "Giá Tiền (VNĐ)", "Trạng Thái", "Ghi Chú"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSlot = new JTable(tableModel);
        tableSlot.setRowHeight(28);
        tableSlot.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableSlot.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
        tableSlot.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); 

        tableSlot.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 12));
                
                if (value != null) {
                    String status = value.toString();
                    if ("Đã đỗ".equals(status)) {
                        setForeground(new Color(211, 47, 47));
                    } else if ("Còn trống".equals(status)) {
                        setForeground(new Color(46, 125, 50));
                    } else {
                        setForeground(Color.BLACK);
                    }
                }
                
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                } else {
                    setBackground(table.getBackground());
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableSlot);
        panelRight.add(scrollPane, BorderLayout.CENTER);

        lblTotalCount = new JLabel("Tổng số vị trí: 0 | Đã đỗ: 0 |  Còn trống: 0");
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

    private void autoFillPrice() {
        if (txtGiaTien.getText().trim().isEmpty() || txtGiaTien.getText().equals("5000") || txtGiaTien.getText().equals("10000")) {
            String selectedType = (String) cbLoaiSlot.getSelectedItem();
            if ("Xe máy".equalsIgnoreCase(selectedType)) {
                txtGiaTien.setText("5000");
            } else if ("Ô tô".equalsIgnoreCase(selectedType)) {
                txtGiaTien.setText("10000");
            }
        }
    }

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
        int total = list != null ? list.size() : 0;
        long occupied = list != null ? list.stream().filter(ParkingSlot::isTrangThai).count() : 0;
        long available = total - occupied;

        lblTotalCount.setText(String.format("Tổng số vị trí: %d  |   Đã đỗ: %d  |  Còn trống: %d", total, occupied, available));
    }

    public void clearParkingSlotInfo() {
        txtId.setText("");
        txtTenViTri.setText("");
        cbLoaiSlot.setSelectedIndex(0);
        autoFillPrice();
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

    public String validateSearch() {
        return txtSearchGeneral.getText().trim();
    }

    public void cancelSearchParkingSlot() {
        txtSearchGeneral.setText("");
        cbFilterTrangThai.setSelectedIndex(0);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void addUndoListener(ActionListener l) { btnUndo.addActionListener(l); }
    public void addAddParkingSlotListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditParkingSlotListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteParkingSlotListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addClearListener(ActionListener l) { btnClear.addActionListener(l); }
    public void addSortParkingSlotListener(ActionListener l) { btnSort.addActionListener(l); }
    public void addSearchDialogListener(ActionListener l) { btnSearch.addActionListener(l); }
    public void addCancelSearchParkingSlotListener(ActionListener l) { btnCancelSearch.addActionListener(l); }

    public void addFilterTrangThaiListener(ActionListener l) { cbFilterTrangThai.addActionListener(l); }

    public void addListParkingSlotSelectionListener(ListSelectionListener l) {
        tableSlot.getSelectionModel().addListSelectionListener(l);
    }
}