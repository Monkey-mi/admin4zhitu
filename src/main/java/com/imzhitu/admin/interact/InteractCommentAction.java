package com.imzhitu.admin.interact;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.InteractComment;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractCommentLabelTree;
import com.imzhitu.admin.interact.service.InteractCommentService;

public class InteractCommentAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5458395827792742247L;
	
	
	private Logger logger = Logger.getLogger(InteractCommentAction.class);
	private Integer labelId = 0;
	private Integer groupId = 0;
	private String comment;
	private File commentFile;
	private String content;
	private String ids;
	private String labelName;
	private String labelJSON;
	private Integer id;
	private Boolean hasTotal;
	private Integer selected = 0;
	private String interactCommentJSON;
	
	@Autowired
	private InteractCommentService interactCommentService;
	
	/**
	 * 根据标签获取评论列表
	 * 
	 * @return
	 */
	public String queryCommentListByLabel() {
		try {
			interactCommentService.buildComments(labelId, comment, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * （跨域）根据标签获取评论列表
	 * @return
	 */
	public String queryCommentListByLabel4Callback() {
		PrintWriter out = null;
		String jsonCallback = request.getParameter("callback");
		try {
			if(comment != null) {
				comment = new String(comment.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1);
		}
		try {
			out = response.getWriter();
			interactCommentService.buildComments(labelId, comment, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(jsonCallback + "(" +json.toString() + ")");
			out.flush();
		} catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(jsonCallback + "(" +json.toString() + ")");
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 保存评论
	 * @return
	 */
	public String saveComment() {
		try {
			interactCommentService.saveComment(commentFile, content, labelId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据ids删除评论
	 * @return
	 */
	public String deleteCommentByIds() {
		try {
			interactCommentService.deleteCommentByIds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询评论
	 * 
	 * @return
	 */
	public String queryCommentById() {
		try {
			InteractComment comm = interactCommentService.getCommentById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, comm, OptResult.JSON_KEY_COMMENT, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新评论
	 * 
	 * @return
	 */
	public String updateComment() {
		try {
			interactCommentService.updateComment(id, content, labelId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有标签
	 * @return
	 */
	public String queryAllLabel() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<InteractCommentLabel> list = interactCommentService.getAllLabels();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * （跨域）查询所有标签
	 * @return
	 */
	public String queryAllLabel4Callback() {
		PrintWriter out = null;
		String jsonCallback = request.getParameter("callback");
		try {
			out = response.getWriter();
			List<InteractCommentLabel> list = interactCommentService.getAllLabels();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsonCallback + "(" +jsArray.toString() + ")");
			out.flush();
		} catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(jsonCallback + "(" +json.toString() + ")");
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 查询评论标签
	 * 
	 * @return
	 */
	public String queryLabel() {
		try {
			interactCommentService.buildLabel(maxId, page, rows, groupId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有标签分组
	 * 
	 * @return
	 */
	public String queryAllLabelGroup() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<InteractCommentLabel> list = interactCommentService.getAllLabelGroup();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 查询标签分组
	 * 
	 * @return
	 */
	public String queryLabelGroup() {
		try {
			interactCommentService.buildLabelGroup(maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除标签分组
	 * 
	 * @return
	 */
	public String deleteLabelGroupByIds() {
		try {
			interactCommentService.deleteLabelGroups(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除标签
	 * @return
	 */
	public String deleteLabelByIds() {
		try {
			interactCommentService.deleteLabels(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存标签
	 * @return
	 */
	public String saveLabel() {
		try {
			interactCommentService.saveLabel(labelName, groupId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据JSON字符串修改标签
	 * @return
	 */
	public String updateLabelByJSON() {
		try {
			interactCommentService.updateLabelByJSON(labelJSON);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询标签树
	 * 
	 * @return
	 */
	public String queryLabelTree() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<InteractCommentLabelTree> list = interactCommentService.getLabelTree(id, selected, hasTotal);
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 获取标签树
	 */
	
	public String queryAllLabelTree() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<InteractCommentLabelTree> list = interactCommentService.getAllLabelTree();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 更新评论内容
	 * @return
	 */
	public String updateCommentContentById(){
		try{
			interactCommentService.updateCommentContentById(content, id);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新评论内容
	 * @return
	 */
	public String updateCommentByJSON(){
		try{
			interactCommentService.updateCommentContentByJSON(interactCommentJSON);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public InteractCommentService getInteractCommentService() {
		return interactCommentService;
	}

	public void setInteractCommentService(
			InteractCommentService interactCommentService) {
		this.interactCommentService = interactCommentService;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public File getCommentFile() {
		return commentFile;
	}

	public void setCommentFile(File commentFile) {
		this.commentFile = commentFile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getLabelJSON() {
		return labelJSON;
	}

	public void setLabelJSON(String labelJSON) {
		this.labelJSON = labelJSON;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getHasTotal() {
		return hasTotal;
	}

	public void setHasTotal(Boolean hasTotal) {
		this.hasTotal = hasTotal;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}
	
	public void setInteractCommentJSON(String interactCommentJSON){
		this.interactCommentJSON =interactCommentJSON;
	}
	
	public String getInteractCommentJSON(){
		return this.interactCommentJSON;
	}
	
}
