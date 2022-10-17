package ku.cs.oakcoding.app.models.org;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public class WorkspaceManager {

    private final ObservableMap<String, Organization> orgTable;

    private AutoUpdateCSV orgIndexFile;

    public WorkspaceManager(AutoUpdateCSV orgIndexFile) {
        this.orgTable = FXCollections.observableHashMap();
        this.orgIndexFile = orgIndexFile;

        Set<String> orgKeys = orgIndexFile.getPrimaryKeySet();

        for (String key : orgKeys) {
            Organization org = new Organization(key, this.orgIndexFile.getDataWhere(key, "ORG_NAME"));
            this.orgTable.put(key, org);
        }
    }
}
