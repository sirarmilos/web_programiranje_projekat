package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.repository.SlikaProbaRepository;

@Service
public class SlikaService {

    @Autowired
    private SlikaProbaRepository slikaProbaRepository;
}
