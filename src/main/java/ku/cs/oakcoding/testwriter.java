package ku.cs.oakcoding;

import ku.cs.oakcoding.app.models.IDmanager;

import java.io.*;

public class testwriter {
    public static void main(String[] args) {
//        File file = new File("data" + File.separator + "user.csv");
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

        IDmanager iDmanager = new IDmanager();
        iDmanager.changePassword("ingfosbreak","qwertyuiop","191245ing");


    }


}
