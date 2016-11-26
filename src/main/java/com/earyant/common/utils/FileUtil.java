package com.earyant.common.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.earyant.CommonConstants;

public class FileUtil {
	
	public static void copyDirectory(
			String sourceDirName, String destinationDirName) {

			copyDirectory(new File(sourceDirName), new File(destinationDirName));
		}

		public static void copyDirectory(File source, File destination) {
			if (source.exists() && source.isDirectory()) {
				if (!destination.exists()) {
					destination.mkdirs();
				}

				File[] fileArray = source.listFiles();

				for (int i = 0; i < fileArray.length; i++) {
					if (fileArray[i].isDirectory()) {
						copyDirectory(
							fileArray[i],
							new File(destination.getPath() + File.separator
								+ fileArray[i].getName()));
					}
					else {
						copyFile(
							fileArray[i],
							new File(destination.getPath() + File.separator
								+ fileArray[i].getName()));
					}
				}
			}
		}

		public static void copyFile(String source, String destination) {
			copyFile(source, destination, false);
		}

		public static void copyFile(
			String source, String destination, boolean lazy) {

			copyFile(new File(source), new File(destination), lazy);
		}

		public static void copyFile(File source, File destination) {
			copyFile(source, destination, false);
		}

		public static void copyFile(File source, File destination, boolean lazy) {
			if (!source.exists()) {
				return;
			}

			if (lazy) {
				String oldContent = null;

				try {
					oldContent = read(source, "");
				}
				catch (Exception e) {
					return;
				}

				String newContent = null;

				try {
					newContent = read(destination, "");
				}
				catch (Exception e) {
				}

				if (oldContent == null || !oldContent.equals(newContent)) {
					copyFile(source, destination, false);
				}
			}
			else {
				if ((destination.getParentFile() != null) &&
					(!destination.getParentFile().exists())) {

					destination.getParentFile().mkdirs();
				}

				try {
					FileChannel srcChannel =
						new FileInputStream(source).getChannel();
					FileChannel dstChannel =
						new FileOutputStream(destination).getChannel();

					dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

					srcChannel.close();
					dstChannel.close();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		public static boolean delete(String file) {
			return delete(new File(file));
		}

		public static boolean delete(File file) {
			if (file.exists()) {
				return file.delete();
			}
			else {
				return false;
			}
		}

		public static void deltree(String directory) {
			deltree(new File(directory));
		}

		public static void deltree(File directory) {
			if (directory.exists() && directory.isDirectory()) {
				File[] fileArray = directory.listFiles();

				for (int i = 0; i < fileArray.length; i++) {
					if (fileArray[i].isDirectory()) {
						deltree(fileArray[i]);
					}
					else {
						fileArray[i].delete();
					}
				}

				directory.delete();
			}
		}

		public static byte[] getBytes(File file) throws IOException {
			if (file == null || !file.exists()) {
				return null;
			}

			FileInputStream in = new FileInputStream(file);

			byte[] bytes = getBytes(in);

			in.close();

			return bytes;
		}

		public static byte[] getBytes(InputStream in) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			int c = in.read();

			while (c != -1) {
				out.write(c);
				c = in.read();
			}

			out.close();

			return out.toByteArray();
		}

		public static String getShortFileName(String fullFileName) {
			int pos = fullFileName.lastIndexOf("/");

			if (pos == -1) {
				pos = fullFileName.lastIndexOf("\\");
			}

			String shortFileName =
				fullFileName.substring(pos + 1, fullFileName.length());

			return shortFileName;
		}

		public static boolean exists(String fileName) {
			File file = new File(fileName);

			return file.exists();
		}
		
		public static boolean isDir(String fileName) {
			File file = new File(fileName);
			if(file.isDirectory()){
			    //目录
				return true;
			} else {
				return false;
			}
			
		}
		
		public static String getFileName (String dirPath) throws IOException {
			File file = new File(dirPath);
			return file.getName();
		}

		public static String[] listFiles(String dirName) throws IOException {
			return listFiles(new File(dirName));
		}
		
		
		public static String[] listDirs(String fileName) throws IOException {
			return listDirs(new File(fileName));
		}

		public static String[] listDirs(File file) throws IOException {
			List<String> dirs = new ArrayList<String>();

			File[] fileArray = file.listFiles();

			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isDirectory()) {
					dirs.add(fileArray[i].getName());
				}
			}

			return (String[])dirs.toArray(new String[0]);
		}

		public static String[] listFiles(File file) throws IOException {
			List<String> files = new ArrayList<String>();

			File[] fileArray = file.listFiles();

			for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
				if (fileArray[i].isFile()) {
					files.add(fileArray[i].getName());					
				}
			}

