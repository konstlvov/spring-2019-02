package booklib;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="users")
public class User {
	@Id
	private String id;
	private String login;
	private String password;
	private List<String> roles;

	public String get_id() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getRoles() {
		return roles;
	}
}
