package quanlybaixe.controller;

import quanlybaixe.action.CheckLogin;
import quanlybaixe.entity.User;
import quanlybaixe.view.LoginView; // Sẽ tự động hết đỏ sau khi bạn đổi tên gói view sau
import quanlybaixe.view.MainView;
import quanlybaixe.view.ManagerView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author PC
 */
public class LoginController {
    private CheckLogin checkLogin;
    private LoginView loginView;
    private ManagerView managerView;
    private MainView mainView;
    
    public LoginController(LoginView view) {
        this.loginView = view;
        this.checkLogin = new CheckLogin();
        view.addLoginListener(new LoginListener());
    }
    
    public void showLoginView() {
        loginView.setVisible(true);
    }
    
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (checkLogin.checkUser(user)) {
                mainView = new MainView();
                MainController mainController = new MainController(mainView);
                mainController.showMainView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        }
    }
}