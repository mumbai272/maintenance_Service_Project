package com.rest.dto;


import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * DTO For Social media data.
 * 
 * @author vinayaksm
 *
 */
@XmlRootElement(name = "soicalMedia")
public class SocialMediaDTO implements Cloneable {

	private Integer identifier;
	@NotBlank(message="{validation.empty}")
	private String name;
	@NotBlank
	@URL
	private String url;

	private String description;

	public SocialMediaDTO(final Integer identifier, final String name, final String url, final String description) {
		super();
		this.identifier = identifier;
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

	public SocialMediaDTO() {
		super();
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

	@Override
	public String toString() {
		return "SocialMediaDTO [id=" + identifier + ", name=" + name + ", url=" + url + ", description=" + description
				+ "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
