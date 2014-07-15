package me.iarekylew00t.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtils {
	public static final byte[] BUFFER = new byte[8192];

	public static final File zipFile(File src, File dest) throws IOException {
		ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(dest));
		ZipEntry entry = new ZipEntry(src.getName());
		zip.putNextEntry(entry);
		
		FileInputStream in = new FileInputStream(src);
		int len;
		while ((len = in.read(BUFFER)) > 0) {
			zip.write(BUFFER, 0, len);
		}
		
		in.close();
		zip.closeEntry();
		zip.close();
		return dest;
	}
}
