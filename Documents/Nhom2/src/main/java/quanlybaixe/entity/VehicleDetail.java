package quanlybaixe.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VehicleDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleDetail extends Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date ngayVaoBai;   // Thay cho OpeningDate
    private String mauXe;      // Thay cho type (Ví dụ: Đỏ, Đen, Trắng...)
    private byte[] anhXe;      // Giữ nguyên tính năng lưu ảnh (Chụp xe hoặc chụp biển số)

    public VehicleDetail() {
        super();
    }

    public VehicleDetail(int id, String bienSo, String loaiXe, Date ngayGui, double giaTien, 
                         String ngayVaoBai, String mauXe, byte[] anhXe) throws ParseException {
        super(id, bienSo, loaiXe, ngayGui, giaTien);
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        this.ngayVaoBai = fDate.parse(ngayVaoBai);
        this.mauXe = mauXe;
        this.anhXe = anhXe;
    }

    public Date getNgayVaoBai() {
        return this.ngayVaoBai;
    }

    public void setNgayVaoBai(Date ngayVaoBai) {
        this.ngayVaoBai = ngayVaoBai;
    }

    public String getMauXe() {
        return this.mauXe;
    }

    public void setMauXe(String mauXe) {
        this.mauXe = mauXe;
    }

    public byte[] getAnhXe() {
        return anhXe;
    }

    public void setAnhXe(byte[] anhXe) {
        this.anhXe = anhXe;
    }
}