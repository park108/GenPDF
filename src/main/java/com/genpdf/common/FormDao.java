package com.genpdf.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.toIntExact;

@Repository
public class FormDao {

    @Autowired
    private JdbcTemplate template;

    public FormDao() {
        super();
    }

    public ArrayList<Form> getFormList(String org) {

        ArrayList<Form> formList = new ArrayList<Form>();

        String sql = "SELECT * FROM form WHERE org = ?";

        Object[] params = new Object[] {org};

        List<Map<String, Object>> result = template.queryForList(sql, params);

        Form form;

        for(Map<String, Object> map : result) {

            form = new Form(org
                    , (String) map.get("doc_type")
                    , toIntExact((Long) map.get("seq"))
                    , (String) map.get("description")
                    , (String) map.get("font_code")
                    , (Float) map.get("font_size_title")
                    , (Float) map.get("font_size_body")
                    , (Float) map.get("font_size_table_header")
                    , (Float) map.get("font_size_table_body")
                    , (Float) map.get("font_size_footer")
                    , (Float) map.get("margin_left")
                    , (Float) map.get("margin_right")
                    , (Float) map.get("margin_bottom")
                    , (Float) map.get("margin_top")
                    , (String) map.get("logo_image_path")
                    , (String) map.get("sign_image_path")
            );

            formList.add(form);
        }

        return formList;
    }

    public Form getForm(String org, String docType, int seq) {

        Form form = null;

        String sql = "SELECT * FROM form WHERE org = ? AND doc_type = ? AND seq = ?";

        Object[] params = new Object[] {org, docType, seq};

        Map result = template.queryForMap(sql, params);

        if(null != result) {

            form = new Form(org
                    , docType
                    , seq
                    , (String) result.get("description")
                    , (String) result.get("font_code")
                    , (Float) result.get("font_size_title")
                    , (Float) result.get("font_size_body")
                    , (Float) result.get("font_size_table_header")
                    , (Float) result.get("font_size_table_body")
                    , (Float) result.get("font_size_footer")
                    , (Float) result.get("margin_left")
                    , (Float) result.get("margin_right")
                    , (Float) result.get("margin_bottom")
                    , (Float) result.get("margin_top")
                    , (String) result.get("logo_image_path")
                    , (String) result.get("sign_image_path")
            );
        }

        return form;
    }

    public int setForm(Form form) {

        String sql = "INSERT INTO code VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "description = ?" +
                ", font_code = ?" +
                ", font_size_title = ?" +
                ", font_size_body = ?" +
                ", font_size_table_header = ?" +
                ", font_size_table_body = ?" +
                ", font_size_footer = ?" +
                ", margin_left = ?" +
                ", margin_right = ?" +
                ", margin_bottom = ?" +
                ", margin_top = ?" +
                ", logo_image_path = ?" +
                ", sign_image_path = ?";

        Object[] params = new Object[] {
                form.getOrg()
                , form.getDocType()
                , form.getSeq()
                , form.getDescription()
                , form.getFontCode()
                , form.getFontSizeTitle()
                , form.getFontSizeBody()
                , form.getFontSizeTableHeader()
                , form.getFontSizeTableBody()
                , form.getFontSizeFooter()
                , form.getMarginLeft()
                , form.getMarginRight()
                , form.getMarginBottom()
                , form.getMarginTop()
                , form.getLogoImagePath()
                , form.getSignImagePath()

                , form.getDescription()
                , form.getFontCode()
                , form.getFontSizeTitle()
                , form.getFontSizeBody()
                , form.getFontSizeTableHeader()
                , form.getFontSizeTableBody()
                , form.getFontSizeFooter()
                , form.getMarginLeft()
                , form.getMarginRight()
                , form.getMarginBottom()
                , form.getMarginTop()
                , form.getLogoImagePath()
                , form.getSignImagePath()
        };

        int result = template.update(sql, params);

        return result;
    }
}
