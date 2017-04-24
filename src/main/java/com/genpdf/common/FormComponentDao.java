package com.genpdf.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FormComponentDao {

    @Autowired
    private JdbcTemplate template;

    private SimpleJdbcInsert insert;

    public FormComponentDao() {
        super();
    }

    public Map<String, FormComponent> getFormComponentMap(long formId) {

        Map<String, FormComponent> componentMap = new HashMap<String, FormComponent>();

        String sql = "SELECT * FROM form_component WHERE form_id = ?";

        Object[] params = new Object[] {formId};

        List<Map<String, Object>> result = template.queryForList(sql, params);

        FormComponent component;

        for(Map<String, Object> map : result) {

            component = new FormComponent(
                    (Integer) map.get("id")
                    , (Integer) map.get("form_id")
                    , (String) map.get("code")
                    , (String) map.get("description")
                    , (Boolean) map.get("is_hide")
                    , (Float) map.get("x")
                    , (String) map.get("x_unit")
                    , (Float) map.get("y")
                    , (String) map.get("y_unit")
                    , (Float) map.get("width")
                    , (String) map.get("width_unit")
                    , (Float) map.get("height")
                    , (String) map.get("height_unit")
                    , ((String) map.get("align")).toCharArray()[0]
                    , (Integer) map.get("background_color_r")
                    , (Integer) map.get("background_color_g")
                    , (Integer) map.get("background_color_b")
            );

            componentMap.put(component.getCode(), component);
        }

        return componentMap;
    }

    public FormComponent getFormComponent(long id) {

        FormComponent formComponent = null;

        String sql = "SELECT * FROM form_component WHERE id = ?";

        Object[] params = new Object[] {id};

        Map result = template.queryForMap(sql, params);

        if(null != result) {

            formComponent = new FormComponent(
                    id
                    , (Integer) result.get("form_id")
                    , (String) result.get("code")
                    , (String) result.get("description")
                    , (Boolean) result.get("is_hide")
                    , (Float) result.get("x")
                    , (String) result.get("x_unit")
                    , (Float) result.get("y")
                    , (String) result.get("y_unit")
                    , (Float) result.get("width")
                    , (String) result.get("width_unit")
                    , (Float) result.get("height")
                    , (String) result.get("height_unit")
                    , ((String) result.get("align")).toCharArray()[0]
                    , (Integer) result.get("background_color_r")
                    , (Integer) result.get("background_color_g")
                    , (Integer) result.get("background_color_b")
            );
        }

        return formComponent;
    }

    public long insertFormComponent(FormComponent component) {

        insert = new SimpleJdbcInsert(this.template).withTableName("form_component").usingGeneratedKeyColumns("id");

        final Map<String, Object> params = new HashMap<>();
        params.put("form_id", component.getFormId());
        params.put("code", component.getCode());
        params.put("description", component.getDescription());
        params.put("is_hide", component.getHide());
        params.put("x", component.getX());
        params.put("x_unit", component.getxUnit());
        params.put("y", component.getY());
        params.put("y_unit", component.getyUnit());
        params.put("width", component.getWidth());
        params.put("width_unit", component.getWidthUnit());
        params.put("height", component.getHeight());
        params.put("height_unit", component.getHeightUnit());
        params.put("align", component.getAlign());
        params.put("background_color_r", component.getBackgroundColorR());
        params.put("background_color_g", component.getBackgroundColorG());
        params.put("background_color_b", component.getBackgroundColorB());

        Number id = insert.executeAndReturnKey(params);

        return id.longValue();
    }

    public int updateFormComponent(FormComponent component) {

        String sql = "UPDATE form_component SET " +
                "code = ?" +
                ", description = ?" +
                ", is_hide = ?" +
                ", x = ?" +
                ", x_unit = ?" +
                ", y = ?" +
                ", y_unit = ?" +
                ", width = ?" +
                ", width_unit = ?" +
                ", height = ?" +
                ", height_unit = ?" +
                ", align = ?" +
                ", background_color_r = ?" +
                ", background_color_g = ?" +
                ", background_color_b = ?" +
                "WHERE id = ?";

        Object[] params = new Object[] {
                component.getCode()
                , component.getDescription()
                , component.getHide()
                , component.getX()
                , component.getxUnit()
                , component.getY()
                , component.getyUnit()
                , component.getWidth()
                , component.getWidthUnit()
                , component.getHeight()
                , component.getHeightUnit()
                , component.getAlign()
                , component.getBackgroundColorR()
                , component.getBackgroundColorG()
                , component.getBackgroundColorB()

                , component.getId()
        };

        int result = template.update(sql, params);

        return result;
    }

    public int delFormComponent(long id) {

        String sql = "DELETE FROM form_component WHERE id = ?";

        Object[] params = new Object[] {id};

        int result = template.update(sql, params);

        return result;
    }
}
