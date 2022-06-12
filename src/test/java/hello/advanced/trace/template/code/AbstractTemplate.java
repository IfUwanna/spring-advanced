package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {


    public void excute() {

        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행 (변하는 부분)
        call();
        //비즈니스 로직 종료

        // 로깅 로직
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("resultTime={}ms",resultTime);

    }

    protected abstract void call(); // 변하는부분 : 비즈니스 로직을 자식 클래스에서 오버라이딩한다.
}
