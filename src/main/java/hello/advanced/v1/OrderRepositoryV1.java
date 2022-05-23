package hello.advanced.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final HelloTraceV1 trace;

    /**
     * methodName : save
     * author : Jihun Park
     * description : 주문 저장
     * @param itemId
     */
    public void save(String itemId){

        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepository.save");
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