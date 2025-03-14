package plural.capstone2.EntertainmentApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plural.capstone2.EntertainmentApp.service.ArtistService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private ArtistService artistService;

}
