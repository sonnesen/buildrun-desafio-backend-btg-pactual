package tech.buildrun.btgpactual.orderms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;

import tech.buildrun.btgpactual.orderms.factory.OrderResponseFactory;
import tech.buildrun.btgpactual.orderms.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    OrderServiceImpl orderService;

    @Captor
    ArgumentCaptor<Long> customerIdCaptor;

    @Captor
    ArgumentCaptor<PageRequest> pageRequestCaptor;

    @Nested
    class ListOrders {

        @Test
        void shouldReturnHttpOk() {
            // ARRANGE - prepara todos os mocks para a execucao
            var customerId = 1L;
            var page = 0;
            var pageSize = 10;
            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllByCustomerId(anyLong(), any());
            doReturn(BigDecimal.valueOf(20.50))
                    .when(orderService).findTotalOnOrdersByCustomerId(anyLong());

            // ACT - executar o metodo a ser testado
            var response = customerController.getOrdersByCustomer(customerId, page, pageSize);

            // ASSERT - verifica se a execucao foi certinha
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParamtersToService() {
            // ARRANGE - prepara todos os mocks para a execucao
            var customerId = 1L;
            var page = 0;
            var pageSize = 10;
            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService)
                    .findAllByCustomerId(customerIdCaptor.capture(), pageRequestCaptor.capture());
            doReturn(BigDecimal.valueOf(20.50))
                    .when(orderService).findTotalOnOrdersByCustomerId(customerIdCaptor.capture());

            // ACT - executar o metodo a ser testado
            var response = customerController.getOrdersByCustomer(customerId, page, pageSize);

            // ASSERT - verifica se a execucao foi certinha
            assertEquals(2, customerIdCaptor.getAllValues().size());
            assertEquals(customerId, customerIdCaptor.getAllValues().get(0));
            assertEquals(customerId, customerIdCaptor.getAllValues().get(1));
            assertEquals(page, pageRequestCaptor.getValue().getPageNumber());
            assertEquals(pageSize, pageRequestCaptor.getValue().getPageSize());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {
            // ARRANGE - prepara todos os mocks para a execucao
            var customerId = 1L;
            var page = 0;
            var pageSize = 10;
            var totalOnOrders = BigDecimal.valueOf(20.50);
            var pagination = OrderResponseFactory.buildWithOneItem();

            doReturn(pagination)
                    .when(orderService).findAllByCustomerId(anyLong(), any());
            doReturn(totalOnOrders)
                    .when(orderService).findTotalOnOrdersByCustomerId(anyLong());

            // ACT - executar o metodo a ser testado
            var response = customerController.getOrdersByCustomer(customerId, page, pageSize);

            // ASSERT - verifica se a execucao foi certinha
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().getData());
            assertNotNull(response.getBody().getPagination());
            assertNotNull(response.getBody().getSummary());

            assertEquals(totalOnOrders, response.getBody().getSummary().getTotalOnOrders());

            assertEquals(pagination.getTotalElements(),
                    response.getBody().getPagination().getTotalElements().intValue());
            assertEquals(pagination.getTotalPages(), response.getBody().getPagination().getTotalPages());
            assertEquals(pagination.getNumber(), response.getBody().getPagination().getPage());
            assertEquals(pagination.getSize(), response.getBody().getPagination().getPageSize());

            assertEquals(pagination.getContent(), response.getBody().getData());
        }
    }

}