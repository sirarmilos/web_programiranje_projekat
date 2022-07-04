package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vezbe.demo.model.SlikaProba;
import vezbe.demo.service.SlikaService;

import java.io.IOException;

@RestController
public class SlikaRestController {

    @Autowired
    private SlikaService slikaService;

}
