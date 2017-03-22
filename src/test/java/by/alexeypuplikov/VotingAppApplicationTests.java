package by.alexeypuplikov;

import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.models.VotingOption;
import by.alexeypuplikov.repositories.VotingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class VotingAppApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private VotingRepository votingRepository;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private List<Voting> votingList = new ArrayList<>();

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.votingRepository.deleteAll();

        this.votingList.add(votingRepository.save(new Voting("Sports", null)));
        this.votingList.add(votingRepository.save(new Voting("Laptop", null)));
    }

    @Test
    public void getAllVotingTest() throws Exception {
        mockMvc.perform(get("/voting"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.votingList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].topic", is(this.votingList.get(0).getTopic())))
                .andExpect(jsonPath("$[1].id", is(this.votingList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].topic", is(this.votingList.get(1).getTopic())));
    }

    @Test
    public void addVotingTest() throws Exception {
        String votingJson = json(new Voting("Cars", null));
        mockMvc.perform(post("/voting")
                .contentType(contentType)
                .content(votingJson))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/voting")
                .contentType(contentType)
                .content(votingJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addVotingOptionTest() throws Exception {
        String votingOptionJson = json(new VotingOption("Hp", votingRepository.findByTopic("Laptop")));
        mockMvc.perform(post("/voting/Laptop/addVotingOption")
                .contentType(contentType)
                .content(votingOptionJson))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/voting/Laptop/addVotingOption")
                .contentType(contentType)
                .content(votingOptionJson))
                .andExpect(status().is4xxClientError());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
