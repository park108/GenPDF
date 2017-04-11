package com.genpdf.common;

public class Code {

    String codeSet;
    String code;
    String codeSetName;
    String codeName;
    Boolean isNotUse;

    String attr1;
    String attr2;
    String attr3;

    public Code() {
    }

    public Code(String codeSet, String codeSetName, String code, String codeName, Boolean isNotUse, String attr1, String attr2, String attr3) {
        this.codeSet = codeSet;
        this.codeSetName = codeSetName;
        this.code = code;
        this.codeName = codeName;
        this.isNotUse = isNotUse;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
    }

    public String getCodeSet() {
        return codeSet;
    }

    public void setCodeSet(String codeSet) {
        this.codeSet = codeSet;
    }

    public String getCodeSetName() {
        return codeSetName;
    }

    public void setCodeSetName(String codeSetName) {
        this.codeSetName = codeSetName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Boolean getNotUse() {
        return isNotUse;
    }

    public void setNotUse(Boolean notUse) {
        isNotUse = notUse;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }
}
