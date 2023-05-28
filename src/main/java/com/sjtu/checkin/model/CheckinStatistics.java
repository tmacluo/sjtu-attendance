package com.sjtu.checkin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckinStatistics {
    private String label;
    private String type;
    private Integer value;
}
