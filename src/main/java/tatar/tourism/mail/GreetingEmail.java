package tatar.tourism.mail;

import freemarker.template.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilya Evlampiev on 01.11.2015.
 */
public class GreetingEmail extends EmailTemplate {

    private String user;
    private String password;
    private String uuid;

    @Override
    public String getSubject() {
        return "Greeting email";
    }

    @Override
    public String getTemplateAddress() {
        return "greeting.ftl";
    }

    @Override
    public Map<String, String> getParametersMap() {
        Map<String, String> rootMap = new HashMap<String, String>();
        rootMap.put("user", this.user);
        rootMap.put("password", this.password);
        rootMap.put("uuid", this.uuid);
        return rootMap;
    }

    public GreetingEmail(String locale, String to, String user, String password, String uuid)
    {
        super(locale,to);
        this.user=user;
        this.password=password;
        this.uuid=uuid;
    }



}
