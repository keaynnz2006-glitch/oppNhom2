package quanlybaixe.entity;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter; // <-- THÊM DÒNG NÀY
import quanlybaixe.utils.DateAdapter; // <-- THÊM DÒNG NÀY (Nhớ chỉnh đúng package nếu cần)

@XmlRootElement(name = "VehicleDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String bienSo;
    private String loaiXe;
    private String mauXe;

    @XmlJavaTypeAdapter(DateAdapter.class) // <-- THÊM DÒNG NÀY (BẮT BUỘC)
    private Date ngayVaoBai;

    private String hinhAnh;
    private String viTriDo;
    private double giaTien;

    public VehicleDetail() {}

    public VehicleDetail(int id, String bienSo, String loaiXe, String mauXe, Date ngayVaoBai, String hinhAnh, String viTriDo, double giaTien) {
        this.id = id;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.mauXe = mauXe;
        this.ngayVaoBai = ngayVaoBai;
        this.hinhAnh = hinhAnh;
        this.viTriDo = viTriDo;
        this.giaTien = giaTien;
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

    public Date getNgayVaoBai() { return ngayVaoBai; }
    public void setNgayVaoBai(Date ngayVaoBai) { this.ngayVaoBai = ngayVaoBai; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }

    public String getAnhXe() { return hinhAnh; }
    public void setAnhXe(String anhXe) { this.hinhAnh = anhXe; }

    public String getViTriDo() { return viTriDo; }
    public void setViTriDo(String viTriDo) { this.viTriDo = viTriDo; }

    public double getGiaTien() { return giaTien; }
    public void setGiaTien(double giaTien) { this.giaTien = giaTien; }
}