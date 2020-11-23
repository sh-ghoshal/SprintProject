package com.sprint.groceryshopping.dto;




import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDTO {

		private static final long serialVersionUID = 1L;
		@NotNull
		private long code;
		
		@NotNull
		@Size(min=2, message = "name must not be less than 2 characters")
		private String name;
		
		@NotNull
		private String price;
		
		@NotNull(message = "Description cannot be empty")
		private String description;
		
		@NotNull
		private String brand;
		
		public ProductDTO() {
			
		}
		 
		public ProductDTO(String name, String price, String description, String brand) {
				
			
			//this.code = code;
			this.name = name;
			this.price = price;
			this.description = description;
			this.brand = brand;
		}


		public long getCode() {
			return code;
		}


		public void setCode(long code) {
			this.code = code;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getPrice() {
			return price;
		}


		public void setPrice(String price) {
			this.price = price;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public String getBrand() {
			return brand;
		}


		public void setBrand(String brand) {
			this.brand = brand;
		}

		@Override
		public String toString() {
			return "ProductDTO [code=" + code + ", name=" + name + ", price=" + price + ", description=" + description
					+ ", brand=" + brand + "]";
		}
		
}