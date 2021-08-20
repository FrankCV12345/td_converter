package com.traduciendoelderecho.converter.models.entities;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import java.util.Objects;

public class XWFParagraphPublication extends XWPFParagraph {
    public XWFParagraphPublication(CTP prgrph, IBody part) {
        super(prgrph, part);
    }

    public boolean isTitle() {
        return Objects.nonNull(this.getNumFmt()) && Objects.nonNull(this.getNumIlvl()) && Objects.nonNull(this.getNumLevelText());
    }
}
