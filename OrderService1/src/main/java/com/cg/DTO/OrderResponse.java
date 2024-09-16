package com.cg.DTO;

import java.util.List;
import com.cg.entity.OrderItem;
import com.cg.entity.OrderStatus;
import com.cg.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderResponse {
    private int orderId;
    private int userId;
    private double totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private List<OrderItem> orderItems;
}
