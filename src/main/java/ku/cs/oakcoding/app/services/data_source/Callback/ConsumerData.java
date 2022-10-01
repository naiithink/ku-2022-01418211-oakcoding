package ku.cs.oakcoding.app.services.data_source.Callback;

import ku.cs.oakcoding.app.helpers.hotspot.BanStatus;
import ku.cs.oakcoding.app.models.users.Consumer;

public class ConsumerData implements ManageDataType<Consumer> {
    public ConsumerData(){}
    @Override
    public Consumer instanceCreate(String [] data) {
        Consumer consumer = new Consumer(BanStatus.valueOf(data[1].trim()),data[2].trim(),data[3].trim(),data[4].trim(),data[5].trim(),data[6].trim());
        return consumer;
    }
    @Override
    public String instanceWrite(Object obj) { return ((Consumer) obj).toString();}

    @Override
    public String getKey(String [] data){
        return data[2].trim();
    }
}
