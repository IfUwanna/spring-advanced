package hello.advanced.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepositoryV1;
    private final HelloTraceV1 trace;


    /**
     * methodName : orderItem
     * author : Jihun Park
     * description : 주문 저장
     * @param itemId
     */
    public void orderItem(String itemId){

        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem");
            orderRepositoryV1.save(itemId);
            trace.end(status);

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }
}
