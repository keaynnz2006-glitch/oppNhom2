package quanlybaixe.utils; // <-- SỬA DÒNG NÀY (thay vì quanlybaixe.entity)

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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