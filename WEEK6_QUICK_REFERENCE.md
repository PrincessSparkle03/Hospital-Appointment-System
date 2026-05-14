# Week 6 Quick Reference - Inheritance Implementation

## One-Sentence Summary
**Week 6 added a `Person` superclass that `Patient` and `Doctor` now extend, demonstrating inheritance as a mechanism for sharing structure and code reuse.**

---

## Key Concepts

### Inheritance (Week 6)
```
Inheritance = Shared Structure and Code Reuse
Person (Superclass) - stores name, phone, validation
    ↓
    ├── Patient (Subclass) - adds age, gender, dateOfBirth
    ├── Doctor (Subclass) - adds specialist, hourlyRate, schedule
```

### Files Modified/Created

| File | Change | Status |
|------|--------|--------|
| `Model/Person.java` | **Created** - New superclass | ✓ Complete |
| `Model/Patient.java` | Updated to extend Person | ✓ Complete |
| `Model/Doctor.java` | Updated to extend Person | ✓ Complete |
| `Main/Main.java` | No changes needed | ✓ Working |
| `Main/HospitalSystem.java` | No changes needed | ✓ Working |

---

## Person Superclass (New)

```java
public class Person {
    protected String name;      // Accessible to subclasses
    protected String phone;     // Accessible to subclasses
    
    public Person(String name, String phone) { }
    public void setName(String name) { }
    public void setPhone(String phone) { }
    public String getName() { }              // Returns raw name
    public String getPhone() { }             // Returns masked phone
    public String getPhoneRaw() { }          // Returns unmasked phone
}
```

---

## Patient (Now Subclass)

```java
public class Patient extends Person implements Displayable {
    // Removed: name, phone (inherited)
    // Added: call to super()
    
    private int age;
    private String gender;
    private LocalDate dateOfBirth;
    private List<Appointment> appointments;
    
    public Patient(String name, int age, String gender, 
                   LocalDate dateOfBirth, String phone) {
        super(name, phone);        // Initialize inherited fields
        setAge(age);
        setGender(gender);
        setDateOfBirth(dateOfBirth);
        this.appointments = new ArrayList<>();
        patientCount++;
    }
}
```

---

## Doctor (Now Subclass)

```java
public class Doctor extends Person implements Displayable {
    // Removed: name (inherited)
    // Changed: getName() -> getName() (inherited) + getDisplayName() (new)
    
    private String specialist;
    private double hourlyRate;
    private Schedule schedule;
    private List<Appointment> appointments;
    
    public Doctor(String name, String specialist, double hourlyRate, 
                  Schedule schedule, String phone) {
        super(name, phone);        // Initialize inherited fields
        setSpecialist(specialist);
        this.hourlyRate = hourlyRate;
        setSchedule(schedule);
        this.appointments = new ArrayList<>();
        doctorCount++;
    }
    
    // New method for formatted display
    public String getDisplayName() {
        return "Dr. " + this.name;
    }
}
```

---

## What Stayed the Same (Week 5 Content)

### Interfaces (Still Working)
- ✓ `Displayable` - display(), getSummary()
- ✓ `Bookable` - isAvailable(), book(), release()
- ✓ `StatusManageable` - updateStatus(), isActive()
- ✓ `Searchable` - searchByName()

### Classes (No Inheritance Changes)
- ✓ `Appointment` - does NOT extend Person
- ✓ `TimeSlot` - does NOT extend Person
- ✓ `Schedule` - does NOT extend Person
- ✓ `HospitalSystem` - does NOT extend Person
- ✓ `AppointmentStatus` - enum, unchanged

---

## Quick Test

```java
// Both are now Persons
Patient p = new Patient("Alice", 30, "Female", LocalDate.of(1994, 5, 15), "01234567890");
Doctor d = new Doctor("Bob", "Cardiology", 150.0, schedule, "09876543210");

// Inherited from Person
System.out.println(p.getName());           // Alice
System.out.println(d.getName());           // Bob
System.out.println(p.getPhone());          // *******7890
System.out.println(d.getPhone());          // *******3210

// Patient-specific
System.out.println(p.getAge());            // 30
System.out.println(p.getGender());         // Female

// Doctor-specific
System.out.println(d.getDisplayName());    // Dr. Bob
System.out.println(d.getSpecialist());     // CARDIOLOGY
```

---

## Compilation & Execution

```bash
# Compile (from project root)
javac -d bin Main/HospitalSystem.java Main/Main.java Model/*.java

# Run
java -cp bin Main.Main

# Result: ✓ All tests pass
# 3 Patients created and registered
# 3 Doctors created and registered
# Appointments booked, cancelled, and managed
# Inheritance working correctly
```

---

## Design Principles Applied

1. **DRY (Don't Repeat Yourself)** - Name/phone validation in one place
2. **Single Responsibility** - Person handles person logic, subclasses handle specific logic
3. **Open/Closed Principle** - Easy to add new person types (Nurse, Admin) by extending Person
4. **Liskov Substitution** - Patient and Doctor can be used anywhere Person is expected
5. **Clear Naming** - `getName()` = raw, `getDisplayName()` = formatted

---

## Common Mistakes to Avoid

❌ **Wrong:** `Doctor doctor = new Doctor(...); doctor.name = "...";` (trying to set protected field)
✓ **Right:** Use setters that inherit from Person

❌ **Wrong:** Having both Patient and Appointment extend Person
✓ **Right:** Only actual people (Patient, Doctor) extend Person

❌ **Wrong:** Duplicating name/phone validation in both Patient and Doctor
✓ **Right:** Keep validation in Person superclass

---

## Next Steps for Week 7 (Speculation)

- Add more superclasses (e.g., `Staff` for Doctor/Nurse/Admin)
- Implement `abstract` classes for partial implementation
- Consider method overriding and polymorphism
- Implement `final` classes/methods where appropriate
- Use `super` keyword for calling parent methods
- Implement `instanceof` checks for type-specific behavior

---

## Glossary

| Term | Meaning | Example |
|------|---------|---------|
| **Superclass** | Parent class in inheritance | `Person` |
| **Subclass** | Child class in inheritance | `Patient` or `Doctor` |
| **extends** | Keyword to inherit from a class | `class Patient extends Person` |
| **super()** | Call to parent class constructor | `super(name, phone);` |
| **protected** | Access modifier for inheritance | `protected String name;` |
| **IS-A** | Relationship for inheritance | Patient IS-A Person |
| **HAS-A** | Relationship for composition | Patient HAS-A List<Appointment> |
| **Override** | Redefine inherited method | Custom `display()` in Patient |

---

## Resources

- Concept: Person superclass with Patient and Doctor subclasses
- Benefit: Code reuse, maintainability, clear domain model
- Pattern: Template Method (superclass defines structure, subclasses add specifics)
- Full documentation: See `WEEK6_INHERITANCE_SUMMARY.md`
