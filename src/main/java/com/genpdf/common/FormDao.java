package com.genpdf.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class FormDao {

    @Autowired
    private JdbcTemplate template;

    public FormDao() {
        super();
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
}
