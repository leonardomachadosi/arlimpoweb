package br.ufma.lsdi.configuration.JSF;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ErrorHandler implements ErrorController {

    private static final String PATH = "/error";

    @GetMapping(value = PATH)
    public ModelAndView error() {
        ModelMap modelMap = new ModelMap();
        return new ModelAndView(new RedirectView(PATH, true), modelMap);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
