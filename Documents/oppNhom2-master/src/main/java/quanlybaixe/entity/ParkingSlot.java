package quanlybaixe.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ParkingSlot")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParkingSlot implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;             // ID định danh slot
    private String tenViTri;    // Ví dụ: A01, B02, Khu A - Tầng 1
    private String loaiSlot;    // Xe máy, Ô tô, Xe điện
    private double giaTien;     // Giá đỗ xe niêm yết
    private boolean trangThai; // true: Đã có xe, false: Trống
    private String ghiChu;      // Ghi chú thêm

    public ParkingSlot() {}

    public ParkingSlot(int id, String tenViTri, String loaiSlot, double giaTien, boolean trangThai, String ghiChu) {
        this.id = id;
        this.tenViTri = tenViTri;
        this.loaiSlot = loaiSlot;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // --- GETTER & SETTER ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenViTri() { return tenViTri; }
    public void setTenViTri(String tenViTri) { this.tenViTri = tenViTri; }

    public String getLoaiSlot() { return loaiSlot; }
    public void setLoaiSlot(String loaiSlot) { this.loaiSlot = loaiSlot; }

    public double getGiaTien() { return giaTien; }
    public void setGiaTien(double giaTien) { this.giaTien = giaTien; }

    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    @Override
    public String toString() {
        return tenViTri != null ? tenViTri : "";
    }
}