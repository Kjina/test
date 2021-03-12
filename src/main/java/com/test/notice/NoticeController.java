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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeController {
	@Autowired
	NoticeRepository noticeRepository;

	@ExceptionHandler
	@GetMapping("/list")
	public String getAllTutorials(@RequestParam(required = false) String title, Model model) {

	    System.out.println("aaaaa");
		
		List<Notice> list;
		if (title == null) {
			list = noticeRepository.findAll();
		}else {
			list = noticeRepository.findByTitle(title);
		}
		
		model.addAttribute("list", list);

		return "notice/list";
	}

	@GetMapping("/{id}")
	public ResponseEntity<Notice> getNotice(@PathVariable("id") long id) {
		Optional<Notice> noticeData = noticeRepository.findById(id);

		if (noticeData.isPresent()) {
			return new ResponseEntity<>(noticeData.get(), HttpStatus.OK);
		}else { 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ExceptionHandler
	@PostMapping("/create")
	public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
		System.out.println("cccc");
		notice.setRegDt(new Date());
		Notice _notice = noticeRepository.save(notice);
		return new ResponseEntity<>(_notice, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Notice> updateNotice(@PathVariable("id") long id, @RequestBody Notice notice) {
		Optional<Notice> noticeData = noticeRepository.findById(id);

		if (noticeData.isPresent()) {
			Notice _notice = noticeData.get();
			_notice.setTitle(notice.getTitle());
			_notice.setContent(notice.getContent());
			_notice.setRegDt(new Date());
			return new ResponseEntity<>(noticeRepository.save(_notice), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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
		noticeRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
