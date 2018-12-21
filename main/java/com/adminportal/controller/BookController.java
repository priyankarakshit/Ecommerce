package com.adminportal.controller;

import com.adminportal.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.adminportal.domain.Book;
import com.adminportal.service.BookService;

import org.hibernate.Session;
import com.adminportal.utility.HibernateUtil;
import java.sql.*;

@Controller
@RequestMapping("/book")
public class BookController {

	private static final Logger logging = Logger.getLogger(AdminportalApplication.class.getName());

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBook(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		logging.log(Level.INFO, "you're at book add page");
		return "addBook";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
		bookService.save(book);

		MultipartFile bookImage = book.getBookImage();

		try {
			byte[] bytes = bookImage.getBytes();
			String name = book.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logging.log(Level.INFO, "you've got redirected to the booklist");

		return "redirect:bookList";
	}
	
	@RequestMapping("/bookInfo")
	public String bookInfo(@RequestParam("id") Long id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		logging.log(Level.INFO, "you're seeing book info");
		return "bookInfo";
	}
	
	@RequestMapping("/updateBook")
	public String updateBook(@RequestParam("id") Long id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		logging.log(Level.INFO, "you're shown book update page");
		return "updateBook";
	}
	
	@RequestMapping(value="/updateBook", method=RequestMethod.POST)
	public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
		
		bookService.save(book);
		
		MultipartFile bookImage = book.getBookImage();
		
		if(!bookImage.isEmpty()) {
			try {
				byte[] bytes = bookImage.getBytes();
				String name = book.getId() + ".png";
				
				Files.delete(Paths.get("src/main/resources/static/image/book/"+name));
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logging.log(Level.INFO, "you were redirected to book info");
		return "redirect:/book/bookInfo?id="+book.getId();
	}
	
	@RequestMapping("/bookList")
	public String bookList(Model model) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);		
		logging.log(Level.INFO, "you're seeing the book list");
		return "bookList";
		
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) {
		bookService.removeOne(Long.parseLong(id.substring(8)));
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);
		logging.log(Level.INFO, "you were redirected to book list after removing book by post");
		return "redirect:/book/bookList";
	}

}
