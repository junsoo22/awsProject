package org.example.webController;

import org.example.webController.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController   //컨트롤러를 Json으로 반환하는 컨트롤러로 만드러줌
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    //RequestParam: 외부에서 aPI로 넘긴 파라미터를 가져오는 어노테이션
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        //외부에서 name(@requestParam("name)이란 이름으로 넘긴 파라미터를 name(String name)에 저장하게 된다..
        return new HelloResponseDto(name,amount);
    }
}
