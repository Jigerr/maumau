package de.berlin.htw.kba.maumau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Stack;

@RestController
public class HelloController {

    @RequestMapping("/")
    public Map<String, String> index() {

        Stack stack = new Stack();
        CardMasterService mauMaster = new CardMasterServiceImpl();
        mauMaster.fillStack(stack);
        mauMaster.shuffleStack(stack);
        mauMaster.showCards(stack);
        

        Map<String,String> myMap = new HashMap<String, String>();
        myMap.put("1", "Hallo");
        myMap.put("2", "Huhu");
        return myMap;
    }
    
    @RequestMapping("/stack")
    public List<String> stack() {

        Stack stack = new Stack();
        CardMasterService mauMaster = new CardMasterServiceImpl();
        mauMaster.fillStack(stack);
        mauMaster.shuffleStack(stack);
        
        List<String> list = new ArrayList<String>();
        list.add("Hallo");
        list.add("Huhu");
        return list;
    }

}
