package com.test.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeRepository noticeRepository;

	@GetMapping("/list")
	public ResponseEntity<List<Notice>> getAllTutorials(@RequestParam(required = false) String title) {
		try {
			List<Notice> list = new ArrayList<Notice>();

			if (title == null)
				noticeRepository.findAll().forEach(list::add);
			else
				noticeRepository.findByTitle(title).forEach(list::add);

			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Notice> getNotice(@PathVariable("id") long id) {
		Optional<Notice> noticeData = noticeRepository.findById(id);

		if (noticeData.isPresent()) {
			return new ResponseEntity<>(noticeData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
		try {
			notice.setRegDt(new Date());
			Notice _notice = noticeRepository.save(notice);
			return new ResponseEntity<>(_notice, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteNotice(@PathVariable("id") long id) {
		try {
			noticeRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/all")
	public ResponseEntity<HttpStatus> deleteAllNotice() {
		try {
			noticeRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
