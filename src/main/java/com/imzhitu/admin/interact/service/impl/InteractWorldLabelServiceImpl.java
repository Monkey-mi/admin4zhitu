package com.imzhitu.admin.interact.service.impl;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractLikeFollowCommentLabel;
import com.imzhitu.admin.common.pojo.InteractWorldCommentLabel;
import com.imzhitu.admin.interact.service.CommentService;
import com.imzhitu.admin.interact.service.InteractLikeFollowCommentLabelService;
import com.imzhitu.admin.interact.service.InteractWorldCommentLabelService;
import com.imzhitu.admin.interact.service.InteractWorldLabelService;

/**
 * 分享列表快速操作评论标签实现类
 * 
 * @author zhangbo 2015年8月6日
 *
 */
@Service
public class InteractWorldLabelServiceImpl implements InteractWorldLabelService {

	/**
	 * 分词资源配置
	 * 
	 * @author zhangbo 2015年8月6日
	 */
	private Configuration cfg;

	/**
	 * 标记是否加载过关键字列表
	 * 
	 * @author zhangbo 2015年8月6日
	 */
	private boolean keywordAdded = false;

	public InteractWorldLabelServiceImpl() {
		// 通过构造器加载系统资源
		cfg = DefaultConfig.getInstance();
		cfg.setUseSmart(true);
		Dictionary.initial(cfg);
	}

	@Autowired
	private InteractWorldCommentLabelService worldCommentLabelService;

	@Autowired
	private InteractLikeFollowCommentLabelService likeFollowCommentLabelService;

	@Autowired
	private com.hts.web.ztworld.service.ZTWorldService webWorldService;

	@Autowired
	private CommentService commentLabelService;

	@Override
	public void queryWorldLabels(Integer worldId, Map<String, Object> jsonMap) throws Exception {

		List<InteractWorldCommentLabel> worldCommentLabelList = worldCommentLabelService
				.queryWorldCommentLabel(worldId);
		
		if (worldCommentLabelList.size() != 0 && null != worldCommentLabelList) {
			// worldCommentLabelList若存在，则数据库中只有一条记录，所以这里直接取第一个
			List<InteractLikeFollowCommentLabel> likeFollowCommentList = likeFollowCommentLabelService
					.queryCommentLabelNameByLabelIds(worldCommentLabelList.get(0).getLabelIds());
			jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, likeFollowCommentList);
			jsonMap.put(OptResult.JSON_KEY_INTERACT, Tag.TRUE);
		} else {
			List<InteractCommentLabel> worldCommentList = getLikeLikeFollowCommentLabelList(worldId);
			jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, worldCommentList);
			// 若返回结果为空，则interact设置为true，否则为false，展示确定“对勾”图标
			if (worldCommentList.size() == 0) {
				jsonMap.put(OptResult.JSON_KEY_INTERACT, Tag.TRUE);
			} else {
				jsonMap.put(OptResult.JSON_KEY_INTERACT, Tag.FALSE);
			}
		}
	}

	private List<InteractCommentLabel> getLikeLikeFollowCommentLabelList(Integer worldId) throws Exception {
		// 声明返回值，对象是LabelId与LabelName键值对的集合
		List<InteractCommentLabel> list = new ArrayList<InteractCommentLabel>();

		HTWorldDto world = webWorldService.getHTWorldDtoById(worldId, false);

		// 获取所有二级标签
		List<InteractCommentLabel> lableAllList = commentLabelService.getAllCommentLableUse();

		if (null != world.getWorldDesc() && !world.getWorldDesc().isEmpty()) {
			list = IKAnalysisWorldDesc(world.getWorldDesc(), lableAllList);
		} else if (world.getWorldLabel() != null && !world.getWorldLabel().isEmpty()) {
			// 织图标签的构成形式是标签名称根据“,”隔开
			String[] worldLabel = StringUtil.convertStringToStrs(world.getWorldLabel());
			list = handleWorldLabelMatchCommentLabel(worldLabel, lableAllList);
		}

		return list;
	}

	/**
	 * 根据分词获取
	 * 
	 * @return 根据评论标签集合为分词基础，描述中包含了哪些评论标签，做为返回结果，结果集为描述匹配上的标签名称集合
	 * @author zhangbo 2015年8月6日
	 * @param lableAllList
	 * @throws Exception
	 */
	private List<InteractCommentLabel> IKAnalysisWorldDesc(String worldDesc, List<InteractCommentLabel> lableAllList)
			throws Exception {
		// 声明返回值
		List<InteractCommentLabel> result = new ArrayList<InteractCommentLabel>();

		// 得到评论标签名称集合
		Map<String, Integer> map = getLabelNamesByCommentLabelList(lableAllList);

		if (!keywordAdded) {
			Dictionary.getSingleton().addWords(map.keySet());
			keywordAdded = true;
		}

		Reader input = null; // 分词
		Lexeme lexeme = null;

		try {
			input = new StringReader(worldDesc);
			IKSegmenter iks = new IKSegmenter(input, cfg); // 构建IK分词器，使用smart分词模式
			while (null != (lexeme = iks.next())) {
				// 若评论标签包含织图描述分出的词，那么把对应的标签放入返回值中
				if (map.keySet().contains(lexeme.getLexemeText())) {
					InteractCommentLabel dto = new InteractCommentLabel();
					dto.setId(map.get(lexeme.getLexemeText()));
					dto.setLabelName(lexeme.getLexemeText());
					result.add(dto);
				}
			}
		} finally {
			input.close();
		}
		return result;
	}

	/**
	 * 匹配织图标签与评论标签，用织图标签去匹配评论标签，返回匹配成功的结果
	 * 
	 * @param worldLabel
	 *            织图标签名称集合
	 * @param lableAllList
	 *            评论标签集合
	 * @return list 返回匹配后评论标签
	 * @author zhangbo 2015年8月6日
	 */
	private List<InteractCommentLabel> handleWorldLabelMatchCommentLabel(String[] worldLabel,
			List<InteractCommentLabel> lableAllList) {
		// 声明返回值
		List<InteractCommentLabel> result = new ArrayList<InteractCommentLabel>();

		// 得到评论标签名称集合
		Map<String, Integer> map = getLabelNamesByCommentLabelList(lableAllList);
		for (String label : worldLabel) {
			// 若评论标签包含织图标签，那么把对应的标签放入返回值中
			if (map.keySet().contains(label)) {
				InteractCommentLabel dto = new InteractCommentLabel();
				dto.setId(map.get(label));
				dto.setLabelName(label);
				result.add(dto);
			}
		}
		return result;
	}

	/**
	 * 根据评论标签集合获取评论标签名称集合
	 * 
	 * @param lableAllList
	 *            评论标签集合
	 * @return map key：评论标签名称 value：评论标签id
	 * @author zhangbo 2015年8月6日
	 */
	private Map<String, Integer> getLabelNamesByCommentLabelList(List<InteractCommentLabel> lableAllList) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (InteractCommentLabel dto : lableAllList) {
			map.put(dto.getLabelName(), dto.getId());
		}
		return map;
	}

}
