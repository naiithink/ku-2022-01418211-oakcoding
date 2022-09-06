package ku.cs.oakcoding.app.models.Complaint;

public class Complaint {
    private String topic;
    private String content;
    private String authorName;
    private String status;
    private int numVote;

    public Complaint(String topic, String content, String authorName){
        this(topic,content,authorName,"unfinished",0);
    }
    public Complaint(String topic, String content, String authorName, String status, int numVote) {
        this.topic = topic;
        this.content = content;
        this.authorName = authorName;
        this.status = status;
        this.numVote = numVote;
    }

    public boolean reportComplaint(){
        if (this.topic != null && this.content != null && this.authorName != null && this.status != null){
            return true;
        }
        return false;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumVote() {
        return numVote;
    }

    public void setNumVote(int numVote) {
        this.numVote = numVote;
    }
}
