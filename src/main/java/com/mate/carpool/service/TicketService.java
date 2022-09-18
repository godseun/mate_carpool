package com.mate.carpool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mate.carpool.model.TicketEntity;
import com.mate.carpool.persistence.TicketRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketService {

  @Autowired
  private TicketRepository ticketRepository;

  public TicketEntity create(final TicketEntity ticketEntity) {
    log.debug(ticketEntity.toString());

    // TODO: 유효성검사, 동일한 날짜와 시간의 티켓 검증로직필요
    return ticketRepository.save(ticketEntity);
  }

}
