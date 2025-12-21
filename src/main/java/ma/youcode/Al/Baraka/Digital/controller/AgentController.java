package ma.youcode.Al.Baraka.Digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
public class AgentController {
    @GetMapping
    ResponseEntity<String> agent(){
        return ResponseEntity.ok("AGENT_BANCAIRE");
    }
}
