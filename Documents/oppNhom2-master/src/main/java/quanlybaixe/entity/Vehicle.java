package quanlybaixe.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Vehicle")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Vehicle {
    protected int id;          // ID tự tăng
    protected String bienSo;   // Biển số xe
    protected String loaiXe;   // Loại xe (Ô tô, Xe máy...)
    protected String mauXe;    // Màu xe

    public Vehicle() {
    }

    public Vehicle(int id, String bienSo, String loaiXe, String mauXe) {
        this.id = id;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.mauXe = mauXe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public String getMauXe() {
        return mauXe;
    }

    public void setMauXe(String mauXe) {
        this.mauXe = mauXe;
    }
}