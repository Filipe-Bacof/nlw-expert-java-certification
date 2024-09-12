package com.filipebacof.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipebacof.certification_nlw.modules.questions.entities.QuestionEntity;
import com.filipebacof.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.filipebacof.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.filipebacof.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.filipebacof.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.filipebacof.certification_nlw.modules.students.entities.StudentEntity;
import com.filipebacof.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.filipebacof.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) {
    List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());

    dto.getQuestionsAnswers()
        .stream().forEach(questionAnswer -> {
          var question = questionsEntity.stream().filter(q -> q.getId()
              .equals(questionAnswer.getQuestionID())).findFirst().get();

          var findCorrectAlternative = question.getAlternatives().stream()
              .filter(alternative -> alternative.isCorrect()).findFirst().get();

          if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
            questionAnswer.setCorrect(true);
          } else {
            questionAnswer.setCorrect(false);
          }
        });

    var student = studentRepository.findByEmail(dto.getEmail());

    UUID studentID;

    if (student.isEmpty()) {
      var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
      studentCreated = studentRepository.save(studentCreated);
      studentID = studentCreated.getId();
    } else {
      studentID = student.get().getId();
    }

    List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

    CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
        .technology(dto.getTechnology())
        .studentID(studentID)
        .answersCertificationsEntity(answersCertifications)
        .build();

    var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

    return certificationStudentCreated;
  }
}
