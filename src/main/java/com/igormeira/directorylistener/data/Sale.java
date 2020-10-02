package com.igormeira.directorylistener.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Sale {

    private String id;
    private List<Item> itens;
    private String salesmanName;

    public Sale() {
        this.id = null;
        this.itens = new ArrayList<Item>();
        this.salesmanName = null;
    }

    public Double getItensPrice(){
        return itens.stream()
                .mapToDouble(v -> v.getPrice())
                .sum();
    }


}
