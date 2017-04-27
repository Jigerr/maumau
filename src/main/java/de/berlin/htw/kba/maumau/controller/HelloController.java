package de.berlin.htw.kba.maumau.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.berlin.htw.kba.maumau.engine.CardMaster;
import de.berlin.htw.kba.maumau.engine.CardMasterImpl;
import de.berlin.htw.kba.maumau.model.Stack;

@RestController
public class HelloController {

    @RequestMapping("/")
    public Map<String, String> index() {

        Stack stack = new Stack();
        CardMaster mauMaster = new CardMasterImpl();
        mauMaster.initDeck(stack);
        mauMaster.shuffleDeck(stack);
        

        Map<String,String> myMap = new HashMap<String, String>();
        myMap.put("1", "Hallo");
        myMap.put("2", "Huhu");
        return myMap;
    }
    
    @RequestMapping("/stack")
    public List<String> stack() {

        Stack stack = new Stack();
        CardMaster mauMaster = new CardMasterImpl();
        mauMaster.initDeck(stack);
        mauMaster.shuffleDeck(stack);
        
        List<String> list = new ArrayList<String>();
        list.add("Hallo");
        list.add("Huhu");
        return list;
    }

}
