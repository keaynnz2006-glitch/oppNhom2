/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlybaixe.view;
import quanlybaixe.entity.VehicleDetail;
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
import java.util.Calendar;
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
public class ManagerView extends javax.swing.JFrame {

    /**
     * Creates new form ManagerView
     */
    private SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
    private String filename=null;
    private byte[] specialPerson_image=null;
    private byte[] image=null;
    private String [] columnNames = new String [] {
        "STT", "Họ và tên", "Năm sinh", "Quê quán", "Ngày mở hồ sơ", "Loại đối tượng", "Ảnh"};
    private String [] columnNames2 = new String [] {
        "<none>","Số lượng"};
    private Object data = new Object [][] {};
    Chart chart=new Chart();
    //private int countSpecialPerson;
    //private String search;
    //private int searchID;
    //private CircleLabel circlePanel = new CirclePanel(100);
    public ManagerView() {
        initComponents();
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSearch.setEnabled(true);
        lblImage.setIcon(new ImageIcon("default-image.png"));
        tableSpecialPerson.setDefaultRenderer(Object.class, new MyRenderer());
        tableStatistic.setDefaultRenderer(Object.class, new MyRenderer2());
        //jLabel14.setLablFor(new CircleLabel());      
        OpeningDateChooser.setBackground(new Color(0, 204, 255));
        chart1.addLegend("Số lượng", new Color(0, 178, 238));
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

    private String abbreviation(String name) {
        return name;
    }
    
    public class MyRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            TableColumnModel columnModel=table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(150);
            columnModel.getColumn(2).setPreferredWidth(10);
            columnModel.getColumn(3).setPreferredWidth(270);
            columnModel.getColumn(4).setPreferredWidth(50);
            columnModel.getColumn(5).setPreferredWidth(80);
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

        SearchDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        CheckBoxName = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        CheckBoxAddress = new javax.swing.JCheckBox();
        CheckBoxYear = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        FieldSearch = new javax.swing.JTextField();
        btnCancelDialog1 = new javax.swing.JButton();
        btnSearchDialog = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        btnGroupSearch = new javax.swing.ButtonGroup();
        StatisticView = new javax.swing.JFrame();
        panelChart = new javax.swing.JPanel();
        ScrollPaneStatistic = new javax.swing.JScrollPane();
        tableStatistic = new javax.swing.JTable();
        lblTable = new javax.swing.JLabel();
        chart1 = new com.raven.chart.Chart();
        lblChart = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnStatisticAge = new javax.swing.JButton();
        btnStatisticUnder = new javax.swing.JButton();
        btnStatisticType = new javax.swing.JButton();
        timingTargetAdapter1 = new org.jdesktop.animation.timing.TimingTargetAdapter();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnCancelSearch = new javax.swing.JButton();
        btnManagerUndo = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnSortByOpeningDate = new javax.swing.JButton();
        btnSortByName = new javax.swing.JButton();
        btnStatistic = new javax.swing.JButton();
        btnSortByYear = new javax.swing.JButton();
        btnSortByID = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        ComboBoxType = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        btnImage = new javax.swing.JButton();
        BirthdayChooser = new com.toedter.calendar.JDateChooser();
        OpeningDateChooser = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextAreaAddress = new javax.swing.JTextArea();
        FieldSum = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        FieldName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSpecialPerson = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        FieldID = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        SearchDialog.setResizable(false);
        SearchDialog.setSize(new java.awt.Dimension(450, 370));

        jPanel3.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel10.setText("Tìm kiếm");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(170, 40, 110, 29);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("Chọn tiêu chí tìm kiếm:");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(40, 190, 290, 29);

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
        CheckBoxName.setBounds(20, 230, 85, 20);
        CheckBoxName.setOpaque(false);

        jLabel11.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/search.png"));
        jPanel3.add(jLabel11);
        jLabel11.setBounds(30, 130, 30, 30);

        btnGroupSearch.add(CheckBoxAddress);
        CheckBoxAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxAddress.setText("Quê quán");
        CheckBoxAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(CheckBoxAddress);
        CheckBoxAddress.setBounds(160, 230, 100, 20);
        CheckBoxAddress.setOpaque(false);

        btnGroupSearch.add(CheckBoxYear);
        CheckBoxYear.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxYear.setText("Năm sinh");
        CheckBoxYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(CheckBoxYear);
        CheckBoxYear.setBounds(320, 230, 100, 25);
        CheckBoxYear.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Nhập nội dung tìm kiếm:");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(40, 90, 290, 29);

        FieldSearch.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 102)));
        jPanel3.add(FieldSearch);
        FieldSearch.setBounds(60, 130, 340, 40);
        FieldSearch.setOpaque(false);

        btnCancelDialog1.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnCancelDialog1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelDialog1.setText("Hủy");
        btnCancelDialog1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnCancelDialog1);
        btnCancelDialog1.setBounds(240, 270, 140, 50);
        btnCancelDialog1.setBorder(new RoundedBorder(20));
        btnCancelDialog1.setOpaque(false);

        btnSearchDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnSearchDialog.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSearchDialog.setText("Tìm kiếm");
        btnSearchDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnSearchDialog);
        btnSearchDialog.setBounds(50, 270, 140, 50);
        btnSearchDialog.setBorder(new RoundedBorder(20));
        btnSearchDialog.setOpaque(false);

        jLabel15.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/viewSearchView.png"));
        jLabel15.setText("=");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(0, 0, 450, 360);

        javax.swing.GroupLayout SearchDialogLayout = new javax.swing.GroupLayout(SearchDialog.getContentPane());
        SearchDialog.getContentPane().setLayout(SearchDialogLayout);
        SearchDialogLayout.setHorizontalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );
        SearchDialogLayout.setVerticalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );

        StatisticView.setTitle("Thống kê");
        StatisticView.setResizable(false);
        StatisticView.setSize(new java.awt.Dimension(1250, 600));
        StatisticView.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                StatisticViewWindowOpened(evt);
            }
        });

        panelChart.setBackground(new java.awt.Color(102, 204, 255));

        tableStatistic.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tableStatistic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            columnNames2
        ));
        tableStatistic.setRowHeight(30);
        ScrollPaneStatistic.setViewportView(tableStatistic);

        lblTable.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTable.setText("Thống kê số lượng theo mục");

        lblChart.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblChart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChart.setText("Biểu đồ");

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));

        btnStatisticAge.setBackground(new java.awt.Color(0, 0, 102));
        btnStatisticAge.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnStatisticAge.setForeground(new java.awt.Color(255, 255, 255));
        btnStatisticAge.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Calendar.png"));
        btnStatisticAge.setText("Tuổi");
        btnStatisticAge.setBorder(null);
        btnStatisticAge.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatisticAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticAgeActionPerformed(evt);
            }
        });

        btnStatisticUnder.setBackground(new java.awt.Color(0, 0, 102));
        btnStatisticUnder.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnStatisticUnder.setForeground(new java.awt.Color(255, 255, 255));
        btnStatisticUnder.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Undo.png"));
        btnStatisticUnder.setText("Quay lại");
        btnStatisticUnder.setBorder(null);
        btnStatisticUnder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatisticUnder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticUnderActionPerformed(evt);
            }
        });

        btnStatisticType.setBackground(new java.awt.Color(0, 0, 102));
        btnStatisticType.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnStatisticType.setForeground(new java.awt.Color(255, 255, 255));
        btnStatisticType.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/People.png"));
        btnStatisticType.setText("Loại đối tượng");
        btnStatisticType.setBorder(null);
        btnStatisticType.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatisticType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnStatisticAge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStatisticUnder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStatisticType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(btnStatisticAge, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnStatisticType, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStatisticUnder, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTable, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addComponent(ScrollPaneStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chart1, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(lblChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 32, Short.MAX_VALUE))
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTable, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChart, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPaneStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout StatisticViewLayout = new javax.swing.GroupLayout(StatisticView.getContentPane());
        StatisticView.getContentPane().setLayout(StatisticViewLayout);
        StatisticViewLayout.setHorizontalGroup(
            StatisticViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelChart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        StatisticViewLayout.setVerticalGroup(
            StatisticViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý các đối tượng đặc biệt trong khu vực");
        setName("ManagerFrame"); // NOI18N
        setResizable(false);

        //jPanel1.setLayout(new AbsoluteLayout());
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(0, 102, 204, 175));

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

        btnCancelSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnCancelSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelSearch.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/cancel.png"));
        btnCancelSearch.setText("Hủy tìm kiếm");
        btnCancelSearch.setEnabled(false);
        btnCancelSearch.setToolTipText("");
        btnCancelSearch.setBorder(null);
        btnCancelSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelSearchActionPerformed(evt);
            }
        });

        btnManagerUndo.setBackground(new java.awt.Color(0, 0, 102));
        btnManagerUndo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnManagerUndo.setForeground(new java.awt.Color(255, 255, 255));
        btnManagerUndo.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/LogOut.png"));
        btnManagerUndo.setText("Quay lại");
        btnManagerUndo.setToolTipText("");
        btnManagerUndo.setBorder(null);
        btnManagerUndo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManagerUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerUndoActionPerformed(evt);
            }
        });

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

        btnEdit.setBackground(new java.awt.Color(0, 0, 102));
        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Edit.png"));
        btnEdit.setText("Sửa");
        btnEdit.setBorder(null);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        ImageIcon imageIcon = new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/logo.png");
        Image image = imageIcon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        imageIcon=new ImageIcon(image);
        jLabel14.setIcon(imageIcon);
        jLabel4.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnManagerUndo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCancelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btnManagerUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 170, 660);
        //jPanel2.setOpaque(false);

        btnSortByOpeningDate.setBackground(new java.awt.Color(51, 204, 255));
        btnSortByOpeningDate.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByOpeningDate.setText("Sắp xếp theo ngày mở hồ sơ");
        btnSortByOpeningDate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByOpeningDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByOpeningDateActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByOpeningDate);
        btnSortByOpeningDate.setBounds(780, 330, 210, 40);

        btnSortByName.setBackground(new java.awt.Color(51, 204, 255));
        btnSortByName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByName.setText("Sắp xếp theo tên");
        btnSortByName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByNameActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByName);
        btnSortByName.setBounds(400, 330, 150, 40);

        btnStatistic.setBackground(new java.awt.Color(51, 204, 255));
        btnStatistic.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnStatistic.setText("Thống kê");
        btnStatistic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });
        jPanel1.add(btnStatistic);
        btnStatistic.setBounds(1010, 330, 150, 40);
        //btnSortByID.setOpaque(false);

        btnSortByYear.setBackground(new java.awt.Color(51, 204, 255));
        btnSortByYear.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByYear.setText("Sắp xếp theo năm sinh");
        btnSortByYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByYearActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByYear);
        btnSortByYear.setBounds(580, 330, 180, 40);

        btnSortByID.setBackground(new java.awt.Color(51, 204, 255));
        btnSortByID.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByID.setText("Sắp xếp theo ID");
        btnSortByID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByIDActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByID);
        btnSortByID.setBounds(230, 330, 150, 40);
        //btnSortByID.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Ảnh:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(870, 60, 50, 42);

        ComboBoxType.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        ComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<none>", "Nghiện hút", "Xâm phạm ANQG", "Có tiền án", "Có tiền sự", "Thường xuyên đánh bạc", "Nhân thân đặc biệt", "Hay tụ tập khiếu kiện" }));
        jPanel1.add(ComboBoxType);
        ComboBoxType.setBounds(930, 270, 260, 45);
        ComboBoxType.setOpaque(false);

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setText("Tổng số đối tượng:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(420, 50, 160, 21);

        btnImage.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnImage.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnImage.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/green pin.png"));
        btnImage.setText("<html>Thêm ảnh<br> ");
        btnImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImageActionPerformed(evt);
            }
        });
        jPanel1.add(btnImage);
        btnImage.setBounds(1093, 60, 100, 50);
        btnImage.setOpaque(false);
        btnImage.setBorder(new RoundedBorder(20));

        BirthdayChooser.setBackground(new java.awt.Color(0, 204, 255));
        BirthdayChooser.setForeground(new java.awt.Color(102, 255, 255));
        BirthdayChooser.setDateFormatString("dd/MM/yyyy");
        BirthdayChooser.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jPanel1.add(BirthdayChooser);
        BirthdayChooser.setBounds(300, 180, 220, 40);

        OpeningDateChooser.setBackground(new java.awt.Color(0, 204, 255));
        OpeningDateChooser.setForeground(new java.awt.Color(102, 255, 255));
        OpeningDateChooser.setDateFormatString("dd/MM/yyyy");
        OpeningDateChooser.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jPanel1.add(OpeningDateChooser);
        OpeningDateChooser.setBounds(450, 270, 250, 40);

        TextAreaAddress.setBackground(new java.awt.Color(255, 255, 255, 0));
        TextAreaAddress.setColumns(20);
        TextAreaAddress.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        TextAreaAddress.setRows(5);
        TextAreaAddress.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        jScrollPane2.setViewportView(TextAreaAddress);
        TextAreaAddress.setOpaque(false);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(930, 180, 260, 70);
        jScrollPane2.setOpaque(false);

        FieldSum.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        FieldSum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FieldSum.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 51, 102)));
        FieldSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumActionPerformed(evt);
            }
        });
        jPanel1.add(FieldSum);
        FieldSum.setBounds(580, 40, 70, 40);
        FieldSum.setOpaque(false);
        FieldSum.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Ngày mở hồ sơ: (dd/MM/yyyy)");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(200, 270, 250, 42);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Loại đối tượng:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(790, 270, 130, 37);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Họ và tên:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(200, 114, 90, 30);

        lblImage.setBackground(new java.awt.Color(153, 153, 153));
        lblImage.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 51, 153)));
        jPanel1.add(lblImage);
        lblImage.setBounds(930, 10, 153, 153);

        FieldName.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldNameActionPerformed(evt);
            }
        });
        jPanel1.add(FieldName);
        FieldName.setBounds(300, 110, 220, 40);
        FieldName.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("ID:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(200, 50, 60, 21);

        jScrollPane1.setBackground(new java.awt.Color(0, 51, 153, 125));

        tableSpecialPerson.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tableSpecialPerson.setModel(new javax.swing.table.DefaultTableModel(
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
        tableSpecialPerson.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tableSpecialPerson.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableSpecialPerson.setRowHeight(30);
        jScrollPane1.setViewportView(tableSpecialPerson);
        tableSpecialPerson.removeColumn(tableSpecialPerson.getColumnModel().getColumn(6));

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(180, 380, 1020, 270);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Quê quán:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(830, 190, 90, 42);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Ngày sinh:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(200, 180, 90, 42);

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
        FieldID.setBounds(260, 38, 70, 40);
        FieldID.setOpaque(false);

        jLabel9.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Lovepik_com-500330964-blue-blazed-background.jpg"));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel9);
        jLabel9.setBounds(-90, 0, 1640, 890);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1207, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckBoxNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxNameActionPerformed

    private void btnSortByIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByIDActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSortByNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByNameActionPerformed

    private void btnCancelSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelSearchActionPerformed

    private void FieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNameActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void FieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSortByOpeningDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByOpeningDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByOpeningDateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSortByYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByYearActionPerformed

    private void btnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImageActionPerformed

    private void FieldSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumActionPerformed

    private void btnManagerUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerUndoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnManagerUndoActionPerformed

    private void btnStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticActionPerformed

    private void StatisticViewWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_StatisticViewWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_StatisticViewWindowOpened

    private void btnStatisticTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticTypeActionPerformed

    private void btnStatisticUnderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticUnderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticUnderActionPerformed

    private void btnStatisticAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticAgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticAgeActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerView().setVisible(true);
            }
        });
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
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
    
    public void SpecialPersonImage()
    {
        String lastImagePath = "";
        JFileChooser chooser=new JFileChooser(lastImagePath);
        chooser.setDialogTitle("Chọn ảnh");
        // Giới hạn chọn tệp đến các tệp hình ảnh
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg")
                    || f.getName().toLowerCase().endsWith(".png")
                    || f.getName().toLowerCase().endsWith(".gif")
                    || f.isDirectory();
            }
            public String getDescription() {
                return "Hình ảnh (*.jpg, *.png, *.gif)";
            }
        });
        chooser.showOpenDialog(null);
        File f=chooser.getSelectedFile();
        filename=f.getAbsolutePath();
        lastImagePath = f.getPath();
        ImageIcon imageIcon=new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
        lblImage.setIcon(imageIcon);
        try
        {
            File image=new File(filename);
            FileInputStream fis=new FileInputStream(image);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            byte[] buf=new byte[1024];
            for (int readNum;(readNum=fis.read(buf))!=-1;)
            {
                bos.write(buf, 0, readNum);
            }
            specialPerson_image=bos.toByteArray();
        }
        catch (Exception e)
        {
            showMessage(e.toString());
        }
    }
    
    /**
     * hiển thị list specialPerson vào bảng tableSpecialPerson
     * 
     * @param list
     */
    public void showListSpecialPersons(List<VehicleDetail> list) {
        int size = list.size();
        // với bảng tableSpecialPerson có 6 cột, 
        // khởi tạo mảng 2 chiều specialPersons, trong đó:
        // số hàng: là kích thước của list specialPerson 
        // số cột: là 7
        Object [][] specialPersons = new Object[size][7];
        for (int i = 0; i < size; i++) {
            specialPersons[i][0] = list.get(i).getId();
            specialPersons[i][1] = list.get(i).getName();
            specialPersons[i][2] = fDate.format(list.get(i).getBirthday());
            specialPersons[i][3] = list.get(i).getAddress();
            specialPersons[i][4] = fDate.format(list.get(i).getOpeningDate());
            specialPersons[i][5] = list.get(i).getType();
            specialPersons[i][6] = list.get(i).getImage();
        }
        //jLabel1.setLayout(null);
        tableSpecialPerson.getColumnModel().getColumn(0).setWidth(3);
        tableSpecialPerson.setModel(new DefaultTableModel(specialPersons, columnNames));
        tableSpecialPerson.removeColumn(tableSpecialPerson.getColumnModel().getColumn(6));
    }
    
    public void showCountListSpecialPersons(List<VehicleDetail> list) {
        int size = list.size();
        FieldSum.setText(String.valueOf(size));
    }
    /**
     * điền thông tin của hàng được chọn từ bảng specialPerson 
     * vào các trường tương ứng của specialPerson.
     */
    public void fillSpecialPersonFromSelectedRow() throws ParseException {
        // lấy chỉ số của hàng được chọn 
        int row = tableSpecialPerson.getSelectedRow();
        if (row >= 0) {
            FieldID.setText(tableSpecialPerson.getModel().getValueAt(row, 0).toString());
            FieldName.setText(tableSpecialPerson.getModel().getValueAt(row, 1).toString());
            BirthdayChooser.setDate(fDate.parse(tableSpecialPerson.getModel().getValueAt(row, 2).toString()));
            TextAreaAddress.setText(tableSpecialPerson.getModel().getValueAt(row, 3).toString());
            //FieldOpeningDate.setText(tableSpecialPerson.getModel().getValueAt(row, 4).toString());
            OpeningDateChooser.setDate(fDate.parse(tableSpecialPerson.getModel().getValueAt(row, 4).toString()));
            ComboBoxType.setSelectedItem(tableSpecialPerson.getModel().getValueAt(row, 5).toString());
            //ImageIcon imageIcon=new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
            //lblImage.setIcon((Icon) tableSpecialPerson.getModel().getValueAt(row, 6));
            byte[] img=(byte[]) tableSpecialPerson.getModel().getValueAt(row, 6);
            specialPerson_image=img;
            ImageIcon imageIcon=new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
            lblImage.setIcon(imageIcon);
            // enable Edit and Delete buttons
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            // disable Add button
            btnAdd.setEnabled(false);
            btnClear.setEnabled(true);
        }
    }

    /**
     * xóa thông tin specialPerson
     */
    public void clearSpecialPersonInfo() {
        FieldID.setText("");
        FieldName.setText("");
        BirthdayChooser.setDate(null);
        TextAreaAddress.setText("");
        //FieldOpeningDate.setText("");
        OpeningDateChooser.setDate(null);
        lblImage.setIcon(new ImageIcon("default-image.png")); 
        specialPerson_image=null;
        ComboBoxType.setSelectedItem("<none>");
        // disable Edit and Delete buttons
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        // enable Add button
        btnAdd.setEnabled(true);
    }
    
    public void searchNameSpecialPersonInfo() {
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(true);
    }
    
    public void displayStatisticView() {
        //FrameSearch = new ManagerView();
        StatisticView.setVisible(true);
        ManagerView.this.setVisible(false);
        StatisticView.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                StatisticView.dispose();
                System.exit(0); // Optional: terminate the entire application
            }
        });
    }
    
    public void cancelDialogSearchSpecialPersonInfo() {
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(false);
    }
    
    /**
     * hiện thị thông tin specialPerson
     * 
     * @param specialPerson
     */
    public void showSpecialPerson(VehicleDetail specialPerson) 
    {
        FieldID.setText("" + specialPerson.getId());
        FieldName.setText(specialPerson.getName());
        BirthdayChooser.setDate(specialPerson.getBirthday());
        TextAreaAddress.setText(specialPerson.getAddress());
        //FieldOpeningDate.setText("" + fDate.format(specialPerson.getOpeningDate()));
        OpeningDateChooser.setDate(specialPerson.getOpeningDate());
        ComboBoxType.setSelectedItem(""+specialPerson.getType());
        // enable Edit and Delete buttons
        byte[] img=specialPerson.getImage();
        ImageIcon imageIcon=new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
        lblImage.setIcon(imageIcon);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        // disable Add button
        btnAdd.setEnabled(false);
    }
    
    /**
     * lấy thông tin specialPerson
     * 
     * @return
     */
    public VehicleDetail getSpecialPersonInfo() {
        // validate specialPerson
        if (!validateName() || !validateYear() || !validateAddress() || !validateImage() || !validateOpeningDate() || !validateType()) {
            return null;
        }
        try {
            VehicleDetail specialPerson = new VehicleDetail();
            if (FieldID.getText() != null && !"".equals(FieldID.getText())) {
                specialPerson.setId(Integer.parseInt(FieldID.getText()));
            }
            specialPerson.setName(capitalizeWords(FieldName.getText().trim()));
            specialPerson.setBirthday(BirthdayChooser.getDate());
            specialPerson.setAddress(capitalizeWords(TextAreaAddress.getText().trim()));
            specialPerson.setOpeningDate(OpeningDateChooser.getDate());
            specialPerson.setType(ComboBoxType.getSelectedItem().toString().trim());
            specialPerson.setImage(specialPerson_image);
            return specialPerson;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
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
    
    private boolean validateType() {
        String type = ComboBoxType.getSelectedItem().toString().trim();
        if (type.equals("<none>")) {
            ComboBoxType.requestFocus();
            showMessage("Bạn chưa chọn loại đối tượng");
            return false;
        }
        return true;
    }
    
    public boolean validateImage() {
        byte[]k=specialPerson_image;
        if (k == null) {
            showMessage("Bạn chưa tải ảnh lên!");
            return false;
        }
        return true;
    }
    
    private boolean validateAddress() {
        String address = TextAreaAddress.getText();
        if (address == null || "".equals(address.trim())) {
            TextAreaAddress.requestFocus();
            showMessage("Quê quán không được trống.");
            return false;
        }
        return true;
    }
    
    private boolean validateYear() {
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
            showMessage("Ngày sinh không được trống");
            return false;
        }
        return true;
    }
    
    private boolean validateOpeningDate() {
        try {
            java.util.Date today=new java.util.Date();
            Date date=OpeningDateChooser.getDate();
            if (date.compareTo(today)==1) {
                OpeningDateChooser.requestFocus();
                showMessage("Ngày nhập không tồn tại hoặc lớn hơn ngày hôm nay");
                return false;
            }
        } catch (Exception e) {
            OpeningDateChooser.requestFocus();
            showMessage("Bạn đã nhập ngày sai định dạng");
            return false;
        }
        return true;
    }

    public int getChooseSelectSearch(){
        if(CheckBoxName.isSelected()) return 1;
        else if(CheckBoxAddress.isSelected()) return 2;
        else if(CheckBoxYear.isSelected()) return 3;
        return 0;
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
    
    public void cancelSearchSpecialPerson()
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
    
    public void UnderViewSpecialPerson()
    {
        StatisticView.setVisible(false);
        ManagerView.this.setVisible(true);
    }
    
    
    /*private boolean validateGPA() {
        try {
            Date prisonReleaseDate = fDate.parse(FieldOpeningDate.getText().trim());
            if (gpa < 0 || gpa > 10) {
                FieldOpeningDate.requestFocus();
                showMessage("GPA không hợp lệ, gpa nên trong khoảng 0 đến 10.");
                return false;
            }
        } catch (Exception e) {
            FieldOpeningDate.requestFocus();
            showMessage("GPA không hợp lệ!");
            return false;
        }
        return true;
    }*/
    
    public void showStatisticTypeSpecialPersons(List<VehicleDetail> list) {
        //tableStatistic=new JTable();
        lblTable.setText("Thống kê số lượng theo loại đối tượng");
        lblChart.setText("Biểu đồ thống kê số lượng theo loại đối tượng");
        chart1.clear();
        int size1 = 18;
        if (tableStatistic.getRowCount()>10){
            size1 = size1 - (tableStatistic.getRowCount()-10);
        }
        chart1.setFont(new java.awt.Font("sansserif", 0, size1)); 
        int size = ComboBoxType.getItemCount();
        columnNames2 = new String [] {
        "Loại đối tượng","Số lượng"};
        // với bảng tableSpecialPerson có 6 cột, 
        // khởi tạo mảng 2 chiều specialPersons, trong đó:
        // số hàng: là kích thước của list specialPerson 
        // số cột: là 7
        Map<String, Integer> countMap = new HashMap<>();
        for (VehicleDetail person : list) {
            if (countMap.containsKey(person.getType())) {
                int count = countMap.get(person.getType());
                countMap.put(person.getType(), count + 1);
            } else {
                countMap.put(person.getType(), 1);
            }
        }
        Object [][] statistic = new Object[countMap.size()][2];
        //Object[][] data = new Object[countMap.size()][2];
        int index = 0;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            chart1.addData(new ModelChart(entry.getKey(), new double[]{ConvertToDouble(entry.getValue())}));
            statistic[index][0] = entry.getKey();
            statistic[index][1] = entry.getValue();
            index++;
        }
        tableStatistic.setModel(new DefaultTableModel(statistic, columnNames2));
        chart1.start();
    }
    
    public void showStatisticAgeSpecialPersons(List<VehicleDetail> list) {
        java.util.Date referenceDate=new java.util.Date();
        //tableStatistic=new JTable();
        lblTable.setText("Thống kê số lượng theo tuổi");
        lblChart.setText("Biểu đồ thống kê số lượng theo tuổi");
        chart1.clear();
        int size1 = 18;
        if (tableStatistic.getRowCount()>10){
            size1 = size1 - (tableStatistic.getRowCount()-10);
        }
        chart1.setFont(new java.awt.Font("sansserif", 0, size1)); 
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int size = ComboBoxType.getItemCount();
        columnNames2 = new String[]{"Tuổi", "Số lượng"};
        Map<Integer, Integer> countMap = new HashMap<>();
        for (VehicleDetail person : list) {
            int age = calculateAge(person.getBirthday(), referenceDate);
            if (countMap.containsKey(age)) {
                int count = countMap.get(age);
                countMap.put(age, count + 1);
            } else {
                countMap.put(age, 1);
            }
        }
        Object [][] statistic = new Object[countMap.size()][2];
        //Object[][] data = new Object[countMap.size()][2];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            chart1.addData(new ModelChart(entry.getKey().toString(), new double[]{ConvertToDouble(entry.getValue())}));
            statistic[index][0] = entry.getKey();
            statistic[index][1] = entry.getValue();
            index++;
        }
        tableStatistic.setModel(new DefaultTableModel(statistic, columnNames2));
        chart1.start();
    }
    
    private int calculateAge(Date birthdate, Date referenceDate) {
    if ((birthdate != null) && (referenceDate != null)) {
        Calendar birthCalendar = Calendar.getInstance();
        Calendar referenceCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthdate);
        referenceCalendar.setTime(referenceDate);

        int birthYear = birthCalendar.get(Calendar.YEAR);
        int referenceYear = referenceCalendar.get(Calendar.YEAR);

        return referenceYear - birthYear;
    } else {
        return 0; // Hoặc giá trị mặc định khác tùy vào yêu cầu của bạn
    }
}
    
    private double ConvertToDouble(Object k)
    {
        return Double.valueOf(k.toString());
    }
    
    public void addAddSpecialPersonListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }
    
    public void addEditSpecialPersonListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }
    
    public void addDeleteSpecialPersonListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }
    
    public void addClearListener(ActionListener listener) {
        btnClear.addActionListener(listener);
    }
    
    public void addSortByOpeningDateListener(ActionListener listener) {
        btnSortByOpeningDate.addActionListener(listener);
    }
    
    public void addSortByIDListener(ActionListener listener) {
        btnSortByID.addActionListener(listener);
    }
    
    public void addSortByNameListener(ActionListener listener) {
        btnSortByName.addActionListener(listener);
    }
    
    public void addSortByYearListener(ActionListener listener) {
        btnSortByYear.addActionListener(listener);
    }
    
    public void addSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }
    
    public void addSearchDialogListener(ActionListener listener) {
        btnSearchDialog.addActionListener(listener);
    }
    
    public void addListSpecialPersonSelectionListener(ListSelectionListener listener) {
        tableSpecialPerson.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addSearchDiaLogSpecialPersonListener(ActionListener listener){
        btnSearchDialog.addActionListener(listener);
    }
    
    public void addCancelSearchSpecialPersonListener(ActionListener listener){
        btnCancelSearch.addActionListener(listener);
    }
    
    public void addImageSpecialPersonListener(ActionListener listener){
        btnImage.addActionListener(listener);
    }
    
    public void addCancelDialogListener(ActionListener listener){
        btnCancelDialog1.addActionListener(listener);
    }
    
    public void addUndoListener(ActionListener listener){
        btnManagerUndo.addActionListener(listener);
    }
    public void addStatisticListener(ActionListener listener){
        btnStatistic.addActionListener(listener);
    }
    
    public void addStatisticAgeListener(ActionListener listener){
        btnStatisticAge.addActionListener(listener);
    }
    
    public void addStatisticTypeListener(ActionListener listener){
        btnStatisticType.addActionListener(listener);
    }
    
    public void addStatisticUnderListener(ActionListener listener){
        btnStatisticUnder.addActionListener(listener);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser BirthdayChooser;
    private javax.swing.JCheckBox CheckBoxAddress;
    private javax.swing.JCheckBox CheckBoxName;
    private javax.swing.JCheckBox CheckBoxYear;
    private javax.swing.JComboBox<String> ComboBoxType;
    private javax.swing.JTextField FieldID;
    private javax.swing.JTextField FieldName;
    private javax.swing.JTextField FieldSearch;
    private javax.swing.JTextField FieldSum;
    private com.toedter.calendar.JDateChooser OpeningDateChooser;
    private javax.swing.JScrollPane ScrollPaneStatistic;
    private javax.swing.JDialog SearchDialog;
    private javax.swing.JFrame StatisticView;
    private javax.swing.JTextArea TextAreaAddress;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelDialog1;
    private javax.swing.JButton btnCancelSearch;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup btnGroupSearch;
    private javax.swing.JButton btnImage;
    private javax.swing.JButton btnManagerUndo;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchDialog;
    private javax.swing.JButton btnSortByID;
    private javax.swing.JButton btnSortByName;
    private javax.swing.JButton btnSortByOpeningDate;
    private javax.swing.JButton btnSortByYear;
    private javax.swing.JButton btnStatistic;
    private javax.swing.JButton btnStatisticAge;
    private javax.swing.JButton btnStatisticType;
    private javax.swing.JButton btnStatisticUnder;
    private com.raven.chart.Chart chart1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChart;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblTable;
    private javax.swing.JPanel panelChart;
    private javax.swing.JTable tableSpecialPerson;
    private javax.swing.JTable tableStatistic;
    private org.jdesktop.animation.timing.TimingTargetAdapter timingTargetAdapter1;
    // End of variables declaration//GEN-END:variables
}
