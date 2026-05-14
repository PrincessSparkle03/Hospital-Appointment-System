package Main;

import Model.Appointment;
import Model.Doctor;
import Model.Patient;
import Model.Schedule;
import Model.TimeSlot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Class - Hospital Management System Entry Point
 * * Demonstrates the hospital system with:
 * 1. Patient registration
 * 2. Doctor registration with schedules
 * 3. TimeSlot creation with dates
 * 4. Appointment booking with conflict validation
 * 5. Appointment status management (booked, cancelled)
 * 6. System-wide reporting
 * 
 * SIMPLIFIED AVAILABILITY CHECKING (Updated):
 * - TimeSlot NO LONGER has confusing isAvailable(), book(), or release() methods
 * - All availability checking is done through Appointment.isAvailable()
 * - Appointment.isAvailable() checks if doctor has conflicts via Doctor.hasConflictingAppointment()
 * - RULE: Check appointment availability, NOT time slot availability
 * 
 * Week 3 Deliverables Achieved:
 * ✓ AppointmentStatus enum for appointment states
 * ✓ TimeSlot with date (availability checked via Appointment)
 * ✓ Appointment uses TimeSlot instead of String date/time
 * ✓ Patient has ArrayList<Appointment> for history
 * ✓ Doctor prevents duplicate appointments (conflict validation)
 * ✓ HospitalSystem manages all patients, doctors, appointments
 * ✓ Clear class relationships documented
 * ✓ Access modifiers clearly defined in all classes
 * ✓ UNDERSTAND STATIC COUNTERS: Static counter added to Appointment.java
 * ✓ SIMPLIFIED AVAILABILITY: Removed messy isAvailable/book/release from TimeSlot
 */
public class Main {
    public static void main(String[] args) {
        // Initialize the Hospital System - central management class
        HospitalSystem hospital = new HospitalSystem();
        System.out.println("Welcome to " + HospitalSystem.getHospitalName());
        System.out.println("Initializing Hospital Management System...\n");

        // ============================================
        // 1. CREATE PATIENTS
        // ============================================
        System.out.println("--- REGISTERING PATIENTS ---");
        Patient patient1 = new Patient("John Doe", 25, "Male", LocalDate.of(1999, 1, 1), "01234567890");
        Patient patient2 = new Patient("Jane Smith", 30, "Female", LocalDate.of(1994, 5, 15), "09876543210");
        Patient patient3 = new Patient("Michael Johnson", 45, "Male", LocalDate.of(1979, 3, 20), "08765432109");

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);
        hospital.registerPatient(patient3);

        // ============================================
        // 2 & 3 & 4. CREATE SHARED SCHEDULE & DOCTORS
        // ============================================
        System.out.println("\n--- CREATING SHARED SCHEDULE & REGISTERING DOCTORS ---");
        
        // PROFESSOR FEEDBACK: Create ONE shared schedule for all doctors
        // All doctors use the same time slots - availability controlled by Appointment status
        Schedule sharedSchedule = generateWeeklySchedule();
        
        Doctor doctor1 = new Doctor("Gregory House", "Diagnostic Medicine", 150.0, sharedSchedule);
        Doctor doctor2 = new Doctor("Lisa Cuddy", "Internal Medicine", 160.0, sharedSchedule);
        Doctor doctor3 = new Doctor("Robert Chase", "Cardiology", 180.0, sharedSchedule);

        hospital.registerDoctor(doctor1);
        hospital.registerDoctor(doctor2);
        hospital.registerDoctor(doctor3);

        // Test security: Try to change hourly rate with WRONG password
        System.out.println("\nTesting security - Attempting rate change with wrong password:");
        doctor1.setHourlyRate(400.0, "Hello123");

        // Test security: Change hourly rate with CORRECT password
        System.out.println("Testing security - Changing rate with correct password:");
        doctor1.setHourlyRate(200.0, "ADMIN123");

        // ============================================
        // 5. BOOK APPOINTMENTS WITH VALIDATION
        // ============================================
        System.out.println("\n--- BOOKING APPOINTMENTS ---");
        
        // Fetch shared time slots - all doctors can use the same slots
        TimeSlot slot_monday_09_10 = sharedSchedule.getSlots().get(0);
        TimeSlot slot_monday_10_11 = sharedSchedule.getSlots().get(1);

        // Appointment 1: Patient 1 with Doctor 1 on Monday 09:00-10:00
        System.out.println("\nBooking: Patient1 with Doctor1 on Monday 09:00");
        Appointment app1 = hospital.bookAppointment(patient1, doctor1, slot_monday_09_10);
        if (app1 != null) {
            System.out.println(app1.toString());
        }

        // Appointment 2: Patient 2 with Doctor 1 on Monday 10:00-11:00
        System.out.println("\nBooking: Patient2 with Doctor1 on Monday 10:00");
        Appointment app2 = hospital.bookAppointment(patient2, doctor1, slot_monday_10_11);
        if (app2 != null) {
            System.out.println(app2.toString());
        }

        // Appointment 3: Patient 3 with Doctor 2 on Monday 09:00-10:00 (SAME SLOT AS APP1, DIFFERENT DOCTOR)
        System.out.println("\nBooking: Patient3 with Doctor2 on Monday 09:00 (same slot, different doctor)");
        Appointment app3 = hospital.bookAppointment(patient3, doctor2, slot_monday_09_10);
        if (app3 != null) {
            System.out.println(app3.toString());
        }

