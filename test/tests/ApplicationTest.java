package tests;
import static tests.ModelHelpers.aTeam;
import static tests.ModelHelpers.aUser;

import java.util.HashMap;
import java.util.Map;

import models.Team;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.db.jpa.JPA;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

import com.google.gson.Gson;



public class ApplicationTest extends FunctionalTest {

	@Before
	public void clearTables(){
		User.deleteAll();
		Team.deleteAll();
	}
	
	@Test
	public void postToCreateUser() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("facebookId", "01234");
    	Response response = POST("/users", params );
    	assertStatus(Http.StatusCode.CREATED, response);
    	assertContentType("application/json", response);
	}
    
	@Test
	public void getUserByFacebookIdAsJson() throws Exception {
    	User user = aUser("0123");
    	Response response = GET("/users/0123");
    	assertIsOk(response);
        assertContentType("application/json", response);
        assertEquals(user, new Gson().fromJson(response.out.toString(), User.class));
	}
	
	@Test
	public void return404whenThereIsNoUserByFacebookId() throws Exception {
    	Response response = GET("/users/0123");
    	assertIsNotFound(response);
	}
	
	@Test
	public void changeUserTeam() throws Exception {
		User user = aUser("3210");
		Team team =aTeam("atletico-mg", "Atletico-MG");
    	Response response = PUT("/users/3210/team","application/x-www-form-urlencoded","teamId=atletico-mg");
    	assertIsOk(response);
        assertContentType("application/json", response);
        
        JPA.em().refresh(user);
        assertEquals(team, user.team);
	}
	
	@Test
	public void importTeams() throws Exception {
		StringBuilder xml = new StringBuilder();
		xml.append("<teams>")
			  .append("<team>")
			    .append("<teamId>america-mg</teamId>")
			    .append("<teamName>América-MG</teamName>")
			  .append("</team>")
			  .append("<team>")
			    .append("<teamId>atletico-mg</teamId>")
			    .append("<teamName>Atlético-MG</teamName>")
			  .append("</team>")
		   .append("</teams>");  
		
		Response response = POST("/teams", "application/xml", xml.toString() );
		assertIsOk(response);
		assertEquals(2,Team.findAll().size());
	}
    
}