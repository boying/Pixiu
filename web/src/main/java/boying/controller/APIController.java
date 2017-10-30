package boying.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by boying on 2017/10/30.
 */
@Controller
@RequestMapping("/api")
public class APIController {

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object login() {
        Map<String, String>  map = new HashMap<>();
        map.put("key", "key");
        map.put("val", "value");
        return map;
    }

}
