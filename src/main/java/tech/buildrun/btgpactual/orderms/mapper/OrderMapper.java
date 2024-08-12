package tech.buildrun.btgpactual.orderms.mapper;

import tech.buildrun.btgpactual.orderms.entity.OrderEntity;
import tech.buildrun.btgpactual.orderms.model.OrderByCustomerResponseData;

public class OrderMapper {

    public static OrderByCustomerResponseData from(final OrderEntity entity) {
        return new OrderByCustomerResponseData()
                .orderId(entity.getOrderId())
                .customerId(entity.getCustomerId())
                .total(entity.getTotal());
    }
}
