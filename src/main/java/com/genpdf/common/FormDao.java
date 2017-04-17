package com.genpdf.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.toIntExact;

@Repository
public class FormDao {

    @Autowired
    private JdbcTemplate template;

    private SimpleJdbcInsert insert;

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

            form = new Form(
                    (Integer) map.get("id")
                    , (String) map.get("org")
                    , (String) map.get("doc_type")
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

    public Form getForm(long id) {

        Form form = null;

        String sql = "SELECT * FROM form WHERE id = ?";

        Object[] params = new Object[] {id};

        Map result = template.queryForMap(sql, params);

        if(null != result) {

            form = new Form(
                    id
                    , (String) result.get("org")
                    , (String) result.get("doc_type")
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

    public long insertForm(Form form) {

        insert = new SimpleJdbcInsert(this.template).withTableName("form").usingGeneratedKeyColumns("id");

        final Map<String, Object> params = new HashMap<>();
        params.put("org", form.getOrg());
        params.put("doc_type", form.getDocType());
        params.put("description", form.getDescription());
        params.put("font_code", form.getFontCode());
        params.put("font_size_title", form.getFontSizeTitle());
        params.put("font_size_body", form.getFontSizeBody());
        params.put("font_size_table_header", form.getFontSizeTableHeader());
        params.put("font_size_table_body", form.getFontSizeTableBody());
        params.put("font_size_footer", form.getFontSizeFooter());
        params.put("margin_bottom", form.getMarginBottom());
        params.put("margin_top", form.getMarginTop());
        params.put("margin_left", form.getMarginLeft());
        params.put("margin_right", form.getMarginRight());
        params.put("logo_image_path", form.getLogoImagePath());
        params.put("sign_image_path", form.getSignImagePath());

        Number id = insert.executeAndReturnKey(params);

        return id.longValue();
    }

    public int updateForm(Form form) {

        String sql = "UPDATE form SET " +
                "org = ?" +
                ", doc_type = ?" +
                ", description = ?" +
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
                ", sign_image_path = ?" +
                "WHERE id = ?";

        Object[] params = new Object[] {
                form.getOrg()
                , form.getDocType()
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

                , form.getId()
        };

        int result = template.update(sql, params);

        return result;
    }
}
