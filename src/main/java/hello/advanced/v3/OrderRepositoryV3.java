package hello.advanced.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace trace;

    /**
     * methodName : save
     * author : Jihun Park
     * description : 주문 저장
     * @param itemId
     */
    public void save(String itemId){

        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepository.save"); // 이전 컨텍스트 전달받
            //저장 로직
            if("ex".equals(itemId)){
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            trace.end(status);

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
