package testing.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import testing.order.Order;
import testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartHandler cartHandler;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;

    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resoultCart = cartService.processCart(cart);
        then(cartHandler).should().sendToPrepare(cart);

        //then
        verify(cartHandler).sendToPrepare(cart);
        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resoultCart.getOrders(), hasSize(1));
        assertThat(resoultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void processCartShouldNotSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(false);

        //when
        Cart resoultCart = cartService.processCart(cart);


        //then
        verify(cartHandler, never()).sendToPrepare(cart);
        then(cartHandler).should(never()).sendToPrepare(cart);
        assertThat(resoultCart.getOrders(), hasSize(1));
        assertThat(resoultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));

    }

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatcher() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(false);

        //when
        Cart resoultCart = cartService.processCart(cart);

        //then
        verify(cartHandler, never()).sendToPrepare(any(Cart.class));
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class));
        assertThat(resoultCart.getOrders(), hasSize(1));
        assertThat(resoultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));

    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
        CartHandler cartHandler = mock(CartHandler.class);

        given(cartHandler.canHandleCart(cart)).willReturn(true, false, false, true);

        //then
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
    }

    @Test
    void processCartShouldNotSendToPrepareWithLambdas() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(false);

        //when
        Cart resoultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should(never()).sendToPrepare(cart);
        assertThat(resoultCart.getOrders(), hasSize(1));
        assertThat(resoultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));

    }

    @Test
    void canHandleCartShouldThrowException() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willThrow(IllegalArgumentException.class);

        //when
        //then\
        assertThrows(IllegalArgumentException.class, () -> cartService.processCart(cart));
    }

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        //ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resoultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cartArgumentCaptor.capture());

        assertThat(cartArgumentCaptor.getValue().getOrders().size(),equalTo(1));

        assertThat(resoultCart.getOrders(), hasSize(1));
        assertThat(resoultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void shouldDoNothingWhenProcessCart() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

//        doNothing().when(cartHandler).sendToPrepare(cart);
        willDoNothing().given(cartHandler).sendToPrepare(cart);
        /*        willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart);*/

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void shouldAnswerWhenProcessCart() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

/*        doAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);*/

/*        when(cartHandler.canHandleCart(cart)).then(i -> {
            Cart argumentCart = i.getArgument(0);
            argumentCart.clearCart();
            return true;
        });*/

/*        willAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).given(cartHandler).canHandleCart(cart);*/


        given(cartHandler.canHandleCart(cart)).will(i -> {
            Cart argumentCart = i.getArgument(0);
            argumentCart.clearCart();
            return true;
        });

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders().size(), equalTo(0));

    }

}