			return (String[])files.toArray(new String[0]);
		}

		public static void mkdirs(String pathName) {
			File file = new File(pathName);

			file.mkdirs();
		}
		
		public static boolean MakeDirectory(String FilePath) {
			File mFile = new File(FilePath);
			if ( !mFile.isDirectory())
				mFile.mkdirs();
			return mFile.isDirectory();
		}

		public static boolean move(
			String sourceFileName, String destinationFileName) {

			return move(new File(sourceFileName), new File(destinationFileName));
		}

		public static boolean move(File source, File destination) {
			if (!source.exists()) {
				return false;
			}

			destination.delete();

			return source.renameTo(destination);
		}

		public static String read(String fileName, String rn) throws IOException {
			rn = CommonUtils.nvl(rn, CommonConstants.RN);
			return read(new File(fileName),  rn);
		}

		public static String read(File file, String rn) throws IOException {
			StringBuffer sb = new StringBuffer();
			rn = CommonUtils.nvl(rn, CommonConstants.RN);
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			String line = null;

			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(rn);//
			}

			br.close();

			return sb.toString().trim();
		}

		public static String replaceSeparator(String fileName) {
			return CommonUtils.replace(fileName, '\\', "/");
		}

		public static List toList(Reader reader) {
			List list = new ArrayList();

			try {
				BufferedReader br = new BufferedReader(reader);

				String line = null;

				while ((line = br.readLine()) != null) {
					list.add(line);
				}

				br.close();
			}
			catch (IOException ioe) {
			}

			return list;
		}

		public static List toList(String fileName) {
			try {
				return toList(new FileReader(fileName));
			}
			catch (IOException ioe) {
				return new ArrayList();
			}
		}

		public static Properties toProperties(FileInputStream fis) {
			Properties props = new Properties();

			try {
				props.load(fis);
			}
			catch (IOException ioe) {
			}

			return props;
		}

		public static Properties toProperties(String fileName) {
			try {
				return toProperties(new FileInputStream(fileName));
			}
			catch (IOException ioe) {
				return new Properties();
			}
		}

		public static void write(String fileName, String s) throws IOException {
			write(new File(fileName), s);
		}

		public static void write(String fileName, String s, boolean lazy)
			throws IOException {

			write(new File(fileName), s, lazy);
		}

		public static void write(String pathName, String fileName, String s)
			throws IOException {

			write(new File(pathName, fileName), s);
		}

		public static void write(
				String pathName, String fileName, String s, boolean lazy)
			throws IOException {

			write(new File(pathName, fileName), s, lazy);
		}

		public static void write(File file, String s) throws IOException {
			write(file, s, false);
		}

		public static void write(File file, String s, boolean lazy)
			throws IOException {

			if (file.getParent() != null) {
				mkdirs(file.getParent());
			}

			if (file.exists()) {
				String content = read(file, "");

				if (content.equals(s)) {
					return;
				}
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			bw.flush();
			bw.write(s);

			bw.close();
		}

		public static void write(String fileName, byte[] byteArray)
			throws IOException {

			write(new File(fileName), byteArray);
		}

		public static void write(File file, byte[] byteArray) throws IOException {
			if (file.getParent() != null) {
				mkdirs(file.getParent());
			}

			FileOutputStream fos = new FileOutputStream(file);

			fos.write(byteArray);

			fos.close();
		}
		
		
		public static long getFileSizes(File f) throws Exception{//取得文件大小
	        long s=0;
	        if (f.exists()) {
	            FileInputStream fis = null;
	            fis = new FileInputStream(f);
	           s= fis.available();
	        } else {
	            f.createNewFile();
	            System.out.println("文件不存在");
	        }
	        return s;
	    }
	    // 递归
	    public static long getFileSize(File f)throws Exception//取得文件夹大小
	    {
	        long size = 0;
	        File flist[] = f.listFiles();
	        for (int i = 0; i < flist.length; i++)
	        {
	            if (flist[i].isDirectory())
	            {
	                size = size + getFileSize(flist[i]);
	            } else
	            {
	                size = size + flist[i].length();
	            }
	        }
	        return size;
	    }

	    public static String FormetFileSize(long fileS) {//转换文件大小
	        DecimalFormat df = new DecimalFormat("#.00");
	        String fileSizeString = "";
	        if (fileS < 1024) {
	            fileSizeString = df.format((double) fileS) + "B";
	        } else if (fileS < 1048576) {
	            fileSizeString = df.format((double) fileS / 1024) + "K";
	        } else if (fileS < 1073741824) {
	            fileSizeString = df.format((double) fileS / 1048576) + "M";
	        } else {
	            fileSizeString = df.format((double) fileS / 1073741824) + "G";
	        }
	        return fileSizeString;
	    }
	   
	    public static long getlist(File f){//递归求取目录文件个数
	        long size = 0;
	        File flist[] = f.listFiles();
	        size=flist.length;
	        for (int i = 0; i < flist.length; i++) {
	            if (flist[i].isDirectory()) {
	                size = size + getlist(flist[i]);
	                size--;
	            }
	        }
	        return size;

	 
	   }

}
