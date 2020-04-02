package com.cimb.tcj.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserAccess {

	@Id
	private Long id;
	private String username;
	private String resourceUri;
	private boolean isPostAllow;
	private boolean isGetAllow;
	private boolean isDeleteAllow;
	private boolean isPutAllow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public boolean isPostAllow() {
		return isPostAllow;
	}

	public void setPostAllow(boolean isPostAllow) {
		this.isPostAllow = isPostAllow;
	}

	public boolean isGetAllow() {
		return isGetAllow;
	}

	public void setGetAllow(boolean isGetAllow) {
		this.isGetAllow = isGetAllow;
	}

	public boolean isDeleteAllow() {
		return isDeleteAllow;
	}

	public void setDeleteAllow(boolean isDeleteAllow) {
		this.isDeleteAllow = isDeleteAllow;
	}

	public boolean isPutAllow() {
		return isPutAllow;
	}

	public void setPutAllow(boolean isPutAllow) {
		this.isPutAllow = isPutAllow;
	}


}
