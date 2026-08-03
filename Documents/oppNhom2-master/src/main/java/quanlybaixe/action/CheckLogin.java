package quanlybaixe.action;

import quanlybaixe.entity.User;

/**
 *
 * @author PC
 */
public class CheckLogin {
    public boolean checkUser(User user) {
        if (user != null) {
          
            if ("admin".equals(user.getUsername()) 
                    && "admin".equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}