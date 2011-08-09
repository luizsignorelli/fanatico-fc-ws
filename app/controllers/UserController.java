package controllers;

import models.Team;
import models.User;
import play.db.DB;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;
import play.mvc.Http;

public class UserController extends Controller{

	public static void get(String facebookId){
		User user = User.findByFacebookId(facebookId);
		notFoundIfNull(user);	
		renderJSON( user );
		
	}
	
	public static void create(String facebookId){
		User user = new User(facebookId);
		user.save();
		response.status = Http.StatusCode.CREATED;
		renderJSON(user);
	}
	
	public static void changeTeam(String facebookId, String teamId){
		User user = User.findByFacebookId(facebookId);
		Team team = Team.findByTeamId(teamId);
		//User u = user.changeTeam(team);
		user.team = team;
		user.save();
		renderJSON( user );
	}
}