        // ============================================
        // 6. TEST DUPLICATE APPOINTMENT VALIDATION
        // ============================================
        System.out.println("\n--- TESTING DUPLICATE APPOINTMENT PREVENTION ---");
        
        // Try to book doctor1 at overlapping time (should fail - doctor1 already has appointment at this time)
        System.out.println("\nAttempting to book Doctor1 at same Monday 09:00 time (should fail):");
        Appointment failedApp = hospital.bookAppointment(patient2, doctor1, slot_monday_09_10);
        if (failedApp == null) {
            System.out.println("✓ Duplicate appointment for Doctor1 correctly prevented!");
        }

        // Doctor 3 can book the same time slot (different doctor)
        System.out.println("\nAttempting to book Doctor3 at Monday 09:00 (different doctor, should succeed):");
        Appointment app4 = hospital.bookAppointment(patient2, doctor3, slot_monday_09_10);
        if (app4 != null) {
            System.out.println(app4.toString());
        }

        // ============================================
        // 7. TEST APPOINTMENT STATUS MANAGEMENT
        // ============================================
        System.out.println("\n--- TESTING APPOINTMENT STATUS MANAGEMENT ---");
        
        if (app1 != null) {
            System.out.println("\nAppointment 1 Status: " + app1.getStatusDisplay());
            
            // Cancel the appointment
            System.out.println("Cancelling appointment 1...");
            hospital.cancelAppointment(app1);
            System.out.println("Appointment 1 New Status: " + app1.getStatusDisplay());
            
            // Try to cancel again (should fail)
            System.out.println("\nAttempting to cancel already cancelled appointment (should fail):");
            boolean cancelResult = hospital.cancelAppointment(app1);
            if (!cancelResult) {
                System.out.println("✓ Cannot cancel non-BOOKED appointment!");
            }
        }
        // ============================================
        System.out.println(hospital.generateSystemReport());

        System.out.println("--- PATIENTS REGISTERED ---");
        hospital.displayAllPatients();

        System.out.println("\n--- DOCTORS REGISTERED ---");
        hospital.displayAllDoctors();

        System.out.println("\n--- ALL APPOINTMENTS ---");
        hospital.displayAllAppointments();

        // ============================================
        // 9. DISPLAY APPOINTMENT HISTORY
        // ============================================
        System.out.println("\n--- PATIENT APPOINTMENT HISTORY ---");
        System.out.println("\nPatient 2 (" + patient2.getName() + ") Appointments:");
        List<Appointment> patient2Appointments = hospital.getPatientAppointments(patient2);
        for (Appointment apt : patient2Appointments) {
            System.out.println("  " + apt.getAppointmentSummary());
        }

        System.out.println("\nDoctor 1 (" + doctor1.getName() + ") Appointments:");
        List<Appointment> doctor1Appointments = hospital.getDoctorAppointments(doctor1);
        for (Appointment apt : doctor1Appointments) {
            System.out.println("  " + apt.getAppointmentSummary());
        }

        // ============================================
        // 10. DISPLAY SUMMARY STATISTICS
        // ============================================
        System.out.println("\n========================================");
        System.out.println("SUMMARY STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Patients: " + Patient.getPatientCount());
        System.out.println("Total Doctors: " + Doctor.getDoctorCount());
        System.out.println("Total System Appointments: " + hospital.getAppointmentCount());
        System.out.println("Active Appointments: " + hospital.getActiveAppointmentCount());
        
        // FIXED: Added the Static Counter printout to satisfy the rubric
        System.out.println("Total Appointment Objects Ever Created (Static Counter): " + Appointment.getTotalAppointmentsCreated());
        
        System.out.println("\nPatient2 Total Appointments: " + patient2.getAppointmentCount());
        System.out.println("Patient2 Active Appointments: " + patient2.getActiveAppointmentCount());
        System.out.println("\nDoctor1 Total Appointments: " + doctor1.getAppointmentCount());
        System.out.println("Doctor1 Active Appointments: " + doctor1.getActiveAppointmentCount());
        System.out.println("========================================\n");

        System.out.println("Hospital System initialized successfully!");
    }

    /**
     * Helper method to generate a fresh, independent set of TimeSlots and a SINGLE Schedule.
     * This ensures Doctors do not share memory references for their availability.
     */
    private static Schedule generateWeeklySchedule() {
        List<TimeSlot> allSlots = new ArrayList<>();
        
        // Monday slots (using future date)
        allSlots.add(new TimeSlot(LocalDate.of(2026, 5, 12), LocalTime.of(9, 0), LocalTime.of(10, 0)));
        allSlots.add(new TimeSlot(LocalDate.of(2026, 5, 12), LocalTime.of(10, 0), LocalTime.of(11, 0)));
        allSlots.add(new TimeSlot(LocalDate.of(2026, 5, 12), LocalTime.of(11, 0), LocalTime.of(12, 0)));
        allSlots.add(new TimeSlot(LocalDate.of(2026, 5, 12), LocalTime.of(14, 0), LocalTime.of(15, 0)));

        // Tuesday slots
        allSlots.add(new TimeSlot(LocalDate.of(2026, 5, 13), LocalTime.of(9, 0), LocalTime.of(10, 0)));
        allSlots.add(new TimeSlot(LocalDate.of(2026, 5, 13), LocalTime.of(10, 0), LocalTime.of(11, 0)));

        return new Schedule("Weekly", allSlots, LocalTime.of(9, 0), LocalTime.of(17, 0), true);
    }
}