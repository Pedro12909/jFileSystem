package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FolderTest {

    private Author author;
    private Permissions perms;

    private Folder folderA;
    private Folder folderB1;
    private Folder folderB2;
    private Folder folderC;

    private File fileB1;
    private File fileB2;
    private File fileC;
    private File fileD;

    @BeforeEach
    void setUp() {
        author = new Author("John");
        perms = new Permissions(true, true, true);

        folderA = new Folder("FolderA", author, perms);
        folderB1 = new Folder("FolderB", author, perms);
        folderB2 = new Folder("FolderB", author, perms);
        folderC = new Folder("FolderC", author, perms);

        fileB1 = new File("FileB", author, perms);
        fileB2 = new File("FileB", author, perms);
        fileC = new File("FileC", author, perms);
        fileD = new File("FileD", author, perms);
    }


    @Test
    public void testIsParentOfFolder() {
        folderA.addChild(folderB1);
        folderA.addChild(folderC);
        folderA.addChild(fileB1);

        folderB1.addChild(folderB2);
        folderB1.addChild(fileB2);
        folderB1.addChild(fileC);

        folderC.addChild(fileD);

        assertEquals(folderA.findFolder(folderB1), folderB1);
        assertEquals(folderA.findFolder(folderB2), folderB2);
        assertEquals(folderA.findFolder(folderC), folderC);
        assertEquals(folderB1.findFolder(folderB2), folderB2);
        assertEquals(folderB1.findFolder(folderA), null);
    }
}
