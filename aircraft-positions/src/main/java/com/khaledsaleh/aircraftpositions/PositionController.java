package com.khaledsaleh.aircraftpositions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Controller
public class PositionController {
    private final AircraftRepository repository;
    private final RSocketRequester requester;
    private final WebClient client = WebClient.create("http://localhost:7634/aircraft");

    public PositionController(AircraftRepository repository, RSocketRequester.Builder builder){
        this.repository = repository;
        this.requester = builder.tcp("localhost", 7635);
    }

    // HTTP Requester
    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model){
        Flux<Aircraft> aircraftFlux = repository.deleteAll()
                        .thenMany(client.get()
                                .retrieve()
                                .bodyToFlux(Aircraft.class)
                                .filter(plane -> !plane.getReg().isEmpty())
                                .flatMap(repository::save));
        model.addAttribute("currentPositions", aircraftFlux);
        return "positions";
    }

    // RSocket client endpoint
    @ResponseBody
    @GetMapping(value = "/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentACPositionStream(){
        return requester.route("acstream")
                .data("Requesting aircraft positions")
                .retrieveFlux(Aircraft.class);
    }

}
