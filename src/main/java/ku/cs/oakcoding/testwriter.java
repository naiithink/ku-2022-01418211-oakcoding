package ku.cs.oakcoding;


import ku.cs.oakcoding.app.services.FactoryDatabase;
import ku.cs.oakcoding.app.services.data_source.DataSourceCSV;
import ku.cs.oakcoding.app.constants.DataType;
import ku.cs.oakcoding.app.models.data.DataList;

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

//        DataManager dataManager = new DataManager();
//        dataManager.changePassword("ingfosbreak","qwertyuiop","191245ing");

        DataSourceCSV dataSourceListCSV = FactoryDatabase.getDataSource(DataType.USER);
        DataList dataList = (DataList) dataSourceListCSV.readData();


        dataSourceListCSV.writeData(dataList);

//        User testUser = (User) dataList.getUsersMap().get("QuarXZ");
//        Object newObj = testUser;
//        if (newObj instanceof User)
//            System.out.println("yes");




    }


}
