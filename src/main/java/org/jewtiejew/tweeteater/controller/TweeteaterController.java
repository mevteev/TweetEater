package org.jewtiejew.tweeteater.controller;

import org.jewtiejew.tweeteater.keyword.KeywordCollector;
import org.jewtiejew.tweeteater.listener.QueueListener;
import org.jewtiejew.tweeteater.twitter.TweetsReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mike on 07.06.18.
 */
@Controller
@RequestMapping("/")
public class TweeteaterController {

    @Autowired
    KeywordCollector collector;

    @Autowired
    TweetsReader reader;

    @Autowired
    QueueListener listener;


    @RequestMapping(method = RequestMethod.GET)
    public String printKeywords(Model model) {
        model.addAttribute("isRunning", reader.isRunning());
        model.addAttribute("keywords", collector.get());
        return "printKeywords";
    }

    @RequestMapping(path="/stop", method = RequestMethod.GET)
    public String stopServer(Model model) {
        reader.stop();
        listener.stop();

        return "redirect:/";
    }

    @RequestMapping(path="/start", method = RequestMethod.GET)
    public String startServer(Model model) {
        reader.start();
        listener.start();

        return "redirect:/";
    }

}
