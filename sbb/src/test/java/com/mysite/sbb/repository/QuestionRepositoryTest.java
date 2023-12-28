package com.mysite.sbb.repository;

import com.mysite.sbb.entity.Question;
import com.mysite.sbb.service.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Test
    public void testJpa() {

        Question q1 = new Question();

        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());

        questionRepository.save(q1);

        Question q2 = new Question();

        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);
    }

    @Test
    public void testJpa2() {

        List<Question> all = questionRepository.findAll();
        Assertions.assertEquals(2, all.size());

        Question q = all.get(0);
        Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());

    }

    @Test
    public void testJpa3() {

        Optional<Question> op = questionRepository.findById(1L);

        if(op.isPresent()) {
            Question q = op.get();
            Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    public void testJpa4() {

        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        Assertions.assertEquals(1, q.getId());
    }

    @Test
    public void testJpa5() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
        Assertions.assertEquals(1, q.getId());
    }

    @Test
    public void testJpa6() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    public void testJpa7() {

        Optional<Question> op = questionRepository.findById(1L);
        Assertions.assertTrue(op.isPresent());
        Question q = op.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);
    }

    @Test
    public void testJpa8() {

        Assertions.assertEquals(2, questionRepository.count());
        Optional<Question> oq = questionRepository.findById(1L);
        Assertions.assertTrue(oq.isPresent());
        Question q = oq.get();
        questionRepository.delete(q);
        Assertions.assertEquals(1, questionRepository.count());
    }

    @Test
    public void testDummiesData() {

        IntStream.rangeClosed(1, 300).forEach(i -> {
            String subject = "테스트 데이터입니다 : [" + i + "]";
            String content = "테스트 내용";
            questionService.create(subject, content);
        });
    }

    @Test
    public void testDelete() {
        for(Long l=2L; l <= 306L; l++) {
            questionRepository.deleteById(l);
        }
    }
}
