package hello;

public class Person {

    private Long personId;
    private String lastName;
    private String firstName;

    public Person() {
    }

    public Person(Long personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getPersonId() {
        return this.personId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }

}
