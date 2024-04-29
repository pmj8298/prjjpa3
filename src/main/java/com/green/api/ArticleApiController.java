package com.green.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.service.ArticleService;

@RestController
public class ArticleApiController {

	//@Autowired
	//private ArticleRepository articleRepository;
	
	
	// controller 에는 Mapping 을 무조건 가지고 있어야 함
	@Autowired
	private ArticleService articleService;
	
	// GET LIST : 목록조회
	@GetMapping("/api/articles")
	  public List<Article> index(){
		return articleService.index();
	}
	
	// GET ID   : ID로 조회
	@GetMapping("/api/articles/{id}")
	public Article show(@PathVariable Long id) {
		Article article = articleService.show(id);
		return article;
	}
	
	
	// POST     : INSERT - create
	// 결과     : 저장된 article 객체, 상태코드 <- 저장되었습니다
	// Generic  : 파라미터 type 을 객체(T) type 을 사용해라
	// ResponseEntity '<T>' : class type , '<?>' - T는 외부에 입력된 type
	// 지금은 몰라서 T, ?로 나오는거고 실행 후 Article로
	// ResponseEntity<Article>
	// = Article Data + http state code : 200
	// {"id":1, "title":"새글", "content":"새글 내용"} -> 400 error
	// {"title":"새글", "content":"새글 내용"}         -> 200 error
	// HttpStatus.Ok          : 200
	// HttpStatus.BAD.REQUEST : 400 
	// .body(null) == .build()
	// @RequestBody : 넘어오는 값 json
	@PostMapping("/api/articles")
	 public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
			
		Article created = articleService.create( dto);
		ResponseEntity<Article> result
		  = (created != null) 
				?  ResponseEntity.status(HttpStatus.OK).body(created)
				:  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				
		return result;
	}
	
	
	
	// PATCH    : UPDATE
	// DELETE   : DELETE
}
