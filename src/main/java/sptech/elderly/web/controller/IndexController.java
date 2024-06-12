package sptech.elderly.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String index() {
        return "<div style='height: 100vh; font-family: system-ui; display: flex; justify-content: center; align-items: center;'>" +
                    "<h1 style='text-align: center;'>" +
                        "Olá usuário!<br>" +
                        "Você pode saber como utilizar essa API via nosso " +
                        "<a style='color: #229ef7;' href='/swagger-ui/index.html'>Swagger</a>!" +
                    "</h1>" +
                "</div>";
    }
}
