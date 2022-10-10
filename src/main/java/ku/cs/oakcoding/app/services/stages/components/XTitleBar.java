package ku.cs.oakcoding.app.services.stages.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class XTitleBar
        extends HBox {

    private static final Paint DEFAULT_BACKGROUND_FILL = Color.valueOf("#ffffff");

    private final SimpleObjectProperty<Paint> backgroundFillProperty = new SimpleObjectProperty<>(DEFAULT_BACKGROUND_FILL);

    private final SimpleBooleanProperty xStageControlButtonAlignLeft = new SimpleBooleanProperty(true);

    public XTitleBar(Paint backgroundFill, boolean xStageControlButtonAlignLeft) {
        super();

        this.xStageControlButtonAlignLeft.set(xStageControlButtonAlignLeft);
        this.backgroundFillProperty.set(backgroundFill);
        super.setBackground(new Background(new BackgroundFill(this.backgroundFillProperty.get(), null, null)));
    }

    public XTitleBar(Paint backgroundFill) {
        this(backgroundFill, true);
    }

    public SimpleBooleanProperty getXStageControlButtonAlignLeftProperty() {
        return this.xStageControlButtonAlignLeft;
    }

    public boolean getXStageControlButtonAlignLeft() {
        return this.xStageControlButtonAlignLeft.get();
    }

    public void setXStageControlButtonAlignLeft(boolean xStageControlButtonAlignLeft) {
        this.xStageControlButtonAlignLeft.set(xStageControlButtonAlignLeft);
    }
}
