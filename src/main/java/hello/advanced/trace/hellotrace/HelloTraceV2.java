package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * packageName    : hello.advanced.trace.hellotrace
 * fileName       : HelloTraceV2
 * author         : Jihun Park
 * date           : 2022/05/22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/22        Jihun Park       최초 생성
 */
@Slf4j
@Component
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    /**
    [796bccd9] OrderController.request()
    [796bccd9] |-->OrderService.orderItem()
    [796bccd9] |   |-->OrderRepository.save()
    [796bccd9] |   |<--OrderRepository.save() time=1004ms
    [796bccd9] |<--OrderService.orderItem() time=1014ms
    [796bccd9] OrderController.request() time=1016ms
    */

    // [796bccd9] |   |<--OrderRepository.save() time=1004ms
    public TraceStatus begin(String message){

        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId,startTimeMs,message);
    };

    //V2에서 추가. 두번째부터는 이걸로 호출!! 이전 컨텍스트를 받아서 다음 ID를 구함.
    public TraceStatus beginSync(TraceId beforeTraceId, String message){

        TraceId nextId = beforeTraceId.createNextId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);
        return new TraceStatus(nextId,startTimeMs,message);
    };


    //[796bccd9] |   |<--OrderRepository.save() time=1004ms
    public void end(TraceStatus status){
        complete(status, null);
    };


    //[b7119f27] | |<X-OrderRepository.save() time=0ms ex=java.lang.IllegalStateException: 예외 발생!
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) { Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }
    //level=0 : 아무것도 없음
    //level=1     : |-->
    //level=1     : |   |-->
    //level=1+ex  : |<X-
    //level=2+ex  : |   |<X-
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }

}
