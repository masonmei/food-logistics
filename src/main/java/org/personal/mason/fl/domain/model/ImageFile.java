package org.personal.mason.fl.domain.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * The persistent class for the merchant database table.
 */
@Entity
@Table(name = "fl_image")
public class ImageFile extends Auditing<User, Long> implements Serializable {

	private static final long serialVersionUID = 6061570144861125698L;

	@Column(name = "name")
	private String name;

	@Column(name = "size")
	private Long size;

	@Column(name = "type", length = 100)
	private String type;

	@Lob
	@Basic
	@Column(name = "content")
	private byte[] content;

	public ImageFile() {
	}

	public ImageFile(Long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}