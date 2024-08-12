package tech.buildrun.btgpactual.orderms.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import tech.buildrun.btgpactual.orderms.api.CustomersApi;
import tech.buildrun.btgpactual.orderms.model.OrderByCustomerResponse;
import tech.buildrun.btgpactual.orderms.model.OrderByCustomerResponseData;
import tech.buildrun.btgpactual.orderms.model.OrderByCustomerResponseSummary;
import tech.buildrun.btgpactual.orderms.model.PaginationData;
import tech.buildrun.btgpactual.orderms.service.OrderService;

@RestController
public class CustomerController implements CustomersApi {

    private final OrderService orderService;

    public CustomerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderByCustomerResponse> getOrdersByCustomer(final Long customerId, final Integer page,
            final Integer pageSize) {
        final var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        final var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(customerId);

        return ResponseEntity.ok(new OrderByCustomerResponse()
                .summary(buildSummary(totalOnOrders))
                .data(pageResponse.getContent())
                .pagination(buildPagination(pageResponse)));
    }

    private PaginationData buildPagination(final Page<OrderByCustomerResponseData> pageResponse) {
        return new PaginationData()
                .page(pageResponse.getNumber())
                .pageSize(pageResponse.getSize())
                .totalElements(pageResponse.getNumberOfElements())
                .totalPages(pageResponse.getTotalPages());
    }

    private OrderByCustomerResponseSummary buildSummary(final BigDecimal totalOnOrders) {
        return new OrderByCustomerResponseSummary().totalOnOrders(totalOnOrders);
    }
}
