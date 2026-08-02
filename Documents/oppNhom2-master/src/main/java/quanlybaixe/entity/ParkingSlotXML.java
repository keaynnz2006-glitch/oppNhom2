package quanlybaixe.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ParkingSlots") // Tên tag bao ngoài trong file XML
@XmlAccessorType(XmlAccessType.FIELD)
public class ParkingSlotXML {
    
    @XmlElement(name = "ParkingSlot") // Tên tag cho từng phần tử bên trong
    private List<ParkingSlot> parkingSlots;

    public List<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    public void setParkingSlots(List<ParkingSlot> parkingSlots) {
        this.parkingSlots = parkingSlots;
    } 
}