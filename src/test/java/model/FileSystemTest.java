package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileSystemTest {

    private FileSystem instance;

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
        instance = new FileSystem();

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
    }


    @Test
    public void testFindItem() {
        instance.addItemToFolder("/", fileA);
        instance.addItemToFolder("/", folderA);

        folderA.addChild(folderB1);
        folderA.addChild(folderC);
        folderA.addChild(fileB1);

        folderB1.addChild(folderB2);
        folderB1.addChild(fileB2);
        folderB1.addChild(fileC);

        folderC.addChild(fileD);

        assertEquals(instance.findItem("/FolderA/FolderB/FileC"), fileC);
        assertEquals(instance.findItem("/FolderA/FolderB/FileB"), fileB2);
        assertEquals(instance.findItem("/FolderA"), folderA);
        assertEquals(instance.findItem("/FolderA/FolderB/FolderB"), folderB2);
        assertEquals(instance.findItem("/FolderA/FolderB"), folderB1);
        assertEquals(instance.findItem("/FolderA/FolderB"), folderB1);

        assertNull(instance.findItem("test"));
        assertNull(instance.findItem("//"));
        assertNull(instance.findItem("fd//asd"));
        assertNull(instance.findItem("fd/FolderA"));
        assertNull(instance.findItem("/FolderA///FolderB"));
        assertNull(instance.findItem("/FolderA/FolderB/FileB/"));
        assertNull(instance.findItem("/FolderA/FolderB/FileB////"));
    }

    @Test
    public void testAddChild() {
        Folder root = ((Folder)instance.findItem("/"));

        instance.addItemToFolder("/", fileA);
        assertEquals("/FileA", fileA.getAbsolutePath());
        assert(root.getAllChildren().contains(fileA));

        instance.addItemToFolder("/", folderA);

        folderA.addChild(folderB1);
        folderA.addChild(folderC);
        folderA.addChild(fileB1);

        folderB1.addChild(folderB2);
        folderB1.addChild(fileB2);
        folderB1.addChild(fileC);

        folderC.addChild(fileD);

        File testFile = new File("TestFile", author, perms);
        instance.addItemToFolder("/FolderA/FolderB/FolderB", testFile);
        assertEquals("/FolderA/FolderB/FolderB/TestFile",
                testFile.getAbsolutePath());
        assert(folderB2.getAllChildren().contains(testFile));
        assertEquals(1, folderB2.getAllChildren().size());
    }

    @Test
    public void testCopyItem() {
        instance.addItemToFolder("/", fileA);
        instance.addItemToFolder("/", folderA);

        folderA.addChild(folderB1);
        folderA.addChild(folderC);
        folderA.addChild(fileB1);

        folderB1.addChild(folderB2);
        folderB1.addChild(fileB2);
        folderB1.addChild(fileC);

        folderC.addChild(fileD);

        Throwable e;
        e = assertThrows(IllegalArgumentException.class, () -> {
            instance.copyItem(folderA, "/FolderA/FolderB");
        });
        assertEquals("Cannot add item to itself.", e.getMessage());
        assertFalse(folderB1.getAllChildren().contains(folderA));

        instance.copyItem(folderB1, "/");

        assertTrue(folderA.getAllChildren().contains(folderB1));
        assertEquals("/FolderB", folderB1.getAbsolutePath());
        assertEquals("/FolderB/FileB", fileB2.getAbsolutePath());
        assertEquals("/FolderB/FileC", fileC.getAbsolutePath());
        assertEquals("/FolderB/FolderB", folderB2.getAbsolutePath());
    }

    @Test
    public void testMoveItem() {
        instance.addItemToFolder("/", fileA);
        instance.addItemToFolder("/", folderA);

        folderA.addChild(folderB1);
        folderA.addChild(folderC);
        folderA.addChild(fileB1);

        folderB1.addChild(folderB2);
        folderB1.addChild(fileB2);
        folderB1.addChild(fileC);

        folderC.addChild(fileD);

        instance.moveItem(folderB1, "/");

        assertTrue(!folderA.getAllChildren().contains(folderB1));
        assertEquals("/FolderB", folderB1.getAbsolutePath());
        assertEquals("/FolderB/FileB", fileB2.getAbsolutePath());
        assertEquals("/FolderB/FileC", fileC.getAbsolutePath());
        assertEquals("/FolderB/FolderB", folderB2.getAbsolutePath());
    }

    @Test
    public void testDeleteChild() {
        instance.addItemToFolder("/", fileA);
        instance.addItemToFolder("/", folderA);

        folderA.addChild(folderB1);
        folderA.addChild(folderC);
        folderA.addChild(fileB1);

        folderB1.addChild(folderB2);
        folderB1.addChild(fileB2);
        folderB1.addChild(fileC);

        folderC.addChild(fileD);

        instance.deleteItem(folderB2);
        assertTrue(!folderB1.getAllChildren().contains(folderB2));

        instance.deleteItem("/FolderA/FolderB");
        assertTrue(!folderA.getAllChildren().contains(folderB1));
        assertEquals(2, folderA.getAllChildren().size());
    }
}
