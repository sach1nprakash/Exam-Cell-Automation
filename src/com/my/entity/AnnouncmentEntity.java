package com.my.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="announcments")
public class AnnouncmentEntity {
	@Id
	private String announcment;

	public String getAnnouncment() {
		return announcment;
	}

	public void setAnnouncment(String announcment) {
		this.announcment = announcment;
	}
}
