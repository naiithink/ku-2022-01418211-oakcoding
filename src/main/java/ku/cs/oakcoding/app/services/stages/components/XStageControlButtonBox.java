package ku.cs.oakcoding.app.services.stages.components;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.layout.HBox;

public class XStageControlButtonBox extends HBox {

    private final ObservableList<XStageControlButton> xStageControlButtonList = FXCollections.observableArrayList();

    private final ObservableMap<XStageControlButtonType, Integer> buttonArrangementMap = FXCollections.observableHashMap();

    /**
     * @todo Test listener
     * @todo Test rearrangement
     */
    public XStageControlButtonBox(XStageControlButton... xStageControlButtons) {
        super();

        buttonArrangementMap.addListener(new MapChangeListener<XStageControlButtonType, Integer>() {

            @Override
            public void onChanged(Change<? extends XStageControlButtonType, ? extends Integer> change) {
                for (XStageControlButtonType type : XStageControlButtonType.values()) {
                    xStageControlButtonList.set(buttonArrangementMap.get(type), getXStageControlButton(type));
                }
            }
        });

        xStageControlButtonList.addListener(new ListChangeListener<XStageControlButton>() {

            @Override
            public void onChanged(Change<? extends XStageControlButton> change) {
                XStageControlButtonBox.super.getChildren().setAll(xStageControlButtonList);
            }
        });
    }

    public void addXStageControlButton(XStageControlButton button) {
        this.xStageControlButtonList.add(button);
    }

    public void removeXStageControlButton(XStageControlButton button) {
        this.xStageControlButtonList.remove(button);
    }

    private XStageControlButton getXStageControlButton(XStageControlButtonType type) {
        for (XStageControlButton button : xStageControlButtonList) {
            if (button.getXStageControlButtonType().equals(type))
                return button;
        }

        return null;
    }
}
