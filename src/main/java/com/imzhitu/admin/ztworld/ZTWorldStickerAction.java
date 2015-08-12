package com.imzhitu.admin.ztworld;

import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.ZTWorldSticker;
import com.imzhitu.admin.common.pojo.ZTWorldStickerSet;
import com.imzhitu.admin.common.pojo.ZTWorldStickerType;
import com.imzhitu.admin.ztworld.service.ZTWorldStickerService;

/**
 * <p>
 * 织图贴纸子模块控制器
 * </p>
 * 
 * 创建时间:2014-12-29
 * @author lynch
 *
 */
public class ZTWorldStickerAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162869629917137864L;
	
	@Autowired
	private ZTWorldStickerService stickerService;
	
	private ZTWorldStickerType type = new ZTWorldStickerType();
	private ZTWorldSticker sticker = new ZTWorldSticker();
	private ZTWorldStickerSet stickerSet = new ZTWorldStickerSet();
	
	private Integer id;
	private String ids;
	private Integer valid;
	private Integer weight;
	
	private Boolean addAllTag = false;
	
	/**
	 * 查询贴纸类型列表
	 * 
	 * @return
	 */
	public String queryType() {
		try {
			stickerService.buildTypes(type, page, rows, jsonMap, addAllTag);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询贴纸类型
	 * 
	 * @return
	 */
	public String queryTypeById() {
		try {
			ZTWorldStickerType type = stickerService.queryTypeById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, type, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存贴纸类型
	 * 
	 * @return
	 */
	public String saveType() {
		try {
			stickerService.saveType(type);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸类型
	 * 
	 * @return
	 */
	public String updateType() {
		try {
			stickerService.updateType(type);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除贴纸分类
	 * 
	 * @return
	 */
	public String deleteTypes() {
		try {
			stickerService.deleteTypes(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸分类有效性
	 * 
	 * @return
	 */
	public String updateTypeValid() {
		try {
			stickerService.updateTypeValid(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸分类排序
	 * 
	 * @return
	 */
	public String updateTypeSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			stickerService.updateTypeSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新分类权重
	 * 
	 * @return
	 */
	public String updateTypeWeight() {
		try {
			Integer res = stickerService.updateTypeWeight(id, weight);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, res, OptResult.JSON_KEY_WEIGHT, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸缓存
	 * 
	 * @return
	 */
	public String updateStickerCache() {
		try {
			stickerService.updateStickerCache();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询贴纸列表
	 * 
	 * @return
	 */
	public String querySticker() {
		try {
			stickerService.buildStickers(sticker, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有贴纸分类
	 * 
	 * @return
	 */
	public String queryAllType(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<ZTWorldStickerType> list = stickerService.queryAllType(addAllTag, type);
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		}catch(Exception e){
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		}finally{
			out.close();
		}
		return null;
	}
	
	/**
	 * 根据id查询贴纸
	 * 
	 * @return
	 */
	public String queryStickerById() {
		try {
			ZTWorldSticker sticker = stickerService.queryStickerById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, sticker, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存贴纸
	 * 
	 * @return
	 */
	public String saveSticker() {
		try {
			stickerService.saveSticker(sticker);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸
	 * 
	 * @return
	 */
	public String updateSticker() {
		try {
			stickerService.updateSticker(sticker);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除贴纸
	 * 
	 * @return
	 */
	public String deleteStickers() {
		try {
			stickerService.deleteStickers(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸权重
	 * 
	 * @return
	 */
	public String updateStickerWeight() {
		try {
			Integer res = stickerService.updateStickerWeight(id, weight);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, res, OptResult.JSON_KEY_WEIGHT, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸置顶权重
	 * 
	 * @return
	 */
	public String updateStickerTopWeight() {
		try {
			Integer res = stickerService.updateStickerTopWeight(id, weight);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, res, OptResult.JSON_KEY_WEIGHT, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸序号
	 * 
	 * @return
	 */
	public String updateStickerSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			stickerService.updateStickerSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸有效性
	 * 
	 * @return
	 */
	public String updateStickerValid() {
		try {
			stickerService.updateStickerValid(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询系列列表
	 * 
	 * @return
	 */
	public String querySet() {
		try {
			stickerService.buildSet(stickerSet, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询set
	 * 
	 * @return
	 */
	public String querySetById() {
		try {
			ZTWorldStickerSet set = stickerService.querySetById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, set, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存系列
	 * 
	 * @return
	 */
	public String saveSet() {
		try {
			stickerService.saveSet(stickerSet);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新系列
	 * 
	 * @return
	 */
	public String updateSet() {
		try {
			stickerService.updateSet(stickerSet);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除贴纸
	 * 
	 * @return
	 */
	public String deleteSets() {
		try {
			stickerService.deleteSets(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新贴纸分类排序
	 * 
	 * @return
	 */
	public String updateSetSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			stickerService.updateSetSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新分类权重
	 * 
	 * @return
	 */
	public String updateSetWeight() {
		try {
			Integer res = stickerService.updateSetWeight(id, weight);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, res, OptResult.JSON_KEY_WEIGHT, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public ZTWorldStickerType getType() {
		return type;
	}

	public void setType(ZTWorldStickerType type) {
		this.type = type;
	}

	public ZTWorldSticker getSticker() {
		return sticker;
	}

	public void setSticker(ZTWorldSticker sticker) {
		this.sticker = sticker;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Boolean getAddAllTag() {
		return addAllTag;
	}

	public void setAddAllTag(Boolean addAllTag) {
		this.addAllTag = addAllTag;
	}

	public ZTWorldStickerSet getStickerSet() {
		return stickerSet;
	}

	public void setStickerSet(ZTWorldStickerSet stickerSet) {
		this.stickerSet = stickerSet;
	}
	
}
