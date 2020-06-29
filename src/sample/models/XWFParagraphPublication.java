package sample.models;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import java.math.BigInteger;
import java.util.Optional;

public class XWFParagraphPublication extends XWPFParagraph {
    public XWFParagraphPublication(CTP prgrph, IBody part) {
        super(prgrph, part);
    }
    public Optional<XWFParagraphPublication> isTitle(){
        String FormatNum  = this.getNumFmt();
        BigInteger Ilvl  = this.getNumIlvl();
        String NumLvlText  = this.getNumLevelText();
        String paragraph = this.getText();

        return Optional.of(this);
    }
}
