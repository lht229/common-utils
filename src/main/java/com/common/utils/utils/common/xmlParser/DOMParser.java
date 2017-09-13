package com.common.utils.utils.common.xmlParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * 由W3C提供的接口，它将整个XML文档读入内存，构建一个DOM树来对各个节点(Node)进行操作。
 * @ClassName:DOMParser
 * @Description:
 * @date:2015年11月25日 下午1:12:23
 * @author:haitao.liu
 * @version 1.0.0
 *在应用程序中，基于DOM的XML分析器将一个XML文档转换成一个对象模型的集合（通常称DOM树），
 *应用程序正是通过对这个对象模型的操作，来实现对XML文档数据的操作。通过DOM接口，
 *应用程序可以在任何时候访问XML文档中的任何一部分数据，因此，这种利用DOM接口的机制也被称作随机访问机制。
 *
 *
 *当文档比较大或者结构比较复杂时，对内存的需求就比较高。而且，对于结构复杂的树的遍历也是一项耗时的操作。
 *所以，DOM分析器对机器性能的要求比较高，实现效率不十分理想。不过，
 *由于DOM分析器所采用的树结构的思想与XML文档的结构相吻合，同时鉴于随机访问所带来的方便，
 *因此，DOM分析器还是有很广泛的使用价值的。
 *
 */
public class DOMParser {
//	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
////        read();
//        write();
//
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        InputStream in = DOMParser.class.getClassLoader().getResourceAsStream("test.xml");
//
//        Document doc = db.parse(in);
//        //获得根元素结点
//        Element root = doc.getDocumentElement();
//
//        parseElement(root);
//
//    }

    public static void read() {

    	 // step 1: 获得dom解析器工厂（工作的作用是用于创建具体的解析器）
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
        	// step 2:获得具体的dom解析器
            DocumentBuilder builder = dbf.newDocumentBuilder();
            InputStream in = DOMParser.class.getClassLoader().getResourceAsStream("test.xml");
            // step3: 解析一个xml文档，获得Document对象（根结点）
            Document doc = builder.parse(in);
            // root <university>
            Element root = doc.getDocumentElement();
            if (root == null) return;
            System.err.println(root.getAttribute("name"));
            // all college node
            NodeList collegeNodes = root.getChildNodes();
            if (collegeNodes == null) return;
            for(int i = 0; i < collegeNodes.getLength(); i++) {
                Node college = collegeNodes.item(i);
                if (college != null && college.getNodeType() == Node.ELEMENT_NODE) {
                    System.err.println("\t" + college.getAttributes().getNamedItem("name").getNodeValue());
                    // all class node
                    NodeList classNodes = college.getChildNodes();
                    if (classNodes == null) continue;
                    for (int j = 0; j < classNodes.getLength(); j++) {
                        Node clazz = classNodes.item(j);
                        if (clazz != null && clazz.getNodeType() == Node.ELEMENT_NODE) {
                            System.err.println("\t\t" + clazz.getAttributes().getNamedItem("name").getNodeValue());
                            // all student node
                            NodeList studentNodes = clazz.getChildNodes();
                            if (studentNodes == null) continue;
                            for (int k = 0; k < studentNodes.getLength(); k++) {
                                Node student = studentNodes.item(k);
                                if (student != null && student.getNodeType() == Node.ELEMENT_NODE) {
                                    System.err.print("\t\t\t" + student.getAttributes().getNamedItem("name").getNodeValue());
                                    System.err.print(" " + student.getAttributes().getNamedItem("sex").getNodeValue());
                                    System.err.println(" " + student.getAttributes().getNamedItem("age").getNodeValue());
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void write() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            InputStream in = DOMParser.class.getClassLoader().getResourceAsStream("test.xml");
            Document doc = builder.parse(in);
            // root <university>
            Element root = doc.getDocumentElement();
            if (root == null) return;
            // 修改属性
            root.setAttribute("name", "tsu");
            NodeList collegeNodes = root.getChildNodes();
            if (collegeNodes != null) {
                for (int i = 0; i <collegeNodes.getLength() - 1; i++) {
                    // 删除节点
                    Node college = collegeNodes.item(i);
                    if (college.getNodeType() == Node.ELEMENT_NODE) {
                        String collegeName = college.getAttributes().getNamedItem("name").getNodeValue();
                        if ("c1".equals(collegeName) || "c2".equals(collegeName)) {
                            root.removeChild(college);
                        } else if ("c3".equals(collegeName)) {
                            Element newChild = doc.createElement("class");
                            newChild.setAttribute("name", "c4");
                            college.appendChild(newChild);
                        }
                    }
                }
            }
            // 新增节点
            Element addCollege = doc.createElement("college");
            addCollege.setAttribute("name", "c5");
            root.appendChild(addCollege);
            Text text = doc.createTextNode("text");
            addCollege.appendChild(text);

            // 将修改后的文档保存到文件
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            File file = new File("src/dom-modify.xml");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            StreamResult xmlResult = new StreamResult(out);
            transFormer.transform(domSource, xmlResult);
            System.out.println(file.getAbsolutePath());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


	/**
	 * 使用递归解析给定的任意一个xml文档并且将其内容输出到命令行上
	 * parseElement
	 * @param element
	 *void
	 * @exception
	 * @since  1.0.0
	 * @author haitao.liu
	 * @date 2015年11月25日下午1:28:43
	 */
    private static void parseElement(Element element)
    {
        String tagName = element.getNodeName();

        NodeList children = element.getChildNodes();

        System.out.print("<" + tagName);

        //element元素的所有属性所构成的NamedNodeMap对象，需要对其进行判断
        NamedNodeMap map = element.getAttributes();

        //如果该元素存在属性
        if(null != map)
        {
            for(int i = 0; i < map.getLength(); i++)
            {
                //获得该元素的每一个属性
                Attr attr = (Attr)map.item(i);

                String attrName = attr.getName();
                String attrValue = attr.getValue();

                System.out.print(" " + attrName + "=\"" + attrValue + "\"");
            }
        }

        System.out.print(">");

        for(int i = 0; i < children.getLength(); i++)
        {
            Node node = children.item(i);
            //获得结点的类型
            short nodeType = node.getNodeType();

            if(nodeType == Node.ELEMENT_NODE)
            {
                //是元素，继续递归
                parseElement((Element)node);
            }
            else if(nodeType == Node.TEXT_NODE)
            {
                //递归出口
                System.out.print(node.getNodeValue());
            }
            else if(nodeType == Node.COMMENT_NODE)
            {
                System.out.print("<!--");

                Comment comment = (Comment)node;

                //注释内容
                String data = comment.getData();

                System.out.print(data);

                System.out.print("-->");
            }
        }

        System.out.print("</" + tagName + ">");
    }
}
