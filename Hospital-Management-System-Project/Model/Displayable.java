package Model;

/**
 * Displayable Interface
 * 
 * Defines behavior for classes that can display their information in a readable format.
 * This interface allows different classes to provide their own implementation of how
 * they present themselves to the user.
 * 
 * Purpose:
 * - Organize display/reporting behavior across multiple classes
 * - Allow polymorphism: different objects display themselves differently
 * - Make code more maintainable by centralizing display methods
 * 
 * Implemented by: Patient, Doctor, Appointment, TimeSlot, Schedule
 */
public interface Displayable {
    /**
     * Displays detailed information about this object
     * Each implementing class provides its own display format
     */
    void display();
    
    /**
     * Returns a brief summary of this object
     * Useful for quick lookups and list displays
     * @return A formatted string summary
     */
    String getSummary();
}
