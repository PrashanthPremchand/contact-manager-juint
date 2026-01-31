package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    public static void setUpAll() {
        System.out.println("ContactManagerTest setup");
    }

    @BeforeEach
    public void setup() {
        contactManager = new ContactManager();
    }

    @Test
    public void shouldCreateContact() {
        contactManager.addContact("John", "Smith", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts()
                .stream()
                .anyMatch(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Smith") &&
                        contact.getPhoneNumber().equals("0123456789")
                        )
        );
    }

    @Test
    @DisplayName("Should not create contact when first name is null")
    public  void shouldNotCreateContactWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class,
                () -> contactManager.addContact(null, "Smith", "0123456789"));
    }
    @Test
    @DisplayName("Should not create contact when last name is null")
    public  void shouldNotCreateContactWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class,
                () -> contactManager.addContact("John", null, "0123456789"));
    }

    @Test
    @DisplayName("Should not create contact when email is null")
    public  void shouldNotCreateContactWhenEmailIsNull() {
        Assertions.assertThrows(RuntimeException.class,
                () -> contactManager.addContact("John", "Smith", null));
    }

    @AfterEach
    public void tearDown() {
        System.out.println("ContactManagerTest tearDown");
    }
    @AfterAll
    public static void tearDownAll() {
        System.out.println("ContactManagerTest tearDownAll");
    }

    @Test
    @DisplayName("Should create contact on Mac OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on Mac OS")
    public void shouldCreateContactOnlyOnMac() {
        contactManager.addContact("John", "Smith", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts()
                .stream()
                .anyMatch(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Smith") &&
                        contact.getPhoneNumber().equals("0123456789")
                )
        );
    }

    @Test
    @DisplayName("Should create contact on Mac OS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    public void shouldNotCreateContactOnWindows() {
        contactManager.addContact("John", "Smith", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts()
                .stream()
                .anyMatch(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Smith") &&
                        contact.getPhoneNumber().equals("0123456789")
                )
        );
    }

    @Test
    @DisplayName("Should create test contact on developer machine")
    public void shouldCreateTestContactOnDeveloperMachine() {
        Assumptions.assumeTrue("TEST".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Smith", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }




    @Nested
    class RepeatedNestedTestClass {
        @RepeatedTest(value = 5, name = "Repeating contact creation test {currentRepetition} vs {totalRepetitions}")
        @DisplayName("Repeated create contact test 5 times")
        public void shouldTestContactCreationRepeatedly() {
            contactManager.addContact("John", "Smith", "0123456789");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @Nested
    class ParameterizedTestClass {
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456789", "0123456789"})
        @DisplayName("Should test contact creation using value source")
        public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
            contactManager.addContact("John", "Smith", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }



        @ParameterizedTest
        @CsvSource({"0123456789", "0123456789", "0123456789"})
        @DisplayName("Should create contact using CSV source")
        public void shouldTestContactCreationUsingCsvSource(String phoneNumber) {
            contactManager.addContact("John", "Smith", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        @DisplayName("Should create contact using CSV file source")
        public void shouldTestContactCreationUsingCsvFileSource(String phoneNumber) {
            contactManager.addContact("John", "Smith", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @ParameterizedTest
    @MethodSource("phoneNumberList")
    @DisplayName("Should create contact using method source")
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Smith", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    public static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456789", "0123456789");
    }

}