# Java Learning Guide - Hospital Management System
## Everything Your Teacher Might Ask (For Beginners)

---

## **TABLE OF CONTENTS**
1. [Basic Java Concepts](#basic-java-concepts)
2. [Object-Oriented Programming (OOP)](#object-oriented-programming)
3. [Classes & Objects](#classes--objects)
4. [Encapsulation (Private/Public)](#encapsulation)
5. [Collections (ArrayList, HashMap)](#collections)
6. [Methods (Getters/Setters)](#methods-getterssetters)
7. [Static Variables & Methods](#static-variables--methods)
8. [Enums](#enums)
9. [Common Interview Questions](#common-interview-questions)

---

# **PART 1: BASIC JAVA CONCEPTS**

## **What is Java?**
Java is a **programming language** that runs on the Java Virtual Machine (JVM). It lets you write code once and run it anywhere.

### **Key Features:**
- **Object-Oriented**: Everything is an object
- **Compiled**: Code is converted to bytecode before running
- **Platform Independent**: Works on Windows, Mac, Linux

---

## **Data Types in Java**

### **Primitive Types** (built-in, store values directly)
```java
int age = 25;                    // Whole number: -2147483648 to 2147483647
double salary = 150.50;          // Decimal: 1.8 x 10^-308 to 1.8 x 10^308
boolean isAvailable = true;      // true or false
String name = "John";            // Text/characters
char grade = 'A';                // Single character
```

### **Reference Types** (point to objects in memory)
```java
Patient patient = new Patient(...);  // Points to a Patient object
ArrayList<String> names = new ArrayList<>();  // Points to a list
```

---

## **Variables: Local vs Instance**

### **Instance Variables** (belong to an object)
```java
public class Patient {
    private String name;        // Instance variable - each patient has their own name
    private int age;            // Instance variable - each patient has their own age
}
```

### **Local Variables** (exist only in a method)
```java
public void someMethod() {
    int counter = 0;            // Local variable - only exists inside this method
    System.out.println(counter);
}  // counter is deleted here
```

---

## **String Methods (Text Operations)**

```java
String name = "John Doe";

name.length()           // Returns 8
name.equals("John Doe") // Returns true
name.equalsIgnoreCase("john doe")  // Returns true (ignores uppercase/lowercase)
name.toUpperCase()      // Returns "JOHN DOE"
name.toLowerCase()      // Returns "john doe"
name.substring(0, 4)    // Returns "John"
name.contains("-")      // Returns false
```

**Used in your code:**
```java
if (name == null || name.trim().length() < 2) {
    this.name = "Invalid Name";
}
```
This checks if name is empty or less than 2 characters.

---

## **Boolean Logic (true/false conditions)**

```java
boolean result = true && false;     // AND - both must be true → false
boolean result = true || false;     // OR - one can be true → true
boolean result = !true;             // NOT - flip the value → false

// In if statements:
if (age >= 0 && age <= 150) {       // AND: age must be between 0-150
    // Valid age
} else if (age < 0 || age > 150) {  // OR: either condition
    // Invalid age
}
```

---

# **PART 2: OBJECT-ORIENTED PROGRAMMING (OOP)**

## **What is OOP?**
OOP is a way of organizing code using **objects** instead of just functions.

### **Real-World Analogy:**
```
NOT OOP (Procedural):
- Function: calculateAge(birthDate)
- Function: validateAge(age)
- Function: printAge(age)
- Variables scattered everywhere ❌

OOP:
- Class Patient
  - Variable: dateOfBirth
  - Method: calculateAge()
  - Method: validateAge()
  - Everything organized ✓
```

---

## **The 4 Pillars of OOP**

### **1. ENCAPSULATION (Hiding Details)**

**What it means:** Hide internal details, show only what's necessary.

**Real-world analogy:**
- You drive a car by pressing the pedal ✓
- You don't care HOW the engine works ✓
- The engine details are HIDDEN ✓

**In Java:**
```java
// BAD - Everything public, no protection
public class Patient {
    public String name;
    public int age;
}
Patient p = new Patient();
p.age = -50;  // PROBLEM: Negative age! No validation!

// GOOD - Encapsulation with private/public
public class Patient {
    private String name;           // PRIVATE - can't access directly
    private int age;               // PRIVATE - can't access directly
    
    public void setAge(int age) {  // PUBLIC - controlled access
        if (age >= 0 && age <= 150) {  // VALIDATION!
            this.age = age;
        } else {
            System.out.println("Invalid age");
        }
    }
    
    public int getAge() {
        return age;
    }
}
Patient p = new Patient();
p.setAge(-50);  // Prints "Invalid age" - PROTECTED!
p.setAge(25);   // Works fine
```

### **2. INHERITANCE (Extending Classes)**

**What it means:** A class can inherit properties/methods from another class.

**Real-world analogy:**
```
Doctor inherits from Person:
- Person has: name, age, gender
- Doctor inherits: name, age, gender
- Doctor adds: specialty, hourlyRate
```

**In Java:**
```java
// Parent class
public class Person {
    protected String name;
    protected int age;
}

// Child class (inherits from Person)
public class Doctor extends Person {
    private String specialty;
    
    public Doctor(String name, int age, String specialty) {
        this.name = name;          // Inherited from Person
        this.age = age;            // Inherited from Person
        this.specialty = specialty; // New to Doctor
    }
}
```

### **3. POLYMORPHISM (Many Forms)**

**What it means:** Same method name, different behavior based on object type.

**Real-world analogy:**
```
Shape.draw()
- Circle.draw() → draws a circle
- Square.draw() → draws a square
- Triangle.draw() → draws a triangle
```

**In Java:**
```java
public class Appointment {
    public void cancel() {
        System.out.println("Cancelled");
    }
}

public class RushAppointment extends Appointment {
    @Override
    public void cancel() {
        System.out.println("Rush appointment - charge cancellation fee!");
    }
}
```

### **4. ABSTRACTION (Simplifying Complex Things)**

**What it means:** Show only the essential features, hide complexity.

**Real-world analogy:**
- You click "Book Appointment" button
- Behind scenes: validation, database updates, email sending, etc.
- You don't see the complexity ✓

**In Java:**
```java
// User calls this (simple):
hospital.bookAppointment(patient, doctor, slot);

// Behind the scenes (complex - hidden):
// 1. Validate patient exists
// 2. Check doctor availability
// 3. Check slot not booked
// 4. Create appointment object
// 5. Update patient's list
// 6. Update doctor's list
// 7. Mark slot unavailable
// 8. Send confirmation email
// 9. Log to database
```

---

# **PART 3: CLASSES & OBJECTS**

## **What's a Class?**
A **blueprint** or **template** for creating objects.

```java
public class Patient {
    // Blueprint for creating patients
}

// Now we CREATE objects (instances) from this blueprint:
Patient patient1 = new Patient("John", 25, "Male", "1999-01-01", "01234567890");
Patient patient2 = new Patient("Jane", 30, "Female", "1994-05-15", "09876543210");
```

Think of it like:
- **Class = Cookie Cutter** (the template)
- **Object = Cookie** (each one made from the template)

---

## **Constructor (How to Create Objects)**

A **constructor** is a special method that runs when you create an object.

```java
public class Patient {
    private String name;
    private int age;
    
    // Constructor - runs when you do: new Patient(...)
    public Patient(String name, int age) {
        this.name = name;   // "this" means "this object's name"
        this.age = age;
        System.out.println("Patient created: " + name);
    }
}

// When you run this:
Patient p = new Patient("John", 25);
// Output: "Patient created: John"
```

### **Constructor with Setters (Best Practice)**
```java
public Patient(String name, int age, String gender, ...) {
    setName(name);           // Use setters for validation
    setAge(age);             // Each setter validates input
    setGender(gender);
    // ...
}
```

**Why?** Each setter has validation logic. Calling setters ensures data is valid.

---

## **this Keyword**

`this` means **"this object"** - it refers to the current object.

```java
public class Patient {
    private String name;
    
    public void setName(String name) {  // Parameter "name"
        this.name = name;               // this.name = object's name field
    }
}
```

Without `this`, Java gets confused which "name" you mean:
```java
// CONFUSING:
public void setName(String name) {
    name = name;  // Are we setting the parameter to itself?
}

// CLEAR:
public void setName(String name) {
    this.name = name;  // The field = the parameter
}
```

---

# **PART 4: ENCAPSULATION**

## **Access Modifiers (Public vs Private)**

```java
public class Doctor {
    public String name;          // ANYONE can access/modify
    private double hourlyRate;   // ONLY this class can access
    private List<Appointment> appointments;  // ONLY this class can access
}
```

### **Why Use Private?**
```java
// BAD - No protection:
Doctor doc = new Doctor(...);
doc.hourlyRate = -5000;  // WRONG! But allowed!

// GOOD - Protected:
private double hourlyRate;
public void setHourlyRate(double rate, String password) {
    if (!password.equals("ADMIN123")) {  // Validation!
        System.out.println("Access Denied");
        return;
    }
    if (rate < 0) {  // More validation!
        System.out.println("Rate cannot be negative");
        return;
    }
    this.hourlyRate = rate;  // Safe!
}
```

---

## **Getters vs Setters**

### **Setter (Mutator) - Changes a value**
```java
public void setAge(int age) {
    if (age >= 0 && age <= 150) {
        this.age = age;
    }
}
```

### **Getter (Accessor) - Reads a value**
```java
public int getAge() {
    return age;
}
```

### **Getter with Logic**
```java
// Simple getter:
public int getAge() {
    return age;
}

// Getter with business logic:
public int getAge() {
    if (age < 18) {
        System.out.println("[Note: Patient is a minor]");
    }
    return age;
}

// Getter that formats output:
public String getName() {
    return "Dr. " + name;  // Adds "Dr." prefix automatically
}
```

---

# **PART 5: COLLECTIONS**

## **ArrayList (Dynamic List)**

**What it is:** A list that grows/shrinks automatically.

### **Basic Usage:**
```java
// Create an empty ArrayList
ArrayList<String> names = new ArrayList<>();

// Add items
names.add("John");
names.add("Jane");
names.add("Bob");

// Get size
names.size();  // Returns 3

// Get item at index
names.get(0);  // Returns "John"
names.get(1);  // Returns "Jane"

// Remove item
names.remove(0);  // Removes "John"

// Check if contains
names.contains("Jane");  // Returns true

// Loop through
for (String name : names) {
    System.out.println(name);
}
```

### **In Your Project:**
```java
// Patient has list of appointments:
private List<Appointment> appointments = new ArrayList<>();

// Doctor has list of appointments:
private List<Appointment> appointments = new ArrayList<>();

// Hospital has lists of everything:
private List<Patient> patients = new ArrayList<>();
private List<Doctor> doctors = new ArrayList<>();
private List<Appointment> appointments = new ArrayList<>();
```

---

## **HashMap (Key-Value Pairs)**

**What it is:** A collection where each item has a key (like a dictionary).

### **Basic Usage:**
```java
HashMap<String, Integer> ageMap = new HashMap<>();

// Add key-value pairs
ageMap.put("John", 25);
ageMap.put("Jane", 30);

// Get value by key
ageMap.get("John");  // Returns 25

// Check if key exists
ageMap.containsKey("John");  // Returns true

// Remove by key
ageMap.remove("John");

// Get all values
ageMap.values();  // [30, ...]
```

### **In Your Project:**
```java
// Fast appointment lookup by ID:
private Map<String, Appointment> appointmentMap = new HashMap<>();

appointmentMap.put("00001", appointment1);
appointmentMap.put("00002", appointment2);

// Later, find by ID in milliseconds:
Appointment found = appointmentMap.get("00001");  // O(1) speed!
```

---

## **Why ArrayList vs HashMap?**

| ArrayList | HashMap |
|-----------|---------|
| Ordered list | Key-value pairs |
| Access by position | Access by key |
| `get(0)` | `get("id123")` |
| Slower search | Faster search |
| For sequences | For lookups |

---

# **PART 6: METHODS (GETTERS/SETTERS)**

## **What's a Method?**
A method is a **function inside a class** that performs an action.

```java
public class Patient {
    // Method signature
    public void setName(String name) {
        // Method body
        this.name = name;
    }
}
```

### **Method Components:**
```java
public void setName(String name) {
     ↑     ↑   ↑     ↑
  Access  Return  Name  Parameters
  Modifier Type
```

### **Return Types:**

```java
public void setAge(int age) {
    // void = returns nothing
    this.age = age;
}

public int getAge() {
    // int = returns an integer
    return age;
}

public boolean isAdult() {
    // boolean = returns true/false
    return age >= 18;
}

public String getFormattedName() {
    // String = returns text
    return "Dr. " + name;
}
```

### **Parameters vs Arguments:**
```java
// PARAMETER (in method definition):
public void setAge(int age) {
    // "age" is a parameter
}

// ARGUMENT (when calling):
setAge(25);  // "25" is an argument
```

---

## **Method Overloading (Same Name, Different Parameters)**

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {  // Different parameter type
        return a + b;
    }
    
    public int add(int a, int b, int c) {    // Different number of parameters
        return a + b + c;
    }
}

// All use the same method name "add" but different parameters
Calculator calc = new Calculator();
calc.add(5, 3);           // Uses first one (int, int)
calc.add(5.5, 3.2);       // Uses second one (double, double)
calc.add(5, 3, 2);        // Uses third one (int, int, int)
```

---

# **PART 7: STATIC VARIABLES & METHODS**

## **Static Variables (Shared by All Objects)**

**Regular (Instance) Variables:**
```java
public class Patient {
    private int age;  // Each patient has their OWN age
}

Patient p1 = new Patient();
p1.age = 25;  // p1's age

Patient p2 = new Patient();
p2.age = 30;  // p2's age (separate from p1)
```

**Static Variables:**
```java
public class Patient {
    private static int patientCount = 0;  // SHARED by all patients
    
    public Patient() {
        patientCount++;  // All objects share this counter
    }
}

Patient p1 = new Patient();  // patientCount = 1
Patient p2 = new Patient();  // patientCount = 2
Patient p3 = new Patient();  // patientCount = 3

// Access static variable:
System.out.println(Patient.patientCount);  // Prints 3
```

### **Real-world Analogy:**
```
Instance variable: Each person has their own blood type
Static variable: All humans share one planet Earth
```

---

## **Static Methods**

**Regular (Instance) Methods:**
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

Calculator calc = new Calculator();
calc.add(5, 3);  // Must create object first
```

**Static Methods:**
```java
public class Calculator {
    public static int add(int a, int b) {
        return a + b;
    }
}

// Can call directly without creating object:
Calculator.add(5, 3);
```

### **In Your Project:**
```java
// Static method in Patient:
public static int getPatientCount() {
    return patientCount;
}

// Call directly:
System.out.println(Patient.getPatientCount());  // No need for: new Patient()

// Static method in Appointment:
public static int getTotalAppointmentsCreated() {
    return totalAppointmentsCreated;
}

System.out.println(Appointment.getTotalAppointmentsCreated());
```

---

# **PART 8: ENUMS**

## **What's an Enum?**
An enum is a set of **fixed constants** (unchangeable values).

```java
// BAD - Using strings (typos possible):
String status = "BOOKED";
String status = "booked";      // TYPO! Different!
String status = "BOKKED";      // TYPO! Compiles but wrong!

// GOOD - Using enum (type-safe):
AppointmentStatus status = AppointmentStatus.BOOKED;
// Can't misspell - only: BOOKED, CANCELLED, COMPLETED, RESCHEDULED
```

### **In Your Project:**
```java
public enum AppointmentStatus {
    BOOKED("Booked"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed"),
    RESCHEDULED("Rescheduled");
    
    private final String displayValue;
    
    AppointmentStatus(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}

// Usage:
AppointmentStatus status = AppointmentStatus.BOOKED;

if (status == AppointmentStatus.BOOKED) {  // Type-safe!
    // Can cancel
}
```

### **Why Enums?**
1. **Type Safety**: Compiler catches mistakes
2. **Limited Options**: Only valid values allowed
3. **Readability**: Clear what states exist
4. **No Typos**: Can't write `"BOKKED"` by mistake

---

# **PART 9: COMMON INTERVIEW QUESTIONS**

## **Q1: What's the difference between String and int?**

**Answer:**
```
String:
- Reference type
- Can be null
- Mutable (can use methods like substring)
- Examples: "John", "123", ""

int:
- Primitive type
- Cannot be null
- Immutable (fixed value)
- Examples: 25, -5, 0

// String can contain numbers, but it's text:
String number = "123";  // Text "123"
int number = 123;       // Actual number 123
String text = "Hello";  // You can use number.length(), int cannot
```

---

## **Q2: What's the difference between == and .equals()?**

**Answer:**
```java
String name1 = "John";
String name2 = new String("John");
String name3 = "John";

// == compares memory references (address)
name1 == name2;  // false (different objects)
name1 == name3;  // true (same object in memory)

// .equals() compares content (actual text)
name1.equals(name2);  // true (same text)
name1.equals(name3);  // true (same text)

// BEST PRACTICE for strings:
if (name.equals("John")) {  // ✓ Correct
if (name == "John") {       // ✗ Wrong
```

---

## **Q3: Why use ArrayList instead of Array?**

**Answer:**
```java
// Array - fixed size:
int[] numbers = new int[3];
numbers[0] = 1;
numbers[1] = 2;
numbers[2] = 3;
// numbers[3] = 4;  ERROR! Out of bounds!

// ArrayList - dynamic size:
ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(1);
numbers.add(2);
numbers.add(3);
numbers.add(4);  // Works! Automatically grows!
numbers.add(5);  // Works!

// In hospital system:
private List<Appointment> appointments;  // Don't know size in advance
```

---

## **Q4: What's the difference between private and public?**

**Answer:**
```java
public class Patient {
    public String name;         // Anyone can access
    private int age;            // Only Patient class can access
}

Patient p = new Patient();
p.name = "John";                // ✓ Works (public)
p.age = 25;                     // ✗ Error! Cannot access private field
p.setAge(25);                   // ✓ Works (public method)

// Benefit: setAge() can validate:
public void setAge(int age) {
    if (age >= 0 && age <= 150) {
        this.age = age;
    } else {
        System.out.println("Invalid age");
    }
}
```

---

## **Q5: What's a constructor and why do we need it?**

**Answer:**
```java
// Constructor runs when creating object:
public Patient(String name, int age) {
    this.name = name;
    this.age = age;
    appointments = new ArrayList<>();  // Initialize the list!
}

// Benefit 1: Initialize fields
Patient p = new Patient("John", 25);  // Name and age set automatically

// Benefit 2: Prevent errors
// Without constructor:
Patient p = new Patient();  // appointments is null!
p.appointments.add(...);    // ERROR: NullPointerException!

// With constructor:
Patient p = new Patient(...);  // appointments is initialized
p.appointments.add(...);       // Works!
```

---

## **Q6: What's the difference between instance and static variables?**

**Answer:**
```java
// INSTANCE (each object has its own):
private String name;
private int age;

Patient p1 = new Patient("John", 25);
Patient p2 = new Patient("Jane", 30);
p1.name = "John";  // p1's name
p2.name = "Jane";  // p2's name (different!)

// STATIC (all objects share):
private static int patientCount = 0;

// Every patient shares this ONE counter:
Patient p1 = new Patient(...);  // patientCount = 1
Patient p2 = new Patient(...);  // patientCount = 2
Patient p3 = new Patient(...);  // patientCount = 3

// Access:
Patient.patientCount;  // Use class name, not object
p1.patientCount;       // Also works but looks like instance
```

---

## **Q7: How does method overloading work?**

**Answer:**
```java
// Same method name, different parameters:
public int add(int a, int b) {
    return a + b;
}

public int add(int a, int b, int c) {
    return a + b + c;
}

public double add(double a, double b) {
    return a + b;
}

// Java figures out which one based on arguments:
add(5, 3);        // Uses first (int, int)
add(5, 3, 2);     // Uses second (int, int, int)
add(5.5, 3.2);    // Uses third (double, double)
```

---

## **Q8: What's the difference between ArrayList and HashMap?**

**Answer:**
```java
// ARRAYLIST - ordered list:
ArrayList<String> names = new ArrayList<>();
names.add("John");
names.add("Jane");
names.add("Bob");
names.get(0);       // "John"
names.get(1);       // "Jane"

// HASHMAP - key-value pairs:
HashMap<String, Integer> ages = new HashMap<>();
ages.put("John", 25);
ages.put("Jane", 30);
ages.get("John");   // 25

// In your project:
// ArrayList - for sequences:
private List<Appointment> appointments;  // List of all appointments

// HashMap - for fast lookups:
private Map<String, Appointment> appointmentMap;  // Find by ID quickly
```

---

## **Q9: What's an enum and why use it?**

**Answer:**
```java
// WITHOUT enum (bad):
String status = "BOOKED";
if (status.equals("BOOKED")) {}           // Possible typos
if (status.equals("BOKKED")) {} // TYPO! Compiles but wrong!

// WITH enum (good):
AppointmentStatus status = AppointmentStatus.BOOKED;
if (status == AppointmentStatus.BOOKED) {}  // Type-safe!
// Can't use BOKKED - doesn't exist!

// Benefits:
1. Type safety - compiler catches mistakes
2. Limited options - only valid values
3. Readability - clear what values are possible
```

---

## **Q10: How does the hospital booking system prevent double-booking?**

**Answer:**
```java
// 1. Check time slot is available
if (!timeSlot.isAvailable()) {
    System.out.println("Slot already booked");
    return null;
}

// 2. Check doctor has no conflicting appointments
if (doctor.hasConflictingAppointment(timeSlot)) {
    System.out.println("Doctor already has appointment");
    return null;
}

// 3. Create appointment only if both checks pass
Appointment appointment = new Appointment(...);

// 4. Mark slot as unavailable
timeSlot.setAvailable(false);  // Prevents others from booking this slot
```

---

## **Q11: What's the difference between return types?**

**Answer:**
```java
public void doSomething() {
    System.out.println("Hello");
    // void = returns nothing
}

public int getAge() {
    return 25;
    // int = returns a number
}

public boolean isAdult() {
    return age >= 18;
    // boolean = returns true/false
}

public String getName() {
    return name;
    // String = returns text
}

// Using them:
doSomething();        // Just executes
int age = getAge();   // Get the number
boolean adult = isAdult();  // Get true/false
String name = getName();    // Get the text
```

---

## **Q12: What happens if you don't initialize ArrayList?**

**Answer:**
```java
// WRONG - NullPointerException:
public class Patient {
    private List<Appointment> appointments;  // Not initialized!
    
    public void addAppointment(Appointment apt) {
        appointments.add(apt);  // ERROR! appointments is null!
    }
}

// CORRECT - Initialize in constructor:
public class Patient {
    private List<Appointment> appointments;
    
    public Patient(...) {
        this.appointments = new ArrayList<>();  // Initialize!
        // ...
    }
    
    public void addAppointment(Appointment apt) {
        appointments.add(apt);  // Works!
    }
}
```

---

# **QUICK REFERENCE CHEAT SHEET**

## **Data Types**
```java
int age = 25;
double salary = 150.50;
boolean available = true;
String name = "John";
```

## **Collections**
```java
ArrayList<String> list = new ArrayList<>();
HashMap<String, Integer> map = new HashMap<>();
```

## **Access Modifiers**
```java
public      // Anyone can access
private     // Only this class
protected   // This class + subclasses
```

## **Method Structure**
```java
public int calculateAge(int birthYear) {
    int age = 2024 - birthYear;
    return age;
}
```

## **If/Else**
```java
if (condition) {
    // Execute if true
} else if (otherCondition) {
    // Execute if otherCondition true
} else {
    // Execute if all false
}
```

## **Loops**
```java
// For loop
for (int i = 0; i < 5; i++) {}

// For-each loop
for (String name : namesList) {}

// While loop
while (condition) {}
```

## **Static**
```java
private static int counter = 0;  // Shared by all objects
public static int getCounter() { return counter; }  // Access via class
```

## **Enum**
```java
public enum Status {
    ACTIVE, INACTIVE, PENDING
}
```

---

# **KEY TAKEAWAYS**

✅ **Encapsulation**: Keep data private, expose through getters/setters
✅ **Validation**: Always check inputs before storing
✅ **ArrayList**: Use for lists of unknown size
✅ **HashMap**: Use for fast key-value lookups
✅ **Static**: Use for counters and shared resources
✅ **Enum**: Use for fixed set of constants
✅ **Constructor**: Initialize all fields here
✅ **Null Checks**: Always check for null before using
✅ **Access Modifiers**: Default to private, make public only if needed
✅ **Methods**: Should do ONE thing well

---

# **PART 10: INTERFACES (WEEK 5)**

## **What's an Interface?**

An **interface** is a **contract** that defines what methods a class must implement.

### **Real-World Analogy:**

```
A restaurant menu is like an interface:
- Menu says: "We serve: burger, pizza, salad"
- Different restaurants MUST serve these items
- But each restaurant makes them differently

Interface "Edible":
- Says: "All food must have: taste(), nutrition()"
- Apple must implement taste()
- Burger must implement taste()
- But each does it differently
```

### **Another Analogy:**

```
Driver's License is an interface:
- Government says: "All drivers must know how to: drive(), brake(), turn()"
- Each driver implements these skills differently
- Some drive fast, some drive slow
- But all can: drive, brake, turn
```

---

## **Why Use Interfaces?**

### **Problem Without Interfaces:**

```java
// Lots of different display methods everywhere:
public class Patient {
    public void displayPatientInfo() {
        System.out.println("Name: " + name);
    }
}

public class Doctor {
    public void displayDoctorInfo() {
        System.out.println("Name: " + name);
    }
}

public class Appointment {
    public void displayAppointmentDetails() {
        System.out.println("ID: " + id);
    }
}

// Each class has DIFFERENT method names!
// Can't treat them the same way
```

### **Solution With Interfaces:**

```java
// All classes agree on the same contract:
public interface Displayable {
    void display();
    String getSummary();
}

// All implement the same methods:
public class Patient implements Displayable {
    @Override
    public void display() { /* Patient display */ }
    
    @Override
    public String getSummary() { /* Patient summary */ }
}

public class Doctor implements Displayable {
    @Override
    public void display() { /* Doctor display */ }
    
    @Override
    public String getSummary() { /* Doctor summary */ }
}

// Now we can treat them the same:
ArrayList<Displayable> objects = new ArrayList<>();
objects.add(patient);
objects.add(doctor);
objects.add(appointment);

// Call the same method on all:
for (Displayable obj : objects) {
    obj.display();  // Each one displays itself differently!
}
```

---

## **Interfaces in Your Hospital Project**

### **Interface 1: Displayable**

**Purpose:** Classes that can show their information in readable format

```java
public interface Displayable {
    void display();          // Full detailed display
    String getSummary();     // Brief one-line summary
}
```

**Who implements it:**
- `Patient` - shows patient details
- `Doctor` - shows doctor details
- `Appointment` - shows appointment details
- `TimeSlot` - shows time slot details
- `Schedule` - shows schedule details

**Example Usage:**
```java
Patient p = new Patient("John", 25, "Male", "1999-01-01", "01234567890");
p.display();  // Prints: ==== PATIENT DETAILS ====
              //         Name: John
              //         Age: 25 years
              //         ...

String summary = p.getSummary();  // Returns: "John (Age: 25, Appointments: 0)"
```

---

### **Interface 2: Bookable**

**Purpose:** Objects that can be booked/released and have availability status

```java
public interface Bookable {
    boolean isAvailable();  // Check if available for booking
    void book();            // Mark as booked (unavailable)
    void release();         // Mark as available again
}
```

**Who implements it:**
- `TimeSlot` - individual time slots can be booked
- `Schedule` - entire doctor's schedule can be booked/released

**Example Usage:**
```java
TimeSlot slot = new TimeSlot("2024-12-15", "09:00", "10:00");
System.out.println(slot.isAvailable());  // true

slot.book();  // Patient books this slot

System.out.println(slot.isAvailable());  // false

slot.release();  // Patient cancels appointment

System.out.println(slot.isAvailable());  // true
```

**Why It's Useful:**
- Prevents double-booking (book() ensures slot can't be double-booked)
- Clear interface for availability management
- Release() allows cancellations to free up slots

---

### **Interface 3: StatusManageable**

**Purpose:** Objects that manage status changes (like appointments)

```java
public interface StatusManageable {
    AppointmentStatus getStatus();              // Get current status
    void setStatus(AppointmentStatus newStatus);  // Change status
    boolean hasStatus(AppointmentStatus status);  // Check if has specific status
}
```

**Who implements it:**
- `Appointment` - has status: BOOKED, CANCELLED, COMPLETED, RESCHEDULED

**Example Usage:**
```java
Appointment apt = new Appointment(id, patient, doctor, slot);

System.out.println(apt.getStatus());  // AppointmentStatus.BOOKED

apt.setStatus(AppointmentStatus.COMPLETED);

if (apt.hasStatus(AppointmentStatus.COMPLETED)) {
    System.out.println("Appointment finished!");
}

apt.setStatus(AppointmentStatus.CANCELLED);  // Also frees the slot!
```

**Why It's Useful:**
- Clear method names for status management
- `hasStatus()` prevents typos: `hasStatus(COMPLETED)` vs `status == "COMPLETED"`
- Standardizes status flow across appointment types

---

### **Interface 4: Searchable**

**Purpose:** System classes that manage collections and enable searching

```java
public interface Searchable {
    Object searchByName(String name);  // Find by name
    List<?> getAllRecords();           // Get all records
    int getRecordCount();              // Get total count
}
```

**Who implements it:**
- `HospitalSystem` - searches patients, doctors, appointments

**Example Usage:**
```java
HospitalSystem hospital = new HospitalSystem();
hospital.registerPatient(patient1);
hospital.registerDoctor(doctor1);
hospital.bookAppointment(...);

// Search
Object found = hospital.searchByName("John");  // Finds patient or doctor

// Get all records
List<?> allRecords = hospital.getAllRecords();  // Returns all patients + doctors + appointments

// Count
int total = hospital.getRecordCount();  // Returns total of all records
```

**Why It's Useful:**
- Standardizes search interface
- Can extend to search appointments by date, status, etc.
- `getRecordCount()` useful for admin reports

---

## **How to Create and Implement an Interface**

### **Step 1: Create the Interface**

```java
// File: Displayable.java
public interface Displayable {
    void display();
    String getSummary();
}
```

**Key points:**
- Use `interface` keyword (not `class`)
- Only declare method signatures (no body)
- No `new Displayable()` - interfaces are contracts, not objects

---

### **Step 2: Implement in a Class**

```java
// File: Patient.java
public class Patient implements Displayable {
    private String name;
    private int age;
    
    // Must implement BOTH methods:
    
    @Override
    public void display() {
        System.out.println("==== PATIENT DETAILS ====");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("========================");
    }
    
    @Override
    public String getSummary() {
        return name + " (Age: " + age + ")";
    }
}
```

**Key points:**
- Use `implements` keyword
- Use `@Override` annotation (tells compiler you're implementing interface method)
- Must implement ALL methods from the interface
- Can add additional methods too

---

### **Step 3: Implement Multiple Interfaces**

A class can implement **multiple** interfaces:

```java
public class Appointment implements Displayable, StatusManageable {
    private String id;
    private AppointmentStatus status;
    
    // From Displayable:
    @Override
    public void display() { /* ... */ }
    
    @Override
    public String getSummary() { /* ... */ }
    
    // From StatusManageable:
    @Override
    public AppointmentStatus getStatus() { /* ... */ }
    
    @Override
    public void setStatus(AppointmentStatus newStatus) { /* ... */ }
    
    @Override
    public boolean hasStatus(AppointmentStatus status) { /* ... */ }
}
```

---

## **Key Interface Benefits**

### **Benefit 1: Consistency**

```java
// Without interface - different method names:
patient.displayInfo();
doctor.printDetails();
appointment.showSummary();

// With interface - same method names:
patient.display();
doctor.display();
appointment.display();
```

### **Benefit 2: Polymorphism (One method, many forms)**

```java
// Create a list of Displayable objects:
ArrayList<Displayable> items = new ArrayList<>();
items.add(patient);
items.add(doctor);
items.add(appointment);

// Call the SAME method on all:
for (Displayable item : items) {
    item.display();  // Each one displays itself differently!
}
// Output:
// ==== PATIENT DETAILS ====
// ...
// ==== DOCTOR DETAILS ====
// ...
// ==== APPOINTMENT DETAILS ====
// ...
```

### **Benefit 3: Code Reusability**

```java
// Function that works with ANY Displayable object:
public void printAllDetails(ArrayList<Displayable> items) {
    for (Displayable item : items) {
        item.display();
        System.out.println("Summary: " + item.getSummary());
        System.out.println("---");
    }
}

// Can pass different types:
printAllDetails(patients);      // List of patients
printAllDetails(doctors);       // List of doctors
printAllDetails(appointments);  // List of appointments
```

### **Benefit 4: Loose Coupling**

```java
// Without interface - code is tightly bound:
Patient p = new Patient(...);
p.display();  // Only works for Patient

// With interface - code is flexible:
Displayable d = new Patient(...);
d.display();  // Works for any Displayable!

d = new Doctor(...);  // Can switch types
d.display();  // Still works!
```

---

## **Interface vs Abstract Class**

| Aspect | Interface | Abstract Class |
|--------|-----------|----------------|
| Keyword | `interface` | `abstract class` |
| Methods | Only signatures | Can have bodies |
| Constructor | No | Yes |
| Variables | Constants only | Any type |
| Multiple | Yes | No |
| Purpose | Contract | Shared behavior |

**When to use:**
- **Interface**: Define what classes MUST do (contract)
- **Abstract Class**: Share common code between related classes

---

## **Interview Questions About Interfaces**

### **Q: Why would you use an interface instead of a class?**

**Answer:**
```
Interfaces define a contract - what methods must exist.
Classes can implement different methods in different ways.

Example:
- Interface says: "All objects must have a display() method"
- Patient, Doctor, Appointment all have display()
- But each displays itself differently
- Code can work with ANY Displayable without knowing the exact type
```

### **Q: Can you create an object from an interface?**

**Answer:**
```java
Displayable d = new Displayable();  // ERROR! Can't do this
Displayable d = new Patient(...);   // Correct - Patient implements Displayable
```

### **Q: What happens if you don't implement all methods?**

**Answer:**
```java
public class Patient implements Displayable {
    @Override
    public void display() { /* ... */ }
    // Missing getSummary()!
}
// ERROR: Patient is not abstract and does not override abstract method getSummary()
```

### **Q: Can a class implement multiple interfaces?**

**Answer:**
```java
public class Appointment implements Displayable, StatusManageable {
    // Must implement all methods from both interfaces
}
```

---

## **Class Diagram (Week 5 with Interfaces)**

```
<<interface>>
Displayable
+display(): void
+getSummary(): String
        ↑
        | implements
    ┌───┴───────┬─────────────┬──────────┐
    |           |             |          |
 Patient     Doctor      Appointment   TimeSlot
    |           |             |          |
   ...         ...           ...        ...

<<interface>>
StatusManageable
+getStatus(): AppointmentStatus
+setStatus(AppointmentStatus): void
+hasStatus(AppointmentStatus): boolean
        ↑
        | implements
        |
   Appointment
        |
       ...

<<interface>>
Bookable
+isAvailable(): boolean
+book(): void
+release(): void
        ↑
        | implements
    ┌───┴────────┐
    |            |
 TimeSlot    Schedule
    |            |
   ...          ...

<<interface>>
Searchable
+searchByName(String): Object
+getAllRecords(): List<?>
+getRecordCount(): int
        ↑
        | implements
        |
  HospitalSystem
        |
       ...
```

---

## **Summary: Why Interfaces Matter**

✅ **Standardization**: All Displayable objects have display() and getSummary()
✅ **Flexibility**: Code works with any object implementing the interface
✅ **Maintainability**: Easy to understand what methods are available
✅ **Extensibility**: Easy to add new classes implementing the interface
✅ **Testing**: Can create mock objects implementing the interface
✅ **Large Projects**: Prevents confusion with different method names

---

**Interfaces are one of the most powerful features in Java!**
**They enable you to write flexible, maintainable, professional code.**


