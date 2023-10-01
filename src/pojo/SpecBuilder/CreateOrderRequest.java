package pojo.SpecBuilder;

import java.util.List;

public class CreateOrderRequest {

	private List<CreateOrderDetailsRequest> orders;

	public List<CreateOrderDetailsRequest> getOrders() {
		return orders;
	}

	public void setOrders(List<CreateOrderDetailsRequest> orders) {
		this.orders = orders;
	}

	
}
