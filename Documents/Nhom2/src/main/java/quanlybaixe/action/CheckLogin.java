package quanlybaixe.action;

import quanlybaixe.entity.User;

/**
 *
 * @author PC
 */
public class CheckLogin {
    public boolean checkUser(User user) {
        if (user != null) {
            // Sửa getUserName() thành getUsername() cho đúng với entity User
            if ("admin".equals(user.getUsername()) 
                    && "admin".equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}