package ma.youcode.Al.Baraka.Digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {
    @GetMapping("/admin")
    ResponseEntity<String>  admin(){
        return ResponseEntity.ok("admin");
    }
    @GetMapping("/agent")
    ResponseEntity<String>  agent(){
        return ResponseEntity.ok("AGENT_BANCAIRE");
    }

}
