package cn.org.rapid_framework.generator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 将xml解析成NodeData,NodeData主要是使用Map及List来装attribute
 * 
 * <pre>
 *        String nodeName;
 *        Map attributes = new HashMap();
 *        List<NodeData> childs = new ArrayList();
 * </pre>
 * @author badqiu
 */
public class XMLHelper {

	public static Document getLoadingDoc(String file) throws FileNotFoundException, SAXException, IOException{
		return getLoadingDoc(new FileInputStream(file));
	}
	
    static Document getLoadingDoc(InputStream in) throws SAXException,IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setValidating(false);
        dbf.setCoalescing(true);
        dbf.setIgnoringComments(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(in);
            return db.parse(is);
        } catch (ParserConfigurationException x) {
            throw new Error(x);
        }
    }
    
    private NodeData treeWalk(Element elm) {
        NodeData nodeData = new NodeData();
        nodeData.attributes = attrbiuteToMap(elm.getAttributes());
        nodeData.nodeName = elm.getNodeName();
        nodeData.childs = new ArrayList<NodeData>();
        NodeList list = elm.getChildNodes();
        for(int i = 0; i < list.getLength() ; i++) {
            Node node = list.item(i);
            nodeData.nodeValue = node.getNodeValue();
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                nodeData.childs.add(treeWalk((Element)node));
            }else {
            }
        }
        return nodeData;
    }
    
    private static Map<String,String> attrbiuteToMap(NamedNodeMap attributes) {
        if(attributes == null) return new LinkedHashMap<String,String>();
        Map<String,String> result = new LinkedHashMap<String,String>();
        for(int i = 0; i < attributes.getLength(); i++) {
            result.put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
        }
        return result;
    }
    
    public NodeData parseXML(InputStream in) throws SAXException, IOException {
        Document doc = getLoadingDoc(in);
        return new XMLHelper().treeWalk(doc.getDocumentElement());
    }

    public NodeData parseXML(File file) throws SAXException, IOException {
        FileInputStream in = new FileInputStream(file);
		try {return parseXML(in);}finally{in.close();}
    }
    
    public static class NodeData {
        public String nodeName;
        public String nodeValue;
        public Map<String,String> attributes = new HashMap<String,String>();
        public List<NodeData> childs = new ArrayList<NodeData>();
        
        public String toString() {
            return "nodeName="+nodeName+",attributes="+attributes+" nodeValue="+nodeValue+" child:\n"+childs;
        }
        public Map getElementMap(String nodeNameKey) {
            Map map = new HashMap();
            map.putAll(attributes);
            map.put(nodeNameKey, nodeName);
            return map;
        }
    }
    
    public static String getXMLEncoding(InputStream inputStream) throws UnsupportedEncodingException, IOException {
    	return getXMLEncoding(IOHelper.toString("UTF-8", inputStream));
    }
    
    public static String getXMLEncoding(String s) {
    	if(s == null) return null;
    	Pattern p = Pattern.compile("<\\?xml.*encoding=[\"'](.*)[\"']\\?>");
    	Matcher m = p.matcher(s);
    	if(m.find()) {
    		return m.group(1);
    	}
    	return null;
    }

    public static String removeXmlns(File file) throws IOException {
        InputStream forEncodingInput = new FileInputStream(file);
        String encoding = XMLHelper.getXMLEncoding(forEncodingInput);
        forEncodingInput.close();
        
        InputStream input = new FileInputStream(file);
        String xml = IOHelper.toString(encoding,input);
        xml = XMLHelper.removeXmlns(xml);
        input.close();
        return xml;
    }
    
    //只移除default namesapce
    public static String removeXmlns(String s) {
    	if(s == null) return null;
    	s = s.replaceAll("(?s)xmlns=['\"].*?['\"]", "");
//    	s = s.replaceAll("(?s)xmlns:?\\w*=['\"].*?['\"]", "");
    	s = s.replaceAll("(?s)\\w*:schemaLocation=['\"].*?['\"]", "");
    	return s;
    }
    
    /**
     * 解析attributes为hashMap
     * @param attributes 格式： name='badqiu' sex='F'
     * @return
     */
    public static Map<String, String> parse2Attributes(String attributes) {
        Map result = new HashMap();
        Pattern p = Pattern.compile("(\\w+?)=['\"](.*?)['\"]");
        Matcher m = p.matcher(attributes);
        while(m.find()) {
            result.put(m.group(1), m.group(2));
        }
        return result;
    }
    
    public static void main(String[] args) throws FileNotFoundException, SAXException, IOException {
        String file = "D:/dev/workspaces/alipay/ali-generator/generator/src/table_test.xml";
        NodeData nd = new XMLHelper().parseXML(new FileInputStream(new File(file)));
        
        Map table = nd.attributes;
        List columns = nd.childs;
        System.out.println(table);
        System.out.println(columns);
//        System.out.println(nd);
    }

}
