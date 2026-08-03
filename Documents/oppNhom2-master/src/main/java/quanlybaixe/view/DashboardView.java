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

    public DashboardView(ManagerVehicleDetail manager, MainView mainView) {
        List<VehicleDetail> activeList = manager.getListVehicleDetails();    // Xe đang gửi trong bãi
        List<VehicleDetail> historyList = manager.getHistoryVehicleDetails(); // Xe đã thanh toán xuất bãi

        List<VehicleDetail> allList = new ArrayList<>();
        if (activeList != null) allList.addAll(activeList);
        if (historyList != null) allList.addAll(historyList);

        setTitle("BÁO CÁO & THỐNG KÊ CHI TIẾT BÃI XE");
        setSize(1080, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 247, 250));

        // Bắt sự kiện tắt cửa sổ X -> Tự động hiện lại MainView
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mainView != null) {
                    mainView.setVisible(true);
                }
            }
        });

        // 1. Thẻ Thống Kê
        add(createHeaderPanel(activeList, historyList, allList), BorderLayout.NORTH);

        // 2. TabbedPane chứa các biểu đồ
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabbedPane.addTab("📊 Doanh Thu 30 Ngày Gần Nhất", createRevenueBarChartPanel(historyList));
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

    // --- TAB 1: DOANH THU THEO NGÀY XUẤT BÃI (GIỚI HẠN 30 NGÀY GẦN NHẤT) ---
    private JPanel createRevenueBarChartPanel(List<VehicleDetail> historyList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> dailyRevenue = new TreeMap<>();

        if (historyList != null) {
            for (VehicleDetail v : historyList) {
                // Ưu tiên lấy ngayXuatBai, nếu null mới lấy ngayVaoBai
                Date ngayGhiNhan = v.getNgayXuatBai() != null ? v.getNgayXuatBai() : v.getNgayVaoBai();

                if (ngayGhiNhan != null) {
                    String dateStr = dateFormat.format(ngayGhiNhan);
                    dailyRevenue.put(dateStr, dailyRevenue.getOrDefault(dateStr, 0.0) + v.getGiaTien());
                }
            }
        }

        if (dailyRevenue.isEmpty()) {
            dailyRevenue.put(dateFormat.format(new Date()), 0.0);
        }

        // --- CẮT DỮ LIỆU: CHỈ GIỮ LAI TỐI ĐA 30 NGÀY MỚI NHẤT ---
        int maxDays = 30;
        List<String> dates = new ArrayList<>(dailyRevenue.keySet());
        
        if (dates.size() > maxDays) {
            // Lấy danh sách các ngày cũ hơn mốc 30 ngày để xóa
            List<String> datesToRemove = dates.subList(0, dates.size() - maxDays);
            for (String oldDate : datesToRemove) {
                dailyRevenue.remove(oldDate);
            }
        }

        // Đổ dữ liệu đã lọc vào Dataset
        dailyRevenue.forEach((date, amount) -> dataset.addValue(amount, "Doanh Thu", date));

        JFreeChart barChart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU 30 NGÀY GẦN NHẤT",
                "Ngày Xuất Bãi",
                "Doanh Thu (VNĐ)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        barChart.setBackgroundPaint(Color.WHITE);
        barChart.setTitle(new TextTitle("BIỂU ĐỒ DOANH THU 30 NGÀY GẦN NHẤT", new Font("Segoe UI", Font.BOLD, 16)));

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setRangeGridlinePaint(new Color(220, 220, 220));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(46, 204, 113)); // Màu xanh lá tài lộc
        renderer.setShadowVisible(false);

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", priceFormat));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Segoe UI", Font.BOLD, 11));

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // Xoay 45 độ cho dễ đọc

        return new ChartPanel(barChart);
    }

    // --- TAB 2: CƠ CẤU DOANH THU (PIE CHART) ---
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

    // --- TAB 3: SỐ LƯỢNG XE TRONG BÃI (BAR CHART) ---
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