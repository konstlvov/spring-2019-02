/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package booklib;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.assertj.core.api.Assertions;

import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

  @Autowired
	private BookRepository bookRepository;

	@Autowired
	private WebTestClient webTestClient;

	@Test
  public void shouldReturnLoginPage() throws Exception {
		webTestClient
						.get().uri("/login")
						.accept(MediaType.TEXT_PLAIN)
						.exchange()
						.expectStatus().isOk()
						.expectBody(String.class).consumeWith(response -> {
							Assertions.assertThat(response.getResponseBody().contains("<title>Please sign in</title>")).isTrue();
				     })
		;
  }

	@Test
	public void shouldReturnRedirectToLoginPage() throws Exception {
		webTestClient
						.get().uri("/fluxbooks")
						.accept(MediaType.APPLICATION_JSON)
						.exchange()
						.expectStatus().isEqualTo(HttpStatus.FOUND)
		.expectHeader().valueEquals("Location", "/login")
		;
	}

}

