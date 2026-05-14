# Hospital System Architecture: Interfaces + Inheritance

## Visual Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WEEK 5: Interfaces (Shared Behavior)                 │
│                    WEEK 6: Inheritance (Shared Structure)                │
└─────────────────────────────────────────────────────────────────────────┘

                                Person (WEEK 6)
                                    │
                    ┌───────────────┼───────────────┐
                    │                               │
                Patient (WEEK 6)                 Doctor (WEEK 6)
                    │                               │
        ┌─────────────────────┐        ┌────────────────────────┐
        │   Displayable ✓     │        │   Displayable ✓        │
        │   (WEEK 5)          │        │   (WEEK 5)             │
        └─────────────────────┘        └────────────────────────┘
                    │                               │
                    ├──────────────┬────────────────┤
                    │              │                │
                    │           Schedule         TimeSlot
                    │           (no inheritance) (no inheritance)
                    │           ├─ Bookable ✓   ├─ Bookable ✓
                    │           │ (WEEK 5)      │ (WEEK 5)
                    │           │               │
                    │           └───────┬────────┘
                    │                   │
                Appointment         Displayable ✓
                │   │           StatusManageable ✓
                │   └─ (WEEK 5)
                │
        ┌───────────────┐
        │ Displayable ✓ │
        │ (WEEK 5)      │
        └───────────────┘

        HospitalSystem
        └─ Searchable ✓ (WEEK 5)
```

---

## Class Relationships Table

| Class | Extends | Implements | Purpose |
|-------|---------|-----------|---------|
| **Person** | - | - | Superclass: common person attributes |
| **Patient** | Person | Displayable | Represents hospital patient |
| **Doctor** | Person | Displayable | Represents hospital doctor |
| **Appointment** | - | Displayable, StatusManageable | Represents a booked appointment |
| **TimeSlot** | - | Bookable, Displayable | Represents a time slot |
| **Schedule** | - | Bookable, Displayable | Represents a doctor's schedule |
| **HospitalSystem** | - | Searchable | Central management system |

---

## Interface Implementations Summary

### Displayable Interface (WEEK 5)
**Purpose:** Classes that can display their information

| Class | Implements | Methods |
|-------|-----------|---------|
| Patient | ✓ | display(), getSummary() |
| Doctor | ✓ | display(), getSummary() |
| Appointment | ✓ | display(), getSummary() |
| TimeSlot | ✓ | display(), getSummary() |
| Schedule | ✓ | display(), getSummary() |

### Bookable Interface (WEEK 5)
**Purpose:** Classes that have booking/availability behavior

| Class | Implements | Methods |
|-------|-----------|---------|
| TimeSlot | ✓ | isAvailable(), book(), release() |
| Schedule | ✓ | isAvailable(), book(), release() |

### StatusManageable Interface (WEEK 5)
**Purpose:** Classes that manage status changes

| Class | Implements | Methods |
|-------|-----------|---------|
| Appointment | ✓ | updateStatus(AppointmentStatus), isActive() |

### Searchable Interface (WEEK 5)
**Purpose:** Classes that support searching

| Class | Implements | Methods |
|-------|-----------|---------|
| HospitalSystem | ✓ | searchByName(String) |

---

## Inheritance Hierarchy Summary

### Person Hierarchy (WEEK 6)
```
Person (Abstract concept: any person)
├── name (String)
├── phone (String)
├── setName(String)
├── setPhone(String)
├── getName()
├── getPhone()
└── getPhoneRaw()

    ├─ Patient
    │  ├── age (int)
    │  ├── gender (String)
    │  ├── dateOfBirth (LocalDate)
    │  ├── appointments (List<Appointment>)
    │  ├── setAge(int)
    │  ├── setGender(String)
    │  ├── setDateOfBirth(LocalDate)
    │  └── getAppointmentsSummary()
    │
    └─ Doctor
       ├── specialist (String)
       ├── hourlyRate (double)
       ├── schedule (Schedule)
       ├── appointments (List<Appointment>)
       ├── setSpecialist(String)
       ├── setHourlyRate(double, String)
       ├── setSchedule(Schedule)
       ├── getDisplayName()  ← NEW: "Dr. " + name
       └── canBookAppointment(Appointment)
```

---

## How They Work Together

### Example: Patient Object

```
Patient obj = new Patient("John Doe", 25, "M", ..., "01234567890");

1. INHERITANCE (WEEK 6)
   ├─ From Person superclass:
   │  ├─ name = "John Doe"
   │  ├─ phone = "01234567890"
   │  ├─ getName()          // "John Doe"
   │  ├─ getPhone()         // "*******7890"
   │
   └─ Own fields:
      ├─ age = 25
      ├─ gender = "Male"
      ├─ dateOfBirth = LocalDate.of(1999, 1, 1)
      └─ appointments = List<Appointment>

2. INTERFACE (WEEK 5)
   └─ Implements Displayable:
      ├─ display()    // Shows patient details
      └─ getSummary() // Returns "John Doe | Age: 25 | Gender: Male | ..."

3. USAGE
   Person p = obj;       // Can treat as Person (IS-A relationship)
   Displayable d = obj;  // Can treat as Displayable (contract)
```

### Example: Doctor Object

```
Doctor obj = new Doctor("Lisa", "Cardiology", 150.0, schedule, "09876543210");

