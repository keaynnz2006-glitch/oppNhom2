package quanlybaixe.utils;

import java.io.File;
import java.io.FileWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class FileUtils {

    /**
     * Chuyển đổi đối tượng object về định dạng XML
     * Sau đó lưu vào fileName
     * 
     * @param fileName
     * @param object
     */
    public static void writeXMLtoFile(String fileName, Object object) {
        try {
            // tạo đối tượng JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            // Create đối tượng Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // formating 
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // lưu nội dung XML vào file
            File xmlFile = new File(fileName);
            jaxbMarshaller.marshal(object, xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Đọc nội dung fileName, sau đó chuyển đổi nội dung của file 
     * thành đối tượng có kiểu là clazz.
     * Tự động khởi tạo file XML nếu chưa tồn tại.
     * 
     * @param fileName
     * @param clazz
     * @return
     */
    public static Object readXMLFile(String fileName, Class<?> clazz) {
        try {
            File xmlFile = new File(fileName);

            // Xử lý an toàn: Nếu file chưa tồn tại -> Tự tạo file mới có thẻ root chuẩn
            if (!xmlFile.exists()) {
                String rootTag = clazz.getSimpleName().toLowerCase() + "s";
                if (clazz.getSimpleName().equals("VehicleDetail")) {
                    rootTag = "vehicleDetails";
                } else if (clazz.getSimpleName().equals("ParkingSlot")) {
                    rootTag = "parkingSlots";
                }

                try (FileWriter writer = new FileWriter(xmlFile)) {
                    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
                    writer.write("<" + rootTag + ">\n</" + rootTag + ">");
                }
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return jaxbUnmarshaller.unmarshal(xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}