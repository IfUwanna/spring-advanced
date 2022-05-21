package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : hello.advanced.trace.hellotrace
 * fileName       : HelloTraceV1Test
 * author         : Jihun Park
 * date           : 2022/05/22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/22        Jihun Park       최초 생성
 */
class HelloTraceV1Test {

    @Test
    public void begin_end() throws Exception{
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception(){
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

}