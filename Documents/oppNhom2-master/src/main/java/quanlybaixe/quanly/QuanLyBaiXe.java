package quanlybaixe.quanly;

import com.formdev.flatlaf.FlatLightLaf; // <-- THÊM DÒNG NÀY
import quanlybaixe.controller.LoginController;
import quanlybaixe.view.LoginView;

public class QuanLyBaiXe {
    public static void main(String[] args) {
        
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                LoginController controller = new LoginController(view);
                controller.showLoginView();
            }
        });
    }
}