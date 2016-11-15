package de.codecentric.boot.admin.registration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import de.codecentric.boot.admin.client.registration.Application;

public class ApplicationTest {


	@Test
	public void test_json_format() throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

		Application app = Application.create("test").withHealthUrl("http://health")
				.withServiceUrl("http://service").withManagementUrl("http://management").build();

		DocumentContext json = JsonPath.parse(objectMapper.writeValueAsString(app));

		assertThat(json.read("$.name")).isEqualTo("test");
		assertThat(json.read("$.serviceUrl")).isEqualTo("http://service");
		assertThat(json.read("$.managementUrl")).isEqualTo("http://management");
		assertThat(json.read("$.healthUrl")).isEqualTo("http://health");
	}

	@Test
	public void test_equals_hashCode() {
		Application a1 = Application.create("foo").withHealthUrl("healthUrl")
				.withManagementUrl("mgmt").withServiceUrl("svc").build();
		Application a2 = Application.create("foo").withHealthUrl("healthUrl")
				.withManagementUrl("mgmt").withServiceUrl("svc").build();

		assertThat(a1, is(a2));
		assertThat(a1.hashCode(), is(a2.hashCode()));

		Application a3 = Application.create("foo").withHealthUrl("healthUrl2")
				.withManagementUrl("mgmt").withServiceUrl("svc").build();

		assertThat(a1, not(is(a3)));
		assertThat(a2, not(is(a3)));
	}

	@Test
	public void test_builder_copy() {
		Application app = Application.create("App").withHealthUrl("http://health")
				.withManagementUrl("http://mgmgt").withServiceUrl("http://svc").build();
		Application copy = Application.create(app).build();
		assertThat(app, is(copy));
	}
}
