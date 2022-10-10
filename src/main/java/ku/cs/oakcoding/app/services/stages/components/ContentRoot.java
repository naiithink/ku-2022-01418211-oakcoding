package ku.cs.oakcoding.app.services.stages.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class ContentRoot extends AnchorPane {

    private static final double DEFAULT_ARC = 22.0;

    private static final double NIL_ARC = 0.0;

    private final SimpleBooleanProperty isRoundedCorner = new SimpleBooleanProperty(true);

    private final SimpleDoubleProperty arcProperty = new SimpleDoubleProperty(DEFAULT_ARC);

    private XTitleBar titleBar;

    private ContentPane content;

    public ContentRoot(XTitleBar titleBar, ContentPane content, boolean isRoundedCorner) {
        super(titleBar, content);

        this.titleBar = titleBar;
        this.content = content;
        this.isRoundedCorner.set(isRoundedCorner);

        AnchorPane.setTopAnchor(titleBar, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);

        this.titleBar.toFront();

        this.content.getPageProperty().addListener((observable, oldValue, newValue) -> {
            this.titleBar.toFront();
        });

        if (this.isRoundedCorner.get()) {
            this.arcProperty.addListener((observable, oldValue, newValue) -> {
                Rectangle rootClipper = (Rectangle) this.content.getClip();
                rootClipper.setArcWidth(newValue.doubleValue());
                rootClipper.setArcHeight(newValue.doubleValue());
            });
        } else {
            this.arcProperty.set(NIL_ARC);
        }
    }

    public void clipChildren() {
        final Rectangle rootClipper = new Rectangle();

        rootClipper.setArcWidth(this.arcProperty.get());
        rootClipper.setArcHeight(this.arcProperty.get());

        this.setClip(rootClipper);

        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            rootClipper.setWidth(newValue.getWidth());
            rootClipper.setHeight(newValue.getHeight());
        });
    }

    public ContentPane getContent() {
        return this.content;
    }

    public boolean isRoundedCorner() {
        return this.isRoundedCorner.get();
    }

    public SimpleDoubleProperty arcProperty() {
        return this.arcProperty;
    }

    public double getArc() {
        return this.arcProperty.get();
    }

    public void setArc(double arc) {
        if (this.isRoundedCorner.get())
            this.arcProperty.set(arc);
    }

    public void setNilArc() {
        this.arcProperty.set(NIL_ARC);
    }

    public void setToDefaultArc() {
        if (this.isRoundedCorner.get())
            this.arcProperty.set(DEFAULT_ARC);
    }
}
