package com.test.notice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoticeController {
	@Autowired
	NoticeRepository noticeRepository;

	@ExceptionHandler
	@GetMapping("/list")
	public String getList(@RequestParam(required = false) String title, Model model) {
		List<Notice> list;
		
		if (title == null) {
			list = noticeRepository.findAll();
		}else {
			list = noticeRepository.findByTitle(title);
		}
		
		model.addAttribute("list", list);

		return "notice/list";
	}

	@GetMapping("/regist")
	public String registNotice(Model model) {
		Notice noticeData = new Notice();
		model.addAttribute("notice", noticeData);
		return "notice/regist";
	}
	
	@ExceptionHandler
	@PostMapping("/save")
	public String saveNotice(Notice notice) {
		notice.setRegDt(new Date());
		noticeRepository.save(notice);
		return"redirect:/list";
	}
	
	@GetMapping("/update/{id}")
	public String getNotice(@PathVariable("id") long id, Model model) {
		Optional<Notice> noticeData = noticeRepository.findById(id);
		model.addAttribute("notice", noticeData.get());

		return "notice/update";
	}
	
	@ExceptionHandler
	@PutMapping("/updateNotice/{id}")
	public ResponseEntity<HttpStatus> updateNotice(@PathVariable("id") long id,Notice notice) {
		System.out.println("notice"+notice);
		
		Optional<Notice> noticeData = noticeRepository.findById(notice.getId());
		Notice _notice = noticeData.get();
		_notice.setTitle(notice.getTitle());
		_notice.setContent(notice.getContent());
		_notice.setRegDt(new Date());
		
		noticeRepository.save(_notice);	
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ExceptionHandler
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteNotice(@PathVariable("id") long id) {
		noticeRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler
	@DeleteMapping("/delete/all")
	public ResponseEntity<HttpStatus> deleteAllNotice() {
		System.out.println("??");
		noticeRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
