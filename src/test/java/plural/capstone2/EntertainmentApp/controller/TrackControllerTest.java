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
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.Genre;
import plural.capstone2.EntertainmentApp.service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Tag("integration")
class TrackControllerTest {

    private Track track;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrackService trackService;

    @BeforeEach
    void setUp() {
        track = new Track("High Hopes",500, Genre.ROCK,1988,85);
        trackService.insertTrack(track);
    }

    @AfterEach
    void tearDown() {
        trackService.resetTrackDataStore();
    }

    @Test
    void findAllTracks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main/tracks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findTrackById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main/tracks/" + track.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(track.getId()));
    }

    @Test
    void insertTrack() throws Exception {
        mockMvc.perform(post("/main/tracks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(track.getTitle()));
    }

    @Test
    void updateTrack_shouldReturnStatusOkWhenAllParametersMatch() throws Exception {
        track.setDurationSeconds(600);
        mockMvc.perform(put("/main/tracks/" + track.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.durationSeconds").value(600));
    }

    @Test
    void updateTrack_shouldReturnStatusBadWhenIdsDoNotMatch() throws Exception {
        mockMvc.perform(put("/main/tracks/" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTrack_shouldInsertTrackWhenIdIsZero() throws Exception {
        Track track1 = new Track();
        mockMvc.perform(put("/main/tracks/" + 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(track1)))
                .andExpect(status().isOk());

        Track insertedTrack = trackService.findTrackById(1);

        assertNotNull(insertedTrack);
        assertEquals(insertedTrack.getTitle(), trackService.findTrackById(1).getTitle());
    }

    @Test
    void deleteTrack() throws Exception {
        trackService.insertTrack(track);
        mockMvc.perform(delete("/main/tracks/" + track.getId()))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/main/tracks" + track.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void resetTracks() throws Exception {
        mockMvc.perform(delete("/main/tracks/reset"))
                .andExpect(status().isNoContent());
    }
}
