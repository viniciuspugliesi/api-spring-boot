package com.viniciuspugliesi.apispringboot.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String slug;
	private Float price;
	private Double description;
	private String image;
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name="product_category_id")
	private ProductCategory productCategory;
	
	public Product() {
	}

	public Product(Integer id, String name, String slug, Float price, Double description, String image, Brand brand, ProductCategory productCategory) {
		super();
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.price = price;
		this.description = description;
		this.image = image;
		this.brand = brand;
		this.productCategory = productCategory;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Double getDescription() {
		return description;
	}

	public void setDescription(Double description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
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
		Product other = (Product) obj;
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
		builder.append("Product [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", slug=");
		builder.append(slug);
		builder.append(", price=");
		builder.append(price);
		builder.append(", description=");
		builder.append(description);
		builder.append(", image=");
		builder.append(image);
		builder.append(", brand=");
		builder.append(brand);
		builder.append(", productCategory=");
		builder.append(productCategory);
		builder.append("]");
		return builder.toString();
	}
	
	
}
