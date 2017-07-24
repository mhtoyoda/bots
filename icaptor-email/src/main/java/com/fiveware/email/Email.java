package com.fiveware.email;

import java.util.Set;
/**
 * @author valdisnei
 */
public interface Email {

	String getFromAddress();
	
	Set<String> getToAddresses();
	
	Set<String> getCcAddresses();
	
	Set<String> getBccAddresses();
	
	Set<String> getAttachments();
	
	String getSubject();
	
	String getBody();
}