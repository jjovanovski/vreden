package com.jj.vreden.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/board")
public class BoardController {

    @GetMapping(path = "/{id}")
    @ResponseBody
    public String getBoard(Principal principal, @PathVariable int id) {
        return "Hello " + principal.getName() + " this is board " + id;
    }

}
