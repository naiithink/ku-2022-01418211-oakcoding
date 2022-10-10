package ku.cs.oakcoding.app.services.stages;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ku.cs.oakcoding.app.services.stages.components.ContentRoot;
import ku.cs.oakcoding.app.services.stages.components.Page;

public class XStage extends Stage {

    private Stage owner;

    private XStageStyle style;

    private final ContentRoot root;

    public XStage(Stage owner, XStageStyle style, ContentRoot root) {
        switch (style) {
            case SYSTEM -> super.initStyle(StageStyle.DECORATED);
            case DEFAULT, CUSTOM -> {
                super.initStyle(StageStyle.TRANSPARENT);
                root.clipChildren();
            }
        }

        this.owner = owner;
        this.style = style;
        this.root = root;
    }

    public XStageStyle getXStageStyle() {
        return style;
    }

    public ContentRoot getUnmodifiableRoot() {
        return this.root;
    }

    public void changePage(Page page, Paint fill) {
        root.getContent().changePage(page, fill);
    }

    public void changePage(Page page) {
        root.getContent().changePage(page);
    }

    public double getArc() {
        return this.root.arcProperty().get();
    }

    public void setArc(double arc) {
        this.root.arcProperty().set(arc);
    }

    public void toggleFullScreen() {
        if (super.isFullScreen()) {
            root.setNilArc();
            super.setFullScreen(false);
        } else {
            root.setToDefaultArc();
            super.setFullScreen(true);
        }
    }

    public void showStage() {
        this.show();
    }
}
