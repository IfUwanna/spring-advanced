package hello.advanced.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    /**
     * V4 : 추상 템플릿 적용
     */
    @GetMapping("/v4/request")
    public String request(String itemId){

        // 템플릿 메서드 클래스 선언 (내부익명클래스 상속)
        AbstractTemplate<String> template = new AbstractTemplate<String>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };
        // 실행
        return template.execute("OrderController.request()");

    }
}
