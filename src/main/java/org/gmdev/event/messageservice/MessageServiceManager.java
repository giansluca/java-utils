package org.gmdev.event.messageservice;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageServiceManager {
	
	private final MessageEventManager eventsManager;
	private final ExecutorService executorService;
	private final BlockingQueue<EventMessage> StockBlockingQueue;
	private final BlockingQueue<EventMessage> ForexkBlockingQueue;
	
	public MessageServiceManager() {
		eventsManager = MessageEventManager.getInstance();
		executorService = Executors.newFixedThreadPool(5);
		StockBlockingQueue = new LinkedBlockingDeque<>(100);
		ForexkBlockingQueue = new LinkedBlockingDeque<>(100);
	}
	
	public void start() {
		registerSubscribers();
		startExchangeService();
		startConsumerService_1();
		startConsumerService_2();
	}
	
	/**
	 * The exchange service - scheduled service that creates different Message Object from an API Call simulation
	 */
	private void startExchangeService() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		Runnable exchangeTask = () -> {
			try {
				// get the Json String simulating a API Call
				String jsonFlatMessage = producerService();
				
				//System.out.println(jsonFlatMessage);
				// parse the Json String using Gson
				JsonElement jelement = JsonParser.parseString(jsonFlatMessage);
				JsonObject  jobject = jelement.getAsJsonObject();
				JsonArray messageArray = jobject.getAsJsonArray("messages");
				
				for (JsonElement jsonElement : messageArray) {
					JsonObject jsonMessage = jsonElement.getAsJsonObject();
					String type = jsonMessage.get("type").getAsString();
					String message = jsonMessage.get("message").getAsString();
						
					// i have used the '-STOCK' string for the message type to simulate an external service
					// giving me type coded in a different way that mine - so i need to add some logic at this stage to 
					// separate the messages.
						
					if (type != null && type.equalsIgnoreCase("-STOCK")) {
						// put the Stock type message on the Stock queue 
						StockBlockingQueue.put(new StockMessage(message));
					} else if (type !=null && type.equalsIgnoreCase("-FOREX")) {
						// put the Forex type message on the Forex queue
						ForexkBlockingQueue.put(new ForexMessage(message));
					}
				}
				
				System.out.println("Stock queue size: " + StockBlockingQueue.size());
				System.out.println("Forex queue size: " + ForexkBlockingQueue.size());
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		scheduledExecutorService.scheduleAtFixedRate(exchangeTask, 0, 60, TimeUnit.SECONDS);
	}
	
	/**
	 * The Consumer service for the STOCK messages queue
	 */
	private void startConsumerService_1() {
		Runnable consumerStockTask = () -> {
			while (true) {
				try {
					// get the Stock message from the Stock queue
					EventMessage stockMessage = StockBlockingQueue.take();
					// pass the message to the eventManager to notify all the Stock subscribers
					List<Future<String>> futureList = eventsManager.notifySubscribers(stockMessage);
					
					//wait for all the tasks calling get()
					for (Future<String> future : futureList) {
						String futureResponse = future.get();
						System.out.println(futureResponse);
					}
					System.out.println("Notification process finished for the Stock message: " + stockMessage);
					
					// wait 5 second for next execution
					TimeUnit.SECONDS.sleep(1);
				} catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		executorService.execute(consumerStockTask);
	}
		
	
	/**
	 * The Consumer service for the FOREX messages queue
	 */
	private void startConsumerService_2() {
		Runnable consumerForexTask = () -> {
			while (true) {
				try {
					// get the Stock message from the Stock queue
					EventMessage forexMessage = ForexkBlockingQueue.take();
					// pass the message to the eventManager to notify all the forex subscribers
					List<Future<String>> futureList = eventsManager.notifySubscribers(forexMessage);
					
					for (Future<String> future : futureList) {
						String futureResponse = future.get();
						System.out.println(futureResponse);
					}
					
					System.out.println("Notification process finished for the Forex message: " + forexMessage);
					
					// wait 5 second for next execution
					TimeUnit.SECONDS.sleep(1);
				} catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		executorService.execute(consumerForexTask);
	}
	
	/**
	 * Register all the subscribers (for the interested Topics)
	 */
	private void registerSubscribers() {
		// The data could be pulled From a DB for example
		eventsManager.subscribe(new EmailSubscriber("aaaa@eeeee.com"), FactoryUtil.getStockTopic(), FactoryUtil.getForexTopic());
		eventsManager.subscribe(new RestSubscriber("www.abc/wer/uytr"), FactoryUtil.getStockTopic());
	}
	
	/**
	 * The producer service - creates different Messages in a Json format (it simulate a web API response)
	 */
	private String producerService() {
		// create Json String, representing some messages using Gson (google) library
		JsonObject jobject = new JsonObject();
		JsonArray messages = new JsonArray();
		
		JsonObject stock_1 = new JsonObject();
		stock_1.addProperty("type", "-STOCK");
		stock_1.addProperty("message", "The STOCK market is going UP!");
		messages.add(stock_1);
		
		JsonObject stock_2 = new JsonObject();
		stock_2.addProperty("type", "-STOCK");
		stock_2.addProperty("message", "The STOCK market is going GOOD!");
		messages.add(stock_2);
		
		JsonObject forex_1 = new JsonObject();
		forex_1.addProperty("type", "-FOREX");
		forex_1.addProperty("message", "The FOREX market is going DOWN");
		messages.add(forex_1);
		
		JsonObject forex_2 = new JsonObject();
		forex_2.addProperty("type", "-FOREX");
		forex_2.addProperty("message", "The FOREX market is WAITING");
		messages.add(forex_2);
		
		JsonObject forex_3 = new JsonObject();
		forex_3.addProperty("type", "-FOREX");
		forex_3.addProperty("message", "The FOREX market is going UP");
		messages.add(forex_3);
		
		// add the array to the JsonObject
		jobject.add("messages", messages);
		
		return jobject.toString();
	}
	

}
