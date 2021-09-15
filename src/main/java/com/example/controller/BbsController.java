package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.service.ArticleService;
import com.example.service.CommentService;

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
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 記事フォームの初期化.
	 * @return 記事フォーム
	 */
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	
	
	
	
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleService.findAll();
		for(Article article : articleList) {
			int articleId = article.getId();
			List<Comment> commentList = commentService.findByArticleId(articleId);
			article.setCommentList(commentList);
		}
		
		// 記事リストをスコープに格納する
		model.addAttribute("articleList", articleList);
		return "view/bbsview";
	}
	
	/**
	 * 記事の投稿.
	 * 
	 * @param form フォーム
	 * @param result リザルト
	 * @param model モデル
	 * @return 掲示板画面
	 */
	@RequestMapping(value = "/postarticle")
	public String postarticle(@Validated ArticleForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return index(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleService.insert(article);
		return "redirect:/bbs";
	}
	
	/**
	 * コメントの投稿.
	 * 
	 * @param form フォーム
	 * @param result リザルト
	 * @param model モデル
	 * @return 掲示板画面
	 */
	@RequestMapping(value = "/postcomment")
	public String postcoment(@Validated CommentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return index(model);
		}
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		commentService.insert(comment);
		return "redirect:/bbs";
	}

}
