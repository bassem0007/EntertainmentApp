package plural.capstone2.EntertainmentApp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.ArtistType;
import plural.capstone2.EntertainmentApp.enums.Genre;
import plural.capstone2.EntertainmentApp.service.ArtistService;

import plural.capstone2.EntertainmentApp.service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Tag("integraiton")
class ArtistTrackControllerTest {

    private Artist artist;

    private Track track;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrackService trackService;

    @Autowired
    private ArtistService artistService;

    @BeforeEach
    void setUp() {
         artist =  new Artist(
                "Pink Floyd",
                ArtistType.GROUP,
                "One of the most successful and influential rock groups in history",
                "British",
                1964);
         artist = artistService.insertArtist(artist);

         track = new Track("High Hopes",500, Genre.ROCK,1988,85);
         track = trackService.insertTrack(track);
    }

    @Test
    void addTrackToArtist() throws Exception {
        mockMvc.perform(post("/main/artists/" + artist.getId() + "/tracks/" + track.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(artist.getName()));

        assertEquals(track.getTitle(), artist.getTracks().get(0).getTitle());
    }

    @Test
    void removeTrackFromArtist() throws Exception {
        mockMvc.perform(delete("/main/artists/" + artist.getId() + "/tracks/" + track.getId()))
                .andExpect(status().isAccepted());

        assertEquals(0, track.getArtists().size());
    }

    @Test
    void addArtistToTrack() throws Exception {
        mockMvc.perform(post("/main/tracks/" + track.getId() + "/artists/" + artist.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value(track.getTitle()));

        assertEquals(artist.getName(), track.getArtists().get(0).getName());

    }

    @Test
    void removeArtistFromTrack() throws Exception {
        mockMvc.perform(delete("/main/tracks/" + track.getId() + "/artists/" + artist.getId()))
                .andExpect(status().isAccepted());

        assertEquals(0, artist.getTracks().size());
    }

}