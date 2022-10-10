package ku.cs.oakcoding.app.services.stages.components;

public enum XStageControlButtonType {

    CLOSE                       ("#ed6a5e"),
    MINIMIZE                    ("#f5bf4f"),
    FULL_SCREEN_TOGGLE          ("#62c555");

    public final String hexColor;

    private XStageControlButtonType(String hexColor) {
        this.hexColor = hexColor;
    }
}
