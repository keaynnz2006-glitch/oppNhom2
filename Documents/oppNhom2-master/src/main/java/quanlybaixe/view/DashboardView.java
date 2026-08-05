package quanlybaixe.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import quanlybaixe.action.ManagerVehicleDetail;
import quanlybaixe.entity.VehicleDetail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardView extends JFrame {

    private final DecimalFormat priceFormat = new DecimalFormat("#,###");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private DefaultCategoryDataset revenueDataset;
    private JFreeChart revenueBarChart;
    private JComboBox<String> cbFilterType;
    private JComboBox<Integer> cbMonth;
    private JComboBox<Integer> cbYear;
    private JPanel monthYearPanel;
    private List<VehicleDetail> historyList;

    public DashboardView(ManagerVehicleDetail manager, MainView mainView) {
        List<VehicleDetail> activeList = manager.getListVehicleDetails();    
        this.historyList = manager.getHistoryVehicleDetails(); 

        List<VehicleDetail> allList = new ArrayList<>();
        if (activeList != null) allList.addAll(activeList);
        if (this.historyList != null) allList.addAll(this.historyList);

        setTitle("BÁO CÁO & THỐNG KÊ CHI TIẾT BÃI XE");
        setSize(1080, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 247, 250));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mainView != null) {
                    mainView.setVisible(true);
                }
            }
        });

        add(createHeaderPanel(activeList, historyList, allList), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabbedPane.addTab("📊 Doanh Thu & Lọc Chi Tiết", createRevenueTabWithFilterPanel());
        tabbedPane.addTab("🥧 Cơ Cấu Doanh Thu", createRevenueByVehicleTypePanel(allList));
        tabbedPane.addTab("🚗 Xe Trong Bãi Hiện Tại", createVehicleCountPanel(activeList));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel(List<VehicleDetail> activeList, List<VehicleDetail> historyList, List<VehicleDetail> allList) {
        JPanel headerPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        headerPanel.setBackground(new Color(245, 247, 250));

        int activeCount = activeList != null ? activeList.size() : 0;
        double actualRevenue = historyList != null ? historyList.stream().mapToDouble(VehicleDetail::getGiaTien).sum() : 0;
        double totalExpectedRevenue = allList.stream().mapToDouble(VehicleDetail::getGiaTien).sum();

        headerPanel.add(createCard("XE ĐANG TRONG BÃI", activeCount + " Chiếc", new Color(41, 128, 185)));
        headerPanel.add(createCard("DOANH THU ĐÃ THU", priceFormat.format(actualRevenue) + " VNĐ", new Color(39, 174, 96)));
        headerPanel.add(createCard("TỔNG DOANH THU DỰ KIẾN", priceFormat.format(totalExpectedRevenue) + " VNĐ", new Color(142, 68, 173)));

        return headerPanel;
    }

    private JPanel createCard(String title, String value, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitle.setForeground(new Color(236, 240, 241));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValue.setForeground(Color.WHITE);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    private JPanel createRevenueTabWithFilterPanel() {
        JPanel mainTabPanel = new JPanel(new BorderLayout(10, 10));
        mainTabPanel.setBackground(Color.WHITE);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(new Color(236, 240, 241));

        filterPanel.add(new JLabel("Thời Gian Lọc:"));
        cbFilterType = new JComboBox<>(new String[]{"Hôm nay", "7 ngày gần nhất", "30 ngày gần nhất", "Theo Tháng/Năm"});
        cbFilterType.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        monthYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        monthYearPanel.setOpaque(false);

        Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        cbMonth = new JComboBox<>(months);
        Calendar cal = Calendar.getInstance();
        cbMonth.setSelectedItem(cal.get(Calendar.MONTH) + 1);

        Integer[] years = {cal.get(Calendar.YEAR) - 2, cal.get(Calendar.YEAR) - 1, cal.get(Calendar.YEAR)};
        cbYear = new JComboBox<>(years);
        cbYear.setSelectedItem(cal.get(Calendar.YEAR));

        monthYearPanel.add(new JLabel("Tháng:"));
        monthYearPanel.add(cbMonth);
        monthYearPanel.add(new JLabel("Năm:"));
        monthYearPanel.add(cbYear);
        monthYearPanel.setVisible(false);

        JButton btnFilter = new JButton("Lọc Dữ Liệu");
        btnFilter.setBackground(new Color(52, 152, 219));
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);

        filterPanel.add(cbFilterType);
        filterPanel.add(monthYearPanel);
        filterPanel.add(btnFilter);

        mainTabPanel.add(filterPanel, BorderLayout.NORTH);

        revenueDataset = new DefaultCategoryDataset();
        revenueBarChart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU",
                "Thời Gian",
                "Doanh Thu (VNĐ)",
                revenueDataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        revenueBarChart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = revenueBarChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setRangeGridlinePaint(new Color(220, 220, 220));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(46, 204, 113));
        renderer.setShadowVisible(false);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", priceFormat));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Segoe UI", Font.BOLD, 11));

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        mainTabPanel.add(new ChartPanel(revenueBarChart), BorderLayout.CENTER);

        cbFilterType.addActionListener(e -> {
            boolean isMonthSelected = "Theo Tháng/Năm".equals(cbFilterType.getSelectedItem());
            monthYearPanel.setVisible(isMonthSelected);
            mainTabPanel.revalidate();
        });

        btnFilter.addActionListener(e -> updateRevenueChartData());

        cbFilterType.setSelectedItem("7 ngày gần nhất");
        updateRevenueChartData();

        return mainTabPanel;
    }

    private void updateRevenueChartData() {
        revenueDataset.clear();
        String option = (String) cbFilterType.getSelectedItem();
        Map<String, Double> dailyRevenue = new TreeMap<>();

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        if (historyList != null) {
            for (VehicleDetail v : historyList) {
                Date ngayGhiNhan = v.getNgayXuatBai() != null ? v.getNgayXuatBai() : v.getNgayVaoBai();
                if (ngayGhiNhan == null) continue;

                Calendar itemCal = Calendar.getInstance();
                itemCal.setTime(ngayGhiNhan);
                itemCal.set(Calendar.HOUR_OF_DAY, 0);
                itemCal.set(Calendar.MINUTE, 0);
                itemCal.set(Calendar.SECOND, 0);
                itemCal.set(Calendar.MILLISECOND, 0);

                boolean match = false;

                if ("Hôm nay".equals(option)) {
                    match = itemCal.getTimeInMillis() == now.getTimeInMillis();
                } else if ("7 ngày gần nhất".equals(option)) {
                    long diffInDays = (now.getTimeInMillis() - itemCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                    match = diffInDays >= 0 && diffInDays < 7;
                } else if ("30 ngày gần nhất".equals(option)) {
                    long diffInDays = (now.getTimeInMillis() - itemCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                    match = diffInDays >= 0 && diffInDays < 30;
                } else if ("Theo Tháng/Năm".equals(option)) {
                    int selectedMonth = (Integer) cbMonth.getSelectedItem();
                    int selectedYear = (Integer) cbYear.getSelectedItem();
                    match = (itemCal.get(Calendar.MONTH) + 1 == selectedMonth) && (itemCal.get(Calendar.YEAR) == selectedYear);
                }

                if (match) {
                    String dateStr = dateFormat.format(ngayGhiNhan);
                    dailyRevenue.put(dateStr, dailyRevenue.getOrDefault(dateStr, 0.0) + v.getGiaTien());
                }
            }
        }

        if (dailyRevenue.isEmpty()) {
            revenueDataset.addValue(0, "Doanh Thu", "Không có dữ liệu");
        } else {
            dailyRevenue.forEach((date, amount) -> revenueDataset.addValue(amount, "Doanh Thu", date));
        }

        revenueBarChart.setTitle(new TextTitle("BIỂU ĐỒ DOANH THU - " + option.toUpperCase(), new Font("Segoe UI", Font.BOLD, 16)));
    }

    private JPanel createRevenueByVehicleTypePanel(List<VehicleDetail> allList) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Double> revenues = allList.stream()
                .collect(Collectors.groupingBy(
                        v -> (v.getLoaiXe() == null || v.getLoaiXe().trim().isEmpty()) ? "Khác" : v.getLoaiXe(),
                        Collectors.summingDouble(VehicleDetail::getGiaTien)
                ));

        revenues.forEach(dataset::setValue);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "CƠ CẤU DOANH THU THEO LOẠI XE",
                dataset,
                true, true, false
        );

        pieChart.setBackgroundPaint(Color.WHITE);
        pieChart.setTitle(new TextTitle("CƠ CẤU DOANH THU THEO LOẠI XE", new Font("Segoe UI", Font.BOLD, 16)));

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} VNĐ ({2})", new DecimalFormat("#,###"), new DecimalFormat("0.0%")
        ));
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 11));

        Color[] colors = {new Color(46, 204, 113), new Color(155, 89, 182), new Color(241, 196, 15), new Color(52, 152, 219)};
        int i = 0;
        for (Object key : dataset.getKeys()) {
            plot.setSectionPaint((Comparable<?>) key, colors[i % colors.length]);
            i++;
        }

        return new ChartPanel(pieChart);
    }

    private JPanel createVehicleCountPanel(List<VehicleDetail> activeList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (activeList != null) {
            Map<String, Long> counts = activeList.stream()
                    .collect(Collectors.groupingBy(
                            v -> (v.getLoaiXe() == null || v.getLoaiXe().trim().isEmpty()) ? "Khác" : v.getLoaiXe(),
                            Collectors.counting()
                    ));

            counts.forEach((loaiXe, count) -> dataset.addValue(count, "Số lượng", loaiXe));
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "SỐ LƯỢNG XE ĐANG GỬI TRONG BÃI HIỆN TẠI",
                "Loại Xe",
                "Số Lượng (Chiếc)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        barChart.setBackgroundPaint(Color.WHITE);
        barChart.setTitle(new TextTitle("SỐ LƯỢNG XE ĐANG GỬI TRONG BÃI HIỆN TẠI", new Font("Segoe UI", Font.BOLD, 16)));

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setRangeGridlinePaint(new Color(230, 230, 230));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(52, 152, 219));
        renderer.setShadowVisible(false);

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Segoe UI", Font.BOLD, 12));

        return new ChartPanel(barChart);
    }
}