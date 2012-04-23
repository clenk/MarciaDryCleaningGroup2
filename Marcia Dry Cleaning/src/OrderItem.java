
public class OrderItem
{
	String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String[] getServices()
	{
		return services;
	}
	public void setServices(String[] services)
	{
		this.services = services;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	String[] services;
	double price;
	public OrderItem(String object, String[] services, double[] prices) {
		this.name = object;
		for(int i = 0; i < prices.length; i++) {
			this.price += prices[i];
		}
		this.services = services;
	}
}
