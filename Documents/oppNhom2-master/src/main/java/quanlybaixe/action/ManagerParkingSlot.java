package quanlybaixe.action;

import quanlybaixe.entity.ParkingSlotXML;
import quanlybaixe.entity.ParkingSlot;
import quanlybaixe.utils.FileUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ManagerParkingSlot {
    private static final String PARKING_FILE_NAME = "ParkingSlots.xml";
    private List<ParkingSlot> listParkingSlots;
    
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
    
    // --- TÌM KIẾM ---
    
    // Tìm kiếm theo Tên vị trí đỗ (Ví dụ: A01, B02)
    public List<ParkingSlot> searchByTenViTri(String search) {
        List<ParkingSlot> temp = new ArrayList<ParkingSlot>();
        if (search == null) return temp;
        for (ParkingSlot slot : listParkingSlots) {
            if (slot.getTenViTri() != null && slot.getTenViTri().toLowerCase().contains(search.toLowerCase())) {
                temp.add(slot);
            }
        }
        return temp;
    }
    
    // Tìm kiếm theo Loại Slot (Ví dụ: Xe máy, Ô tô, VIP)
    public List<ParkingSlot> searchByLoaiSlot(String search) {
        List<ParkingSlot> temp = new ArrayList<ParkingSlot>();
        if (search == null) return temp;
        for (ParkingSlot slot : listParkingSlots) {
            if (slot.getLoaiSlot() != null && slot.getLoaiSlot().toLowerCase().contains(search.toLowerCase())) {
                temp.add(slot);
            }
        }
        return temp;
    }
    
    // crud

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
    
    // Kiểm tra tên vị trí đỗ trùng lặp
    public boolean isViTriUnique(ParkingSlot slot) {
        String viTri = slot.getTenViTri();
        if (viTri == null) return true;
        for (ParkingSlot existingSlot : listParkingSlots) {
            if (existingSlot.getTenViTri() != null && 
                existingSlot.getTenViTri().equalsIgnoreCase(viTri) && 
                existingSlot.getId() != slot.getId()) {
                return false; 
            }
        }
        return true; 
    }
    
    public void edit(ParkingSlot slot) {
        int size = listParkingSlots.size();
        for (int i = 0; i < size; i++) {
            if (listParkingSlots.get(i).getId() == slot.getId()) {
                listParkingSlots.get(i).setTenViTri(slot.getTenViTri());
                listParkingSlots.get(i).setLoaiSlot(slot.getLoaiSlot());
                listParkingSlots.get(i).setGiaTien(slot.getGiaTien());
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
    
    // --- SẮP XẾP ---

    // Sắp xếp theo tên vị trí đỗ bãi xe tiếng Việt
    public void sortSlotsByViTri() {
        Collections.sort(listParkingSlots, new Comparator<ParkingSlot>() {
            public int compare(ParkingSlot p1, ParkingSlot p2) {
                Collator collator = Collator.getInstance(new Locale("vi", "VN"));
                String v1 = p1.getTenViTri() != null ? p1.getTenViTri() : "";
                String v2 = p2.getTenViTri() != null ? p2.getTenViTri() : "";
                return collator.compare(v1, v2);
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
    
    // Sắp xếp theo ID vị trí
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

    // --- BỔ SUNG: CẬP NHẬT TRẠNG THÁI Ô ĐỖ KHI THÊM/XÓA XE ---
    public void updateSlotStatus(String tenViTri, boolean isOccupied) {
        if (tenViTri == null) return;
        for (ParkingSlot slot : listParkingSlots) {
            if (slot.getTenViTri() != null && slot.getTenViTri().equalsIgnoreCase(tenViTri)) {
                slot.setTrangThai(isOccupied); // true = Đã có xe, false = Trống
                writeListParkingSlots(listParkingSlots);
                break;
            }
        }
    }

    // --- BỔ SUNG: LẤY DANH SÁCH VỊ TRÍ CÒN TRỐNG ĐỂ CHỌN KHI CHO XE MỚI VÀO ---
    public List<ParkingSlot> getAvailableParkingSlots() {
        List<ParkingSlot> availableSlots = new ArrayList<ParkingSlot>();
        for (ParkingSlot slot : listParkingSlots) {
            if (!slot.isTrangThai()) { // Chỉ lấy chỗ có trangThai == false (còn trống)
                availableSlots.add(slot);
            }
        }
        return availableSlots;
    }
}