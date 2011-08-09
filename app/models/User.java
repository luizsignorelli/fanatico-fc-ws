package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;

@Entity
public class User extends Model{

	public String facebookId;
	
	@ManyToOne
	public Team team;

	public User(String facebookId) {
		this.facebookId = facebookId;
	}

	public static User findByFacebookId(String facebookId) {
		return find("byFacebookId", facebookId).first();
	}

	public User changeTeam(Team team) {
		this.team = team;
		return save();
	}
	
	
}
