/**
 * Description: This program is to test the TextBuddy with a number of different test cases
 * Program is done by
 * Name: Lim Shu Na
 * Matriculation Number: A0112502A
 * Tutorial Group: F13
 */

import static org.junit.Assert.*;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author SHUNA
 *
 */

public class TextBuddyTest {

	@Before
	public void oneTimeSetUp(){
		TextBuddy.createOrSaveFile(new String[] {"testfile.txt"});
	}
	
	@Test
	public void testAddNormal() {
		String[] cmd = {"add", "mickey mouse in the house"};
		String expected = "added to testfile.txt: \"mickey mouse in the house\"";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", cmd));
	}
	
	@Test
	public void testAddIsEmpty() {
		String[] cmd = {"add" };
		String expected = "There is nothing to add";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", cmd));
	}
	
	@Test
	public void testDeleteNormal() {
		String[] commandToAdd1 = {"add", "little brown fox"};
		TextBuddy.executeCommand("testfile.txt", commandToAdd1);
		String[] commandToAdd2 = {"add", "jumped over the moon"};
		TextBuddy.executeCommand("testfile.txt", commandToAdd2);
		String[] commandToAdd3 = {"add", "mickey mouse in the house"};
		TextBuddy.executeCommand("testfile.txt", commandToAdd3);
		
		String[] commandToDelete = {"delete", "1"};
		String expected = "deleted from testfile.txt: \"little brown fox\"";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", commandToDelete));
	}
	
	@Test
	public void testDeleteIsEmpty() {
		String[] commandToAdd1 = { "add", "little brown fox" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd1);
		String[] commandToAdd2 = { "add", "jumped over the moon" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd2);
		String[] commandToAdd3 = {"add", "mickey mouse in the house"};
		TextBuddy.executeCommand("testfile.txt", commandToAdd3);

		String[] commandToDelete = { "delete" };
		String expected = "Arguments invalid";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", commandToDelete));
	}
	
	@Test
	public void testDeleteOutOfBound() {
		String[] commandToAdd1 = { "add", "little brown fox" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd1);
		String[] commandToAdd2 = { "add", "jumped over the moon" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd2);
		String[] commandToAdd3 = {"add", "mickey mouse in the house"};
		TextBuddy.executeCommand("testfile.txt", commandToAdd3);

		String[] command = { "delete", "5" };
		String expected = "No file to be deleted";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", command));
	}
	
	@Test
	public void testClearNormal() {
		String[] commandToAdd1 = { "add", "little brown fox" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd1);
		String[] commandToAdd2 = { "add", "jumped over the moon" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd2);
		String[] commandToAdd3 = {"add", "mickey mouse in the house"};
		TextBuddy.executeCommand("testfile.txt", commandToAdd3);
		
		String[] cmd = { "clear" };
		String expected = "all content cleared from testfile.txt";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", cmd));
	}
	
	@Test
	public void testClearIsEmpty() {
		String[] cmd = { "clear" };
		String expected = "No contents to be clear from the file";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", cmd));
	}
	
	@Test
	public void testSearchNormal() {
		String[] commandToAdd1 = { "add", "little brown fox" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd1);
		String[] commandToAdd2 = { "add", "jumped over the moon" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd2);
		String[] commandToAdd3 = { "add", "mickey mouse in the house" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd3);
		
		String[] cmd = { "search", "mouse" };
		String expected = "3.mickey mouse in the house";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", cmd));
	}
	
	@Test
	public void testSearchIsEmpty() {
		String[] commandToAdd1 = { "add", "little brown fox" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd1);
		String[] commandToAdd2 = { "add", "jumped over the moon" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd2);
		String[] commandToAdd3 = { "add", "mickey mouse in the house" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd3);
		
		String[] command = { "search" };
		String expected = "Invalid search arguments";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", command));
	}
	
	@Test
	public void testSortNormal() {
		String[] commandToAdd1 = { "add", "little brown fox" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd1);
		String[] commandToAdd2 = { "add", "jumped over the moon" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd2);
		String[] commandToAdd3 = { "add", "mickey mouse in the house" };
		TextBuddy.executeCommand("testfile.txt", commandToAdd3);
		
		String[] command = { "sort" };
		String expected = "File sorted";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", command));
	}

	@Test
	public void testSortIsEmpty() {
		String[] command = { "sort" };
		String expected = "Nothing in the file to sort";
		assertEquals(expected, TextBuddy.executeCommand("testfile.txt", command));
	}
	
	@After
	public void tearDown() {
		TextBuddy.clearList();
		File file = new File("testfile.txt");
		file.delete();
	}
}
