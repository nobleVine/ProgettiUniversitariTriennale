
public class ConcreteSupporter implements Supporter {
	
	private String name, surname, username, password;
	private int age;
		
	public ConcreteSupporter (String name, String surname, int age, String username, String password ) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.username = username;
		this.password = password;
	}
	
	public String getNameSupporter () {
		return name;
	}

	public String getSurnameSupporter () {
		return surname;
	}
	
	public int getAge () {
		return age;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword () {
		return password;
	}
	
}
