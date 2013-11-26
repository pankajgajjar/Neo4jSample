package com.cs.data.nosql.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

public class Neo4jService {
	private static GraphDatabaseService graphDb = null;
	static final String DB_PATH = "/Users/cs-2/Desktop/MyFolder/Training/Neo4j -Mumbai/USBKEY/mydb";
	
	private static enum RelTypes implements RelationshipType{
		KNOWS
	}
	protected Neo4jService() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		registerShutdownHook(graphDb);
	}
	
	private static void registerShutdownHook(final GraphDatabaseService graphDb){
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run(){
				graphDb.shutdown();
			}
		});
	}
	public  GraphDatabaseService getGraphDBServiceInstance(){
		return graphDb;
	}
	
	public boolean shutdownGraphDBServiceInstance(){
		graphDb.shutdown();
		return true;
	}
	
	public long createTwoNodesWithRelationship(String node1,String node2){
		Transaction tx = graphDb.beginTx();
		Node node1Obj = null;
		Node node2Obj = null;
		
		try{
			node1Obj = graphDb.createNode();
			node1Obj.setProperty("name", node1);
			
			node2Obj = graphDb.createNode();
			node2Obj.setProperty("name", node2);
			
			Relationship relationShip = node1Obj.createRelationshipTo(node2Obj,RelTypes.KNOWS);
			relationShip.setProperty("relation", "bestfriend");
			
			tx.success();
		}
		finally{
			tx.finish();
		}
		return node1Obj.getId();
			
	}
	
	public List<String> getAllNodes(){
		Transaction tx = graphDb.beginTx();
		List <String> result = new ArrayList<String>();
		try{
			Iterator <Node> list  = graphDb.getAllNodes().iterator();
			while(list.hasNext()){
				Node node = list.next();
				if(node.hasProperty("name"))
					result.add((String)node.getProperty("name"));
			}
			
		}finally{
			tx.finish();
		}
		return result;
	}
	
	//TODO
	public String findNodesByRelation(long nodeId,String relationName){
		Transaction tx = graphDb.beginTx();
		Node resultNodes = null;
		try{
			resultNodes = graphDb.getNodeById(nodeId);
		}finally{
			tx.finish();
		}
		return null;
	}
	
	public boolean cleanUpAllNodes(){
			try {
				FileUtils.deleteRecursively(new File(DB_PATH));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
	}
	
}
