package org.gmdev.event.messageservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactoryUtil {
	
	/**
	 * initialize a Map with all the topics, key is the same of topic class name
	 */
	private final static Map<String, String> topicMap;
	static {
		topicMap = new HashMap<>();
		topicMap.put("StockMessage", "STOCK");
		topicMap.put("ForexMessage", "FOREX");
	}
	
	public static String getStockTopic() {
		return topicMap.get("StockMessage");
	}
		
	public static String getForexTopic() {
		return topicMap.get("ForexMessage");
	}
	
	public static List<String> getAllTopics() {
		List<String> topics = new ArrayList<String>(topicMap.values());
		
		return topics;
	}
	
}
