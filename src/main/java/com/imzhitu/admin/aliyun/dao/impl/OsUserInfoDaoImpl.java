package com.imzhitu.admin.aliyun.dao.impl;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.impl.BaseOsDaoImpl;
import com.hts.web.base.database.OpenSearch;
import com.hts.web.base.database.RowSelection;
import com.imzhitu.admin.aliyun.dao.OsUserInfoDao;
import com.opensearch.javasdk.CloudsearchSearch;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Repository
public class OsUserInfoDaoImpl extends BaseOsDaoImpl implements OsUserInfoDao {

	private static String INDEX_NAME = OpenSearch.USER_INFO;
	
	@Override
	public JSONObject searchId(String userName, int start, int limit) {
		String res;
		CloudsearchSearch searcher = new CloudsearchSearch(getSearchClient());
		searcher.addIndex(INDEX_NAME);
		searcher.setStartHit(start);
		searcher.setHits(limit);
		searcher.setFormat("json");
		searcher.addFetchField("id");
		searcher.setQueryString("user_name:'" + userName + "'");
		try {
			res = searcher.search();
			JSONObject js = JSONObject.fromObject(res);
			if(!js.get("status").toString().toLowerCase().equals("ok")) {
				throw new RuntimeException(res);
			}
			return js.getJSONObject("result");
//			JSONArray items = jsObj.getJSONArray("items");
//			ids = new Integer[items.size()];
//			total = jsObj.getInt("total");
//			for(int i = 0; i < ids.length; i++) {
//				ids[i] = items.getJSONObject(i).getInt("id");
//			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}
}
