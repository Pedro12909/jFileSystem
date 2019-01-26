package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FileSystemTest {

    private Author author;
    private Permissions perms;

    private Folder folderA;
    private Folder folderB1;
    private Folder folderB2;
    private Folder folderC;

    private File fileA;
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

        fileA = new File("FileA", author, perms);
        fileB1 = new File("FileB", author, perms);
        fileB2 = new File("FileB", author, perms);
        fileC = new File("FileC", author, perms);
        fileD = new File("FileD", author, perms);

        folderB1.addChild(folderB2);
        folderB1.addChild(fileB2);
        folderB1.addChild(fileC);

        folderC.addChild(fileD);

        folderA.addChild(folderB1);
        folderA.addChild(folderC);
        folderA.addChild(fileB1);
    }


    @Test
    public void testFindItem() {
        FileSystem instance = FileSystem.getInstance();

        instance.addItemToFolder("/", fileA);
        instance.addItemToFolder("/", folderA);

        assertEquals(instance.findItem("/FolderA/FolderB/FileC"), fileC);
        assertEquals(instance.findItem("/FolderA/FolderB/FileB"), fileB2);
        assertEquals(instance.findItem("/FolderA/FolderB/FileB/"), fileB2);
        assertEquals(instance.findItem("/FolderA/FolderB/FileB////"), fileB2);
        assertEquals(instance.findItem("/FolderA"), folderA);
        assertEquals(instance.findItem("/FolderA/FolderB/FolderB"), folderB2);
        assertEquals(instance.findItem("/FolderA/FolderB"), folderB1);
        assertEquals(instance.findItem("/FolderA/FolderB/"), folderB1);

        assertNull(instance.findItem("test"));
        assertNull(instance.findItem("//"));
        assertNull(instance.findItem("fd//asd"));
        assertNull(instance.findItem("fd/FolderA"));
        assertNull(instance.findItem("/FolderA///FolderB"));
    }
}
