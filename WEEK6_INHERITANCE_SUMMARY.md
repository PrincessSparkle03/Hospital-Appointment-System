# Week 6: Inheritance Implementation - Summary

## Overview
Week 6 successfully implemented inheritance in the Hospital Appointment System. The `Person` superclass was created to share common attributes between `Patient` and `Doctor`, demonstrating the difference between interface contracts (Week 5) and shared structure (Week 6).

---

## Changes Implemented

### 1. Created `Person.java` Superclass
**Location:** `Hospital-Management-System-Project/Model/Person.java`

**Attributes moved to superclass:**
- `name` (protected String)
- `phone` (protected String)

**Methods in superclass:**
- `setName(String name)` - Validates name is not empty or too short
- `setPhone(String phone)` - Validates phone has at least 10 digits
- `getName()` - Returns raw name (unformatted)
- `getPhone()` - Returns masked phone number for privacy
- `getPhoneRaw()` - Returns unmasked phone number for internal use

**Design Decision:** Using `protected` access modifier allows subclasses to directly access these fields while maintaining encapsulation from other classes.

---

### 2. Updated `Patient.java` - Now Extends Person
**Changes:**
- `public class Patient extends Person implements Displayable`
- Removed duplicate fields: `name`, `phone`
- Removed duplicate methods: `setName()`, `setPhone()`, `getPhone()`
- Constructor now calls `super(name, phone)` to initialize inherited fields
- Patient-specific attributes remain: `age`, `gender`, `dateOfBirth`, `appointments`

**Constructor:**
```java
public Patient(String name, int age, String gender, LocalDate dateOfBirth, String phone) {
    super(name, phone);  // Initialize inherited fields
    setAge(age);
    setGender(gender);
    setDateOfBirth(dateOfBirth);
    this.appointments = new ArrayList<>();
    patientCount++;
}
```

---

### 3. Updated `Doctor.java` - Now Extends Person
**Changes:**
- `public class Doctor extends Person implements Displayable`
- Removed duplicate field: `name`
- Removed duplicate method: `setName()`
- Constructor now calls `super(name, phone)` to initialize inherited fields
- Added new method: `getDisplayName()` for professional formatting ("Dr. " + name)
- Updated `display()` and `getSummary()` to use `getDisplayName()`
- Doctor-specific attributes remain: `specialist`, `hourlyRate`, `schedule`, `appointments`

**Constructor Options:**
```java
// Full constructor with phone
public Doctor(String name, String specialist, double hourlyRate, Schedule schedule, String phone) {
    super(name, phone);
    setSpecialist(specialist);
    this.hourlyRate = hourlyRate;
    setSchedule(schedule);
    this.appointments = new ArrayList<>();
    doctorCount++;
}

// Overloaded constructor without phone (backward compatible)
public Doctor(String name, String specialist, double hourlyRate, Schedule schedule) {
    this(name, specialist, hourlyRate, schedule, "No Phone");
}
```

---

## Key Design Decisions

### 1. **Is-A Relationship**
- **Patient IS a Person** ✓ Correct inheritance
- **Doctor IS a Person** ✓ Correct inheritance
- **Appointment IS NOT a Person** ✓ No inheritance (different responsibility)
- **TimeSlot IS NOT a Person** ✓ No inheritance (represents time, not a person)
- **Schedule IS NOT a Person** ✓ No inheritance (represents availability, not a person)
- **HospitalSystem IS NOT a Person** ✓ No inheritance (management system, not a person)

### 2. **Separation of Concerns**
- **Person class:** Common attributes and validation for all people
- **Patient class:** Patient-specific attributes (age, gender, date of birth)
- **Doctor class:** Doctor-specific attributes (specialty, hourly rate, schedule)

### 3. **Method Naming Convention Fix**
Following Week 5 feedback about `Doctor.getName()` returning formatted output:
- `getName()` - Returns raw name (inherited from Person)
- `getDisplayName()` - Returns professional format "Dr. " + name (new method in Doctor)
- This separation allows search/data operations to work with raw names while display shows formatted names

---

## Attributes Comparison

### Person (Superclass)
| Attribute | Type | Access | Purpose |
|-----------|------|--------|---------|
| name | String | protected | Store person's full name |
| phone | String | protected | Store person's contact phone |

### Patient (Subclass)
| Attribute | Type | Access | Purpose |
|-----------|------|--------|---------|
| age | int | private | Store patient's age |
| gender | String | private | Store patient's gender |
| dateOfBirth | LocalDate | private | Store patient's date of birth |
| appointments | List<Appointment> | private | Store patient's appointment history |
| patientCount | int | private static | Track total patients created |

### Doctor (Subclass)
| Attribute | Type | Access | Purpose |
|-----------|------|--------|---------|
| specialist | String | private | Store doctor's medical specialty |
| hourlyRate | double | private | Store doctor's hourly rate |
| schedule | Schedule | private | Store doctor's working schedule |
| appointments | List<Appointment> | private | Store doctor's appointments |
| doctorCount | int | private static | Track total doctors created |