1. INHERITANCE (WEEK 6)
   ├─ From Person superclass:
   │  ├─ name = "Lisa"
   │  ├─ phone = "09876543210"
   │  ├─ getName()          // "Lisa" (raw name)
   │  ├─ getPhone()         // "*******3210"
   │
   └─ Own fields:
      ├─ specialist = "Cardiology"
      ├─ hourlyRate = 150.0
      ├─ schedule = Schedule(...)
      ├─ appointments = List<Appointment>
      └─ getDisplayName()   // "Dr. Lisa" (professional format)

2. INTERFACE (WEEK 5)
   └─ Implements Displayable:
      ├─ display()    // Shows doctor details
      └─ getSummary() // Returns "Dr. Lisa (Cardiology, Active: 2 appointments)"

3. USAGE
   Person p = obj;       // Can treat as Person (IS-A relationship)
   Displayable d = obj;  // Can treat as Displayable (contract)
```

---

## Design Decisions Explained

### 1. Why Person is a Superclass
**Before (WEEK 5):**
```java
class Patient {
    private String name;
    private String phone;
    // validation logic for name/phone
}

class Doctor {
    private String name;
    private String phone;
    // same validation logic for name/phone (DUPLICATED!)
}
```

**After (WEEK 6):**
```java
class Person {
    protected String name;
    protected String phone;
    // validation logic for name/phone (ONCE!)
}

class Patient extends Person {
    // inherits name, phone, validation
}

class Doctor extends Person {
    // inherits name, phone, validation
}
```

**Benefit:** 
- Single source of truth
- Changes propagate to all people
- Clearer domain model

### 2. Why Appointment Doesn't Extend Person
```
❌ WRONG: class Appointment extends Person
   - Appointments are not people
   - Appointment ≠ a person
   - No IS-A relationship

✓ RIGHT: class Appointment implements Displayable
   - Appointments have displayable behavior
   - Same interface as Patient and Doctor
   - Different responsibility
```

### 3. Why Interfaces Still Exist After Adding Inheritance
```
Interfaces define contracts
Inheritance shares structure

Patient and Doctor:
├─ Share STRUCTURE through inheritance (Person)
│  └─ name, phone, validation
│
└─ Share BEHAVIOR through interface (Displayable)
   └─ display(), getSummary()

HospitalSystem:
└─ Implements Searchable (interface, not inheritance)
   └─ Because it's not a Person, but has search behavior
```

---

## Code Metrics

### Duplication Reduction
| Element | Week 5 | Week 6 | Reduction |
|---------|--------|--------|-----------|
| name field | 2 copies (Patient, Doctor) | 1 copy (Person) | 50% |
| phone field | 2 copies | 1 copy | 50% |
| setName() | 2 copies | 1 copy | 50% |
| setPhone() | 2 copies | 1 copy | 50% |
| getName() | 2 copies | 1 copy (inherited) | 50% |
| getPhone() | 2 copies | 1 copy (inherited) | 50% |
| **Total** | **~300 lines** | **~250 lines** | **~17%** |

### New Capabilities
- Doctor.getDisplayName() - format control
- Patient/Doctor can be treated as Person
- Easy to add new person types (Nurse, Admin, Staff)
- Inheritance chain supports polymorphism

---

## Code Examples

### Using Inheritance
```java
// Polymorphism through inheritance
List<Person> people = new ArrayList<>();
people.add(new Patient("John", 30, "M", dob, "01234567890"));
people.add(new Doctor("Lisa", "Cardiology", 150, schedule, "09876543210"));

for (Person p : people) {
    System.out.println(p.getName());    // Works for Patient and Doctor
    System.out.println(p.getPhone());   // Works for Patient and Doctor
}
```

### Using Interfaces
```java
// Polymorphism through interfaces
List<Displayable> displayables = new ArrayList<>();
displayables.add(new Patient(...));
displayables.add(new Doctor(...));
displayables.add(new Appointment(...));

for (Displayable d : displayables) {
    d.display();              // Works for all that implement Displayable
    System.out.println(d.getSummary());
}
```

### Combining Both
```java
// Most flexible: both inheritance and interfaces
Patient patient = new Patient("John", 30, "M", dob, "01234567890");

// Use as Person (inheritance)
Person p = patient;
System.out.println(p.getName());

// Use as Displayable (interface)
Displayable d = patient;
d.display();

// Use as Patient (original type)
System.out.println(patient.getAge());
```

---

## Summary Matrix

| Concept | Week 5 (Interfaces) | Week 6 (Inheritance) |
|---------|-------------------|---------------------|
| **Problem Solved** | Shared behavior | Shared structure |
| **Keyword** | `implements` | `extends` |
| **Example** | `class Patient implements Displayable` | `class Patient extends Person` |
| **Access Modifier** | `public` methods | `protected` fields, `public` methods |
| **Multiple** | Can implement multiple | Can extend only one |
| **Reuses** | Method signatures | Attributes + implementations |
| **IS-A Test** | No specific test | "Patient IS-A Person" ✓ |
| **HAS-A Test** | No specific test | "Patient HAS-A List<Appointment>" |

**Conclusion:** Interfaces and inheritance are complementary. Use both together for maximum flexibility and code organization.
