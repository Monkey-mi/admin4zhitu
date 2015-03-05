package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 举报表
 * </p>
 * 
 * 创建时间：2014年3月4日 10:48:14
 * 
 * @author zxx
 *
 */

public class ZTWorldReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4977127208039988207L;
	
	private Integer commentId;		//评论id
	private Integer phoneCode;		// 手机辨别代号
	private Integer user_id;		//用户id
	private String authorName; 		// 作者名字
	private String authorAvatar; 	// 作者头像
	private Integer clickCount; 	// 查看次数
	private Integer likeCount; 		// 被“赞”次数
	private Integer commentCount; 	// 被评论次数
	private String worldURL; 		// 连接路径
	private String worldDesc; 		// 世界描述
	private String titleThumbPath; 	// 首页缩略图路径
	private String worldLabel; 		// 世界标签
	private String worldType; 		// 织图分类
	private Integer world_id;		//世界id
	private String report_content;	//举报内容
	private Date report_date;		//被举报日期
	private Integer reportValid;	//有效标志
	
	public ZTWorldReport(
		 Integer commentId,
		 Integer user_id,
		 Integer world_id,
		 String report_content,
		 Date report_date,
		 Integer reportValid,
		 Integer phoneCode,
		 String authorName,
		 String authorAvatar,
		 Integer clickCount,
		 Integer likeCount,
		 String worldURL,
		 String worldDesc,
		 String titleThumbPath,
		 String worldLabel,
		 String worldType,
		 Integer commentCount
		 ){
		
		super();
		this.commentId = commentId;
		this.user_id = user_id;
		this.world_id = world_id;
		this.report_content = report_content;
		this.report_date = report_date;
		this.reportValid = reportValid;
		this.phoneCode = phoneCode;
		this.authorName = authorName;
		this.authorAvatar = authorAvatar;
		this.clickCount = clickCount;
		this.likeCount = likeCount;
		this.worldURL = worldURL;
		this.worldDesc = worldDesc;
		this.titleThumbPath = titleThumbPath;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
		this.commentCount = commentCount;
	}
	
	public ZTWorldReport(){
		super();
	}
	
	/**
	 * 因为封装中需要id，而由于查询过程中涉及到两个表，两个表中都有id字段，另一个表太多字段了。所以采用
	 * 修改report的表中的id字段为commentId，其作用还是id。
	 * @return
	 */
	public Integer getId(){
		return commentId;
	}
	public void setId(Integer id){
		this.commentId = id;
	}
	
	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}
	
	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}
	
	public String getWorldLabel() {
		return worldLabel;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}
	
	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}
	
	public String getWorldDesc() {
		return worldDesc;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}
	
	public String getWorldURL() {
		return worldURL;
	}

	public void setWorldURL(String worldURL) {
		this.worldURL = worldURL;
	}
	
	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	
	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	
	public String getAuthorAvatar() {
		return authorAvatar;
	}

	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Integer getCommentId(){
		return commentId;
	}
	public void setCommentId(Integer commentId){
		this.commentId = commentId;
	}
	
	public Integer getUser_id(){
		return user_id;
	}
	public void setUser_id(Integer user_id){
		this.user_id = user_id;
	}
	
	public Integer getWorld_id(){
		return world_id;
	}
	public void setWorld_id(Integer world_id){
		this.world_id = world_id;
	}
	
	public String getReport_content(){
		return this.report_content;
	}
	public void setReport_content(String report_content){
		this.report_content = report_content;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReport_date(){
		return this.report_date;
	}
	public void setReport_date(Date report_date){
		this.report_date = report_date;
	}
	
	public Integer getReportValid(){
		return this.reportValid;
	}
	public void setReportValid(Integer reportValid){
		this.reportValid = reportValid;
	}

}
