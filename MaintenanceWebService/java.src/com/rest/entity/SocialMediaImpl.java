package com.rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * Entity class for social media
 * 
 * @author vinayaksm
 *
 */
//@Entity
//@Table(name = "SOCIAL_MEDIA")
public class SocialMediaImpl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6533304902615896919L;
	@Id
	@TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "id", pkColumnValue = "id_Next_Value", allocationSize = 1 )
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
	@Column(name="id")
	private Integer identifier;
	@Column(unique = true, nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String url;

	private String description;

	public SocialMediaImpl() {
		super();
	}

	public SocialMediaImpl(final String name,final String url, final String description) {
		super();
		this.name = name;
		this.url = url;
		this.description = description;
	}	

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(final Integer identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
