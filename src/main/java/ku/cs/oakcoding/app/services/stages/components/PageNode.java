package ku.cs.oakcoding.app.services.stages.components;

import javafx.scene.paint.Paint;

public class PageNode extends Page {

    private PageNode previous;

    private PageNode next;

    public PageNode(PageNode previous, boolean isResponsive, Paint defaultFill) {
        super(isResponsive, defaultFill);

        this.previous = previous;
    }

    public PageNode getPrevious() {
        return this.previous;
    }

    public PageNode getNext() {
        return this.next;
    }

    public void setNext(PageNode next) {
        this.next = next;
    }
}
