package ku.cs.oakcoding.app.services.data_source;

public interface ManageDataType<T>{
        public T instanceCreate(String [] data);
        public String instanceWrite(Object obj);
        public String getKey(String [] data);

}
