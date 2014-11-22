package jk.kamoru.util;

//import static org.junit.Assert.*;

import java.io.File;

import jk.kamoru.KamoruException;

import org.junit.Test;

public class FileUtilsTest {

	@Test(expected=KamoruException.class)
	public void testValidateFile() {
		FileUtils.validateFile(new File("/aaa/xxx/aaa/"), "not a file");
	}

}
