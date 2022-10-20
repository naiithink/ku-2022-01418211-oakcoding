package ku.cs.oakcoding.app.models.complaints;

import java.nio.file.Path;

public class Evidence {

    private final String EVIDENCE_ID;

    private Path evidencePath;

    public Evidence(String evidenceID, Path evidencePath) {
        this.EVIDENCE_ID = evidenceID;
        this.evidencePath = evidencePath;
    }

    public String getEvidenceID() {
        return EVIDENCE_ID;
    }

    public Path getEvidencePath() {
        return evidencePath;
    }

    public void setEvidencePath(Path evidencePath) {
        this.evidencePath = evidencePath;
    }
}
