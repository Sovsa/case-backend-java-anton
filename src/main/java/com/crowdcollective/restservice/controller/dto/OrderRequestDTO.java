package com.crowdcollective.restservice.controller.dto;

import java.util.Set;

public record OrderRequestDTO(Set<OrderRowRequestDTO> orderRows) {

}
