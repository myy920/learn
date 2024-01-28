package excelImport;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testEI")
public class TestEIController {


    @PostMapping("/f1")
    User f1(@RequestBody User user){
        user.setName(user.getName() + " f1");
        user.setAge(user.getAge() + 1);
        return user;
    }

}
