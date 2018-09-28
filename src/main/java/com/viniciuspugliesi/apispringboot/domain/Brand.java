package com.viniciuspugliesi.apispringboot.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Brand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String slug;
	
	public Brand() {
	}

	public Brand(Integer id, String name, String slug) {
		super();
		this.id = id;
		this.name = name;
		this.slug = slug;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Brand other = (Brand) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Brand [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", slug=");
		builder.append(slug);
		builder.append("]");
		return builder.toString();
	}
	
	
}
