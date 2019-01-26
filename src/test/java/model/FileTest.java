package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileTest {

    private Author author;
    private Permissions perms;

    @BeforeEach
    void setUp() {
        author = new Author("John");
        perms = new Permissions(true, true, true);
    }

    @Test
    void validatesValidFileNames() {
        // Not supposed to throw exception
        new File("test", author, perms);
        new File("test1", author, perms);
        new File("Test", author, perms);
        new File("Test56", author, perms);
        new File("4", author, perms);
        new File(".gitignore", author, perms);
        new File("testFile123.pdf", author, perms);
        new File("test test", author, perms);
        new File("test-test", author, perms);
    }

    @Test
    void validatesInvalidFileNames() {
        Throwable e;

        e = assertThrows(IllegalArgumentException.class, () -> {
            new File("", author, perms);
        });

        assertEquals("Invalid file name given.", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> {
            new File("    ", author, perms);
        });

        assertEquals("Invalid file name given.", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> {
            new File("test!", author, perms);
        });

        assertEquals("Invalid file name given.", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> {
            new File("% &/#/&123Test", author, perms);
        });

        assertEquals("Invalid file name given.", e.getMessage());
    }
}
