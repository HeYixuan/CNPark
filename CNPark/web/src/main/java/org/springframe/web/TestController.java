package org.springframe.web;

import org.springframe.security.SpringSecurityService;
import org.springframe.util.JwtTokenUtil;
import org.springframe.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class TestController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SpringSecurityService springSecurityService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

   @PostMapping("/get")
    public ResponseEntity<String> get(){
       int i = 1/0;
       return new ResponseEntity<String>(null);
    }
}
