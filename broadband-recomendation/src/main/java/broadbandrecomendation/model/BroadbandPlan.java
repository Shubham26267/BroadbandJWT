package broadbandrecomendation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "broadband_plans")
public class BroadbandPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Plan name is required")
	private String name;
	
	@Min(value = 1, message = "Speed must be greater than 0 Mbps")
	private int speed;
	
	@Min(value = 1, message = "Price must be greater than 0")
	private double price;
	
	@Min(value = 1, message = "Data cap must be grater than 0 GB")
	private int dataCap;
	
	private String description;
	
	
	public BroadbandPlan() {
	}


	public BroadbandPlan(String name, int speed, double price, int dataCap, String description) {
		super();
		this.name = name;
		this.speed = speed;
		this.price = price;
		this.dataCap = dataCap;
		this.description = description;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getDataCap() {
		return dataCap;
	}


	public void setDataCap(int dataCap) {
		this.dataCap = dataCap;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
