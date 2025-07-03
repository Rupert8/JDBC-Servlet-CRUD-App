package service;

import dao.CustomerDao;
import dto.CustomersDto;
import entity.Customers;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class CustomersService {
    @Getter
    private static final CustomersService instance = new CustomersService();
    private final CustomerDao customerDao = CustomerDao.getInstance();

    public List<CustomersDto> findAll() {
        return customerDao.findAll().stream().map(customers ->
                CustomersDto.builder()
                        .id(customers.getId())
                        .firstName(customers.getFirstName())
                        .lastName(customers.getLastName())
                        .email(customers.getEmail()).build()).collect(Collectors.toList());
    }


    private CustomersService() {
    }

}
