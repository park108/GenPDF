package com.genpdf.common;

public class Code {

    String codeSet;
    String code;
    String codeSetName;
    String codeName;
    Boolean isNotUse;

    public Code() {
    }

    public Code(String codeSet, String codeSetName, String code, String codeName, Boolean isNotUse) {
        this.codeSet = codeSet;
        this.codeSetName = codeSetName;
        this.code = code;
        this.codeName = codeName;
        this.isNotUse = isNotUse;
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
}
