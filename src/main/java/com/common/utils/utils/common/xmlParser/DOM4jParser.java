package com.common.utils.utils.common.xmlParser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


/**
 *   四、【对比】
 *		1、【DOM】
 *		DOM是基于树的结构，通常需要加载整文档和构造DOM树，然后才能开始工作。
 *		优点：
 *		    a、由于整棵树在内存中，因此可以对xml文档随机访问
 *		    b、可以对xml文档进行修改操作
 *		    c、较sax，dom使用也更简单。
 *		缺点：
 *		    a、整个文档必须一次性解析完
 *		    a、由于整个文档都需要载入内存，对于大文档成本高
 *		2、【SAX】
 *		SAX类似流媒体，它基于事件驱动的，因此无需将整个文档载入内存，使用者只需要监听自己感兴趣的事件即可。
 *		优点：
 *		    a、无需将整个xml文档载入内存，因此消耗内存少
 *		    b、可以注册多个ContentHandler
 *		缺点：
 *		    a、不能随机的访问xml中的节点
 *		    b、不能修改文档
 *		3、【JDOM】
 *		JDOM是纯Java的处理XML的API，其API中大量使用Collections类，
 *		优点：
 *		    a、DOM方式的优点
 *		    b、具有SAX的Java规则
 *		缺点
 *		    a、DOM方式的缺点
 *		4、【DOM4J】
 *		这4中xml解析方式中，最优秀的一个，集易用和性能于一身。
*/





/**
 * dom4j是目前在xml解析方面是最优秀的(Hibernate、Sun的JAXM也都使用dom4j来解析XML)，
 * 它合并了许多超出基本 XML 文档表示的功能，包括集成的 XPath 支持、XML Schema 支持以及用于大文档或流化文档的基于事件的处理
 * @ClassName:DOM4jParser
 * @Description:
 * @date:2015年11月25日 下午5:32:37
 * @author:haitao.liu
 * @version 1.0.0
 *
 */
public class DOM4jParser {
//	 public static void main(String[] args) {
//	        read1();
//	        //read2();
//	        //write();
//	    }

	    public static void read1() {
	        try {
	            SAXReader reader = new SAXReader();
	            InputStream in = DOM4jParser.class.getClassLoader().getResourceAsStream("test.xml");
	            Document doc = reader.read(in);
	            Element root = doc.getRootElement();
	            readNode(root, "");
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void readNode(Element root, String prefix) {
	        if (root == null) return;
	        // 获取属性
	        List<Attribute> attrs = root.attributes();
	        if (attrs != null && attrs.size() > 0) {
	            System.err.print(prefix);
	            for (Attribute attr : attrs) {
	                System.err.print(attr.getValue() + " ");
	            }
	            System.err.println();
	        }
	        // 获取他的子节点
	        List<Element> childNodes = root.elements();
	        prefix += "\t";
	        for (Element e : childNodes) {
	            readNode(e, prefix);
	        }
	    }

	    public static void read2() {
	        try {
	            SAXReader reader = new SAXReader();
	            InputStream in = DOM4jParser.class.getClassLoader().getResourceAsStream("test.xml");
	            Document doc = reader.read(in);
	            doc.accept(new MyVistor());
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void write() {
	        try {
	            // 创建一个xml文档
	            Document doc = DocumentHelper.createDocument();
	            Element university = doc.addElement("university");
	            university.addAttribute("name", "tsu");
	            // 注释
	            university.addComment("这个是根节点");
	            Element college = university.addElement("college");
	            college.addAttribute("name", "cccccc");
	            college.setText("text");

	            File file = new File("src/dom4j-modify.xml");
	            if (file.exists()) {
	                file.delete();
	            }
	            file.createNewFile();
	            XMLWriter out = new XMLWriter(new FileWriter(file));
	            out.write(doc);
	            out.flush();
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	class MyVistor extends VisitorSupport {
	    @Override
        public void visit(Attribute node) {
	        System.out.println("Attibute: " + node.getName() + "="
	                + node.getValue());
	    }

	    @Override
        public void visit(Element node) {
	        if (node.isTextOnly()) {
	            System.out.println("Element: " + node.getName() + "="
	                    + node.getText());
	        } else {
	            System.out.println(node.getName());
	        }
	    }

	    @Override
	    public void visit(ProcessingInstruction node) {
	        System.out.println("PI:" + node.getTarget() + " " + node.getText());
	    }
}
