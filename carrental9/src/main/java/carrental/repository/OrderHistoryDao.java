package carrental.repository;

import java.util.List;

import carrental.domain.OrderHistory;

public interface OrderHistoryDao {
	List<OrderHistory> find(Integer orderId);
	OrderHistory findLatest(Integer orderId);
	void create(OrderHistory orderHistory);
}
