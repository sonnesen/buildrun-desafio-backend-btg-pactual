package tech.buildrun.btgpactual.orderms.listener;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.MessageBuilder;

import tech.buildrun.btgpactual.orderms.factory.OrderCreatedEventFactory;
import tech.buildrun.btgpactual.orderms.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderCreatedListenerTest {

    @Mock
    OrderServiceImpl orderService;

    @InjectMocks
    OrderCreatedListener orderCreatedListener;

    @Nested
    class Listen {

        @Test
        void shouldCallServiceWithCorrectParameters() {

            // ARRANGE
            var event = OrderCreatedEventFactory.buildWithOneItem();
            var message = MessageBuilder.withPayload(event).build();

            // ACT
            orderCreatedListener.listen(message);

            // ASSERT
            verify(orderService, times(1)).save(eq(message.getPayload()));
        }
    }

}