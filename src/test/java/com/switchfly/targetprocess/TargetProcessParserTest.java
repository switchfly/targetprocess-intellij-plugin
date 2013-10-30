package com.switchfly.targetprocess;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.Comment;
import com.switchfly.targetprocess.model.User;
import org.junit.Test;

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
        assertEquals(
            "\tProin a fringilla ante. Duis vitae nisl dolor&#44; id suscipit quam. Duis nec arcu eu lacus vestibulum tincidunt. Vestibulum malesuada odio in metus tempus condimentum. Maecenas nec tristique augue. Integer id elementum neque. Curabitur eget felis lectus&#44; eu tristique risus. Sed massa elit&#44; luctus at condimentum vel&#44; condimentum ac ligula. Etiam imperdiet tellus sed nibh condimentum vehicula. Nullam accumsan nisi ultricies sem viverra in vulputate risus commodo. Phasellus aliquet porta ipsum id pulvinar. Nam vulputate vulputate porttitor. Pellentesque ultrices diam et risus vehicula sit amet porttitor leo luctus. Maecenas ut nulla sit amet dolor pulvinar vehicula a id massa",
            assignable1.getDescription());
        assertEquals(new Date(1337176800000L), assignable1.getCreateDate());
        assertEquals(new Date(1337176800000L), assignable1.getModifyDate());
        assertEquals("Request", assignable1.getEntityTypeName());
        assertEquals("Tau Product - Kanban #1", assignable1.getProjectName());

        Assignable assignable5 = assignables.get(4);
        assertEquals(14, assignable5.getId());
        assertEquals("Support Portal integration", assignable5.getName());
        assertEquals(
            "<div>\n\t<div>\n\t\tAs a user</div>\n\t<div>\n\t\tI want to quickly navigate between various teams and projects boards without selecting correct teams and projects all the time</div>\n\t<div>\n\t\tSo I can see what I want with less clicks</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tGiven board Team1 Board</div>\n\t<div>\n\t\tAnd P1 and T1 are selected in context</div>\n\t<div>\n\t\tWhen I click Remember Context button</div>\n\t<div>\n\t\tThen P1 and T1 are remembered for Team1 Board</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tWhen I navigate to Kanban Board</div>\n\t<div>\n\t\tAnd select T2 and P2 in context</div>\n\t<div>\n\t\tAnd then navigate to Team1 Board</div>\n\t<div>\n\t\tThen P1 and T1 are selected in context automatically</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tI can reset context for the board using &#91;x&#93; icon near Remember Context button.</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tOnly Admin or Board Owner can Remember and Reset Context</div>\n\t<div>\n\t\t </div>\n\t<div>\n\t\tTransitions between boards</div>\n</div>\n<div>\n\t </div>\n",
            assignable5.getDescription());
        assertEquals(new Date(1336572000000L), assignable5.getCreateDate());
        assertEquals(new Date(1354115443000L), assignable5.getModifyDate());
        assertEquals("Feature", assignable5.getEntityTypeName());
        assertEquals("Tau Product Web Site - Scrum #1", assignable5.getProjectName());

        Assignable assignable10 = assignables.get(9);
        assertEquals(20, assignable10.getId());
        assertEquals("Accessibility tests failing for home page", assignable10.getName());
        assertNull(assignable10.getDescription());
        assertEquals(new Date(1336572000000L), assignable10.getCreateDate());
        assertEquals(new Date(1354548198000L), assignable10.getModifyDate());
        assertEquals("Bug", assignable10.getEntityTypeName());
        assertEquals("Tau Product Web Site - Scrum #1", assignable10.getProjectName());
    }

    @Test
    public void testParseAssignablesEmptyResponse() {
        List<Assignable> assignables = parser.parseAssignables(getEmptyResponse());
        assertTrue(assignables.isEmpty());
    }

    @Test
    public void testParseAssignable() {
        Assignable assignable = parser.parseAssignable(getClass().getResourceAsStream("/Assignables.json"));
        assertEquals(8, assignable.getId());
        assertEquals("Android native application", assignable.getName());
        assertEquals(
            "\tProin a fringilla ante. Duis vitae nisl dolor&#44; id suscipit quam. Duis nec arcu eu lacus vestibulum tincidunt. Vestibulum malesuada odio in metus tempus condimentum. Maecenas nec tristique augue. Integer id elementum neque. Curabitur eget felis lectus&#44; eu tristique risus. Sed massa elit&#44; luctus at condimentum vel&#44; condimentum ac ligula. Etiam imperdiet tellus sed nibh condimentum vehicula. Nullam accumsan nisi ultricies sem viverra in vulputate risus commodo. Phasellus aliquet porta ipsum id pulvinar. Nam vulputate vulputate porttitor. Pellentesque ultrices diam et risus vehicula sit amet porttitor leo luctus. Maecenas ut nulla sit amet dolor pulvinar vehicula a id massa",
            assignable.getDescription());
        assertEquals(new Date(1337176800000L), assignable.getCreateDate());
        assertEquals(new Date(1337176800000L), assignable.getModifyDate());
        assertEquals("Request", assignable.getEntityTypeName());
        assertEquals("Tau Product - Kanban #1", assignable.getProjectName());
    }

    @Test
    public void testParseAssignableEmptyResponse() {
        assertNull(parser.parseAssignable(getEmptyResponse()));
    }

    @Test
    public void testParseComments() {
        List<Comment> comments = parser.parseComments(getClass().getResourceAsStream("/Comments.json"));
        assertEquals(3, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals("I am not a happy camper", comment1.getDescription());
        assertEquals(new Date(1377789436000L), comment1.getCreateDate());
        User owner1 = comment1.getOwner();
        assertEquals(1, owner1.getId());
        assertEquals("Administrator", owner1.getFirstName());
        assertEquals("Administrator", owner1.getLastName());

        Comment comment2 = comments.get(1);
        assertEquals("<p>Original story id&#58;325</p>", comment2.getDescription());
        assertEquals(new Date(1354880233000L), comment2.getCreateDate());
        User owner2 = comment2.getOwner();
        assertEquals(2, owner2.getId());
        assertEquals("FirstName", owner2.getFirstName());
        assertEquals("LastName", owner2.getLastName());

        Comment comment3 = comments.get(2);
        assertEquals("<div>\n\tnew</div>\n", comment3.getDescription());
        assertEquals(new Date(1352796305000L), comment3.getCreateDate());
        User owner3 = comment3.getOwner();
        assertEquals(1, owner3.getId());
        assertEquals("Administrator", owner3.getFirstName());
        assertEquals("Administrator", owner3.getLastName());
    }

    @Test
    public void testParseCommentsEmptyResponse() {
        List<Comment> comments = parser.parseComments(getEmptyResponse());
        assertTrue(comments.isEmpty());
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
