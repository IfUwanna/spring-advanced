package hello.advanced;

import hello.advanced.trace.logtrace.FiledLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace(){  // LogTrace 인터페이스 수동 빈등록
        return new FiledLogTrace();
    }
}
