package com.learningstorm.storm_example;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.*;
import backtype.storm.topology.base.*;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import org.jpmml.evaluator.SupportVectorMachineModelEvaluator;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
//import org.dmg.pmml.IOUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.dmg.pmml.*;
import org.jpmml.evaluator.*;
import org.xml.sax.*;
import javax.xml.bind.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.xml.sax.InputSource;

import com.esotericsoftware.minlog.Log;

import javax.xml.transform.sax.SAXSource;
import org.jpmml.model.ImportFilter;
import org.jpmml.model.JAXBUtil;
import scala.App;
import java.net.URL;

import org.apache.storm.hdfs.bolt.HdfsBolt;

public class SVMBolt extends BaseRichBolt {

	  /* JPMML's SVM model evaluator object */
	  SupportVectorMachineModelEvaluator eval=null;
	  ModelEvaluator<MiningModel> modelEvaluator;
	  public SVMBolt() { }
	  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
	    PMML model = null;
	    
	    ClassLoader classLoader = getClass().getClassLoader();
		//File file1 = new File(classLoader.getResource("single_audit_svm.xml").getFile());

		URL dataFile = classLoader.getResource("iris_rf.pmml");
		//dataReader = new InputStreamReader(dataFile.openStream());
	    
	    try { //load PMML
	    //  model = IOUtil.unmarshal(new File(validateFilePath("/home/murugdi/single_audit_svm.xml")));
	     
	    	//model = createPMMLfromFile(validateFilePath("/home/murugdi/single_audit_svm.xml"));
	    	model = createPMMLfromFile(dataFile.openStream());
	     // model = JPMMLUtils.loadModel("/home/murugdi/single_audit_svm.xml");;
	    }catch(Exception e) { e.printStackTrace();}
	    
	    //eval = new SupportVectorMachineModelEvaluator(model);
	    modelEvaluator = new MiningModelEvaluator(model);
	  }
	  
	  public void execute(Tuple input) {
	  // collect the input tuple in a variable
	  String ip = input.getString(0);
	  // split the input record on ","
	  String[] var = ip.split(",");
	        
	  //Log.debug("DEBUG", "************ First Value ***********",var[0]);
	  
	  
	  //if(isInteger(var[0]))
	  try
	  	{
	  		int i = (int)Float.parseFloat(var[0]);
	  		int j = (int)Float.parseFloat(var[1]);
	  		int k = (int)Float.parseFloat(var[2]);
	  		int l = (int)Float.parseFloat(var[3]);
		  HashMap<FieldName, String> params = new HashMap<FieldName, String>();
		  params.put(new FieldName("Sepal.Length"),Integer.toString(i));
		  params.put(new FieldName("Sepal.Width"),Integer.toString(j));
		  params.put(new FieldName("Petal.Length"),Integer.toString(k));
		  params.put(new FieldName("Petal.Width"),Integer.toString(l));
		  // evaluate the parameters to determine the category
		  System.out.println("************ Model Evaluator ***********"+modelEvaluator.evaluate(params));
		  }
	  catch(Exception e){
		  System.out.println("Catching whatever happens****************  "+e.toString() );
	  }
	  }
	  
	  public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
		} 

	  public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
	    declarer.declare(new Fields("test"));

	  }
	  com.google.common.collect.Range asd;
//	  asd.
	  
	  private static String validateFilePath(final String filePath)
		        throws IOException {
		        File file = new File(filePath);
		        if (!file.exists()) {
		            throw new IOException("'" + filePath + "' is not a vaild file path.");
		        }

		        return filePath;
		    }
	  
	// unmarshal the given file to a PMML model
	  public PMML createPMMLfromFile(InputStream is) throws SAXException, JAXBException, FileNotFoundException {
	   // File pmmlFile = fileName;
	    //String pmmlString = new Scanner(pmmlFile).useDelimiter("\\Z").next();
	   
	   // InputStream is = new ByteArrayInputStream(pmmlString.getBytes());
		//  dataReader
	    InputSource source = new InputSource(is);
	    SAXSource transformedSource = ImportFilter.apply(source);
	    
	    return JAXBUtil.unmarshalPMML(transformedSource);
	  }

	}