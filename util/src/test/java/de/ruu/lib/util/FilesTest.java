package de.ruu.lib.util;

import static java.io.File.separatorChar;
import static javax.tools.JavaFileObject.Kind.SOURCE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FilesTest
{
	@Test public void toSourceFilePath()
	{
		Class<?> class_ = getClass();
		assertEquals
		(
				class_
						.getPackage()
						.getName()
						.replace('.', separatorChar) +
								separatorChar + class_.getSimpleName() + SOURCE.extension,
				Files.toSourceFilePath(getClass()),
				"unexpected source file path for " + class_.getName()
		);
	}

	@Test public void toPackagePathClass()
	{
		Class<?> class_ = getClass();
		String directoryname = class_.getPackage().getName().replace('.', separatorChar);
		assertEquals
		(
				directoryname,
				Files.toDirectoryName(class_),
				"unexpected package path for " + class_.getName()
		);
	}

	@Test public void toDirectoryNamePackage()
	{
		Package package_ = getClass().getPackage();
		String directoryname = package_.getName().replace('.', separatorChar);
		assertEquals
		(
				directoryname,
				Files.toDirectoryName(package_),
				"unexpected package path for " + package_.getName()
		);
	}

	@Test public void toDirectoryName()
	{
		String packagename = getClass().getPackage().getName();
		String directoryname = packagename.replace('.', separatorChar);
		assertEquals
		(
				directoryname,
				Files.toDirectoryName(packagename),
				"unexpected directory name for " + packagename
		);
	}

	@Test public void toDirectoryName_a_b_c()
	{
		String packagename = "a.b.c";
		String directoryname = "a" + separatorChar + "b" + separatorChar + "c"; // a\b\c on windows, a/b/c on unix
		assertEquals
		(
				directoryname,
				Files.toDirectoryName(packagename),
				"unexpected directory name for " + packagename
		);
	}
}