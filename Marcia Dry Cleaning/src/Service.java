
public class Service {
	private int id;
	private String desc;
	private double price;
	private String time;
	public int getId() {
		return id;
	}
	
	// Getters and Setters
	public String getDesc() {
		return desc;
	}
	public double getPrice() {
		return price;
	}
	public String getTime() {
		return time;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	// Constructor
	public Service(int id, String desc, double price, String time) {
		super();
		this.id = id;
		this.desc = desc;
		this.price = price;
		this.time = time;
	}
	
	
}
