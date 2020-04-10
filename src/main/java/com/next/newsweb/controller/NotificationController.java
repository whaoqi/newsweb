package com.next.newsweb.controller;

import com.next.newsweb.dto.NotificationDTO;
import com.next.newsweb.enums.NotificationTypeEnum;
import com.next.newsweb.model.User;
import com.next.newsweb.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {/*是否登陆*/
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_NEWS.getType() == notificationDTO.getType()) {
            return "redirect:/news/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
