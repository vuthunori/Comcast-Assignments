package com.cim.exercise;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdResourceIT {
	private static String endpointUrl;
	
	@BeforeClass
	public static void beforeClass() {
		endpointUrl = System.getProperty("service.url");
		//endpointUrl = "http://localhost:13000";
	}
	
	@Test
	public void testCreateAdCampaign() throws Exception {
		this.wait(5000);
		List<Object> providers = new ArrayList<Object>();
	    providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
	    AdCampaignModel inputBean = new AdCampaignModel();
	    inputBean.setPartner_id("123");
	    inputBean.setDuration(5000);
	    inputBean.setAd_content("This is ad content for partner 123.");
		WebClient client = WebClient.create(endpointUrl + "/ad", providers);
		Response r = client.accept("application/json")
				.type("application/json")
				.post(inputBean);
		assertEquals(Response.Status.CREATED.getStatusCode(), r.getStatus());
	}
	
	@Test
	public void testGetAdCampaign() throws Exception {
		testCreateAdCampaign();
		WebClient client = WebClient.create(endpointUrl + "/ad/123");
		Response r = client.accept("application/json").get();
		assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
		MappingJsonFactory factory = new MappingJsonFactory();
		JsonParser parser = factory.createJsonParser((InputStream)r.getEntity());
		AdCampaignModel response = parser.readValueAs(AdCampaignModel.class);
		assertEquals(response.getPartner_id(), "123");
		assertEquals(response.getAd_content(), "This is ad content for partner 123.");
	}
}
