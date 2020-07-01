package sample.models;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

public class XWFParagraphPublication extends XWPFParagraph {
    public XWFParagraphPublication(CTP prgrph, IBody part) {
        super(prgrph, part);
    }
    public boolean isTitle(){
        return  (this.getNumFmt() == null && this.getNumIlvl() == null  && this.getNumLevelText() == null) ? false : true;
    }
}
