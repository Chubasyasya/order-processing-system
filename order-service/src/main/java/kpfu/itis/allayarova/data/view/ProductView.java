package kpfu.itis.allayarova.data.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductView {
    private Long id;
    private String name;
    private String description;
    private Double price;
}