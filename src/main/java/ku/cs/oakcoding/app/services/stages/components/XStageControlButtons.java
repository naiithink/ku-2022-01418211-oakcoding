package ku.cs.oakcoding.app.services.stages.components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import ku.cs.oakcoding.app.services.stages.XStage;

public class XStageControlButtons {

    protected XStageControlButtons() {}

    protected static final double DEFAULT_RADIUS = 6.5;

    public static XStageControlButton newXStageControlButton(XStage stage, double radius, XStageControlButtonType type) {
        XStageControlButton stageControlButton = null;

        switch (type) {
            case CLOSE:
                stageControlButton = new XStageControlButton(type, radius, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                    }
                });

            case MINIMIZE:
                stageControlButton = new XStageControlButton(type, radius, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        stage.setIconified(true);
                    }
                });

            case FULL_SCREEN_TOGGLE:
                stageControlButton = new XStageControlButton(type, radius, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if (stage.getStyle().equals(StageStyle.TRANSPARENT)) {

                        }

                        if (stage.isFullScreen()) {
                            stage.setFullScreen(false);
                        } else {
                            stage.setFullScreen(true);
                        }
                    }
                });
        }

        return stageControlButton;
    }

    public static XStageControlButton newXStageControlButton(XStage stage, XStageControlButtonType type) {
        return newXStageControlButton(stage, DEFAULT_RADIUS, type);
    }
}
