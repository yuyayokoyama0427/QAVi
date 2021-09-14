package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.service.ArticleService;

/**
 * 掲示板を操作するコントローラー.
 * 
 * @author yuyayokoyama
 *
 */
@Controller
@RequestMapping("/bbs")
@Transactional
public class BbsController {
	
	@Autowired
	private ArticleService articleService;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleService.findAll();
		// 記事リストをスコープに格納する
		model.addAttribute("articleList", articleList);
		return "view/bbsview";
	}

}
