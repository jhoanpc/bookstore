package com.spacegroup.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
public class StockUpdateDTO implements Serializable {

    private Integer quantityChange;



}
