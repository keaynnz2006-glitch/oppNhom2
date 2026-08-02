package quanlybaixe.entity;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ParkingSlot")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParkingSlot implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String bienSo;
    private String loaiXe;
    private String mauXe;
    private Date ngayGui;
    private double giaTien;
    private String tenViTri;  // Ví dụ: A01, B02, Khu A - Tầng 1
    private String loaiSlot;  // Xe máy, Ô tô, Xe điện
    private boolean trangThai; // true: Đã có xe, false: Trống
    private String ghiChu;

    public ParkingSlot() {}

    public ParkingSlot(int id, String bienSo, String loaiXe, String mauXe, Date ngayGui, double giaTien, String tenViTri, String loaiSlot, boolean trangThai, String ghiChu) {
        this.id = id;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.mauXe = mauXe;
        this.ngayGui = ngayGui;
        this.giaTien = giaTien;
        this.tenViTri = tenViTri;
        this.loaiSlot = loaiSlot;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // --- GETTER & SETTER ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }

    public String getLoaiXe() { return loaiXe; }
    public void setLoaiXe(String loaiXe) { this.loaiXe = loaiXe; }

    public String getMauXe() { return mauXe; }
    public void setMauXe(String mauXe) { this.mauXe = mauXe; }

    public Date getNgayGui() { return ngayGui; }
    public void setNgayGui(Date ngayGui) { this.ngayGui = ngayGui; }

    public double getGiaTien() { return giaTien; }
    public void setGiaTien(double giaTien) { this.giaTien = giaTien; }

    public String getTenViTri() { return tenViTri; }
    public void setTenViTri(String tenViTri) { this.tenViTri = tenViTri; }

    public String getLoaiSlot() { return loaiSlot; }
    public void setLoaiSlot(String loaiSlot) { this.loaiSlot = loaiSlot; }

    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}