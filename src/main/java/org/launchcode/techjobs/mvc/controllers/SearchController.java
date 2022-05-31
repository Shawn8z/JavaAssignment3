package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {


    @GetMapping(value = "")
    public String search(Model model, String pickedType) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("pickedType", "all");
        return "search";
    }

    @GetMapping(value = "results")
    public String userClickOnSearchResultElement(Model model,
                                                 @RequestParam String term1,
                                                 @RequestParam String column1,
                                                 @RequestParam String term2,
                                                 @RequestParam String column2) {

        ArrayList<Job> result;
        ArrayList<Job> term1List;

        term1List = JobData.findByColumnAndValue(column1, term1);
        result = JobData.findByColumnAndValueWithinList(column2, term2, term1List);


        model.addAttribute("jobs", result);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("pickedType", column2);
        model.addAttribute("previousTerm", term2);
        return "search";
    }


    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping(value = "results")
    public String displaySearchResults(Model model,
                                       @RequestParam(name="searchType") String searchType,
                                       @RequestParam(name="searchTerm") String searchTerm,
                                       String pickedType,
                                       String previousTerm) {

        ArrayList<Job> jobs = new ArrayList<>();


        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")) {
            pickedType = "all";
            jobs = JobData.findAll();
        } else {
            pickedType = searchType;
            previousTerm = searchTerm;
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("pickedType", pickedType);
        model.addAttribute("previousTerm", previousTerm);

        return "search";
    }
}
