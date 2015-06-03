package com.handle.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.apache.log4j.*;

/**
 * @author Administrator
 * @Desc: load configure file,and supply some interface to access the
 *        parameters.
 *
 */
public class CfgLoader {
	private static final Logger log = Logger.getLogger(CfgLoader.class);
	private Element cfgElement = null;
	private Map<String, String> replaceStrMap = null;

	/**
	 * @Desc: load configure file
	 * @param cfgFile:config
	 *        file name cfgId:config id
	 * @return 0:success; 1:failure
	 */
	public int load(String cfgFile, String cfgId) {
		if (cfgFile == null) {
			cfgFile = "conf/default.xml";
		}
		if (cfgId == null) {
			cfgId = "default";
		}
		cfgElement = null;
		log.debug("cfgFile=" + cfgFile + ",cfgId=" + cfgId);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(cfgFile);
			Element root = doc.getDocumentElement(); // get root element
			NodeList cfgItemList = root.getElementsByTagName("cfgItem");
			for (int i = 0; i < cfgItemList.getLength(); i++) {
				// get one cfgItem
				Element cfgItem = (Element) cfgItemList.item(i);
				String cfgIdStr = cfgItem.getAttribute("id");
				if (cfgId.equals(cfgIdStr)) {
					cfgElement = cfgItem;
					break;
				}
			}

			if (cfgElement == null) {
				log.error("parameter cfgId:" + cfgId + " is NOT in config file:" + cfgFile);
				return 1;
			}
		} catch (Exception e) {
			log.error("", e);
			return 1;
		}

		return 0;
	}

	/**
	 * @Desc: load configure file
	 * @param cfgFile:config
	 *            file name
	 * @return 0:success; 1:failure
	 */
	public int load(String cfgFile) {
		int ret = load(cfgFile, "default");
		return ret;
	}

	/**
	 * @Desc: get parameter value by parameter name
	 * @param paraName
	 * @return null:means no specific value
	 */
	public String getParaValueByName(String paraName) {
		NodeList names = cfgElement.getElementsByTagName(paraName);
		Element e = (Element) names.item(0);
		if (e == null) {
			log.error("parameter:" + paraName + " is NOT in config file.");
			return null;
		}
		Node n = e.getFirstChild();
		if (n == null)
			return null;
		String value = n.getNodeValue();
		value = replaceStr(value);
		return value.trim();
	}

	/**
	 * @Desc: get interface type
	 * @param no
	 * @return FILE: file interface DB: database interface "": not valid
	 *         type,error
	 */
	public String getIfType() {
		String type = cfgElement.getAttribute("type");
		/*
		 * if(!type.equalsIgnoreCase("FILE") && !type.equalsIgnoreCase("DB")){
		 * log.error("interface type"+type+" is invalid."); return ""; }
		 */
		return type;
	}

	public List getOutputList(String listName) {
		List objList = new ArrayList();
		NodeList outputList = cfgElement.getElementsByTagName(listName);
		if (outputList == null) {
			log.error("parameter:" + listName + " is NOT in config file.");
			return null;
		}
		int length = outputList.getLength();
		for (int i = 0; i < length; i++) {
			Node outputNode = outputList.item(i);
			NodeList paraList = outputNode.getChildNodes();
			int paraListLen = paraList.getLength();
			Map nvMap = new HashMap();
			for (int j = 0; j < paraListLen; j++) {
				Node paraNode = paraList.item(j);
				if (paraNode.getNodeType() == Node.ELEMENT_NODE) {
					String name = paraNode.getNodeName();
					if (name.equalsIgnoreCase("sqlList")) {
						NodeList sqlNodeList = paraNode.getChildNodes();
						int sqlNodeListLen = sqlNodeList.getLength();
						List sqlList = new ArrayList();
						for (int n = 0; n < sqlNodeListLen; n++) {
							Node sqlNode = sqlNodeList.item(n);
							if (sqlNode.getNodeType() == Node.ELEMENT_NODE) {
								String sqlNodeName = sqlNode.getNodeName();
								if (sqlNodeName.equalsIgnoreCase("sql")) {
									if (sqlNode.getFirstChild() != null) {
										String sqlNodeValue = sqlNode.getFirstChild().getNodeValue();
										sqlNodeValue = replaceStr(sqlNodeValue);
										sqlList.add(sqlNodeValue);
									}
								}
							}
						}
						nvMap.put("sqlList", sqlList);
					} else {
						if (paraNode.getFirstChild() != null) {
							String value = paraNode.getFirstChild()
									.getNodeValue();
							value = replaceStr(value);
							nvMap.put(name, value);
						}
					}
				}
			}
			objList.add(nvMap);
		}
		if (objList.size() == 0) {
			log.error("parameter:" + listName + " is empty!");
			return null;
		}
		return objList;
	}



	public void setReplaceStrMap(Map replaceStrHash) {
		this.replaceStrMap = replaceStrHash;
	}

	private String replaceStr(String souStr) {
		String tarStr = souStr;
		if (this.replaceStrMap == null)
			return souStr;
		Iterator iter = this.replaceStrMap.entrySet().iterator();
		while (iter.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
			tarStr = tarStr.replace((String) entry.getKey(), (String) entry
					.getValue());
		}
		return tarStr;
	}

	public Element getCfgElement() {
		return cfgElement;
	}

	public static void main(String[] args) throws Exception {
	    File cfgFile = new File("D:/homeWork/school-spring-hibernate.xml");
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(cfgFile);
        Element root = doc.getDocumentElement(); // get root element
        System.out.println(doc.getElementById("sessionFactory").getAttribute("class"));
        System.out.println(root.getNodeName());
    }









}

