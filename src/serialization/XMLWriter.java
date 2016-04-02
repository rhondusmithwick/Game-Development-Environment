//package serialization;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import java.io.File;
//
///**
// * Created by rhondusmithwick on 4/1/16.
// *
// * @author Rhondu Smithwick
// */
//public class XMLWriter<T> {
//    public final String myFile;
//
//    public XMLWriter(String myFile) {
//        this.myFile = myFile;
//    }
//
//    public void write(Class<T> theClass, T... objects) {
//        try {
//            File file = new File(myFile);
//            JAXBContext jaxbContext = JAXBContext.newInstance(theClass);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            for (T obj: objects) {
//                jaxbMarshaller.marshal(obj, file);
//                jaxbMarshaller.marshal(obj, System.out);
//            }
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
