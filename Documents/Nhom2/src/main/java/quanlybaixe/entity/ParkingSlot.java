package quanlybaixe.entity;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ParkingSlot")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParkingSlot extends Vehicle {
    private String tenViTri;    // Ví dụ: Khu A - 101
    private String loaiSlot;    // Loại slot: Ô tô, Xe máy (Dùng cho Dropdown list box chọn)
    private boolean trangThai;  // true: Đang đỗ xe, false: Vị trí còn trống
    private String ghiChu;      // Ghi chú thêm nếu có

    public ParkingSlot() {
        super();
        this.tenViTri = "";
        this.loaiSlot = "Xe máy";
        this.trangThai = false;
        this.ghiChu = "";
    }

    public ParkingSlot(int id, String bienSo, String loaiXe, Date ngayGui, double giaTien, 
                       String tenViTri, String loaiSlot, boolean trangThai, String ghiChu) {
        super(id, bienSo, loaiXe, ngayGui, giaTien);
        this.tenViTri = tenViTri;
        this.loaiSlot = loaiSlot;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public String getTenViTri() {
        return tenViTri;
    }

    public void setTenViTri(String tenViTri) {
        this.tenViTri = tenViTri;
    }

    public String getLoaiSlot() {
        return loaiSlot;
    }

    public void setLoaiSlot(String loaiSlot) {
        this.loaiSlot = loaiSlot;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}