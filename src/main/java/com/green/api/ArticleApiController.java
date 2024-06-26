package com.green.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.service.ArticleService;

@RestController // @Controller + @ResponseBody - @ResponseBody 이거 때문에 json 으로 찍히는 거임
public class ArticleApiController {

	//@Autowired
	//private ArticleRepository articleRepository;
	
	
	// controller 에는 Mapping 을 무조건 가지고 있어야 함
	@Autowired
	private ArticleService articleService;
	
	// GET LIST : 목록조회
	// http://localhost:9090/api/articles
	// @GetMapping("/api/articles")
	 @GetMapping(value="/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
	// @GetMapping(value="/api/articles", produces = MediaType.APPLICATION_XML_VALUE)
	// @GetMapping(value="/api/articles", produces = "apllication/xml;charset=utf-8") -jackson 라이브러리 추가 필요할 수도 있음
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
	// HttpStatus.BAD.REQUEST : 400  - 사용자가 만든 error(user defined) 
	// .body(null) == .build()
	// @RequestBody : json string 으로 넘어오는 값을 java 의 객체(ArticleFor)로 저장
	@PostMapping("/api/articles")
	 public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
			
		Article created = articleService.create(dto);
		ResponseEntity<Article> result
		  = (created != null) 
				?  ResponseEntity.status(HttpStatus.OK).body(created)
				:  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				
		return result;
	}
	
	
	
	// PATCH    : UPDATE
	@PatchMapping("/api/articles/{id}")
	public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
		System.out.println("id:" + id + ",dto:" + dto);
		Article updated = articleService.update(id,dto);
		ResponseEntity<Article> result
		  = (updated != null) 
				?  ResponseEntity.status(HttpStatus.OK).body(updated)
				:  ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 error
				
		return result;
	}
	
	
	// DELETE   : DELETE
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Article> delete(@PathVariable Long id, @RequestBody ArticleForm dto){
		System.out.println("id:" + id + ",dto:" + dto);
		Article deleted = articleService.delete(id);
		ResponseEntity<Article> result
		= (deleted != null) 
		?  ResponseEntity.status(HttpStatus.NO_CONTENT).build()
		:  ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 error
		
		return result;
	}
	
	/*
	[ 
       {title:"시간 예약"  , content:"1240"},
       {title:"테이블 지정", content:"A12"},
       {title:"메뉴 선택"  , content:"Branch A"}
    ]
	 */
	 
	// Transaction : 세 개의 data 를 받아서 서비스 함수에 넘겨주고 결과를 받는다
	@PostMapping("/api/transaction-test")
	public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
		
		List<Article> createdList = articleService.createArticles(dtos);
		
		ResponseEntity<List<Article>> result
		= (createdList != null) 
		?  ResponseEntity.status(HttpStatus.OK).body(createdList)
		:  ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 error
		
		return result; 
	}
	
	@PostMapping("/api/transaction-test2")
	public ResponseEntity<List<Article>> transactionTest2(@RequestBody List<ArticleForm> dtos){
		
		List<Article> createdList = articleService.createArticles2(dtos);
		
		ResponseEntity<List<Article>> result
		= (createdList != null) 
		?  ResponseEntity.status(HttpStatus.OK).body(createdList)
				:  ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 error
		
		return result; 
	}
}
