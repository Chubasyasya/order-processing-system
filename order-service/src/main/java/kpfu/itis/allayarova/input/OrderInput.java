package kpfu.itis.allayarova.input;

import lombok.Data;

import java.util.List;

@Data
public class OrderInput {
    private String date;
    private Long customerId;
    private List<OrderItemInput> items;
}