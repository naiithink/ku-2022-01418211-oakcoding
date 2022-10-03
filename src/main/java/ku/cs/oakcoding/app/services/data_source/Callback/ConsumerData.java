package ku.cs.oakcoding.app.services.data_source.Callback;

import ku.cs.oakcoding.app.helpers.hotspot.BanStatus;
import ku.cs.oakcoding.app.models.ConsumerUser;
import ku.cs.oakcoding.app.models.users.Consumer;

public class ConsumerData implements ManageDataType<ConsumerUser> {
    public ConsumerData(){}
    @Override
    public ConsumerUser instanceCreate(String [] data) {

        ConsumerUser consumer = new ConsumerUser(BanStatus.valueOf(data[1].trim()),data[2].trim(),data[3].trim(),data[4].trim(),data[5].trim(),data[6].trim());
        return consumer;
    }
    @Override
    public String instanceWrite(Object obj) { return ((ConsumerUser) obj).formatCSV();}

    @Override
    public String getKey(String [] data){
        return data[2].trim();
    }
}
