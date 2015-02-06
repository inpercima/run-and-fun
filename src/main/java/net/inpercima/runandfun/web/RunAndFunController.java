package net.inpercima.runandfun.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

import net.inpercima.runandfun.service.RunAndFunService;
import net.inpercima.runandfun.service.RunAndFunServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunAndFunController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunAndFunController.class);

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String getUser(@RequestParam(value = "code", required = true) String code) throws MalformedURLException,
            IOException {
        String body = "";
        RunAndFunService runAndFun = new RunAndFunServiceImpl();
        Properties properties = loadProperties();
        LOGGER.debug("loaded properties", properties);
        if (properties != null && properties.getProperty("clientId") != ""
                && properties.getProperty("clientSecret") != "") {
            body = runAndFun.getAccessToken(code, properties.getProperty("clientId"),
                    properties.getProperty("clientSecret"), properties.getProperty("redirectUri"));
            
            body = runAndFun.getUserData(body);
        } else {
            body = "Die Properties für die App wurden nicht gesetzt!";
        }
        return body;
    }

    private Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/run-and-fun.properties");
        prop.load(in);
        in.close();
        return prop;
    }

}
