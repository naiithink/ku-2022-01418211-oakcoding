package ku.cs.oakcoding.app.services;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.org.Department;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public final class WorkspaceService implements AppService {

    private static WorkspaceManager workspaceManager;

    private static AutoUpdateCSV departmentDataSource;

    public WorkspaceService() {}

    @Override
    public void start() {
        try {
            departmentDataSource = new AutoUpdateCSV("departments", "DEP_ID", "departments", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value()).resolve(OakAppDefaults.APP_DEP_FILE.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Department entry file not found");
            System.exit(1);
        }

        Set<Department> orgEntrySet = new HashSet<>();
        Set<String> primaryKeySet = departmentDataSource.getPrimaryKeySet();

        for (String primaryKey : primaryKeySet) {
            orgEntrySet.add(new Department(primaryKey, null));
        }

        workspaceManager = new WorkspaceManager(departmentDataSource);
    }

    public static WorkspaceManager getWorkspaceManager() {
        return workspaceManager;
    }
}
