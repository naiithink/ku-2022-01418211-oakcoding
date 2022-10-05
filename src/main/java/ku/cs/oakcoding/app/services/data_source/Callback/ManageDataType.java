package ku.cs.oakcoding.app.services.data_source.Callback;

public interface ManageDataType<T>{
        public T instanceCreate(String [] data);
        public String instanceWrite(Object obj);
        public String getKey(String [] data);
        public String getQuoteFormat(Object o);
        public String formatCSV(Object o);
}
