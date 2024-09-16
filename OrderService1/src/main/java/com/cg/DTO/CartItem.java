package com.cg.DTO;

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
public class CartItem {
    private int productId;
    private int quantity;
    private double price;
   // private int id;

    
//    @Override
//    public String toString() {
//        return "CartItem{" +
//                "productId=" + productId +
//                ", quantity=" + quantity +
//                ", price=" + price +
//                ", id=" + id +
//                '}';
//    }
}
