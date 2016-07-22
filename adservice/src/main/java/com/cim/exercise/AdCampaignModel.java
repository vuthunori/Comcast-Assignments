package com.cim.exercise;


public class AdCampaignModel {
    private String partner_id;
    private Integer duration;
    private String ad_content;
    private Long timestamp;
	
	public String getPartner_id() {
	
		return partner_id;
	}
	
	public void setPartner_id(String partner_id) {
	
		this.partner_id = partner_id;
	}
	
	public Integer getDuration() {
	
		return duration;
	}
	
	public void setDuration(Integer duration) {
	
		this.duration = duration;
	}
	
	public String getAd_content() {
	
		return ad_content;
	}
	
	public void setAd_content(String ad_content) {
	
		this.ad_content = ad_content;
	}
	
	public Long getTimestamp() {

		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {

		this.timestamp = timestamp;
	}
   
}
