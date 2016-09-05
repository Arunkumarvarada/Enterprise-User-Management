package com.altiux.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.knappsack.swagger4springweb.controller.ApiDocumentationController;
import com.wordnik.swagger.model.ApiInfo;

/**
* This is an example of how you might extend the ApiDocumentationController in order to set your own RequestMapping
* (instead of the default "/api") among other possibilities.  Going this route, you do not necessarily have to define
* the controller in your servlet context.
*/
@Controller
@RequestMapping(value = "/documentation")
public class ExampleDocumentationController extends ApiDocumentationController {

    public ExampleDocumentationController() {
    	//base package
        setBaseControllerPackage("com.altiux.rest.controllers");
        
        List<String> controllerPackages = new ArrayList<String>();
        controllerPackages.add("com.altiux.eum.esystem.controller");
        controllerPackages.add("com.altiux.eum.esite.controller");
        controllerPackages.add("com.altiux.rest.controllers");
       
        setAdditionalControllerPackages(controllerPackages);

        setBaseModelPackage("com.altiux.rest.models");
        
        List<String> modelPackages = new ArrayList<String>();
        //modelPackages.add("com.altiux.rest.controllers.cars");
        setAdditionalModelPackages(modelPackages);

        setApiVersion("v1");

        ApiInfo apiInfo = new ApiInfo("Enterprise User Management Service", "This is a basic web-app to gather information for Enterprise User Managment Service",
                "../terms", "arunkumar.v@altiux.com", "Altiux Innovations", "http://www.altiux.com/");
        setApiInfo(apiInfo);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String documentation() {
        return "documentation";
    }
}
