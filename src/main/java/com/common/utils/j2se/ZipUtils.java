package com.common.utils.j2se;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * 文件解压缩,使用org.apache.tools.ant下的类 不推荐使用
 * @see org.xdemo.superutil.thirdparty.ApacheZip
 * @author <a href="http://www.xdemo.org/">http://www.xdemo.org/</a>
 * 252878950@qq.com
 */
@Deprecated
public class ZipUtils {

	/**
	 * 压缩文件夹
	 *
	 * @param zipFile
	 *            目标文件
	 * @param src
	 *            要压缩的目录
	 * @throws FileNotFoundException
	 */
	public static void zipDir(String zipFile, String src) throws FileNotFoundException {
		File srcdir = new File(src);
		if (!srcdir.exists()) {
			throw new FileNotFoundException(src + "不存在");
		}
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(new File(zipFile));
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		zip.addFileset(fileSet);
		zip.execute();
	}

	/**
	 * 压缩多个指定的文件到zip中
	 *
	 * @param zipFile
	 * @param files
	 * @throws IOException
	 */
	public static void zip(String zipFile, String... files) throws IOException {
		File tempDir=new File(new File(zipFile).getParent()+File.separator+UUID.randomUUID());
		tempDir.mkdirs();
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(new File(zipFile));
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		for (String file : files) {
			File _file = new File(file);
			if (!_file.exists()) {
				throw new IOException(file + "不存在");
			}
			//将所有文件放到一个临时目录
			copyFile(file, tempDir.getAbsolutePath()+File.separator+_file.getName(), 1024);
		}
		fileSet.setDir(tempDir);
		zip.addFileset(fileSet);
		zip.execute();
		//删除临时文件
		deleteByDir(tempDir);
	}

	/**
	 * 压缩多个指定的文件到zip中
	 *
	 * @param zipFile
	 * @param files
	 * @throws IOException
	 */
	public static void zip(String zipFile, File... files) throws IOException {
		File tempDir=new File(new File(zipFile).getParent()+File.separator+UUID.randomUUID());
		tempDir.mkdirs();
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(new File(zipFile));
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		for (File file : files) {
			if (!file.exists()) {
				throw new IOException(file.getAbsolutePath() + "不存在");
			}
			//将所有文件放到一个临时目录
			copyFile(file.getAbsolutePath(), tempDir.getAbsolutePath()+File.separator+file.getName(), 1024);
		}
		fileSet.setDir(tempDir);
		zip.addFileset(fileSet);
		zip.execute();
		deleteByDir(tempDir);
	}

	/**
	 * 解压缩
	 * @param zipFile zip文件
	 * @param dest 解压到的目录
	 * @throws FileNotFoundException
	 */
	public static void unZip(String zipFile, String dest) throws FileNotFoundException {
		File zip = new File(zipFile);
		if (!zip.exists())
			throw new FileNotFoundException(zipFile + "不存在");
		Project proj = new Project();
		Expand expand = new Expand();
		expand.setProject(proj);
		expand.setTaskType("unzip");
		expand.setTaskName("unzip");
		expand.setSrc(zip);
		expand.setDest(new File(dest));
		expand.setEncoding("UTF-8");
		expand.execute();
	}

	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     */
	public static boolean deleteByDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteByDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

	private  static void copyFile(String src, String dest, int bufferSize)throws IOException {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(dest);
		byte[] buffer = new byte[bufferSize];
		int length;

		while ((length = fis.read(buffer)) != -1) {
			fos.write(buffer, 0, length);
		}
		fis.close();
		fos.close();
	}

//	public static void main(String[] args) throws IOException {
//		zip("D:\\xx\\ddd\\d2.zip", new File[]{new File("D:\\x\\d\\2.zip"),new File("D:\\x\\z\\1.txt")});
//	}

}
