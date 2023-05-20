package com.devkets.springtesting.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @GetMapping(value="/greeting")
    public ResponseEntity<String> getGreeting(@RequestParam(name = "name", required=false, defaultValue="Hello World") String name) {
        return ResponseEntity.ok().header("header1", "value1").body(name);
    }

    @GetMapping(value="/sumTwoNumbers")
    public ResponseEntity<String> sumTwoNumbers(
        @RequestParam(name="valueX", required=true) String valueX,
        @RequestParam(name="valueY", required=true) String valueY
        ) {
            int sum = Integer.parseInt(valueX) + Integer.parseInt(valueY);
            return ResponseEntity.ok().body("Sum = " + sum);
        }
}
