/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package quanlybaixe.quanly;

import quanlybaixe.controller.LoginController;
import quanlybaixe.view.LoginView; 
import java.awt.EventQueue;

/**
 *
 * @author PC
 */
public class QuanLyBaiXe 
{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                LoginController controller = new LoginController(view);
                // hiển thị màn hình login
                controller.showLoginView();
            }
        });
    }
}