---

## Inheritance vs Interface: Comparison

| Aspect | Interface (Week 5) | Inheritance (Week 6) |
|--------|-------------------|----------------------|
| **Purpose** | Define shared behavior contract | Define shared structure and code reuse |
| **Example** | `Displayable`, `Searchable`, `Bookable` | `Person` superclass |
| **What's shared** | Method signatures | Attributes and method implementations |
| **Flexibility** | Class can implement multiple interfaces | Class can extend only one class |
| **When to use** | Different unrelated classes share behavior | Related classes share structure |

### In this project:
- **Interfaces** (Week 5): Patient and Doctor both implement `Displayable` (different classes, same behavior)
- **Inheritance** (Week 6): Patient and Doctor both extend `Person` (related classes, shared structure)

---

## Interfaces Retained from Week 5
The following interfaces from Week 5 are **kept and working with inheritance:**
- `Displayable` - Implemented by Patient, Doctor, Appointment, TimeSlot, Schedule
- `Bookable` - Implemented by TimeSlot, Schedule
- `StatusManageable` - Implemented by Appointment
- `Searchable` - Implemented by HospitalSystem

Inheritance and interfaces work together without conflicts. Classes can both inherit from a superclass AND implement interfaces.

---

## Code Quality Improvements

### 1. **Reduced Code Duplication**
- Removed duplicate validation logic for `name` and `phone`
- Person class is single source of truth for person-related validation
- Changes to person validation only need to be made in one place

### 2. **Better Encapsulation**
- Protected fields in Person allow subclass access without exposing to other classes
- Private fields in Patient and Doctor remain encapsulated

### 3. **Cleaner Display Logic**
- Doctor now clearly distinguishes between `getName()` (raw) and `getDisplayName()` (formatted)
- Other classes can use raw name for searches, comparison, data operations

---

## Test Results

✓ **Compilation:** No errors
✓ **Execution:** All tests passed
✓ **Patient functionality:** Working correctly with inherited name/phone
✓ **Doctor functionality:** Working correctly with inherited name/phone
✓ **Security features:** Admin password validation still working
✓ **Appointment management:** Booking, cancellation, status tracking working
✓ **Display output:** Formatted correctly using inheritance

---

## Example Usage

```java
// Creating Patient - uses inherited name and phone
Patient patient1 = new Patient("John Doe", 25, "Male", LocalDate.of(1999, 1, 1), "01234567890");
System.out.println(patient1.getName());        // Output: John Doe (raw)
System.out.println(patient1.getPhone());       // Output: *******7890 (masked)

// Creating Doctor - uses inherited name and phone
Doctor doctor1 = new Doctor("Gregory House", "Diagnostic Medicine", 150.0, schedule);
System.out.println(doctor1.getName());         // Output: Gregory House (raw, inherited)
System.out.println(doctor1.getDisplayName());  // Output: Dr. Gregory House (formatted)
System.out.println(doctor1.getPhone());        // Output: ****No Phone (masked)

// Both are Persons
Person p1 = patient1;  // Patient IS-A Person
Person p2 = doctor1;   // Doctor IS-A Person
```

---

## Reflection Questions Answered

### 1. Which classes in the project share structure?
**Patient and Doctor** share common structure:
- Both have a name (String)
- Both have a phone number (String)
- Both maintain appointment lists
- Both represent people in the hospital system

### 2. Which class became the superclass?
**Person** became the superclass. It encapsulates the common attributes and behaviors that both Patient and Doctor need.

### 3. Which classes became subclasses?
- **Patient** extends Person
- **Doctor** extends Person

### 4. Why is inheritance useful here?
**Benefits of inheritance in this project:**
1. **Code Reuse:** Name and phone validation written once, used by both Patient and Doctor
2. **Maintainability:** Changes to person validation apply to all people automatically
3. **Clarity:** The "is-a" relationship makes the design intent clear (Patient is a Person)
4. **Consistency:** All people in the hospital use the same validation rules
5. **Extensibility:** If new "person" types are added (e.g., Admin, Nurse), they can extend Person

---

## Week 6 Deliverables Completed

✓ **Updated class diagram:** Person superclass with Patient and Doctor subclasses (arrows showing inheritance)
✓ **Updated code:** Person.java, Patient.java, Doctor.java all implement inheritance
✓ **Explanation of superclass attributes:** name, phone moved to Person
✓ **Explanation of subclass attributes:** age/gender/dateOfBirth in Patient; specialist/hourlyRate/schedule in Doctor
✓ **Interface vs Inheritance comparison:** Documented above
✓ **Main() test:** Successfully runs and shows Patient and Doctor work with inheritance
✓ **Reflection:** All questions answered above

---

## Summary
Week 6 successfully demonstrates the fundamental difference between interfaces (Week 5) and inheritance (Week 6). The Person superclass provides shared structure for Patient and Doctor while maintaining clean separation of concerns. The code is more maintainable, reduces duplication, and clearly expresses the domain model of a hospital system where both patients and doctors are people.
