package com.learningstorm.storm_example;

import java.util.Map;


import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.*;
import backtype.storm.topology.base.*;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


public class LearningStormBolt extends BaseBasicBolt{

	  private static final long serialVersionUID = 1L;

	  public void execute(Tuple input, BasicOutputCollector collector) {
	    // fetched the field "site" from input tuple.
	    String test = input.getStringByField("site");
	    // print the value of field "site" on console.
	    System.out.println("Name of input site is : " + test);
	    //collector.emit("Hello");
	    collector.emit(test, new Values("Hello"));
	    
	  }

	  public void declareOutputFields(OutputFieldsDeclarer declarer) {
		  declarer.declare(new Fields("site1"));
	  }
	}