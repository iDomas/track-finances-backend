package com.trackfinances.backend.trackfinancesbackend.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeController {

	@GetMapping(value = ["", "/"])
	@ResponseBody
	fun home(): String {
		return "Welcome!"
	}

}