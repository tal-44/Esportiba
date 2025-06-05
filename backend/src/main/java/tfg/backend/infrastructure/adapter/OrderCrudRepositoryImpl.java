package tfg.backend.infrastructure.adapter;

import tfg.backend.domain.model.Order;
import tfg.backend.domain.model.OrderState;
import tfg.backend.domain.port.IOrderRepository;
import tfg.backend.infrastructure.entity.OrderEntity;
import tfg.backend.infrastructure.entity.UserEntity;
import tfg.backend.infrastructure.mapper.IOrderMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrderCrudRepositoryImpl implements IOrderRepository {
    private final IOrderMapper iOrderMapper;
    private final IOrderCrudRepository iOrderCrudRepository;

    public OrderCrudRepositoryImpl(IOrderMapper iOrderMapper, IOrderCrudRepository iOrderCrudRepository) {
        this.iOrderMapper = iOrderMapper;
        this.iOrderCrudRepository = iOrderCrudRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = iOrderMapper.toOrderEntity(order);

        orderEntity.getOrderProducts().forEach(
                orderProductEntity -> orderProductEntity.setOrderEntity(orderEntity)
        );

        return iOrderMapper.toOrder(iOrderCrudRepository.save(orderEntity));
    }

    @Override
    public Order findByID(Integer id) {
        return iOrderMapper.toOrder(iOrderCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order con id " + id + " no encontrado")
        ));
    }

    @Override
    public Iterable<Order> findAll() {
        return iOrderMapper.toOrderList(iOrderCrudRepository.findAll());
    }

    @Override
    public Iterable<Order> findByUserId(Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return iOrderMapper.toOrderList(iOrderCrudRepository.findByUserEntity(userEntity));
    }

    @Override
    public void updateStateById(Integer id, String state) {
        if (state.equals(OrderState.CANCELLED.toString())){
            iOrderCrudRepository.updateStateById(id, OrderState.CANCELLED);
        }else {
            iOrderCrudRepository.updateStateById(id, OrderState.CONFIRMED);

        }
    }


/*
    @Override
    public void updateStateById(Integer id, String state) {
        OrderState orderState = state.equals(OrderState.CANCELLED.toString()) ? OrderState.CANCELLED : OrderState.CONFIRMED;
        iOrderCrudRepository.updateStateById(id, orderState);
    }
*/
}
