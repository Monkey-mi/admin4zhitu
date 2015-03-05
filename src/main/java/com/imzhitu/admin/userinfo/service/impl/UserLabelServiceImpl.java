package com.imzhitu.admin.userinfo.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.UserLabel;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.UserLabelDto;
import com.imzhitu.admin.common.pojo.UserSexLabelDto;
import com.imzhitu.admin.userinfo.dao.UserInfoDao;
import com.imzhitu.admin.userinfo.dao.UserLabelDao;
import com.imzhitu.admin.userinfo.service.UserLabelService;

@Service
public class UserLabelServiceImpl extends BaseServiceImpl implements
		UserLabelService {

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private UserLabelDao userLabelDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public void saveLabel(Integer sex, File file) throws Exception {
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(file); 
			InputStreamReader isr = new InputStreamReader(fis,"GBK"); //指定以GBK编码读入 
			reader = new BufferedReader(isr);
			String line = null;
			while((line = reader.readLine()) != null) {
				String labelName = line.trim();
				if(!labelName.equals("")) {
					Integer id = webKeyGenService.generateId(KeyGenServiceImpl.USER_LABEL_ID);
					String labelPinyin = StringUtil.getPinYin(labelName);
					userLabelDao.saveLabel(new UserLabel(id, labelName, labelPinyin, Tag.STATE_LOCAL, sex, Tag.TRUE, id, 0));
				}
			}
		} finally {
			reader.close();
		}
	}

	@Override
	public List<UserSexLabelDto> getAllUserLabel() throws Exception {
		final List<UserSexLabelDto> dtoList = new ArrayList<UserSexLabelDto>();
		final UserSexLabelDto maleDto = new UserSexLabelDto(-1, "男生标签");
		final UserSexLabelDto femaleDto = new UserSexLabelDto(-2, "女生标签");
		dtoList.add(maleDto);
		dtoList.add(femaleDto);
		userLabelDao.queryAllLabel(new RowCallback<UserLabelDto>() {
			
			@Override
			public void callback(UserLabelDto t) {
				switch (t.getLabelSex()) {
				case Tag.SEX_MAN:
					maleDto.addChildren(t);
					break;
				case Tag.SEX_FEMALE:
					femaleDto.addChildren(t);
					break;
				default:
					dtoList.add(new UserSexLabelDto(t.getId(), t.getText(), "opend"));
					break;
				}
			}
		});
		return dtoList;
	}

	@Override
	public void buildLabelId(Integer userId, Map<String, Object> jsonMap) throws Exception {
		List<Integer> labelIds = userLabelDao.queryLabelIdByUserId(userId);
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, labelIds);
	}

	@Override
	public void saveLabelUser(Integer userId, String labelIdsStr, String labels, Boolean display)
			throws Exception {
		Integer[] labelIds = StringUtil.convertStringToIds(labelIdsStr);
		userLabelDao.deleteLabelByUserId(userId);
		for(Integer labelId : labelIds) {
			userLabelDao.saveLabelUser(labelId, userId);
		}
		if(display) {
			userInfoDao.updateUserLabel(userId, labels);
		}
		
	}

}
