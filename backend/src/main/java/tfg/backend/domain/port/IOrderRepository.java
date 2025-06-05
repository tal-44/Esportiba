package tfg.backend.domain.port;

import tfg.backend.domain.model.Order;

public interface IOrderRepository {
    Order save (Order order);
    Order findByID (Integer id);
    Iterable<Order> findAll();
    Iterable<Order> findByUserId(Integer UserId);
    void updateStateById(Integer id, String state);
}
