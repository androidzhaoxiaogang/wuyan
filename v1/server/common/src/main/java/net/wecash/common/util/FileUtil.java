/**
 * 
 */
package net.wecash.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author franklin.li
 * 
 */
public class FileUtil {
	public static final int ICON = 0;
	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	private FileUtil() {
		
	}
	static {
		getAllFileType(); // 初始化文件类型信息
	}

	public static void writeStream(InputStream is, OutputStream os)
			throws Exception {
		try {
			int size;
			byte[] b = new byte[2048];
			while ((size = is.read(b)) > 0) {
				os.write(b, 0, size);
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getAllFileType() {
		FILE_TYPE_MAP.put(".jpg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put(".png", "89504E47"); // PNG (png)
		FILE_TYPE_MAP.put(".gif", "47494638"); // GIF (gif)
		FILE_TYPE_MAP.put(".tif", "49492A00"); // TIFF (tif)
		FILE_TYPE_MAP.put(".bmp", "424D"); // Windows Bitmap (bmp)
		FILE_TYPE_MAP.put(".html", "68746D6C3E"); // HTML (html)
	}

	public static boolean fileValidationType(byte[] body) {
		boolean state = false;
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < body.length; i++) {
			int v = body[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		String fileType = stringBuilder.toString();
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP
				.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (fileType.toUpperCase().startsWith(fileTypeHexValue)) {
				state = true;
			}

		}
		return state;
	}
	
	public static byte[] toByteArray(InputStream input){
		byte[] bytes = null;
		try {
		    ByteArrayOutputStream output = new ByteArrayOutputStream();
		    byte[] buffer = new byte[4096];
		    int n = 0;
		    while (-1 != (n = input.read(buffer))) {
		        output.write(buffer, 0, n);
		    }
		    bytes = output.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bytes;
	}
}
