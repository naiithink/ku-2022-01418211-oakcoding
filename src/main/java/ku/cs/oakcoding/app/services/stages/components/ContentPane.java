package ku.cs.oakcoding.app.services.stages.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ContentPane extends StackPane {

    private SimpleObjectProperty<Page> pageProperty = new SimpleObjectProperty<>();

    private final SimpleObjectProperty<Paint> defaultFill = new SimpleObjectProperty<>(new Color(0.0, 0.0, 0.0, 1.0));

    private final SimpleBooleanProperty isUsingDefaultFill = new SimpleBooleanProperty(true);

    public ContentPane(Page page, Paint defaultFill) {
        super(page);
        super.setAlignment(Pos.CENTER);

        this.pageProperty.set(page);

        if (defaultFill != null)
            this.defaultFill.set(defaultFill);

        super.setBackground(new Background(new BackgroundFill(this.defaultFill.get(), null, null)));

        this.defaultFill.addListener((observer, oldValue, newValue) -> {
            if (this.isUsingDefaultFill.get())
                super.setBackground(new Background(new BackgroundFill(this.defaultFill.get(), null, null)));
        });
    }

    public ContentPane(Page page) {
        this(page, null);
    }

    public SimpleObjectProperty<Page> getPageProperty() {
        return this.pageProperty;
    }

    public void changePage(Page page, Paint fill) {
        super.getChildren().remove(this.pageProperty.get());
        super.getChildren().add(page);
        this.pageProperty.set(page);

        this.isUsingDefaultFill.set(false);
    }

    public void changePage(Page page) {
        if (page.getDefaultFill() != null) {
            changePage(page, page.getDefaultFill());
            this.isUsingDefaultFill.set(false);
        } else {
            changePage(page, this.defaultFill.get());
            this.isUsingDefaultFill.set(true);
        }
    }

    public void changeDefaultFillTo(Paint defaultFill) {
        this.defaultFill.set(defaultFill);
    }
}
