package tatar.tourism.mail;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilya Evlampiev on 02.11.2015.
 */
public class ConfirmationEmail extends EmailTemplate {

    private String user;

    @Override
    public String getSubject() {
        return "Confirmation email";
    }

    @Override
    public String getTemplateAddress() {
        return "confirmation.ftl";
    }

    @Override
    public Map<String, String> getParametersMap() {
        Map<String, String> rootMap = new HashMap<String, String>();
        rootMap.put("user", this.user);
        return rootMap;
    }

    public ConfirmationEmail(String locale, String to, String user)
    {
        super(locale,to);
        this.user=user;
    }

}
