/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlybaixe.view;
import quanlybaixe.entity.ParkingSlot;
import com.raven.chart.Chart;
import com.raven.chart.ModelChart;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author PC
 */
public class ResidentView extends javax.swing.JFrame {

    /**
     * Creates new form ResidentView
     */
    private String [] columnNames = new String [] {
        "STT", "Số hộ khẩu", "Địa chỉ", "Vai trò", "Họ và tên", "Ngày sinh", "Liên hệ"};
    private SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
    FlowLayout flowLayout = new FlowLayout();
    public ResidentView() {
        initComponents();
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSearch.setEnabled(true);
        tableResident.setDefaultRenderer(Object.class, new ResidentView.MyRenderer());
    }
    
    private static Image getCircleImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage circleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = circleImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, width, height);
        graphics.setClip(circle);
        graphics.drawImage(image, 0, 0, null);
        graphics.setColor(Color.WHITE);
        graphics.setStroke(new BasicStroke(2));
        graphics.draw(circle);
        return circleImage;
    }
    
    private ImageIcon ImageIconSize(JLabel label, String filename)
    {
        Image image = new ImageIcon(filename).getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon=new ImageIcon(image);
        //jLabel14.setIcon(new ImageIcon(getCircleImage(imageIcon.getImage())));
        return imageIcon;
    }
    
    public class MyRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            TableColumnModel columnModel=table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(60);
            columnModel.getColumn(2).setPreferredWidth(250);
            columnModel.getColumn(3).setPreferredWidth(50);
            columnModel.getColumn(4).setPreferredWidth(130);
            columnModel.getColumn(5).setPreferredWidth(60);
            columnModel.getColumn(5).setPreferredWidth(50);
            //columnModel.getColumn(0).setPreferredWidth(5);
            JTableHeader header = table.getTableHeader();
            header.setBackground(new Color(0, 0, 139));
            header.setForeground(Color.WHITE);
            header.setFont(new java.awt.Font("Times New Roman", 0, 18));
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(new Color(191, 239, 255));
                } else {
                    c.setBackground(new Color(135, 206, 250));
                }
            } else {
                c.setBackground(new Color(193, 255, 193));
            }

            return c;
        }
    }
    
    public class MyRenderer2 extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            TableColumnModel columnModel=table.getColumnModel();
            //columnModel.getColumn(0).setPreferredWidth(5);
            JTableHeader header = table.getTableHeader();
            header.setBackground(new Color(0, 0, 139));
            header.setForeground(Color.WHITE);
            header.setFont(new java.awt.Font("Times New Roman", 0, 18));
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(new Color(191, 239, 255));
                } else {
                    c.setBackground(new Color(135, 206, 250));
                }
            } else {
                c.setBackground(new Color(193, 255, 193));
            }

            return c;
        }
    }
    
    public static String capitalizeWords(String str) {
        str = str.toLowerCase();
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                if (word.equals("tt") || word.equals("tp") || word.equals("tx")) {
                    sb.append(word.toUpperCase());
                } else {
                    sb.append(Character.toUpperCase(word.charAt(0)));
                    sb.append(word.substring(1));
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupSex = new javax.swing.ButtonGroup();
        btnGroupSort = new javax.swing.ButtonGroup();
        btnGroupSearch = new javax.swing.ButtonGroup();
        SearchDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        CheckBoxName = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        CheckBoxAddress = new javax.swing.JCheckBox();
        CheckBoxYear = new javax.swing.JCheckBox();
        CheckBoxIDFamily = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        FieldSearch = new javax.swing.JTextField();
        btnCancelDialog = new javax.swing.JButton();
        btnSearchDialog = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnCancelSearch = new javax.swing.JButton();
        FieldSumCMND = new javax.swing.JTextField();
        btnSort = new javax.swing.JButton();
        btnResidentUndo = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        FieldSumCCCD = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        FieldSumFamily = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        FieldSum = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        CheckBoxSortID = new javax.swing.JCheckBox();
        CheckBoxSortName = new javax.swing.JCheckBox();
        CheckBoxSortIDFamily = new javax.swing.JCheckBox();
        FieldSumDD = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResident = new javax.swing.JTable();
        FieldCMT = new javax.swing.JTextField();
        FieldName = new javax.swing.JTextField();
        ComboBoxRole = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        FieldIDFamily = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ComboBoxCMT = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextAreaAddress = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        CheckBoxFemale = new javax.swing.JCheckBox();
        CheckBoxMale = new javax.swing.JCheckBox();
        BirthdayChooser = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        FieldBirthPlace = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        FieldPhone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        FieldID = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        SearchDialog.setResizable(false);
        SearchDialog.setSize(new java.awt.Dimension(511, 390));

        jPanel3.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel18.setText("Tìm kiếm");
        jPanel3.add(jLabel18);
        jLabel18.setBounds(210, 40, 110, 29);

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setText("Chọn tiêu chí tìm kiếm:");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(40, 190, 290, 29);

        btnGroupSearch.add(CheckBoxName);
        CheckBoxName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxName.setText("Tên");
        CheckBoxName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxNameActionPerformed(evt);
            }
        });
        jPanel3.add(CheckBoxName);
        CheckBoxName.setBounds(170, 230, 85, 20);
        CheckBoxName.setOpaque(false);

        jLabel20.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/search.png"));
        jPanel3.add(jLabel20);
        jLabel20.setBounds(30, 130, 30, 30);

        btnGroupSearch.add(CheckBoxAddress);
        CheckBoxAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxAddress.setText("Địa chỉ");
        CheckBoxAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxAddressActionPerformed(evt);
            }
        });
        jPanel3.add(CheckBoxAddress);
        CheckBoxAddress.setBounds(390, 230, 100, 20);
        CheckBoxAddress.setOpaque(false);

        btnGroupSearch.add(CheckBoxYear);
        CheckBoxYear.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxYear.setText("Năm sinh");
        CheckBoxYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxYearActionPerformed(evt);
            }
        });
        jPanel3.add(CheckBoxYear);
        CheckBoxYear.setBounds(260, 230, 100, 20);
        CheckBoxYear.setOpaque(false);

        btnGroupSearch.add(CheckBoxIDFamily);
        CheckBoxIDFamily.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxIDFamily.setText("Số hộ khẩu");
        CheckBoxIDFamily.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(CheckBoxIDFamily);
        CheckBoxIDFamily.setBounds(20, 230, 120, 25);
        CheckBoxIDFamily.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setText("Nhập nội dung tìm kiếm:");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(40, 90, 290, 29);

        FieldSearch.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 102)));
        jPanel3.add(FieldSearch);
        FieldSearch.setBounds(60, 130, 400, 40);
        FieldSearch.setOpaque(false);

        btnCancelDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnCancelDialog.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelDialog.setText("Hủy");
        btnCancelDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnCancelDialog);
        btnCancelDialog.setBounds(290, 270, 150, 50);
        btnCancelDialog.setBorder(new RoundedBorder(20));
        btnCancelDialog.setOpaque(false);

        btnSearchDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnSearchDialog.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSearchDialog.setText("Tìm kiếm");
        btnSearchDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnSearchDialog);
        btnSearchDialog.setBounds(70, 270, 140, 50);
        btnSearchDialog.setBorder(new RoundedBorder(20));
        btnSearchDialog.setOpaque(false);

        jLabel22.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/viewSearchView.png"));
        jLabel22.setText("=");
        jPanel3.add(jLabel22);
        jLabel22.setBounds(-10, 0, 520, 360);

        javax.swing.GroupLayout SearchDialogLayout = new javax.swing.GroupLayout(SearchDialog.getContentPane());
        SearchDialog.getContentPane().setLayout(SearchDialogLayout);
        SearchDialogLayout.setHorizontalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        );
        SearchDialogLayout.setVerticalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý dân cư trong khu vực");
        setName("Quản lý dân cư trong khu vực"); // NOI18N
        setSize(new java.awt.Dimension(1207, 665));

        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel16.setText("Chọn tiêu chí sắp xếp:");

        btnCancelSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnCancelSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelSearch.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/cancel.png"));
        btnCancelSearch.setText("Hủy tìm kiếm");
        btnCancelSearch.setToolTipText("");
        btnCancelSearch.setBorder(null);
        btnCancelSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelSearchActionPerformed(evt);
            }
        });
        btnCancelSearch.setEnabled(false);

        FieldSumCMND.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FieldSumCMND.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldSumCMND.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSumCMND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumCMNDActionPerformed(evt);
            }
        });

        btnSort.setBackground(new java.awt.Color(0, 0, 102));
        btnSort.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSort.setForeground(new java.awt.Color(255, 255, 255));
        ImageIcon imageIcon2 = new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/sorting.png");
        Image image2 = imageIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnSort.setIcon(new ImageIcon(image2));
        btnSort.setText("Sắp xếp");
        btnSort.setToolTipText("");
        btnSort.setBorder(null);
        btnSort.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortActionPerformed(evt);
            }
        });

        btnResidentUndo.setBackground(new java.awt.Color(0, 0, 102));
        btnResidentUndo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnResidentUndo.setForeground(new java.awt.Color(255, 255, 255));
        btnResidentUndo.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/LogOut.png"));
        btnResidentUndo.setText("Quay lại");
        btnResidentUndo.setToolTipText("");
        btnResidentUndo.setBorder(null);
        btnResidentUndo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResidentUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResidentUndoActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/search.png"));
        btnSearch.setText("Tìm kiếm");
        btnSearch.setBorder(null);
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        FieldSumCCCD.setEditable(false);
        FieldSumCCCD.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FieldSumCCCD.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldSumCCCD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSumCCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumCCCDActionPerformed(evt);
            }
        });

        ImageIcon imageIcon = new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/logo.png");
        Image image = imageIcon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        imageIcon=new ImageIcon(image);
        jLabel14.setIcon(imageIcon);
        jLabel14.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Số người có CCCD:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel12.setText("Tổng số hộ dân:");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel13.setText("Số người có CMND:");

        FieldSumFamily.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FieldSumFamily.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldSumFamily.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSumFamily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumFamilyActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel17.setText("Tổng dân số:");

        FieldSum.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FieldSum.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel15.setText("Số người có định danh:");

        CheckBoxSortID.setBackground(new java.awt.Color(0, 102, 204, 175));
        btnGroupSort.add(CheckBoxSortID);
        CheckBoxSortID.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        CheckBoxSortID.setText("Sắp xếp theo STT");
        CheckBoxSortID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortID.setOpaque(false);

        btnGroupSort.add(CheckBoxSortName);
        CheckBoxSortName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        CheckBoxSortName.setText("Sắp xếp theo tên");
        CheckBoxSortName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortName.setOpaque(false);

        btnGroupSort.add(CheckBoxSortIDFamily);
        CheckBoxSortIDFamily.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        CheckBoxSortIDFamily.setText("Sắp xếp theo số hộ khẩu");
        CheckBoxSortIDFamily.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortIDFamily.setOpaque(false);

        FieldSumDD.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FieldSumDD.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldSumDD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSumDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumDDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(CheckBoxSortID, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(CheckBoxSortName, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(CheckBoxSortIDFamily, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldSumDD, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldSumCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldSumCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldSumFamily, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldSum, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnResidentUndo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(CheckBoxSortID)
                .addGap(3, 3, 3)
                .addComponent(CheckBoxSortName)
                .addGap(6, 6, 6)
                .addComponent(CheckBoxSortIDFamily)
                .addGap(6, 6, 6)
                .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(FieldSum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FieldSumFamily, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FieldSumCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FieldSumCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FieldSumDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnResidentUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        FieldSumCMND.setOpaque(false);
        FieldSumCMND.setEditable(false);
        FieldSumCCCD.setOpaque(false);
        FieldSumCCCD.setEditable(false);
        FieldSumFamily.setOpaque(false);
        FieldSumFamily.setEditable(false);
        FieldSum.setOpaque(false);
        FieldSum.setEditable(false);
        FieldSumDD.setOpaque(false);
        FieldSumDD.setEditable(false);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 230, 780);
        jPanel2.setOpaque(true);

        jScrollPane1.setBackground(new java.awt.Color(0, 51, 153, 125));

        tableResident.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tableResident.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            }, columnNames
        ));
        tableResident.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tableResident.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableResident.setRowHeight(30);
        jScrollPane1.setViewportView(tableResident);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(240, 420, 1030, 260);

        FieldCMT.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldCMT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldCMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCMTActionPerformed(evt);
            }
        });
        jPanel1.add(FieldCMT);
        FieldCMT.setBounds(970, 250, 260, 40);
        FieldCMT.setOpaque(false);

        FieldName.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldNameActionPerformed(evt);
            }
        });
        jPanel1.add(FieldName);
        FieldName.setBounds(390, 170, 250, 40);
        FieldName.setOpaque(false);

        ComboBoxRole.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        ComboBoxRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<none>", "Chủ hộ", "Vợ", "Chồng", "Con", "Bố", "Mẹ", "Người thân khác" }));
        jPanel1.add(ComboBoxRole);
        ComboBoxRole.setBounds(970, 100, 260, 40);
        ComboBoxRole.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Vai trò:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(900, 100, 70, 40);

        FieldIDFamily.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldIDFamily.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldIDFamily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldIDFamilyActionPerformed(evt);
            }
        });
        jPanel1.add(FieldIDFamily);
        FieldIDFamily.setBounds(390, 100, 250, 40);
        FieldIDFamily.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Địa chỉ:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(900, 170, 70, 40);

        ComboBoxCMT.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        ComboBoxCMT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<none>", "CCCD", "CMND", "Định danh" }));
        jPanel1.add(ComboBoxCMT);
        ComboBoxCMT.setBounds(830, 250, 130, 40);
        ComboBoxRole.setOpaque(false);

        TextAreaAddress.setBackground(new java.awt.Color(255, 255, 255, 0));
        TextAreaAddress.setColumns(20);
        TextAreaAddress.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        TextAreaAddress.setRows(5);
        TextAreaAddress.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        jScrollPane2.setViewportView(TextAreaAddress);
        TextAreaAddress.setOpaque(false);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(970, 160, 260, 60);
        jScrollPane2.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Nơi sinh:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(610, 310, 80, 40);

        btnGroupSex.add(CheckBoxFemale);
        CheckBoxFemale.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxFemale.setText("Nữ");
        CheckBoxFemale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxFemaleActionPerformed(evt);
            }
        });
        jPanel1.add(CheckBoxFemale);
        CheckBoxFemale.setBounds(490, 260, 85, 20);
        CheckBoxFemale.setOpaque(false);

        btnGroupSex.add(CheckBoxMale);
        CheckBoxMale.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxMale.setText("Nam");
        CheckBoxMale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxMaleActionPerformed(evt);
            }
        });
        jPanel1.add(CheckBoxMale);
        CheckBoxMale.setBounds(400, 260, 85, 20);
        CheckBoxMale.setOpaque(false);

        BirthdayChooser.setBackground(new java.awt.Color(0, 204, 255));
        BirthdayChooser.setForeground(new java.awt.Color(102, 255, 255));
        BirthdayChooser.setDateFormatString("dd/MM/yyyy");
        BirthdayChooser.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jPanel1.add(BirthdayChooser);
        BirthdayChooser.setBounds(390, 310, 160, 40);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Giới tính:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(310, 250, 80, 40);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Họ và tên:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(300, 170, 90, 40);

        FieldBirthPlace.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldBirthPlace.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldBirthPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldBirthPlaceActionPerformed(evt);
            }
        });
        jPanel1.add(FieldBirthPlace);
        FieldBirthPlace.setBounds(690, 310, 180, 40);
        FieldBirthPlace.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Ngày sinh:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(300, 310, 90, 40);

        FieldPhone.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldPhone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldPhoneActionPerformed(evt);
            }
        });
        jPanel1.add(FieldPhone);
        FieldPhone.setBounds(1030, 310, 200, 40);
        FieldPhone.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Số điện thoại:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(910, 310, 120, 40);

        FieldID.setEditable(false);
        FieldID.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        FieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FieldID.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 51, 102)));
        FieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldIDActionPerformed(evt);
            }
        });
        jPanel1.add(FieldID);
        FieldID.setBounds(390, 40, 70, 40);
        FieldID.setOpaque(false);
        FieldID.setVisible(false);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("<html>Quản lý dân cư huyện X<br> ");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(580, 10, 390, 80);

        btnAdd.setBackground(new java.awt.Color(0, 0, 102));
        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/add.png"));
        btnAdd.setText("Thêm");
        btnAdd.setBorder(null);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd);
        btnAdd.setBounds(330, 370, 170, 44);

        btnEdit.setBackground(new java.awt.Color(0, 0, 102));
        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Edit.png"));
        btnEdit.setText("Cập nhật");
        btnEdit.setBorder(null);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit);
        btnEdit.setBounds(580, 370, 170, 44);

        btnDelete.setBackground(new java.awt.Color(0, 0, 102));
        btnDelete.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/delete.png"));
        btnDelete.setText("Xóa");
        btnDelete.setBorder(null);
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);
        btnDelete.setBounds(830, 370, 170, 44);

        btnClear.setBackground(new java.awt.Color(0, 0, 102));
        btnClear.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/trash.png"));
        btnClear.setText("Làm mới");
        btnClear.setToolTipText("");
        btnClear.setBorder(null);
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel1.add(btnClear);
        btnClear.setBounds(1070, 370, 170, 44);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Số hộ khẩu:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(290, 100, 100, 40);

        jLabel9.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Lovepik_com-500330964-blue-blazed-background.jpg"));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel9);
        jLabel9.setBounds(-30, 0, 1640, 890);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1276, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortActionPerformed

    private void btnResidentUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResidentUndoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResidentUndoActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void FieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNameActionPerformed

    private void FieldCMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCMTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCMTActionPerformed

    private void FieldIDFamilyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldIDFamilyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDFamilyActionPerformed

    private void CheckBoxMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxMaleActionPerformed

    private void CheckBoxFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxFemaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxFemaleActionPerformed

    private void FieldBirthPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldBirthPlaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldBirthPlaceActionPerformed

    private void FieldPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldPhoneActionPerformed

    private void FieldSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumActionPerformed

    private void FieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDActionPerformed

    private void btnCancelSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelSearchActionPerformed

    private void FieldSumFamilyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumFamilyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumFamilyActionPerformed

    private void FieldSumCCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumCCCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumCCCDActionPerformed

    private void FieldSumCMNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumCMNDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumCMNDActionPerformed

    private void FieldSumDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumDDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumDDActionPerformed

    private void CheckBoxNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxNameActionPerformed

    private void CheckBoxYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxYearActionPerformed

    private void CheckBoxAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxAddressActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ResidentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResidentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResidentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResidentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResidentView().setVisible(true);
            }
        });
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public ParkingSlot getResidentInfo() {
        // validate residents
        if (!validateIDFamily() || !validateName() || !validateSex() || !validateBirthday() || !validateAddress() || !validateTypeCMT() || !validateCMT()) {
            return null;
        }
        try {
            ParkingSlot residents = new ParkingSlot();
            if (FieldID.getText() != null && !"".equals(FieldID.getText())) {
                residents.setId(Integer.parseInt(FieldID.getText()));
            }
            if (CheckBoxMale.isSelected()) residents.setSex("Nam"); else residents.setSex("Nữ");
            residents.setIDFamily(FieldIDFamily.getText().trim());
            residents.setRole(ComboBoxRole.getSelectedItem().toString().trim());
            residents.setName(capitalizeWords(FieldName.getText().trim()));
            //residents.setYear(Integer.parseInt(FieldYear.getText().trim()));
            residents.setAddress(capitalizeWords(TextAreaAddress.getText().trim()));
            residents.setBirthday(BirthdayChooser.getDate());
            residents.setTypeCMT(ComboBoxCMT.getSelectedItem().toString().trim());
            residents.setCMT(FieldCMT.getText().trim());
            residents.setBirthPlace(capitalizeWords(FieldBirthPlace.getText().trim()));
            residents.setPhoneNumber(FieldPhone.getText().trim());           
            return residents;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }
    
    public class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        public boolean isBorderOpaque() {
            return true;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
    
    private boolean validateIDFamily() {
        try {
            String idText = FieldIDFamily.getText().trim();
            if (idText.isEmpty() || !idText.matches("\\d+")) {
                showMessage("Số hộ khẩu không hợp lệ");
                return false;
            }
        } catch (Exception e) {
            FieldIDFamily.requestFocus();
            showMessage("Số hộ khẩu không được trống");
            return false;
        }
        return true;
    }
    
    private boolean validateCMT() {
        try {
            String idText = FieldCMT.getText().trim();
            if (idText.isEmpty() || !idText.matches("\\d+")) {
                showMessage("Số chứng minh thư không hợp lệ");
                return false;
            }
        } catch (Exception e) {
            FieldCMT.requestFocus();
            showMessage("Số chứng minh thư không được trống");
            return false;
        }
        return true;
    }
    
    private boolean validateSex() {
        if (!CheckBoxMale.isSelected() && !CheckBoxFemale.isSelected()) {
            showMessage("Bạn chưa chọn giới tính");
            return false;
        }
        return true;
    }
      
    private boolean validateName() {
        String name = FieldName.getText();
        if (name == null || "".equals(name.trim())) {
            FieldName.requestFocus();
            showMessage("Họ và tên không được trống.");
            return false;
        }
        return true;
    }
    
    private boolean validateTypeCMT() {
        String type = ComboBoxCMT.getSelectedItem().toString().trim();
        if (type.equals("<none>")) {
            ComboBoxCMT.requestFocus();
            showMessage("Bạn chưa chọn loại chứng minh thư");
            return false;
        }
        return true;
    }
    
    private boolean validateAddress() {
        String address = TextAreaAddress.getText();
        if (address == null || "".equals(address.trim())) {
            TextAreaAddress.requestFocus();
            showMessage("Địa chỉ không được trống.");
            return false;
        }
        return true;
    }
    
    private boolean validateBirthday() {
        try {
            java.util.Date today=new java.util.Date();
            Date date=BirthdayChooser.getDate();
            if (date.compareTo(today)==1) {
                BirthdayChooser.requestFocus();
                showMessage("Ngày nhập không tồn tại hoặc lớn hơn ngày hôm nay");
                return false;
            }
        } catch (Exception e) {
            BirthdayChooser.requestFocus();
            showMessage("Bạn đã nhập ngày sai định dạng");
            return false;
        }
        return true;
    }
    
    public void showListResidents(List<ParkingSlot> list) {
        int size = list.size();
        // với bảng tableResident có 6 cột, 
        // khởi tạo mảng 2 chiều residents, trong đó:
        // số hàng: là kích thước của list resident 
        // số cột: là 7
        Object [][] residents = new Object[size][7];
        for (int i = 0; i < size; i++) {
            residents[i][0] = list.get(i).getId();
            residents[i][1] = list.get(i).getIDFamily();
            residents[i][2] = list.get(i).getAddress();
            residents[i][3] = list.get(i).getRole();
            residents[i][4] = list.get(i).getName();
            residents[i][5] = fDate.format(list.get(i).getBirthday());
            residents[i][6] = list.get(i).getPhoneNumber();
        }
        //jLabel1.setLayout(null);
        tableResident.getColumnModel().getColumn(0).setWidth(3);
        tableResident.setModel(new DefaultTableModel(residents, columnNames));
        //tableResident.removeColumn(tableResident.getColumnModel().getColumn(6));
    }
    
    
    public void showResidents(ParkingSlot resident) 
    {
        FieldIDFamily.setText("" + resident.getIDFamily());
        FieldName.setText(resident.getName());
        BirthdayChooser.setDate(resident.getBirthday());
        TextAreaAddress.setText(resident.getAddress());
        //FieldOpeningDate.setText("" + fDate.format(resident.getOpeningDate()));
        ComboBoxRole.setSelectedItem(""+resident.getRole());
        FieldPhone.setText(""+resident.getPhoneNumber());
        FieldBirthPlace.setText(""+resident.getBirthPlace());
        if ("Nam".equals(resident.getSex())) {
            CheckBoxMale.setSelected(true);
            CheckBoxFemale.setSelected(false);
        } else if ("Nữ".equals(resident.getSex())) {
            CheckBoxMale.setSelected(false);
            CheckBoxFemale.setSelected(true);
        } else {
            CheckBoxMale.setSelected(false);
            CheckBoxFemale.setSelected(false);
        }
        ComboBoxCMT.setSelectedItem(""+resident.getTypeCMT());
        FieldCMT.setText(resident.getCMT());
        // enable Edit and Delete buttons
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        // disable Add button
        btnAdd.setEnabled(false);
        btnClear.setEnabled(true);
    }
    
    public void fillResidentFromSelectedRow(List<ParkingSlot> list) throws ParseException {
        // lấy chỉ số của hàng được chọn 
        int row = tableResident.getSelectedRow();
        if (row >= 0) {
            int residentID = Integer.parseInt(tableResident.getModel().getValueAt(row, 0).toString());
            ParkingSlot selectedResident = findResidentByID(list, residentID);

            if (selectedResident != null) {
                FieldID.setText(String.valueOf(selectedResident.getId()));
                FieldIDFamily.setText(selectedResident.getIDFamily());
                FieldName.setText(selectedResident.getName());
                BirthdayChooser.setDate(selectedResident.getBirthday());
                TextAreaAddress.setText(selectedResident.getAddress());
                //FieldOpeningDate.setText("" + fDate.format(resident.getOpeningDate()));
                ComboBoxRole.setSelectedItem(selectedResident.getRole());
                FieldPhone.setText(selectedResident.getPhoneNumber());
                FieldBirthPlace.setText(selectedResident.getBirthPlace());
                if ("Nam".equals(selectedResident.getSex())) {
                    CheckBoxMale.setSelected(true);
                    CheckBoxFemale.setSelected(false);
                } else if ("Nữ".equals(selectedResident.getSex())) {
                    CheckBoxMale.setSelected(false);
                    CheckBoxFemale.setSelected(true);
                } else {
                    CheckBoxMale.setSelected(false);
                    CheckBoxFemale.setSelected(false);
                }
                ComboBoxCMT.setSelectedItem(selectedResident.getTypeCMT());
                FieldCMT.setText(selectedResident.getCMT());
                // enable Edit and Delete buttons
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
                // disable Add button
                btnAdd.setEnabled(false);
                btnClear.setEnabled(true);
            }
        }
    }
    
    public void fillResidentFromSelectedRow() throws ParseException {
        // lấy chỉ số của hàng được chọn 
        int row = tableResident.getSelectedRow();
        if (row >= 0) {
            FieldIDFamily.setText(tableResident.getModel().getValueAt(row, 1).toString());
            TextAreaAddress.setText(tableResident.getModel().getValueAt(row, 2).toString());
            ComboBoxRole.setSelectedItem(tableResident.getModel().getValueAt(row, 3).toString());
            FieldName.setText(tableResident.getModel().getValueAt(row, 4).toString());
            BirthdayChooser.setDate(fDate.parse(tableResident.getModel().getValueAt(row, 5).toString()));
            FieldPhone.setText(tableResident.getModel().getValueAt(row, 6).toString());
            
            // enable Edit and Delete buttons
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            // disable Add button
            btnAdd.setEnabled(false);
            btnClear.setEnabled(true);
        }
    }

    private ParkingSlot findResidentByID(List<ParkingSlot> residentsList, int residentID) {
        for (ParkingSlot resident : residentsList) {
            if (resident.getId() == residentID) {
                return resident;
            }
        }
        return null; // Trả về null nếu không tìm thấy đối tượng
    }
    
    public void clearResidentInfo() {
        FieldID.setText("");
        FieldIDFamily.setText("");
        ComboBoxRole.setSelectedItem("<none>");
        FieldName.setText("");
        BirthdayChooser.setDate(null);
        TextAreaAddress.setText("");
        //FieldOpeningDate.setText("");
        CheckBoxMale.setSelected(false);
        CheckBoxFemale.setSelected(false);
        ComboBoxCMT.setSelectedItem("<none>");
        FieldCMT.setText("");
        FieldBirthPlace.setText("");
        FieldPhone.setText("");
        
        // disable Edit and Delete buttons
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        // enable Add button
        btnAdd.setEnabled(true);
    }
    
    public void showCountListResidents(List<ParkingSlot> list) {
        int size = list.size();
        FieldSum.setText(String.valueOf(size));
    }
    
    public void SearchResidentInfo() {
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(true);
    }
    
    public int getChooseSelectSort(){
        if(CheckBoxSortID.isSelected()) return 1;
        else if(CheckBoxSortName.isSelected()) return 2;
        else if(CheckBoxSortIDFamily.isSelected()) return 3;
        return 0;
    }
    
    public void showStatisticTypeCMT(List<ParkingSlot> list) {
        Map<String, Integer> countMap = new HashMap<>();
        for (ParkingSlot person : list) {
            if (countMap.containsKey(person.getTypeCMT())) {
                int count = countMap.get(person.getTypeCMT());
                countMap.put(person.getTypeCMT(), count + 1);
            } else {
                countMap.put(person.getTypeCMT(), 1);
            }
        }

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            String typeCMT = entry.getKey();
            int count = entry.getValue();

            // Tìm trường tương ứng với loại CMT và gán giá trị
            switch (typeCMT) {
                case "CMND":
                    FieldSumCMND.setText(String.valueOf(count));
                    break;
                case "CCCD":
                    FieldSumCCCD.setText(String.valueOf(count));
                    break;
                case "Định danh":
                    FieldSumDD.setText(String.valueOf(count));
                    break;

            }
        }
    }
    
    public void showStatisticIDFamily(List<ParkingSlot> list) {
        Map<String, Integer> countMapIDFamily = new HashMap<>();

        for (ParkingSlot person : list) {
            // Thống kê số hộ khẩu IDFamily
            if (countMapIDFamily.containsKey(person.getIDFamily())) {
                int countIDFamily = countMapIDFamily.get(person.getIDFamily());
                countMapIDFamily.put(person.getIDFamily(), countIDFamily + 1);
            } else {
                countMapIDFamily.put(person.getIDFamily(), 1);
            }
        }

        // Gán giá trị thống kê số hộ khẩu IDFamily
        FieldSumFamily.setText(String.valueOf(countMapIDFamily.size()));
    }
    
    
    public void searchResidentInfo() {
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(true);
    }
    
    public void cancelDialogSearchResidentInfo() {
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(false);
    }
    
    public int getChooseSelectSearch(){
        if(CheckBoxIDFamily.isSelected()) return 1;
        else if(CheckBoxName.isSelected()) return 2;
        else if(CheckBoxYear.isSelected()) return 3;
        else if(CheckBoxAddress.isSelected()) return 4;
        return 0;
    }
    
    public void cancelSearchResident()
    {
        String id=FieldID.getText();
        btnCancelSearch.setEnabled(false);
        btnSearch.setEnabled(true);
        btnClear.setEnabled(true);
        if (id == null || "".equals(id.trim()))
        {
            
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        }
        else
        {
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }
    
    public String validateSearch(){
        String search = FieldSearch.getText();
        if (search == null || "".equals(search.trim())) {
            FieldSearch.requestFocus();
            showMessage("Nội dung tìm kiếm không hợp lệ!");
            return "";
        }
        btnCancelSearch.setEnabled(true);
        SearchDialog.setVisible(false);
        btnAdd.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnClear.setEnabled(false);
        btnSearch.setEnabled(false);
        return search;
    }
    
    public void addUndoListener(ActionListener listener){
        btnResidentUndo.addActionListener(listener);
    }
    
    public void addAddResidentListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }
    
    public void addListResidentSelectionListener(ListSelectionListener listener) {
        tableResident.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addEditResidentListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }
    
    public void addClearListener(ActionListener listener) {
        btnClear.addActionListener(listener);
    }
    
    public void addDeleteSpecialPersonListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }
    
    public void addSortSpecialPersonListener(ActionListener listener) {
        btnSort.addActionListener(listener);
    }
    
    public void addSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }
    
    public void addSearchDialogListener(ActionListener listener) {
        btnSearchDialog.addActionListener(listener);
    }
    
    public void addCancelSearchResidentListener(ActionListener listener){
        btnCancelSearch.addActionListener(listener);
    }
    
    public void addCancelDialogListener(ActionListener listener){
        btnCancelDialog.addActionListener(listener);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser BirthdayChooser;
    private javax.swing.JCheckBox CheckBoxAddress;
    private javax.swing.JCheckBox CheckBoxFemale;
    private javax.swing.JCheckBox CheckBoxIDFamily;
    private javax.swing.JCheckBox CheckBoxMale;
    private javax.swing.JCheckBox CheckBoxName;
    private javax.swing.JCheckBox CheckBoxSortID;
    private javax.swing.JCheckBox CheckBoxSortIDFamily;
    private javax.swing.JCheckBox CheckBoxSortName;
    private javax.swing.JCheckBox CheckBoxYear;
    private javax.swing.JComboBox<String> ComboBoxCMT;
    private javax.swing.JComboBox<String> ComboBoxRole;
    private javax.swing.JTextField FieldBirthPlace;
    private javax.swing.JTextField FieldCMT;
    private javax.swing.JTextField FieldID;
    private javax.swing.JTextField FieldIDFamily;
    private javax.swing.JTextField FieldName;
    private javax.swing.JTextField FieldPhone;
    private javax.swing.JTextField FieldSearch;
    private javax.swing.JTextField FieldSum;
    private javax.swing.JTextField FieldSumCCCD;
    private javax.swing.JTextField FieldSumCMND;
    private javax.swing.JTextField FieldSumDD;
    private javax.swing.JTextField FieldSumFamily;
    private javax.swing.JDialog SearchDialog;
    private javax.swing.JTextArea TextAreaAddress;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelDialog;
    private javax.swing.JButton btnCancelSearch;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup btnGroupSearch;
    private javax.swing.ButtonGroup btnGroupSex;
    private javax.swing.ButtonGroup btnGroupSort;
    private javax.swing.JButton btnResidentUndo;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchDialog;
    private javax.swing.JButton btnSort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableResident;
    // End of variables declaration//GEN-END:variables
}
