package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    private static final IFilter FILTER_ALL = (record) -> true;
    private static final IFilter FILTER_NONE = (record) -> false;

    @Test
    void testForJmbagExisting() {
        List<String> rows = new ArrayList<>();
        rows.add("0000000003\tBosnić\tAndrea\t4\n");
        rows.add("0000000004\tBožić\tMarin\t5\n");
        rows.add("0000000024\tKarlović\tĐive\t5");

        StudentDatabase db = new StudentDatabase(rows);

        assertNotNull(db.forJMBAG("0000000004"));
        assertEquals(
                new StudentRecord("0000000004", "Božić", "Marin", 5),
                db.forJMBAG("0000000004")
        );
    }

    @Test
    void testForJmbagNotExisting() {
        List<String> rows = new ArrayList<>();
        rows.add("0000000003\tBosnić\tAndrea\t4\n");
        rows.add("0000000024\tKarlović\tĐive\t5");

        StudentDatabase db = new StudentDatabase(rows);

        assertNull(db.forJMBAG("0123456789"));
    }


    @Test
    void testForJmbagNull() {
        List<String> rows = new ArrayList<>();
        rows.add("0000000024\tKarlović\tĐive\t5");

        StudentDatabase db = new StudentDatabase(rows);

        assertThrows(NullPointerException.class, () -> db.forJMBAG(null));
    }

    @Test
    void testFilterAll() {
        List<String> rows = new ArrayList<>();
        rows.add("0000000003\tBosnić\tAndrea\t4\n");
        rows.add("0000000004\tBožić\tMarin\t5\n");
        rows.add("0000000024\tKarlović\tĐive\t5");

        StudentDatabase db = new StudentDatabase(rows);

        List<StudentRecord> expected = new ArrayList<>();
        expected.add(new StudentRecord("0000000003", "Bosnić", "Andrea", 4));
        expected.add(new StudentRecord("0000000004", "Božić", "Marin", 5));
        expected.add(new StudentRecord("0000000024", "Karlović", "Đive", 5));

        assertEquals(expected, db.filter(FILTER_ALL));
    }

    @Test
    void testFilterNone() {
        List<String> rows = new ArrayList<>();
        rows.add("0000000003\tBosnić\tAndrea\t4\n");
        rows.add("0000000004\tBožić\tMarin\t5\n");
        rows.add("0000000024\tKarlović\tĐive\t5");

        StudentDatabase db = new StudentDatabase(rows);

        List<StudentRecord> expected = new ArrayList<>();

        assertEquals(expected, db.filter(FILTER_NONE));
    }
}
