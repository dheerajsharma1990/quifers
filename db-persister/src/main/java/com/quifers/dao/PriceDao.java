package com.quifers.dao;

import com.quifers.domain.Price;

public interface PriceDao {

    void savePrice(Price price);

    Price getPrice(String orderId);

}
