package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.service.ArtikalService;

@RestController
public class ArtikalRestController {

    @Autowired
    private ArtikalService artikalService;

}
