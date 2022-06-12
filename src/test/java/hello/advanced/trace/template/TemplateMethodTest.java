package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    public void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {

        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행 (변하는 부분)
        log.info("비즈니스 로직 1 실행");   //service
        //비즈니스 로직 종료

        // 로깅 로직
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("resultTime={}ms",resultTime);

    }

    private void logic2() {

        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행 (변하는 부분)
        log.info("비즈니스 로직 2 실행");   //service
        //비즈니스 로직 종료

        // 로깅 로직 (변하지 않는 부분)
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("resultTime={}ms",resultTime);

    }

    /**
     * 템플릿 메서드 패턴 적용
     */
    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.excute();
        AbstractTemplate template2 = new SubClassLogic2();
        template2.excute();
    }
//
}
