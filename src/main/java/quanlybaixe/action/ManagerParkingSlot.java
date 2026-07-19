package quanlybaixe.action;

import quanlybaixe.entity.ParkingSlotXML;
import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.utils.FileUtils;
import quanlybaixe.view.ResidentView; // Lưu ý: Tên gói view và utils sẽ tự hết đỏ sau khi bạn Refactor đổi tên các gói đó ở bước sau
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

public class ManagerParkingSlot {
    private static final String PARKING_FILE_NAME = "ParkingSlots.xml";
    private List<ParkingSlot> listParkingSlots;
    private ResidentView residentView; 
    
    public ManagerParkingSlot() {
        this.listParkingSlots = readListParkingSlots();
        if (listParkingSlots == null) {
            listParkingSlots = new ArrayList<ParkingSlot>();
        }
    }
    
    public List<ParkingSlot> readListParkingSlots() {
        List<ParkingSlot> list = new ArrayList<ParkingSlot>();
        ParkingSlotXML parkingXML = (ParkingSlotXML) FileUtils.readXMLFile(PARKING_FILE_NAME, ParkingSlotXML.class);
        if (parkingXML != null && parkingXML.getParkingSlots() != null) {
            list = parkingXML.getParkingSlots();
        }
        return list;
    }
    
    public void writeListParkingSlots(List<ParkingSlot> slots) {
        ParkingSlotXML parkingXML = new ParkingSlotXML();
        parkingXML.setParkingSlots(slots);
        FileUtils.writeXMLtoFile(PARKING_FILE_NAME, parkingXML);
    }
    
    // Tìm kiếm theo Biển số xe
    public List<ParkingSlot> searchByBienSo(String search) {
        List<ParkingSlot> temp = new ArrayList<ParkingSlot>();
        for (ParkingSlot slot : listParkingSlots) {
            if (slot.getBienSo() != null && slot.getBienSo().toLowerCase().contains(search.toLowerCase())) {
                temp.add(slot);
            }
        }
        return temp;
    }
    
    // Tìm kiếm theo Tên vị trí đỗ
    public List<ParkingSlot> searchByTenViTri(String search) {
        List<ParkingSlot> temp = new ArrayList<ParkingSlot>();
        for (ParkingSlot slot : listParkingSlots) {
            if (slot.getTenViTri() != null && slot.getTenViTri().toLowerCase().contains(search.toLowerCase())) {
                temp.add(slot);
            }
        }
        return temp;
    }
    
    // Tìm kiếm theo Loại xe
    public List<ParkingSlot> searchByLoaiXe(String search) {
        List<ParkingSlot> temp = new ArrayList<ParkingSlot>();
        for (ParkingSlot slot : listParkingSlots) {
            if (slot.getLoaiXe() != null && slot.getLoaiXe().toLowerCase().contains(search.toLowerCase())) {
                temp.add(slot);
            }
        }
        return temp;
    }
    
    public void add(ParkingSlot slot) {
        int max = 0;
        for (int i = 0; i < listParkingSlots.size(); i++) {
            if (listParkingSlots.get(i).getId() > max) {
                max = listParkingSlots.get(i).getId();
            }
        }
        slot.setId(max + 1);
        listParkingSlots.add(slot);
        writeListParkingSlots(listParkingSlots);
    }
    
    // Kiểm tra vị trí đỗ trùng lặp
    public boolean isViTriUnique(ParkingSlot slot) {
        String viTri = slot.getTenViTri();
        for (ParkingSlot existingSlot : listParkingSlots) {
            if (existingSlot.getTenViTri().equalsIgnoreCase(viTri) && existingSlot.getId() != slot.getId()) {
                return false; 
            }
        }
        return true; 
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(residentView, message);
    }
    
    public void edit(ParkingSlot slot) throws ParseException {
        int size = listParkingSlots.size();
        for (int i = 0; i < size; i++) {
            if (listParkingSlots.get(i).getId() == slot.getId()) {
                listParkingSlots.get(i).setBienSo(slot.getBienSo());
                listParkingSlots.get(i).setLoaiXe(slot.getLoaiXe());
                listParkingSlots.get(i).setNgayGui(slot.getNgayGui());
                listParkingSlots.get(i).setGiaTien(slot.getGiaTien());
                listParkingSlots.get(i).setTenViTri(slot.getTenViTri());
                listParkingSlots.get(i).setLoaiSlot(slot.getLoaiSlot());
                listParkingSlots.get(i).setTrangThai(slot.isTrangThai());
                listParkingSlots.get(i).setGhiChu(slot.getGhiChu());

                writeListParkingSlots(listParkingSlots);
                break;
            }
        }
    }
    
    public boolean delete(ParkingSlot slot) {
        boolean isFound = false;
        int size = listParkingSlots.size();
        for (int i = 0; i < size; i++) {
            if (listParkingSlots.get(i).getId() == slot.getId()) {
                listParkingSlots.remove(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            for (int i = 0; i < listParkingSlots.size(); i++) {
                if (listParkingSlots.get(i).getId() > slot.getId()) {
                    listParkingSlots.get(i).setId(listParkingSlots.get(i).getId() - 1);
                }
            }
            writeListParkingSlots(listParkingSlots);
            return true;
        }
        return false;
    }
    
    // Sắp xếp theo vị trí đỗ bãi xe tiếng Việt
    public void sortSlotsByViTri() {
        Collections.sort(listParkingSlots, new Comparator<ParkingSlot>() {
            public int compare(ParkingSlot p1, ParkingSlot p2) {
                Collator collator = Collator.getInstance(new Locale("vi", "VN"));
                return collator.compare(p1.getTenViTri(), p2.getTenViTri());
            }
        });
    }
    
    // Sắp xếp theo giá tiền đỗ xe tăng dần
    public void sortSlotsByGiaTien() {
        Collections.sort(listParkingSlots, new Comparator<ParkingSlot>() {
            public int compare(ParkingSlot p1, ParkingSlot p2) {
                return Double.compare(p1.getGiaTien(), p2.getGiaTien());
            }
        });
    }
    
    // Sắp xếp theo ID vị trí xe
    public void sortSlotsByID() {
        Collections.sort(listParkingSlots, new Comparator<ParkingSlot>() {
            public int compare(ParkingSlot p1, ParkingSlot p2) {
                return Integer.compare(p1.getId(), p2.getId());
            }
        });
    }
    
    public List<ParkingSlot> getListParkingSlots() {
        return this.listParkingSlots;
    }
}