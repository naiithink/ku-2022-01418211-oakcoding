package ku.cs.oakcoding.app.models.complaints;

public class Evidence {

    private ContentType contentType;

    private Object evidence;

    public Evidence(ContentType contentType, Object evidence) {
        this.contentType = contentType;
        this.evidence = evidence;
    }

    public ContentType getContentType() {
        return contentType;
    }

    protected void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Object getEvidence() {
        return evidence;
    }

    public void setEvidence(Object evidence) {
        this.evidence = evidence;
    }
}
