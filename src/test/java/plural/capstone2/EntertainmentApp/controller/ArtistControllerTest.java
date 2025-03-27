package plural.capstone2.EntertainmentApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.enums.ArtistType;
import plural.capstone2.EntertainmentApp.service.ArtistService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Tag("integration")
class ArtistControllerTest {

    private Artist artist;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        artist = new Artist(
                "Pink Floyd",
                ArtistType.GROUP,
                "One of the most successful and influential rock groups in history",
                "British",
                1964);
    }

    @AfterEach
    void tearDown() {
        artistService.resetArtistDataStore();
    }

    @Test
    void getArtists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main/artists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getArtistById() throws Exception {
        artist = artistService.insertArtist(artist);
        mockMvc.perform(MockMvcRequestBuilders.get("/main/artists/" + artist.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(artist.getId()));
    }

    @Test
    void insertArtist() throws Exception {
        mockMvc.perform(post("/main/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(artist.getName()));
    }

    @Test
    void updateArtist_shouldReturnStatusOkWhenAllParametersMatch() throws Exception {
        artistService.insertArtist(artist);
        artist.setYearFounded(1960);

        mockMvc.perform(put("/main/artists/" + artist.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yearFounded").value(1960));
    }

    @Test
    void updateArtist_shouldReturnStatusBadWhenIdsDoNotMatch() throws Exception {
        mockMvc.perform(put("/main/artists/" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artist)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateArtist_shouldInsertArtistWhenIdIsZero() throws Exception {
        Artist artist1 = new Artist();
        mockMvc.perform(put("/main/artists/" + 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artist1)))
                .andExpect(status().isOk());

        Artist insertedArtist = artistService.findArtistById(1);

        assertNotNull(insertedArtist);
        assertEquals(insertedArtist.getName(), artistService.findArtistById(1).getName());
    }

    @Test
    void deleteArtist() throws Exception {
        artistService.insertArtist(artist);

        mockMvc.perform(delete("/main/artists/" + artist.getId()))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/main/artists/" + artist.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void resetArtists() throws Exception {
        artistService.insertArtist(artist);

        mockMvc.perform(delete("/main/artists/reset"))
                .andExpect(status().isNoContent());
    }

}
