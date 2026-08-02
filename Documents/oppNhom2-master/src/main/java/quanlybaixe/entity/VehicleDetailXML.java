package quanlybaixe.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VehicleDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleDetailXML {
    
    @XmlElement(name = "VehicleDetail")
    private List<VehicleDetail> vehicleDetails;

    public List<VehicleDetail> getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(List<VehicleDetail> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }
}