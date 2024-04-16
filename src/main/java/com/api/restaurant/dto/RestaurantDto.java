package com.api.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private String id;

    private String userId;

    private String restaurantName;

    private String city;

    private String country;

    private Integer deliveryPrice;

    private Integer estimatedDeliveryTime;

    private List<String> cuisines;

    private List<MenuItemDto> menuItems;

    private String imageUrl;

    private Boolean isActivatedByUser;

    private LocalDateTime lastUpdated;
}
