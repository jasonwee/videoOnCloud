package play.learn.java.design.command_query_responsibility_segregation;

import java.util.Objects;


public class Author {
	private String name;
	private String email;
	private String username;

	/**
	 * 
	 * @param name
	 *            name of the author
	 * @param email
	 *            email of the author
	 * @param username
	 *            username of the author
	 */
	public Author(String name, String email, String username) {
		this.name = name;
		this.email = email;
		this.username = username;
	}

	public Author() {
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "AuthorDTO [name=" + name + ", email=" + email + ", username=" + username + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, name, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Author)) {
			return false;
		}
		Author other = (Author) obj;
		return username.equals(other.getUsername()) && email.equals(other.getEmail()) && name.equals(other.getName());

	}

}
