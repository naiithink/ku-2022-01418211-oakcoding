//package ku.cs.oakcoding.app.services.Complaint;
//
//import ku.cs.oakcoding.app.models.Complaint.Complaint;
//import ku.cs.oakcoding.app.services.DataSourceCSV;
//
//import java.io.*;
//
//public class ComplaintDataSource implements DataSourceCSV<Complaint> {
//    private String directoryName;
//    private String fileName;
//
//    public ComplaintDataSource(String directoryName, String fileName) {
//        this.directoryName = directoryName;
//        this.fileName = fileName;
//        checkFileIsExisted();
//    }
//
//    private void checkFileIsExisted() {
//        File file = new File(directoryName);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//
//        String filePath = directoryName + File.separator + fileName;
//        file = new File(filePath);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//    @Override
//    public boolean checkID(String username,String password,String typeCheck){
//        return false;
//    }
//    @Override
//    public Complaint readThatData(String username,String password){
//        Complaint complaint = new Complaint("","","","",Integer.parseInt("2"));
//        return complaint;
//    }
//
//    @Override
//    public void writeData(Complaint complaintObject) {
//        String filePath = directoryName + File.separator + fileName;
//        File file = new File(filePath);
//
//        FileWriter fileWriter = null;
//        BufferedWriter writer = null;
//
//        try {
//            fileWriter = new FileWriter(file,true);
//            writer = new BufferedWriter(fileWriter);
//
//
//            String line = complaintObject.getTopic() + ","
//                    + complaintObject.getContent() + ","
//                    + complaintObject.getAuthorName() + ","
//                    + complaintObject.getStatus() + ","
//                    + complaintObject.getNumVote();
//            writer.append(line);
//            writer.newLine();
//
//
//            writer.close();
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void clearData(){
//        String filePath = directoryName + File.separator + fileName;
//        File file = new File(filePath);
//
//        FileWriter fileWriter = null;
//        BufferedWriter writer = null;
//        try {
//            fileWriter = new FileWriter(file);
//            writer = new BufferedWriter(fileWriter);
//
//            String line = "";
//            writer.append(line);
//            writer.close();
//
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//}
