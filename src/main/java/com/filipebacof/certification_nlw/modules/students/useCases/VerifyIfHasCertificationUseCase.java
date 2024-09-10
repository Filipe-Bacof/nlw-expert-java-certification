package com.filipebacof.certification_nlw.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.filipebacof.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;

@Service
public class VerifyIfHasCertificationUseCase {
  public boolean execute(VerifyHasCertificationDTO dto) {
    if (dto.getEmail().equals("filipebacof@gmail.com") && dto.getTechnology().equals("nodejs")) {
      return true;
    }
    return false;
  }
}
