package tests;
import models.Team;
import models.User;


public class ModelHelpers {

	public static User aUser(String faceboofId) {
		User user = new User(faceboofId);
        user.save();
		return user;
	}
    
	public static Team aTeam(String teamId, String name) {
		Team team = new Team(teamId, name);
        team.save();
		return team;
	}
}
