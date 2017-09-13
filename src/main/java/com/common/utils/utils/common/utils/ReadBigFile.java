package com.common.utils.utils.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读取大文件的几种方式
 * @ClassName:ReadBigFile
 * @Description:
 * @date:2015年8月28日 下午5:50:55
 * @author:haitao.liu
 * @version 1.0.0
 http://www.dianping.com/shop/22701195?_fb_=algo_version%3D1002%26shop_id%3D20932661%26rmp%3DfFEcI0J9bhUNUNklmT1ILk8N7UpqMV3qSg%26adshop_id%3D22701195%26category_id%3D20039%26request_Id%3D14984e13-8294-489f-8875-0ac226fddde0%26bu%3D2%26shopType%3D30%26page_city_id%3D1%26bg%3D9%26ad%3D20032111%26slot%3D4%26display_id%3D10&adidx=2
 *http://www.importnew.com/14512.html
 */
public class ReadBigFile {

	public static String fff = "C:\\mq\\read\\from.xml";

//	public static void main(String[] args) {
//
//	}


	public static void useRandomAccessFile () throws FileNotFoundException, IOException {
		final int BUFFER_SIZE = 0x300000;// 缓冲区大小为3M

		  File f = new File(fff);

		  /**
		   *
		   * map(FileChannel.MapMode mode,long position, long size)
		   *
		   * mode - 根据是按只读、读取/写入或专用（写入时拷贝）来映射文件，分别为 FileChannel.MapMode 类中所定义的
		   * READ_ONLY、READ_WRITE 或 PRIVATE 之一
		   *
		   * position - 文件中的位置，映射区域从此位置开始；必须为非负数
		   *
		   * size - 要映射的区域大小；必须为非负数且不大于 Integer.MAX_VALUE
		   *
		   * 所以若想读取文件后半部分内容，如例子所写；若想读取文本后1/8内容，需要这样写map(FileChannel.MapMode.READ_ONLY,
		   * f.length()*7/8,f.length()/8)
		   *
		   * 想读取文件所有内容，需要这样写map(FileChannel.MapMode.READ_ONLY, 0,f.length())
		   *
		   */

		  MappedByteBuffer inputBuffer = new RandomAccessFile(f, "r")
		    .getChannel().map(FileChannel.MapMode.READ_ONLY,
		      f.length() / 2, f.length() / 2);

		  byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容

		  long start = System.currentTimeMillis();

		  for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {

		   if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {

		    for (int i = 0; i < BUFFER_SIZE; i++)

		     dst[i] = inputBuffer.get(offset + i);

		   } else {

		    for (int i = 0; i < inputBuffer.capacity() - offset; i++)

		     dst[i] = inputBuffer.get(offset + i);

		   }

		   int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE
		     : inputBuffer.capacity() % BUFFER_SIZE;

		   System.out.println(new String(dst, 0, length));// new
		   // String(dst,0,length)这样可以取出缓存保存的字符串，可以对其进行操作

		  }

		  long end = System.currentTimeMillis();

		  System.out.println("读取文件文件一半内容花费：" + (end - start) + "毫秒");

	}


	public static void useByteBuffer() throws IOException {
		 int bufSize = 1024;
		  byte[] bs = new byte[bufSize];
		  ByteBuffer byteBuf = ByteBuffer.allocate(1024);
		  @SuppressWarnings("resource")
		FileChannel channel = new RandomAccessFile(fff, "r").getChannel();
		  while (channel.read(byteBuf) != -1) {
		   int size = byteBuf.position();
		   byteBuf.rewind();
		   byteBuf.get(bs); // 把文件当字符串处理，直接打印做为一个例子。
		   System.out.print(new String(bs, 0, size));
		   byteBuf.clear();
		  }
	}


	public static void useBufferReader() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fff));
		  String line = null;
		  while ((line = br.readLine()) != null) {
		   System.out.println(line);
		  }
	}

	/**
	 * java 读取大容量文件，内存溢出？怎么按几行读取，读取多次
	 * readNLine
	 * @throws IOException
	 *void
	 * @exception
	 * @since  1.0.0
	 * @author haitao.liu
	 * @date 2015年8月28日下午5:47:37
	 */

	public static void readNLine() throws IOException {
		String path = "你要读的文件的路径";
		RandomAccessFile br=new RandomAccessFile(path,"rw");//这里rw看你了。要是之都就只写r
		String str = null, app = null;
		int i=0;
		while ((str = br.readLine()) != null) {
			i++;
			app=app+str;
			if(i>=100){//假设读取100行
				i=0;
//				这里你先对这100行操作，然后继续读
				app=null;
			}
		}
		br.close();
	}
	/**
	 *  当逐行读写大于2G的文本文件时推荐使用以下代码
	 * largeFileIO
	 * @param inputFile
	 * @param outputFile
	 *void
	 * @exception
	 * @since  1.0.0
	 * @author haitao.liu
	 * @date 2015年8月28日下午5:48:46
	 */
	public static  void largeFileIO(String inputFile, String outputFile) {
	        try {
	            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
	            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);//10M缓存
	            FileWriter fw = new FileWriter(outputFile);
	            while (in.ready()) {
	                String line = in.readLine();
	                fw.append(line + " ");
	            }
	            String line = "";
	            while((line = in.readLine()) != null){
	            	System.err.println(line);
	            	}
	            in.close();
	            fw.flush();
	            fw.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	}
}
