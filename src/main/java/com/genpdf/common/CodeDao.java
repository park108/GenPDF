package com.genpdf.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CodeDao {

	@Autowired
	private JdbcTemplate template;

	public CodeDao() {
		super();
	}

    public ArrayList<Code> getCodeSetList() {

	    ArrayList<Code> codeList = new ArrayList<Code>();
	    String sql = "SELECT code_set, code_set_name FROM code_set";

	    List<Map<String, Object>> result = template.queryForList(sql);

	    Code code;

	    for(Map<String, Object> map : result) {

		    code = new Code(
	                (String) map.get("code_set")
			        , (String) map.get("code_set_name")
				    , ""
				    , ""
				    , false
				    , ""
				    , ""
				    , ""
		    );

		    codeList.add(code);
	    }

	    return codeList;
    }

	public Code getCodeSet(String codeSet) {

		Code returnCode = null;

		String sql = "SELECT code_set" +
				", code_set_name" +
				" FROM code_set " +
				" WHERE code_set = ?";

		Object[] params = new Object[] {codeSet};

		Map result = template.queryForMap(sql, params);

		if(null != result) {

			returnCode = new Code((String) result.get("code_set")
					, (String) result.get("code_set_name")
					, null
					, null
					, null
					, ""
					, ""
					, ""
			);
		}

		return returnCode;
	}

	public int setCodeSet(Code code) {

		String sql = "INSERT INTO code_set VALUES (?,?) " +
				"ON DUPLICATE KEY UPDATE " +
				"code_set_name = ?";

		Object[] params = new Object[] {code.getCodeSet(), code.getCodeSetName(), code.getCodeSetName()};

		int result = template.update(sql, params);

		return result;
	}

	public int delCodeSet(String codeSet) {

		String sql = "DELETE FROM code_set WHERE code_set = ?";

		Object[] params = new Object[] {codeSet};

		int result = template.update(sql, params);

		return result;
	}

	public ArrayList<Code> getCodeList(String codeSet) {

		ArrayList<Code> codeList = new ArrayList<Code>();
		String sql = "SELECT code_set.code_set" +
				", code_set.code_set_name" +
				", code.code" +
				", code.code_name" +
				", code.is_not_use" +
				", code.attr1" +
				", code.attr2" +
				", code.attr3" +
				" FROM code INNER JOIN code_set " +
				" ON code_set.code_set = code.code_set" +
				" WHERE code_set.code_set = ?";
		Object[] params = new Object[] {codeSet};

		List<Map<String, Object>> result = template.queryForList(sql, params);

		Code code;

		for(Map<String, Object> map : result) {

			code = new Code(
					(String) map.get("code_set")
					, (String) map.get("code_set_name")
					, (String) map.get("code")
					, (String) map.get("code_name")
					, (Boolean) map.get("is_not_use")
					, (String) map.get("attr1")
					, (String) map.get("attr2")
					, (String) map.get("attr3")
			);

			codeList.add(code);
		}

		return codeList;
	}

    public Code getCode(String codeSet, String code) {

		Code returnCode = null;

		System.out.println(codeSet + ", " + code);

	    String sql = "SELECT code_set.code_set" +
			    ", code_set.code_set_name" +
			    ", code.code" +
			    ", code.code_name" +
			    ", code.is_not_use" +
			    ", code.attr1" +
			    ", code.attr2" +
			    ", code.attr3" +
			    " FROM code INNER JOIN code_set " +
			    " ON code_set.code_set = code.code_set" +
			    " WHERE code_set.code_set = ?" +
			    "   AND code.code = ?";

	    Object[] params = new Object[] {codeSet, code};

	    Map result = template.queryForMap(sql, params);

	    if(null != result) {

		    returnCode = new Code((String) result.get("code_set")
					, (String) result.get("code_set_name")
					, (String) result.get("code")
					, (String) result.get("code_name")
					, (Boolean) result.get("is_not_use")
				    , (String) result.get("attr1")
				    , (String) result.get("attr2")
				    , (String) result.get("attr3")
			);
	    }

	    return returnCode;
    }

    public int setCode(Code code) {

		System.out.println(
				"Selected Code : " + code.getCodeSet()
				+ ", " + code.getCode()
				+ ", " + code.getCodeName()
				+ ", " + code.getNotUse()
		);

	    String sql = "INSERT INTO code VALUES (?, ?, ?, ?, ?, ?, ?) " +
				    "ON DUPLICATE KEY UPDATE " +
				    "code_name = ?" +
				    ", is_not_use = ?" +
				    ", attr1 = ?" +
				    ", attr2 = ?" +
				    ", attr3 = ?";

	    Object[] params = new Object[] {
			    code.getCodeSet()
			    , code.getCode()
	    		, code.getCodeName()
			    , code.getNotUse()
			    , code.getAttr1()
			    , code.getAttr2()
			    , code.getAttr3()
			    , code.getCodeName()
			    , code.getNotUse()
			    , code.getAttr1()
			    , code.getAttr2()
			    , code.getAttr3()};

	    int result = template.update(sql, params);

	    return result;
    }

	public int delCode(String codeSet, String code) {

		String sql = "DELETE FROM code WHERE code_set = ? AND code = ?";

		Object[] params = new Object[] {codeSet, code};

		int result = template.update(sql, params);

		return result;
	}
}
