package quanlybaixe.entity;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Vehicle")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Vehicle {
    protected int id;          // ID tự tăng số nguyên
    protected String bienSo;   // Thay cho name (Biển số xe)
    protected String loaiXe;   // Thay cho address (Ô tô, Xe máy...)
    protected Date ngayGui;    // Giữ kiểu Date (Ngày gửi xe vào bãi)
    protected double giaTien;  // Thuộc tính giá tiền để phục vụ tìm kiếm số và định dạng dấu phẩy

    public Vehicle() {
    }

    public Vehicle(int id, String bienSo, String loaiXe, Date ngayGui, double giaTien) {
        this.id = id;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.ngayGui = ngayGui;
        this.giaTien = giaTien;
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

    public Date getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(Date ngayGui) {
        this.ngayGui = ngayGui;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }
}