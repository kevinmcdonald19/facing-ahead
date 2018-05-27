package hello;

public class SimpleUser {
	private String username;
	private String partnerUsername;
	
	public SimpleUser(User user) {
		this.username = user.getUsername();
		this.partnerUsername = user.getPartnerUsername();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPartnerUsername() {
		return partnerUsername;
	}

	public void setPartnerUsername(String partnerUsername) {
		this.partnerUsername = partnerUsername;
	}
	
	
}
