package com.learningstorm.storm_example;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.ZkHosts;
import storm.kafka.SpoutConfig;
import storm.kafka.KafkaSpout;
import storm.kafka.StringScheme;
import storm.kafka.BrokerHosts;



public class ModelTopology {
	public static void main(String[] args) throws
	  AlreadyAliveException, InvalidTopologyException {
	    // zookeeper hosts for the Kafka cluster
	    BrokerHosts zkHosts = new ZkHosts("localhost:2181");
	    // Create the KafkaSpout configuration
	    // Second argument is the topic name
	    // Third argument is the ZooKeeper root for Kafka
	    // Fourth argument is consumer group id
	    SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, "testKafkaPartition16", "/testKafkaPartition111116", "Consumer_Group_dinesh21111");
	    // Specify that the kafka messages are String
	    kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
	    // We want to consume all the first messages in
	    // the topic every time we run the topology to 
	    // help in debugging. In production, this
	    // property should be false
	    kafkaConfig.forceFromStart = true;
	    // Now we create the topology
	    TopologyBuilder builder = new TopologyBuilder();
	    // set the kafka spout class
	    builder.setSpout("KafkaSpout", new KafkaSpout(kafkaConfig), 1); 
	    builder.setBolt("SVMBolt", new SVMBolt(), 4).shuffleGrouping("KafkaSpout");
	    Config conf = new Config();
	    conf.setDebug(true);
	    // create an instance of LocalCluster class for 
	    // executing topology in local mode.
	    //LocalCluster cluster = new LocalCluster();
	    StormSubmitter stormsubmit = new StormSubmitter();
	    conf.setNumWorkers(3);
	    //conf.setMaxSpoutPending(5000);
	    // LearningStormTopolgy is the name of submitted topology.
	    stormsubmit.submitTopology("ModelTopology", conf, builder.createTopology());
	    //cluster.submitTopology("LearningStormToplogy", conf, builder.createTopology());
	    try {
	      Thread.sleep(1000000);
	    } catch (Exception exception) {
	      System.out.println("Thread interrupted exception : " + exception);
	    }
	    // kill the LearningStormTopology
	    //cluster.killTopology("LearningStormToplogy");
	    // shutdown the storm test cluster
	    //cluster.shutdown();
	}
	    
	    
}
