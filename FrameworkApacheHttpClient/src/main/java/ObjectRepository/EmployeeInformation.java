package ObjectRepository;

public class EmployeeInformation {
	 public EmployeeInformation(int id, String email, String first_Name, String last_Name,String avatar) {
		 this.id=id;
		 this.email = email;
		 this.first_name = first_Name;
		 this.last_name =last_Name;
		 this.avatar = avatar;
	 }
	
	public int id;
	public String email;
	public String first_name;
	public String last_name;
	public String avatar;
}
