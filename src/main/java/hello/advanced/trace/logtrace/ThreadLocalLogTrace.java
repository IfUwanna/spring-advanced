package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

   // private TraceId traceIdHolder; // tracdId 동기화 (V2 파라미터 전달 대체). 동시성 이슈발생!!!!
    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>(); // 각 쓰레드별 저장소 ThreadLocal 사용

    /** tracae Id 시작, +1 */
    private void syncTraceId(){
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.createNextId());
        }
    }
    /** tracae Id 종료, -1 */
    private void releaseTraceId(){
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirestLevel()) {
            traceIdHolder.remove(); //destroy Thread.currentThread()의 저장 데이터 제거
        } else {
            traceIdHolder.set(traceId.createPreviousId());
        }

    }

    @Override
    public TraceStatus begin(String message){

        // TraceId 레벨 증가
        syncTraceId();
        TraceId traceId = traceIdHolder.get();

        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId,startTimeMs,message);
    };

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
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

        // TraceId 레벨 감소
        releaseTraceId();
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
