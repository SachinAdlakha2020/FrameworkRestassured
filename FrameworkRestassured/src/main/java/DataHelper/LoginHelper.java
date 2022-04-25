package DataHelper;

import Repository.LoginRequest;

public class LoginHelper {

	public LoginRequest GetLoginRequestData() {
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.login ="loginText";
		loginRequest.password ="loginPassword";
		return loginRequest;
	}
}
