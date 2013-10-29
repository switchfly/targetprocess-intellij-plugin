package com.switchfly.targetprocess;

import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.User;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TargetProcessParserTest {

    private final TargetProcessParser parser = new TargetProcessParser();

    @Test
    public void testParseAssignables() {
        List<Assignable> assignables = parser.parseAssignables(getClass().getResourceAsStream("/Assignables.json"));
        assertEquals(10, assignables.size());

        Assignable assignable1 = assignables.get(0);
        assertEquals(8, assignable1.getId());
        assertEquals("Android native application", assignable1.getName());
        assertEquals("\tProin a fringilla ante. Duis vitae nisl dolor&#44; id suscipit quam. Duis nec arcu eu lacus vestibulum tincidunt. Vestibulum malesuada odio in metus tempus condimentum. Maecenas nec tristique augue. Integer id elementum neque. Curabitur eget felis lectus&#44; eu tristique risus. Sed massa elit&#44; luctus at condimentum vel&#44; condimentum ac ligula. Etiam imperdiet tellus sed nibh condimentum vehicula. Nullam accumsan nisi ultricies sem viverra in vulputate risus commodo. Phasellus aliquet porta ipsum id pulvinar. Nam vulputate vulputate porttitor. Pellentesque ultrices diam et risus vehicula sit amet porttitor leo luctus. Maecenas ut nulla sit amet dolor pulvinar vehicula a id massa", assignable1.getDescription());
        assertEquals(new Date(1337176800000L), assignable1.getCreateDate());
        assertEquals(new Date(1337176800000L), assignable1.getModifyDate());
        assertEquals("Request", assignable1.getEntityType());
        assertEquals("Tau Product - Kanban #1", assignable1.getProject());

        Assignable assignable5 = assignables.get(4);
        assertEquals(14, assignable5.getId());
        assertEquals("Support Portal integration", assignable5.getName());
        assertEquals("<div>\n\t<div>\n\t\tAs a user</div>\n\t<div>\n\t\tI want to quickly navigate between various teams and projects boards without selecting correct teams and projects all the time</div>\n\t<div>\n\t\tSo I can see what I want with less clicks</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tGiven board Team1 Board</div>\n\t<div>\n\t\tAnd P1 and T1 are selected in context</div>\n\t<div>\n\t\tWhen I click Remember Context button</div>\n\t<div>\n\t\tThen P1 and T1 are remembered for Team1 Board</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tWhen I navigate to Kanban Board</div>\n\t<div>\n\t\tAnd select T2 and P2 in context</div>\n\t<div>\n\t\tAnd then navigate to Team1 Board</div>\n\t<div>\n\t\tThen P1 and T1 are selected in context automatically</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tI can reset context for the board using &#91;x&#93; icon near Remember Context button.</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tOnly Admin or Board Owner can Remember and Reset Context</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tTransitions between boards</div>\n</div>\n<div>\n\t </div>\n", assignable5.getDescription());
        assertEquals(new Date(1336572000000L), assignable5.getCreateDate());
        assertEquals(new Date(1354115443000L), assignable5.getModifyDate());
        assertEquals("Feature", assignable5.getEntityType());
        assertEquals("Tau Product Web Site - Scrum #1", assignable5.getProject());

        Assignable assignable10 = assignables.get(9);
        assertEquals(20, assignable10.getId());
        assertEquals("Accessibility tests failing for home page", assignable10.getName());
        assertNull(assignable10.getDescription());
        assertEquals(new Date(1336572000000L), assignable10.getCreateDate());
        assertEquals(new Date(1354548198000L), assignable10.getModifyDate());
        assertEquals("Bug", assignable10.getEntityType());
        assertEquals("Tau Product Web Site - Scrum #1", assignable10.getProject());
    }

    @Test
    public void testParseAssignablesEmptyResponse() {
        List<Assignable> assignables = parser.parseAssignables(getEmptyResponse());
        assertTrue(assignables.isEmpty());
    }

    @Test
    public void testParseUser() {
        User user = parser.parseUser(getClass().getResourceAsStream("/User.json"));
        assertNotNull(user);
        assertEquals(15, user.getId());
    }

    @Test
    public void testParseUserEmptyResponse() {
        User user = parser.parseUser(getEmptyResponse());
        assertNull(user);
    }

    private InputStream getEmptyResponse() {
        return new ByteArrayInputStream("{  \"Items\": [] }".getBytes());
    }
}
