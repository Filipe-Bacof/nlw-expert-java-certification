package com.filipebacof.certification_nlw;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class Ping {
  @GetMapping("")
  public String retornoPrimeiraController() {
    return "Pong 🏓";
  }
}
