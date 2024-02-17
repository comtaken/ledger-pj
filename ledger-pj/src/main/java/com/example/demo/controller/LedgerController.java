package com.example.demo.controller;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.demo.Form.InputSetting;

@Controller
public class LedgerController {
	@GetMapping("/")
	public String index(Model model) throws Exception {
		//inputStream作成
		FileInputStream stream = new FileInputStream(Paths.get("page1.xml").toFile());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(stream);
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("/InputList/Input");
		NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		
		List<InputSetting> inputSettingList = new ArrayList<>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			InputSetting inputSetting = new InputSetting();
			inputSetting.setId(element.getAttribute("id"));
			inputSetting.setType(element.getAttribute("type"));
			inputSetting.setTop(element.getAttribute("top"));
			inputSetting.setLeft(element.getAttribute("left"));
			inputSetting.setWidth(element.getAttribute("width"));
			inputSetting.setHeight(element.getAttribute("height"));
			inputSettingList.add(inputSetting);
		}
		model.addAttribute("inputSettingList", inputSettingList);

		

		
		return "index";
	}
}
