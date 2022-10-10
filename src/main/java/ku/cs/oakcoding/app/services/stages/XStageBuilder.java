package ku.cs.oakcoding.app.services.stages;

import ku.cs.oakcoding.app.services.stages.components.Page;

public class XStageBuilder implements Builder {

    private XStageStyle style;

    private Page page;

    @Override
    public void setXStageStyle(XStageStyle style) {
        this.style = style;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public XStage getResult() {
        XStage result = null;

        return result;
    }
}
