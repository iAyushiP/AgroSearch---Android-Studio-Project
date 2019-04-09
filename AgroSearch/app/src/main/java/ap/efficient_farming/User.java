package ap.efficient_farming;

public class User {
private int id;
private String FullName, email;
public User(int id, String name, String email)
{
    this.id=id;
    this.FullName = FullName;
    this.email=email;
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id=id;
}
public String getName() {
    return FullName;
}
public void setFullName(String fullName) {
    this.FullName = fullName;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email=email;
}
}

