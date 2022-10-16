package com.shy.docverify.dto;

public class UserDTO {

	private String url;
	private String driver;
	private String userName;
	private String password;

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	
	private UserDTO(UserBuilder builder) {
		this.url = builder.url;
		this.driver = builder.driver;
		this.userName = builder.userName;
		this.password = builder.password;
	}

	public String getUrl() {
		return url;
	}

	public String getDriver() {
		return driver;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDTO [url=" + url + ", driver=" + driver + ", userName=" + userName + ", password=" + password + "]";
	}

	public static class UserBuilder {

		private String url;
		private String driver;
		private String userName;
		private String password;

		public UserBuilder url(String url) {
			this.url = url;
			return this;
		}

		public UserBuilder driver(String driver) {
			this.driver = driver;
			return this;
		}

		public UserBuilder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}
		
		public UserDTO build() {
			UserDTO dto = new UserDTO(this);
			return dto;
		}
	}

}
