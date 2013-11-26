package com.cs.data.nosql.neo4j;


import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Neo4jServiceUnitTests {

	private Neo4jService service;

	public Neo4jServiceUnitTests(){
	}

	@Before
	public void setup(){
		service = new Neo4jService();
	}
	
	@Test
	public void itShouldGraphDBServiceInstance(){
		assertThat(service.getGraphDBServiceInstance()).isNotNull();
	}
	@Test
	public void itShouldShutdownGraphDBServiceInstance(){
		assertThat(service.shutdownGraphDBServiceInstance()).isEqualTo(true);
	}
	@Test
	public void itShouldCreateTwoNodesWithRelationShip(){
		String node1 = "Friend1";
		String node2 = "Friend2";
		long nodeID = service.createTwoNodesWithRelationship(node1, node2);
		assertThat(nodeID).isNotNull();
	}
	@Test
	public void itShouldCleanUpAllNodes(){
		boolean flag = service.cleanUpAllNodes();
		assertThat(flag).isEqualTo(true);
	}
	
	@Test
	public void itShouldGetAllNodes(){
		List<String> result = service.getAllNodes();
		System.out.println(result.toString());
	}
}
