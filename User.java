package bankingproject;

public class User {
    //direct data access from here kaya so getter and setter methods = no encapsulation
    public int id;
    public String username;
    public String password;
    public String fullName;
    public String firstName;
    public String middleName;
    public String lastName;
    public String contact;
    public String address;
    public String birthDate;
    public String employment;
    public String sourceOfFunds;
    public String monthlyFunds;
    public String accountNumber;
    public double balance;
    public boolean isAdmin;

    //user constructor
    public User(int id, String username, String password, String fullName,
                String firstName,String middleName, String lastName,
                String contact, String address, String birthDate,
                String employment, String sourceOfFunds, String monthlyFunds,
                String accountNumber, double balance, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.firstName = firstName;
        this.firstName = middleName;
        this.firstName = lastName;
        this.contact = contact;
        this.address = address;
        this.birthDate = birthDate;
        this.employment = employment;
        this.sourceOfFunds = sourceOfFunds;
        this.monthlyFunds = monthlyFunds;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.isAdmin = isAdmin;

        if (fullName != null && !fullName.isEmpty()) {
            String[] nameParts = fullName.split(" ");
            if (nameParts.length > 0) {
                this.firstName = nameParts[0];
                if (nameParts.length > 2) {
                    this.middleName = nameParts[1];
                    this.lastName = nameParts[2];
                } else if (nameParts.length > 1) {
                    this.lastName = nameParts[1];
                }
            }
        }
    }

    public User() {
        this.isAdmin = false;
    }

}