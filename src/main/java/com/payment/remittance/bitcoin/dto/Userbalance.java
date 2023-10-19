package com.payment.remittance.bitcoin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Userbalance {

  private   BigDecimal satBalance;
  private BigDecimal dollarEstimate;

}
