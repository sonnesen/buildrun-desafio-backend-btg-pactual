package tech.buildrun.btgpactual.orderms.factory;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import tech.buildrun.btgpactual.orderms.model.OrderByCustomerResponseData;

public class OrderResponseFactory {

    public static Page<OrderByCustomerResponseData> buildWithOneItem() {
        var orderResponse = new OrderByCustomerResponseData().orderId(1L).customerId(2L)
                .total(BigDecimal.valueOf(20.50));

        return new PageImpl<>(List.of(orderResponse));
    }
}
