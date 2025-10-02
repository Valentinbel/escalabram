package com.escalabram.escalabram.controller.http;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@Validated // TODO: find why @PathVariable @Size seems to work without Validated
public class ClimberUserController {


}