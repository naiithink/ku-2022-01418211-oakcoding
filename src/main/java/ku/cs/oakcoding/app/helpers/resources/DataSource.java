package ku.cs.oakcoding.app.helpers.resources;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private List<String> header;

    private List<String>[][] data;

    public DataSource(List<String> header, List<List<String>> data) {
        this.header = header;
        this.data = data;
    }

    public List<List<String>> getData() {
        List<List<String>> result = new ArrayList<>(data);
        result.add(0, header);

        return result;
    }

    public List<String> getRecordAt(String headerSelector,
                                    String containingContraint) {

        int columnIndex;

        for (String s : header) {
            if (s.equals(headerSelector)) {
                columnIndex = header.indexOf(s);

                break;
            }
        }

        for ()
    }

    public List<String> getValueWhen(String headerSelector,
                                     String containingContraint) {

        int columnIndex;

        for (String s : header) {
            if (s.equals(headerSelector)) {
                columnIndex = header.indexOf(s);
            }
        }

        for (String s : )

        return 
    }
}
