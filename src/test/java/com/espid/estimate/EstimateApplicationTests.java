package com.espid.estimate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.espid.estimate.admin.sender.model.Sender;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EstimateApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private Sender testSender;

	@Before
	public void setUp() {
		this.testSender = new Sender();
		testSender.setJob("test job");
		testSender.setSpidDept("test dept");
		testSender.setSpidEmail("test email");
		testSender.setSpidId(1001);
		testSender.setSpidName("test name");
		testSender.setSpidTel1("test tel1");
		testSender.setSpidTel2("test tel2");
	}

	@Test
	public void shouldFindNoSendersIfrepositoryIsEmpty() throws Exception {

		/* arrange */

		/* act */
		ResponseEntity<List<Sender>> senders = this.restTemplate.exchange (
			  "http://localhost:" + this.port + "/admin/sender"
			, HttpMethod.GET
			, null
			, new ParameterizedTypeReference<List<Sender>>() {});

		/* assert */
		assertEquals(HttpStatus.OK, senders.getStatusCode());
		assertThat(senders.getBody()).isEmpty();
	}

	@Test
	public void createSender() throws Exception {
		/* arrange */
		Sender postSender = this.testSender;

		/* act */
		ResponseEntity<Sender> postResponse = this.restTemplate.postForEntity (
			    "http://localhost:" + this.port + "/admin/sender"
			  , postSender
			  , Sender.class	
		);

		/* assert */
		assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
		assertEquals("/admin/sender/1001", postResponse.getHeaders().getLocation().getPath());
		assertEquals(postSender, postResponse.getBody());
	}

	@Test
	public void deleteSender() throws Exception {
		/* arrange */
		Sender postSender = this.testSender;

		ResponseEntity<Sender> postResponse = this.restTemplate.postForEntity (
			    "http://localhost:" + this.port + "/admin/sender"
			  , postSender
			  , Sender.class	
		);

		/* act */
		ResponseEntity<Void> deleteResponse = this.restTemplate.exchange (
			  postResponse.getHeaders().getLocation()
			, HttpMethod.DELETE
			, null
			, Void.class
		);

		/* assert */
		assertEquals(HttpStatus.ACCEPTED, deleteResponse.getStatusCode());
	}

	@Test
	public void updateSender() throws Exception {
		/* arrange */
		Sender postSender = this.testSender;

		ResponseEntity<Sender> postResponse = this.restTemplate.postForEntity (
			"http://localhost:" + this.port + "/admin/sender"
			, postSender
			, Sender.class	
		);

		Sender updated = postResponse.getBody();
		updated.setJob("employee");
		updated.setSpidDept("department");
		updated.setSpidEmail("bskim0711@espid.com");
		updated.setSpidName("김병수");
		updated.setSpidTel1(null);
		updated.setSpidTel2("010-4949-2891");

		HttpEntity<Sender> updateEntity = new HttpEntity<>(updated);

		/* act */
		ResponseEntity<Sender> putResponse = this.restTemplate.exchange (
			  "http://localhost:" + this.port + "/admin/sender/{senderId}"
			, HttpMethod.PUT
			, updateEntity
			, Sender.class
			, updated.getSpidId()
		);

		/* assert */
		assertEquals(HttpStatus.OK, putResponse.getStatusCode());
		assertEquals(updated.getJob(), putResponse.getBody().getJob());
		assertEquals(updated.getSpidDept(), putResponse.getBody().getSpidDept());
		assertEquals(updated.getSpidEmail(), putResponse.getBody().getSpidEmail());
		assertEquals(updated.getSpidId(), putResponse.getBody().getSpidId());
		assertEquals(updated.getSpidName(), putResponse.getBody().getSpidName());
		assertEquals(updated.getSpidTel1(), putResponse.getBody().getSpidTel1());
		assertEquals(updated.getSpidTel2(), putResponse.getBody().getSpidTel2());
	}
}
