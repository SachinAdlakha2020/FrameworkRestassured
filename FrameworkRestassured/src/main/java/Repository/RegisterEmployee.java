package Repository;

import com.google.gson.annotations.Expose;

public class RegisterEmployee {
	@Expose(serialize = false, deserialize = false)
	public String scenario;
	@Expose(serialize = true, deserialize = false)
	public String email;
	@Expose(serialize = true, deserialize = false)
	public String password;
	@Expose(serialize = false, deserialize = true)
	public double id;
	@Expose(serialize = false, deserialize = true)
	public String token;
	@Expose(serialize = false, deserialize = true)
	public String error;
}
