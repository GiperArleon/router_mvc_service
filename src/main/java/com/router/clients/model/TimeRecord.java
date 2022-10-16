package com.router.clients.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecord {
    private Integer id;
    private Integer userId;
    private String date;
    private String description;
    private Integer hours;
    private Integer minutes;
}
