package controllers;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import models.Team;
import models.User;
import play.Logger;
import play.db.jpa.GenericModel.JPAQuery;
import play.libs.XPath;
import play.mvc.Controller;

public class TeamsController extends Controller{

	public static void importTeams(){
		final List<Node> teams = teamNodes();
        for (Node team : teams) {
        	Team.createFromXml(team);
		}
	}

	private static List<Node> teamNodes() {
		final Document document = xmlFromBody();
		final Element root = document.getDocumentElement();

        return XPath.selectNodes("team", root);
	}

	private static Document xmlFromBody() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(request.body);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
		return document;
	}
}
