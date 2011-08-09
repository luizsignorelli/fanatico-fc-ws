package tests;
import static tests.ModelHelpers.aTeam;
import static tests.ModelHelpers.aUser;
import models.Team;
import models.User;
import play.test.UnitTest;

public class ModelTest extends UnitTest {

    @org.junit.Test
    public void simpleUserTest() {
        User user = aUser("01234");
        
        User saved = User.find("byFacebookId", "01234").first();
        assertEquals(user, saved);
    }
    
    @org.junit.Test
    public void simpleTeamTest() {
        Team team = aTeam("atletico-mg", "Atlético-MG");
        
        Team saved = Team.find("byTeamId", "atletico-mg").first();
        assertEquals(team, saved);
        assertEquals("http://globoesporte.globo.com/dynamo/semantica/editorias/plantao/futebol/times/atletico-mg/feed.rss", saved.rssFeed);
    }
    
    @org.junit.Test
	public void userTeam() throws Exception {
		User user = aUser("0123");
		user.team = aTeam("galo", "Galo");
		user.save();
		
		Team userTeam = ((User)User.find("byFacebookId", "0123").first()).team;
		assertEquals(user.team, userTeam);
	}

}
