package Model;

/**
 * Person Class - Superclass for Patient and Doctor
 * 
 * Represents a person in the hospital system.
 * Stores common attributes shared by all people (patients and doctors).
 * 
 * This is a foundational class that demonstrates inheritance:
 * - Patient extends Person (A patient IS a person)
 * - Doctor extends Person (A doctor IS a person)
 * 
 * Access Modifiers:
 * - Protected: name, phone (shared with subclasses)
 * - Private: None (all data is protected for subclass access)
 * - Public: Getters, setters, and accessor methods (interface)
 */
public class Person {
    // Protected fields - accessible to subclasses
    protected String name;
    protected String phone;

    /**
     * Constructor: Creates a new person with basic information
     * @param name Person's full name
     * @param phone Person's contact phone number
     */
    public Person(String name, String phone) {
        // Call setters to trigger validation logic
        setName(name);
        setPhone(phone);
    }

    // --- SETTERS (Mutators) with Business Logic ---

    /**
     * Sets the person name
     * Logic: A person name cannot be empty or too short
     * @param name The person's full name
     */
    public void setName(String name) {
        // Logic: A name cannot be empty or too short
        if (name == null || name.trim().length() < 2) {
            this.name = "Invalid Name";
        } else {
            this.name = name;
        }
    }

    /**
     * Sets the person phone number
     * Logic: Validates phone number length
     * @param phone The person's phone number
     */
    public void setPhone(String phone) {
        // Logic: A phone number should usually have at least 10 digits
        if (phone != null && phone.replaceAll("[^0-9]", "").length() >= 10) {
            this.phone = phone;
        } else {
            this.phone = "Invalid Phone Number";
        }
    }

    // --- GETTERS (Accessors) with Business Logic ---

    /**
     * Gets the person name
     * @return The person's full name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the person phone number (masked for privacy)
     * @return Phone number with first digits masked
     */
    public String getPhone() {
        // Logic: Mask the phone number for privacy (Security logic)
        // Show only the last 4 digits: *******1234
        if (phone != null && phone.length() > 4) {
            return "*******" + phone.substring(phone.length() - 4);
        }
        return phone;
    }

    /**
     * Gets the raw phone number (unmasked)
     * Used for internal hospital operations
     * @return Phone number as stored
     */
    public String getPhoneRaw() {
        return phone;
    }
}
