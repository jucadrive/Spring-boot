package com.mysite.sbb.repository;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testJpa1() {

        Optional<Question> oq = questionRepository.findById(2L);
        Assertions.assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);   // 어떤 질문의 답변인지 알기 위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);
    }

    @Test
    public void testJpa2() {

        Optional<Answer> oa = answerRepository.findById(1L);
        Assertions.assertTrue(oa.isPresent());
        Answer a = oa.get();
        Assertions.assertEquals(2, a.getQuestion().getId());
    }

    @Test
    @Transactional
    public void testJpa3() {

        Optional<Question> oq = questionRepository.findById(2L);
        Assertions.assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        Assertions.assertEquals(1, answerList.size());
        Assertions.assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
