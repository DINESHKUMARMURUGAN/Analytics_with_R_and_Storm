package com.learningstorm.storm_example;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.*;
import backtype.storm.topology.base.*;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


public class LearningBolt2 extends BaseBasicBolt{

	  private static final long serialVersionUID = 1L;

	  public void execute(Tuple input, BasicOutputCollector collector) {
	    // fetched the field "site" from input tuple.
	   // String test = input.getStringByField("site1");
	    String test = input.getString(0);
	    // print the value of field "site" on console.
	    System.out.println("Name of input site1 is : " + test);
	    
	    System.out.println("************************" + input.getFields());
	    
	  }

	  public void declareOutputFields(OutputFieldsDeclarer declarer) {

	  }
	}