package com.switchfly.targetprocess;

import java.util.List;
import com.switchfly.targetprocess.model.Assignable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TargetProcessParserTest {

    private final TargetProcessParser parser = new TargetProcessParser();

    @Test
    public void testParseAssignables() {
        List<Assignable> assignables = parser.parseAssignables(getClass().getResourceAsStream("/Assignables.json"));
        assertEquals(10, assignables.size());
    }
}
