package booklib;
import org.springframework.security.core.userdetails.UserDetails;
import booklib.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsersService {
	private ArrayList<UserDetails> users;
	private final UserRepository userRepo;

	public UsersService(UserRepository userRepo) {
		this.userRepo = userRepo;
		this.users = new ArrayList<>();
	}

	public ArrayList<UserDetails> getUsers(){
		org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder();
		for(User u: userRepo.findAll()) {
			String[] arrRoles = new String[u.getRoles().size()];
			u.getRoles().toArray(arrRoles);
			UserDetails user = userBuilder
							.username(u.getLogin())
							.password(u.getPassword())
							.roles(arrRoles)
							.build();
			users.add(user);
		}
		return users;
	};
}
