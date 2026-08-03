package quanlybaixe.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {
    // Sửa định dạng thêm HH:mm để lưu cả Giờ và Phút
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null || v.trim().isEmpty()) {
            return null;
        }
        return dateFormat.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        if (v == null) {
            return "";
        }
        return dateFormat.format(v);
    }
}