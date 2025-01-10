package com.crowdcollective.restservice.controller;

import com.crowdcollective.restservice.controller.dto.CustomerDTO;
import com.crowdcollective.restservice.datamodel.Customer;
import com.crowdcollective.restservice.datamodel.CustomerType;
import com.crowdcollective.restservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping()
    ResponseEntity<Object> getCustomer(@RequestParam(name = "customerid") Integer customerid) {
        Customer customer = customerService.getCustomer(customerid);
        if (customer == null) {
            return ResponseHandler.generateResponse("Could not find customer", HttpStatus.NOT_FOUND, null);
        }
        CustomerDTO customerDTO = new CustomerDTO(customer);
        return ResponseHandler.generateResponse("", HttpStatus.OK, customerDTO);
    }

    @PutMapping()
    ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerType customerType = CustomerType.getCustomerTypeFromName(customerDTO.customerType());

        Customer customer = customerService.createCustomer(customerDTO.customerName(),
                customerType,
                customerDTO.orgNr());
        CustomerDTO responseCustomerDto = new CustomerDTO(customer);
        return ResponseHandler.generateResponse("Customer successfully created", HttpStatus.OK, responseCustomerDto);
    }

    @DeleteMapping()
    ResponseEntity<Object> deleteCustomer(@RequestParam(name = "customerid") Integer customerid) {
        customerService.deleteCustomer(customerid);

        return ResponseHandler.generateResponse("Customer successfully deleted", HttpStatus.OK, null);
    }

    @PostMapping()
    ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer;
        try {
            customer = customerService.updateCustomer(customerDTO);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
        CustomerDTO customerDTOResponse = new CustomerDTO(customer);

        return ResponseHandler.generateResponse("Customer successfully updated", HttpStatus.OK, customerDTOResponse);
    }
}
