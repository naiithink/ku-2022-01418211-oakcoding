package ku.cs.oakcoding.app.services.stages.components;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class XStageControlButton extends Circle implements XButton<MouseEvent> {

    private XStageControlButtonType type;

    private EventHandler<MouseEvent> handler;

    private SimpleObjectProperty<Paint> color = new SimpleObjectProperty<>();

    protected XStageControlButton(XStageControlButtonType type, double radius, EventHandler<MouseEvent> handler) {
        super(radius);

        this.type = type;
        this.handler = handler;
        this.color.set(Color.valueOf(this.type.hexColor));
    }

    public XStageControlButtonType getXStageControlButtonType() {
        return this.type;
    }

    @Override
    public void handle(MouseEvent event) {
        this.handler.handle(event);
    }
}
