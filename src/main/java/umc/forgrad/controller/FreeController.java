package umc.forgrad.controller;

import umc.forgrad.domain.common.Free;
import umc.forgrad.service.FreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FreeController {

    private final FreeService freeService;

    @Autowired
    public FreeController(FreeService freeService) {
        this.freeService = freeService;
    }

    @PostMapping(value = "/plans/memo")
    public String create(FreeFrom form) {

        Free free = new Free();
        free.setMemo(form.getMemo());

        freeService.addMemo(free);

        return "redirect:/";
    }

    @GetMapping(value = "/plans/memo")
    public String list(Model model) {
        List<Free> memos = memoService.findMemos();
        model.addAttribute("memos", memos);
        return "memos/memoList"; //resources/templates/memos/memoList.html
    }
}