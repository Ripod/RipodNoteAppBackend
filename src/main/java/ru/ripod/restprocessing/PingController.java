package ru.ripod.restprocessing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ripod.restmodels.responses.BaseResponse;

@RestController
public class PingController {

    private static final String template = "Hello, %s!";

    @GetMapping("ping")
    public BaseResponse ping(@RequestParam(value = "name", defaultValue = "-1") String name){
        if(name.equals("-1")){
            return new BaseResponse();
        }else{
            return new BaseResponse(0, String.format(template, name));
        }
    }
}
