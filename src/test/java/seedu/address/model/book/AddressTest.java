package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LanguageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Language(null));
    }

    @Test
    public void constructor_invalidLanguage_throwsIllegalArgumentException() {
        String invalidLanguage = "";
        assertThrows(IllegalArgumentException.class, () -> new Language(invalidLanguage));
    }

    @Test
    public void isValidLanguage() {
        // null language
        assertThrows(NullPointerException.class, () -> Address.isValidLanguage(null));

        // invalid languagees
        assertFalse(Address.isValidLanguage("")); // empty string
        assertFalse(Address.isValidLanguage(" ")); // spaces only

        // valid languagees
        assertTrue(Address.isValidLanguage("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidLanguage("-")); // one character
        assertTrue(Address.isValidLanguage("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long language
    }
}
