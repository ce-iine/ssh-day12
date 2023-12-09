package sg.edu.nus.iss.d12workshop.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.d12workshop.exception.RandNoException;
import sg.edu.nus.iss.d12workshop.model.Generate;

@Controller
@RequestMapping("/rand")

public class GenerateRandController {
    

    @GetMapping("/show") // http://localhost:8080/rand/show
    public String showRandomForm(Model m) {
        Generate g = new Generate();
        m.addAttribute("generateObj", g);
        return "generate"; // returns generate.html path: localhost/rand/show
    }

    @GetMapping("/generate") 
    public String generate(@RequestParam Integer numberVal, Model m) { // numberVal is in Generate.java under model
        //requestparam - key value pair - http://localhost:8080/rand/generate?(numberVal)
        this.randomizeNumber(m, numberVal.intValue());
        return "result";
    }

    private void randomizeNumber(Model m, int noOfGenerateNo) {
        int maxGenNo = 31;
        String[] imgNumbers = new String[maxGenNo];
        if (noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo - 1) { // if user input numbers not between 1-30
            throw new RandNoException();
        }

        for (int i = 0; i < maxGenNo; i++) { // generate number image filename and set to array
            if (i > 0) { // system only generates number btwn 1 and 30 (inclusive, no 0)
                // array that holds all file names in string
                imgNumbers[i] = "number" + i + ".jpg";
            }
        }

        // randomised desired numbers in string
        List<String> selectedImgs = new ArrayList<String>();
        Random rand = new Random();
        // use set because set cannot have duplicates
        Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
        // while the retrieved list size is less than what is requested
        while (uniqueResult.size() < noOfGenerateNo) { // once desired number of wanted numbers is fulfilled
            Integer randNoResult = rand.nextInt(maxGenNo);
            if (randNoResult != null) { //checks
                if (randNoResult > 0) {
                    uniqueResult.add(randNoResult);
                }
            }
        }

        Integer currElem = null;
        for (java.util.Iterator<Integer> iter = uniqueResult.iterator(); iter.hasNext();) {
            currElem = iter.next();
            System.out.println(currElem);
            if (currElem != null) {
                selectedImgs.add(imgNumbers[currElem]);
            }

            m.addAttribute("noOfGenerateNo", noOfGenerateNo);
            m.addAttribute("selectedImgs", selectedImgs);
            System.out.println(">>>>>" + selectedImgs);
        }

    }
}
