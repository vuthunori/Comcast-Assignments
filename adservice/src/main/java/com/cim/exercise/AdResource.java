package com.cim.exercise;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

@Path("/ad")
public class AdResource {

	private static Map<String, AdCampaignModel> adCampaignMap = new HashMap<String, AdCampaignModel>();

    @GET
    @Path("/{partnerid}")
    @Produces("application/json")
    public Response ping(@PathParam("partnerid") String partnerId) {
    	AdCampaignModel existingPartnerAdCampaign = adCampaignMap.get(partnerId);
    	if(existingPartnerAdCampaign==null || existingPartnerAdCampaign.getDuration() + existingPartnerAdCampaign.getTimestamp() < System.currentTimeMillis()){
    		return Response.status(new StatusType() {
				
				public int getStatusCode() {
					return 400;
				}
				
				public String getReasonPhrase() {
					return "No active adcampaign for Partner";
				}
				
				public Family getFamily() {
					return null;
				}
			}).build();
    	}
        return Response.status(Status.OK).entity(existingPartnerAdCampaign).build();
    }

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response createAdCampaign(AdCampaignModel input) {

		String partnerId = input.getPartner_id();
		AdCampaignModel existingPartnerAdCampaign = adCampaignMap.get(partnerId);
		if (existingPartnerAdCampaign == null) {
			input.setTimestamp(System.currentTimeMillis());
			adCampaignMap.put(partnerId, input);
		} else {
			if (existingPartnerAdCampaign.getTimestamp() + existingPartnerAdCampaign.getDuration() > System.currentTimeMillis()) {
				input.setTimestamp(System.currentTimeMillis());
				adCampaignMap.put(partnerId, input);
			} else {
				return Response.status(new StatusType() {
					
					public int getStatusCode() {
						return 400;
					}
					
					public String getReasonPhrase() {
						return "Adcampaign already existing for Partner";
					}
					
					public Family getFamily() {
						return null;
					}
				}).build();
			}
		}
		return Response.status(Status.CREATED).entity(input).build();
	}
}

