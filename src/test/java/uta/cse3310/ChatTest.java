package uta.cse3310;
import junit.framework.TestCase;

public class ChatTest extends TestCase {

    public void testAddToChat() {
        Chat chat = new Chat();
        chat.addToChat("User1", "Hello");
        chat.addToChat("User2", "Hi there!");

        assertEquals(2, chat.chatLog.size()); // Assuming chatLog is public or has a getter method
        assertEquals("User1", chat.chatLog.get(0).sender);
        assertEquals("Hello", chat.chatLog.get(0).message);
        assertEquals("User2", chat.chatLog.get(1).sender);
        assertEquals("Hi there!", chat.chatLog.get(1).message);
    }

    public void testClearChat() {
        Chat chat = new Chat();
        chat.addToChat("User1", "Hello");
        chat.addToChat("User2", "Hi there!");

        chat.chatLog.clear();
        assertEquals(0, chat.chatLog.size());
    }
}
