package com.learningstorm.storm_example;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.LocalCluster;



public class LearningStormTopology {
	  public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
	    // create an instance of TopologyBuilder class
	    TopologyBuilder builder = new TopologyBuilder();
	    // set the spout class
	    builder.setSpout("LearningStormSpout", new LearningStormSpout(), 2);
	    // set the bolt class
	    builder.setBolt("LearningStormBolt", new LearningStormBolt(), 4).shuffleGrouping("LearningStormSpout");

	    builder.setBolt("LearningBolt2", new LearningBolt2(), 4).shuffleGrouping("LearningStormBolt");
	    Config conf = new Config();
	    conf.setDebug(true);
	    // create an instance of LocalCluster class for 
	    // executing topology in local mode.
	    LocalCluster cluster = new LocalCluster();
	    StormSubmitter stormsubmit = new StormSubmitter();
	    conf.setNumWorkers(3);
	    conf.setMaxSpoutPending(5000);
	    // LearningStormTopolgy is the name of submitted topology.
	    stormsubmit.submitTopology("LearningStormToplogy", conf, builder.createTopology());
	    //cluster.submitTopology("LearningStormToplogy", conf, builder.createTopology());
	    try {
	      Thread.sleep(1000000);
	    } catch (Exception exception) {
	      System.out.println("Thread interrupted exception : " + exception);
	    }
	    // kill the LearningStormTopology
	    cluster.killTopology("LearningStormToplogy");
	    // shutdown the storm test cluster
	    cluster.shutdown();

	  }
	}