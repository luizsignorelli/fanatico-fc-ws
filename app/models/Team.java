package models;

import javax.persistence.Entity;

import org.w3c.dom.Node;

import play.db.jpa.Model;
import play.libs.XPath;

@Entity
public class Team extends Model{

	public String teamId;
	public String name;
	public String rssFeed;
	
	private static String FEED_URL = "http://globoesporte.globo.com/dynamo/semantica/editorias/plantao/futebol/times/%s/feed.rss";

	public Team(String teamId, String name) {
		this.teamId = teamId;
		this.name = name;
		this.rssFeed = String.format(FEED_URL, teamId);
	}

	public static Team createFromXml(Node team) {
		String teamId = XPath.selectText("teamId",team);
    	String teamName = XPath.selectText("teamName",team);
		return new Team(teamId, teamName).save();
	}

	public static Team findByTeamId(String teamId) {
		return find("byTeamId", teamId).first();
	}
	
	
}
