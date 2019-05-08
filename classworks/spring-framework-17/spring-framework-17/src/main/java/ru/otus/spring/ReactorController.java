package ru.otus.spring;

import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactorController {

    @GetMapping("/flux/one")    
    public Mono<String> one() {
        return Mono.just("one");
    }

    @GetMapping("/flux/ten")
    public Flux<Integer> list() {
        return Flux.range(1, 10).filter(n -> n % 3 == 0).map(n -> n * 100); // 300, 600, 900
    }
}
