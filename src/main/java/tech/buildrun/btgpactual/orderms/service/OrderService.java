package tech.buildrun.btgpactual.orderms.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import tech.buildrun.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import tech.buildrun.btgpactual.orderms.model.OrderByCustomerResponseData;

public interface OrderService {

    void save(OrderCreatedEvent event);

    Page<OrderByCustomerResponseData> findAllByCustomerId(Long customerId, PageRequest pageRequest);

    BigDecimal findTotalOnOrdersByCustomerId(Long customerId);

}