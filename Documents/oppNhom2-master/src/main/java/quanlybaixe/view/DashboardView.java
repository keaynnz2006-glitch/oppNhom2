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

    // Component tab Doanh Thu
    private DefaultCategoryDataset revenueDataset;
    private JFreeChart revenueBarChart;
    private JComboBox<String> cbFilterType;
    private JComboBox<Integer> cbMonth;
    private JComboBox<Integer> cbYear;
    private JPanel monthYearPanel;

    // Component tab Xe Đã Trả
    private DefaultCategoryDataset historyCountDataset;
    private JFreeChart historyCountBarChart;
    private JComboBox<String> cbHistoryFilterType;
    private JComboBox<Integer> cbHistoryMonth;
    private JComboBox<Integer> cbHistoryYear;
    private JPanel historyMonthYearPanel;

    private List<VehicleDetail> historyList;

    public DashboardView(ManagerVehicleDetail manager, MainView mainView) {
        List<VehicleDetail> activeList = null;
        try {
            activeList = manager.getListVehicleDetails();     
            this.historyList = manager.getHistoryVehicleDetails(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.historyList == null) this.historyList = new ArrayList<>();
        if (activeList == null) activeList = new ArrayList<>();

        List<VehicleDetail> allList = new ArrayList<>();
        allList.addAll(activeList);
        allList.addAll(this.historyList);

        setTitle("BÁO CÁO & THỐNG KÊ CHI TIẾT BÃI XE");
        setSize(1150, 750);
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
        tabbedPane.addTab("📜 Thống Kê Xe Đã Trả", createHistoryVehicleCountTabWithFilterPanel());
        tabbedPane.addTab("🥧 Cơ Cấu Doanh Thu", createRevenueByVehicleTypePanel(allList));
        tabbedPane.addTab("🚗 Xe Trong Bãi Hiện Tại", createVehicleCountPanel(activeList));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel(List<VehicleDetail> activeList, List<VehicleDetail> historyList, List<VehicleDetail> allList) {
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 12, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        headerPanel.setBackground(new Color(245, 247, 250));

        int activeCount = activeList.size();
        int historyCount = historyList.size();
        double actualRevenue = historyList.stream().mapToDouble(VehicleDetail::getGiaTien).sum();
        double totalExpectedRevenue = allList.stream().mapToDouble(VehicleDetail::getGiaTien).sum();

        headerPanel.add(createCard("XE ĐANG TRONG BÃI", activeCount + " Chiếc", new Color(41, 128, 185)));
        headerPanel.add(createCard("XE ĐÃ RỜI/TRẢ BÃI", historyCount + " Chiếc", new Color(230, 126, 34)));
        headerPanel.add(createCard("DOANH THU ĐÃ THU", priceFormat.format(actualRevenue) + " VNĐ", new Color(39, 174, 96)));
        headerPanel.add(createCard("TỔNG DOANH THU DỰ KIẾN", priceFormat.format(totalExpectedRevenue) + " VNĐ", new Color(142, 68, 173)));

        return headerPanel;
    }

    private JPanel createCard(String title, String value, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitle.setForeground(new Color(236, 240, 241));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 17));
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

        filterPanel.add(new JLabel("Thời Gian Lọc:"));
        filterPanel.add(cbFilterType);
        filterPanel.add(monthYearPanel);
        filterPanel.add(btnFilter);

        mainTabPanel.add(filterPanel, BorderLayout.NORTH);

        revenueDataset = new DefaultCategoryDataset();
        revenueBarChart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU", "Thời Gian", "Doanh Thu (VNĐ)",
                revenueDataset, PlotOrientation.VERTICAL, false, true, false
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
        if (cbFilterType == null || cbFilterType.getSelectedItem() == null) return;

        revenueDataset.clear();
        String option = (String) cbFilterType.getSelectedItem();
        Map<String, Double> dailyRevenue = new TreeMap<>();

        Calendar now = Calendar.getInstance();
        resetTime(now);

        if (historyList != null) {
            for (VehicleDetail v : historyList) {
                Date ngayGhiNhan = v.getNgayXuatBai();
                if (ngayGhiNhan == null) ngayGhiNhan = v.getNgayVaoBai();
                if (ngayGhiNhan == null) continue;

                Calendar itemCal = Calendar.getInstance();
                itemCal.setTime(ngayGhiNhan);
                resetTime(itemCal);

                if (checkMatch(option, itemCal, now, cbMonth, cbYear)) {
                    String dateStr = dateFormat.format(ngayGhiNhan);
                    dailyRevenue.put(dateStr, dailyRevenue.getOrDefault(dateStr, 0.0) + v.getGiaTien());
                }
            }
        }

        if (dailyRevenue.isEmpty()) {
            revenueDataset.addValue(0, "Doanh Thu", "Chưa có dữ liệu");
        } else {
            dailyRevenue.forEach((date, amount) -> revenueDataset.addValue(amount, "Doanh Thu", date));
        }

        revenueBarChart.setTitle(new TextTitle("BIỂU ĐỒ DOANH THU - " + option.toUpperCase(), new Font("Segoe UI", Font.BOLD, 16)));
    }

    private JPanel createHistoryVehicleCountTabWithFilterPanel() {
        JPanel mainTabPanel = new JPanel(new BorderLayout(10, 10));
        mainTabPanel.setBackground(Color.WHITE);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(new Color(236, 240, 241));

        cbHistoryFilterType = new JComboBox<>(new String[]{"Hôm nay", "7 ngày gần nhất", "30 ngày gần nhất", "Theo Tháng/Năm"});
        cbHistoryFilterType.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        historyMonthYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        historyMonthYearPanel.setOpaque(false);

        Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        cbHistoryMonth = new JComboBox<>(months);
        Calendar cal = Calendar.getInstance();
        cbHistoryMonth.setSelectedItem(cal.get(Calendar.MONTH) + 1);

        Integer[] years = {cal.get(Calendar.YEAR) - 2, cal.get(Calendar.YEAR) - 1, cal.get(Calendar.YEAR)};
        cbHistoryYear = new JComboBox<>(years);
        cbHistoryYear.setSelectedItem(cal.get(Calendar.YEAR));

        historyMonthYearPanel.add(new JLabel("Tháng:"));
        historyMonthYearPanel.add(cbHistoryMonth);
        historyMonthYearPanel.add(new JLabel("Năm:"));
        historyMonthYearPanel.add(cbHistoryYear);
        historyMonthYearPanel.setVisible(false);

        JButton btnFilter = new JButton("Lọc Xe Đã Trả");
        btnFilter.setBackground(new Color(230, 126, 34));
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);

        filterPanel.add(new JLabel("Thời Gian Lọc Xe Đã Trả:"));
        filterPanel.add(cbHistoryFilterType);
        filterPanel.add(historyMonthYearPanel);
        filterPanel.add(btnFilter);

        mainTabPanel.add(filterPanel, BorderLayout.NORTH);

        historyCountDataset = new DefaultCategoryDataset();
        historyCountBarChart = ChartFactory.createBarChart(
                "THỐNG KÊ SỐ LƯỢNG XE ĐÃ TRẢ / XUẤT BÃI", "Loại Xe", "Số Lượng (Chiếc)",
                historyCountDataset, PlotOrientation.VERTICAL, false, true, false
        );

        historyCountBarChart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = historyCountBarChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setRangeGridlinePaint(new Color(230, 230, 230));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(230, 126, 34));
        renderer.setShadowVisible(false);

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Segoe UI", Font.BOLD, 12));

        mainTabPanel.add(new ChartPanel(historyCountBarChart), BorderLayout.CENTER);

        cbHistoryFilterType.addActionListener(e -> {
            boolean isMonthSelected = "Theo Tháng/Năm".equals(cbHistoryFilterType.getSelectedItem());
            historyMonthYearPanel.setVisible(isMonthSelected);
            mainTabPanel.revalidate();
        });

        btnFilter.addActionListener(e -> updateHistoryCountChartData());

        cbHistoryFilterType.setSelectedItem("7 ngày gần nhất");
        updateHistoryCountChartData();

        return mainTabPanel;
    }

    private void updateHistoryCountChartData() {
        if (cbHistoryFilterType == null || cbHistoryFilterType.getSelectedItem() == null) return;

        historyCountDataset.clear();
        String option = (String) cbHistoryFilterType.getSelectedItem();
        Map<String, Long> vehicleCounts = new HashMap<>();

        Calendar now = Calendar.getInstance();
        resetTime(now);

        if (historyList != null && !historyList.isEmpty()) {
            for (VehicleDetail v : historyList) {
                Date ngayGhiNhan = v.getNgayXuatBai();
                if (ngayGhiNhan == null) ngayGhiNhan = v.getNgayVaoBai();
                
                if (ngayGhiNhan == null) {
                    String loaiXe = (v.getLoaiXe() == null || v.getLoaiXe().trim().isEmpty()) ? "Khác" : v.getLoaiXe();
                    vehicleCounts.put(loaiXe, vehicleCounts.getOrDefault(loaiXe, 0L) + 1);
                    continue;
                }

                Calendar itemCal = Calendar.getInstance();
                itemCal.setTime(ngayGhiNhan);
                resetTime(itemCal);

                if (checkMatch(option, itemCal, now, cbHistoryMonth, cbHistoryYear)) {
                    String loaiXe = (v.getLoaiXe() == null || v.getLoaiXe().trim().isEmpty()) ? "Khác" : v.getLoaiXe();
                    vehicleCounts.put(loaiXe, vehicleCounts.getOrDefault(loaiXe, 0L) + 1);
                }
            }
        }

        if (vehicleCounts.isEmpty()) {
            historyCountDataset.addValue(0, "Số lượng xe đã trả", "Chưa có dữ liệu");
        } else {
            vehicleCounts.forEach((loaiXe, count) -> historyCountDataset.addValue(count, "Số lượng xe đã trả", loaiXe));
        }

        historyCountBarChart.setTitle(new TextTitle("THỐNG KÊ SỐ LƯỢNG XE ĐÃ TRẢ - " + option.toUpperCase(), new Font("Segoe UI", Font.BOLD, 16)));
    }

    private JPanel createRevenueByVehicleTypePanel(List<VehicleDetail> allList) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        if (allList != null && !allList.isEmpty()) {
            Map<String, Double> revenues = allList.stream()
                    .collect(Collectors.groupingBy(
                            v -> (v.getLoaiXe() == null || v.getLoaiXe().trim().isEmpty()) ? "Khác" : v.getLoaiXe(),
                            Collectors.summingDouble(VehicleDetail::getGiaTien)
                    ));

            revenues.forEach(dataset::setValue);
        }

        JFreeChart pieChart = ChartFactory.createPieChart("CƠ CẤU DOANH THU THEO LOẠI XE", dataset, true, true, false);
        pieChart.setBackgroundPaint(Color.WHITE);
        pieChart.setTitle(new TextTitle("CƠ CẤU DOANH THU THEO LOẠI XE", new Font("Segoe UI", Font.BOLD, 16)));

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} VNĐ ({2})", new DecimalFormat("#,###"), new DecimalFormat("0.0%")));
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

        if (activeList != null && !activeList.isEmpty()) {
            Map<String, Long> counts = activeList.stream()
                    .collect(Collectors.groupingBy(
                            v -> (v.getLoaiXe() == null || v.getLoaiXe().trim().isEmpty()) ? "Khác" : v.getLoaiXe(),
                            Collectors.counting()
                    ));

            counts.forEach((loaiXe, count) -> dataset.addValue(count, "Số lượng", loaiXe));
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "SỐ LƯỢNG XE ĐANG GỬI TRONG BÃI HIỆN TẠI", "Loại Xe", "Số Lượng (Chiếc)",
                dataset, PlotOrientation.VERTICAL, false, true, false
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

    private void resetTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    private boolean checkMatch(String option, Calendar itemCal, Calendar now, JComboBox<Integer> cbM, JComboBox<Integer> cbY) {
        if ("Hôm nay".equals(option)) {
            return itemCal.getTimeInMillis() == now.getTimeInMillis();
        } else if ("7 ngày gần nhất".equals(option)) {
            long diffInDays = (now.getTimeInMillis() - itemCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
            return diffInDays >= 0 && diffInDays < 7;
        } else if ("30 ngày gần nhất".equals(option)) {
            long diffInDays = (now.getTimeInMillis() - itemCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
            return diffInDays >= 0 && diffInDays < 30;
        } else if ("Theo Tháng/Năm".equals(option)) {
            if (cbM == null || cbY == null || cbM.getSelectedItem() == null || cbY.getSelectedItem() == null) {
                return false;
            }
            int selectedMonth = (Integer) cbM.getSelectedItem();
            int selectedYear = (Integer) cbY.getSelectedItem();
            return (itemCal.get(Calendar.MONTH) + 1 == selectedMonth) && (itemCal.get(Calendar.YEAR) == selectedYear);
        }
        return false;
    }
}