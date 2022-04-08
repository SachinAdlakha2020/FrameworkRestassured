package Repository;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class DataPerPage {

	@Expose(serialize = false, deserialize = false)
	public String scenario;
	@Expose(serialize = false, deserialize = true)
	public int page;
	@Expose(serialize = false, deserialize = true)
	public int per_page;
	@Expose(serialize = false, deserialize = true)
	public int total;
	@Expose(serialize = false, deserialize = true)
	public int total_pages;
	@Expose(serialize = false, deserialize = false)
	public ArrayList<UserData> data;
	@Expose(serialize = false, deserialize = false)
	public Support support;
	
	public class UserData {
		@Expose(serialize = false, deserialize = false)
		public String scenario;
		@Expose(serialize = false, deserialize = true)
		public int id;
		@Expose(serialize = false, deserialize = true)
		public String email;
		@Expose(serialize = false, deserialize = true)
		public String first_name;
		@Expose(serialize = false, deserialize = true)
		public String last_name;
		@Expose(serialize = false, deserialize = true)
		public String avatar;
	}
	
	public class Support {
		@Expose(serialize = false, deserialize = false)
		public String scenario;
		@Expose(serialize = false, deserialize = true)
		public String url;
		@Expose(serialize = false, deserialize = true)
		public String text;
	}
}




