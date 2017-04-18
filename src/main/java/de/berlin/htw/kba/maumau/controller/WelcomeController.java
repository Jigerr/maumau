package de.berlin.htw.kba.maumau.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.berlin.htw.kba.maumau.engine.MauMaster;
import de.berlin.htw.kba.maumau.engine.MauMasterImpl;
import de.berlin.htw.kba.maumau.model.Stack;

@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message = "Hello World";
    private String message2 = "MauMau";
    
    private Stack stack = new Stack();
    
//    @RequestMapping("/")
//    public String welcome(Map<String, Object> model) {
//        model.put("message", this.message);
//        model.put("message2", this.message2);
//        return "welcome";
//    }
    
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
    	
    	MauMaster mauMaster = new MauMasterImpl();
    	mauMaster.initDeck(stack);
        	
        model.put("datas", stack.getDeck());
        return "welcome";
    }

}
