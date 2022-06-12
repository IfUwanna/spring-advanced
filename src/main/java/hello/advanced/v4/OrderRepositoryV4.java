package hello.advanced.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

    private final LogTrace trace;

    /**
     * V4 :  템플릿메서드 패턴 적용
     */
    public void save(String itemId){

        // 템플릿 메서드 클래스 선언 (내부익명클래스 상속)
        AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
            @Override
            protected Void call() {
                //저장 로직
                if("ex".equals(itemId)){
                    throw new IllegalStateException("예외 발생!");
                }
                sleep(1000);
                return null;
            }
        };
        // 실행
        template.execute("OrderRepository.save()");
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
