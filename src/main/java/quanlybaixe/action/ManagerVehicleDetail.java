package quanlybaixe.action;

import quanlybaixe.entity.VehicleDetail;
import quanlybaixe.entity.VehicleDetailXML;
import quanlybaixe.utils.FileUtils;
import quanlybaixe.view.ManagerView; 
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class ManagerVehicleDetail {
    private static final String VEHICLE_DETAIL_FILE_NAME = "VehicleDetails.xml";
    private List<VehicleDetail> listVehicleDetails;

    public ManagerVehicleDetail() {
        this.listVehicleDetails = readListVehicleDetails();
        if (listVehicleDetails == null) {
            listVehicleDetails = new ArrayList<VehicleDetail>();
        }
    }

    public void writeListVehicleDetails(List<VehicleDetail> details) {
        VehicleDetailXML xmlWrapper = new VehicleDetailXML();
        xmlWrapper.setVehicleDetails(details);
        FileUtils.writeXMLtoFile(VEHICLE_DETAIL_FILE_NAME, xmlWrapper);
    }

    public List<VehicleDetail> readListVehicleDetails() {
        List<VehicleDetail> list = new ArrayList<VehicleDetail>();
        VehicleDetailXML xmlWrapper = (VehicleDetailXML) FileUtils.readXMLFile(VEHICLE_DETAIL_FILE_NAME, VehicleDetailXML.class);
        if (xmlWrapper != null && xmlWrapper.getVehicleDetails() != null) {
            list = xmlWrapper.getVehicleDetails();
        }
        return list;
    }
    
    // Tìm kiếm xe theo Biển số xe
    public List<VehicleDetail> searchByBienSo(String search) {
        List<VehicleDetail> temp = new ArrayList<VehicleDetail>();
        for (VehicleDetail detail : listVehicleDetails) {
            if (detail.getBienSo() != null && detail.getBienSo().toLowerCase().contains(search.toLowerCase())) {
                temp.add(detail);
            }
        }
        return temp;
    }
    
    // Tìm kiếm xe theo Loại xe (Ô tô, xe máy...)
    public List<VehicleDetail> searchByLoaiXe(String search) {
        List<VehicleDetail> temp = new ArrayList<VehicleDetail>();
        for (VehicleDetail detail : listVehicleDetails) {
            if (detail.getLoaiXe() != null && detail.getLoaiXe().toLowerCase().contains(search.toLowerCase())) {
                temp.add(detail);
            }
        }
        return temp;
    }

    // Tìm kiếm xe theo Màu xe
    public List<VehicleDetail> searchByMauXe(String search) {
        List<VehicleDetail> temp = new ArrayList<VehicleDetail>();
        for (VehicleDetail detail : listVehicleDetails) {
            if (detail.getMauXe() != null && detail.getMauXe().toLowerCase().contains(search.toLowerCase())) {
                temp.add(detail);
            }
        }
        return temp;
    }

    public void add(VehicleDetail detail) {
        int max = 0;
        for (int i = 0; i < listVehicleDetails.size(); i++) {
            if (listVehicleDetails.get(i).getId() > max) {
                max = listVehicleDetails.get(i).getId();
            }
        }
        detail.setId(max + 1);
        listVehicleDetails.add(detail);
        writeListVehicleDetails(listVehicleDetails);
    }

    public void edit(VehicleDetail detail) throws ParseException {
        int size = listVehicleDetails.size();
        for (int i = 0; i < size; i++) {
            if (listVehicleDetails.get(i).getId() == detail.getId()) {
                listVehicleDetails.get(i).setBienSo(detail.getBienSo());
                listVehicleDetails.get(i).setLoaiXe(detail.getLoaiXe());
                listVehicleDetails.get(i).setNgayGui(detail.getNgayGui());
                listVehicleDetails.get(i).setGiaTien(detail.getGiaTien());
                listVehicleDetails.get(i).setNgayVaoBai(detail.getNgayVaoBai());
                listVehicleDetails.get(i).setMauXe(detail.getMauXe());
                listVehicleDetails.get(i).setAnhXe(detail.getAnhXe());
                writeListVehicleDetails(listVehicleDetails);
                break;
            }
        }
    }
      
    public boolean delete(VehicleDetail detail) {
        boolean isFound = false;
        int size = listVehicleDetails.size();
        for (int i = 0; i < size; i++) {
            if (listVehicleDetails.get(i).getId() == detail.getId()) {
                detail = listVehicleDetails.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            listVehicleDetails.remove(detail);
            writeListVehicleDetails(listVehicleDetails);
            return true;
        }
        return false;
    }

    // Sắp xếp xe theo Biển số xe tiếng Việt
    public void sortDetailsByBienSo() {
        Collections.sort(listVehicleDetails, new Comparator<VehicleDetail>() {
            public int compare(VehicleDetail p1, VehicleDetail p2) {
                Collator collator = Collator.getInstance(new Locale("vi", "VN"));
                return collator.compare(p1.getBienSo(), p2.getBienSo());
            }
        });
    }
    
    // Sắp xếp theo ID
    public void sortDetailsByID() {
        Collections.sort(listVehicleDetails, new Comparator<VehicleDetail>() {
            public int compare(VehicleDetail p1, VehicleDetail p2) {
                return Integer.compare(p1.getId(), p2.getId());
            }
        });
    }
    
    // Sắp xếp theo Ngày vào bãi
    public void sortDetailsByNgayVaoBai() {
        Collections.sort(listVehicleDetails, new Comparator<VehicleDetail>() {
            public int compare(VehicleDetail p1, VehicleDetail p2) {
                if (p1.getNgayVaoBai() == null || p2.getNgayVaoBai() == null) return 0;
                return p1.getNgayVaoBai().compareTo(p2.getNgayVaoBai());
            }
        });
    }

    public List<VehicleDetail> getListVehicleDetails() {
        return listVehicleDetails;
    }

    public void setListVehicleDetails(List<VehicleDetail> listVehicleDetails) {
        this.listVehicleDetails = listVehicleDetails;
    }

    public List<VehicleDetail> getListSpecialPersons() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<VehicleDetail> searchSpecialPersonAddress(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<VehicleDetail> searchSpecialPersonName(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<VehicleDetail> searchSpecialPersonYear(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void sortSpecialPersonByOpeningDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void sortSpecialPersonByName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void sortSpecialPersonByBirthDay() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void sortSpecialPersonByID() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}