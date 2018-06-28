package br.com.alura.store;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;

import br.com.alura.store.dto.ProjectDTO;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JaxrsAndJerseyAluraApplicationTests {

//	private RestTemplate restTemplate;
//
//	private ResponseEntity response;
//
//	@Rule
//	public WireMockRule wireMockRule = new WireMockRule(new WireMockConfiguration().port(8089).httpsPort(8443));
//
//	@Before
//	public void setup() throws Exception {
//		this.restTemplate = new RestTemplate();
//		this.response = null;
//	}

	@Test
	public void contextLoads() {

//        ProjectDTO projectDTO = new ProjectDTO()
//            .withId(Long.valueOf(1))
//            .withName("jersey-project")
//            .withYear(2017);
//
//        stubFor(get(urlEqualTo("/api/resource/"))
//				.willReturn(aResponse()
//						.withStatus(HttpStatus.OK.value())
//						.withHeader("Content-Type", "application/json")
//						.withBody(projectDTO.toString())));
//
//		response = restTemplate.getForEntity("http://localhost:8089/api/projects/", String.class);
//
//		assertThat("Verify Response Body", response.getBody().toString().contains("jersey-project"));
//		assertThat("Verify Status Code", response.getStatusCode().equals(HttpStatus.OK));
//
//		verify(getRequestedFor(urlMatching("/api/projects/.*")));
	}

}
