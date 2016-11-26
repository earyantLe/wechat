package com.ctsig.common.security.domain;

import java.io.Serializable;

public class LoginUserInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4927204602365579354L;

	private String id;
	private String loginName;
	private String password;
	private String empName;
	private String empCode;
	private String email;
	private Long deptId;
	private String deptName;
	private Long empLevelId;
	private String empLevelName;
	private String cityId;
	private String cityName;
	private String areaId;
	private String areaName;
	private Integer pwdIndate;
	private String post;
	private Integer gender;
	private String tel;
	private Integer status;
	private Integer isAdminUser;
	private Integer isSpecialUser;
	private Integer isCusManager=0;
	private String ip;
	private String currentSystemId;
	private String roleIds;
	private String roleType;
	private String organType;
	private String adminSystemId;
	private String canSeeSpecialInfo;
	private String organLevel;//机构层级
	private String levelType;//职级层级
	
	public String getOrganLevel() {
		return organLevel;
	}

	public void setOrganLevel(String organLevel) {
		this.organLevel = organLevel;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the organType
	 */
	public String getOrganType() {
		return organType;
	}

	/**
	 * @param organType the organType to set
	 */
	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getEmpLevelId() {
		return empLevelId;
	}

	public void setEmpLevelId(Long empLevelId) {
		this.empLevelId = empLevelId;
	}

	public Integer getPwdIndate() {
		return pwdIndate;
	}

	public void setPwdIndate(Integer pwdIndate) {
		this.pwdIndate = pwdIndate;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsAdminUser() {
		return isAdminUser;
	}

	public void setIsAdminUser(Integer isAdminUser) {
		this.isAdminUser = isAdminUser;
	}

	public Integer getIsSpecialUser() {
		return isSpecialUser;
	}

	public void setIsSpecialUser(Integer isSpecialUser) {
		this.isSpecialUser = isSpecialUser;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getCurrentSystemId() {
		return currentSystemId;
	}

	public void setCurrentSystemId(String currentSystemId) {
		this.currentSystemId = currentSystemId;
	}

	public String getAdminSystemId() {
		return adminSystemId;
	}

	public void setAdminSystemId(String adminSystemId) {
		this.adminSystemId = adminSystemId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmpLevelName() {
		return empLevelName;
	}

	public void setEmpLevelName(String empLevelName) {
		this.empLevelName = empLevelName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getIsCusManager() {
		return isCusManager;
	}

	public void setIsCusManager(Integer isCusManager) {
		this.isCusManager = isCusManager;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getCanSeeSpecialInfo() {
		return canSeeSpecialInfo;
	}

	public void setCanSeeSpecialInfo(String canSeeSpecialInfo) {
		this.canSeeSpecialInfo = canSeeSpecialInfo;
	}

	
}
