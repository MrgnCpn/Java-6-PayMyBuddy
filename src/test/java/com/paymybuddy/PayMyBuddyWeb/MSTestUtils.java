package com.paymybuddy.PayMyBuddyWeb;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MSTestUtils {
    public static void perform_redirect(MockMvc mockMvc, String protocole, String urlPath, String redirectPath) throws Exception {
        if(protocole.equals("GET")) {
            mockMvc.perform(get(urlPath))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(redirectPath));
        } else if (protocole.equals("POST")) {
            mockMvc.perform(post(urlPath))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(redirectPath));
        }
    }
}
