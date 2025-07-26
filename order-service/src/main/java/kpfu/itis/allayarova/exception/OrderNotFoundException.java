package kpfu.itis.allayarova.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
    private final Long id;

    public OrderNotFoundException(Long id) {
        super("Not found order with id: " + id);
        this.id = id;
    }

}
