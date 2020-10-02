package com.igormeira.directorylistener.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesMan {

    private String name;
    private String document;
    private Double salary;

}
