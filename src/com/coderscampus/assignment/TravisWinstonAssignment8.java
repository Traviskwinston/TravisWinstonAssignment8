package com.coderscampus.assignment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TravisWinstonAssignment8 {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Assignment8 a8 = new Assignment8();
		
		//Add All these lists of 1000 items into this master list
		List<List<Integer>> masterList = new ArrayList<List<Integer>>();
		
		//Use this frequencyMap to count each number. <Number, Frequency of that Number>
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        
        //pool creates a pool of threads that dynamically adjusts size based on workload.
		ExecutorService pool = Executors.newCachedThreadPool();
		
		//For my Understanding: This is a list of CompletableFutures of type: List of Integers.
		List<CompletableFuture<List<Integer>>> tasks = new ArrayList<>();
		
		//This is the Asynchronous Part.
		for (int i=0; i<1000; i++) {
			//Task 
			CompletableFuture<List<Integer>> task = 
					//a8:getNumbers is the Task to be executed Asynchronously, pool is an executor
					CompletableFuture.supplyAsync(a8::getNumbers, pool);
			//Add these CompletableFutures to the List of CompleteableFutures
			tasks.add(task);
		}
		
		//Wait for all tasks to complete
		 CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
	        allTasks.get();
		
		 // Collect results into masterList
	       //for (TYPEofDataInTheList newVar : ListName)
        for (CompletableFuture<List<Integer>> task : tasks) {
        	//.get will pull the List<Integer> part from the CompltableFuture in the list
            masterList.add(task.get());
        }
		
	    //Count the frequency of each unique number
        //for (TYPEofDataInTheList newVar : ListName)
        for (List<Integer> listOfArrays : masterList) {
        	for (Integer numbersInArray : listOfArrays) {
        		frequencyMap.put(numbersInArray, frequencyMap.getOrDefault(numbersInArray, 0) + 1);
        	}
        }
        
        // Display the frequency of each unique number
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
        	System.out.println(entry.getKey() + "=" + entry.getValue());
        }
	 }
}

