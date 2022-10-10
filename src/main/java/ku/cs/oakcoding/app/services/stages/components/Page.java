package ku.cs.oakcoding.app.services.stages.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Page extends Parent {

    private SimpleBooleanProperty isResponsive = new SimpleBooleanProperty(true);

    private SimpleObjectProperty<Paint> defaultFill = new SimpleObjectProperty<>(new Color(0.0, 0.0, 0.0, 1.0));

    public Page(boolean isResponsive, Paint defaultFill) {
        super();

        this.isResponsive.set(isResponsive);

        if (defaultFill != null) {
            this.defaultFill.set(defaultFill);
        }
    }

    public Page(boolean isResponsive) {
        this(isResponsive, null);
    }

    public Page(Paint defaultFill) {
        this(true, defaultFill);
    }

    public Page() {
        this(true, null);
    }

    public boolean getIsResponsive() {
        return isResponsive.get();
    }

    public Paint getDefaultFill() {
        return this.defaultFill.get();
    }
}
