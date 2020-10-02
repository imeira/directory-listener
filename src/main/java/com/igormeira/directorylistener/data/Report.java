package com.igormeira.directorylistener.data;


import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@ToString
public class Report {

    private static final String HEADER_SALESMAN = "001";
    private static final String HEADER_CLIENT = "002";
    private static final String HEADER_SALE = "003";

    private String fileName;

    private List<SalesMan> salesMans;
    private List<Client> clients;
    private List<Sale> sales;

    private Integer clientAmount;
    private Integer salesMansAmount;
    private String salesExpensiveID;
    private String salesMansWorst;


    public Report() {
        this.salesMans = new ArrayList<SalesMan>();
        this.clients = new ArrayList<Client>();
        this.sales = new ArrayList<Sale>();
        this.fileName = null;
        this.clientAmount = 0;
        this.salesMansAmount = 0;
        this.salesExpensiveID = "";
        this.salesMansWorst = "";
    }

    public Report(Report report, String[] line) {
        String id = line[0];
        switch (id) {
            case HEADER_SALESMAN: {
                SalesMan salesMan = new SalesMan();
                salesMan.setDocument(line[1]);
                salesMan.setName(line[2]);
                salesMan.setSalary(Double.parseDouble(line[3]));
                report.getSalesMans().add(salesMan);
                break;

            }
            case HEADER_CLIENT: {
                Client client = new Client();
                client.setDocument(line[1]);
                client.setName(line[2]);
                client.setArea(line[3]);
                report.getClients().add(client);
                break;
            }
            case HEADER_SALE: {
                Sale sale = new Sale();
                sale.setId(line[1]);
                sale.setSalesmanName(line[3]);
                String[] itemLine = line[2].replace("[", "").replace("]", "").split(",");
                Arrays.stream(itemLine)
                        .forEach(item -> {
                            String[] itemArray = item.split("-");
                            sale.getItens().add(new Item(itemArray[0], itemArray[1], Double.valueOf(itemArray[2])));
                        });
                report.getSales().add(sale);
                break;
            }
        }
    }

    public Integer getClientAmount() {
        return getClients().size();
    }

    public Integer getSalesMansAmount() {
        return getSales().size();
    }

    public String getSalesExpensiveID() {
        return sales.size() > 0 ? sales.stream()
                .collect(Collectors.groupingBy(Sale::getId,
                        Collectors.summingDouble(Sale::getItensPrice)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList())
                .get(0).getKey() : "";
    }



    public String getSalesMansWorst() {
        return sales.size() > 0 ? sales.stream()
                .collect(Collectors.groupingBy(Sale::getSalesmanName,
                        Collectors.summingDouble(Sale::getItensPrice)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList())
                .get(0).getKey() : "";
    }

}
