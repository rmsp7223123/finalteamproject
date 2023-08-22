package cloudWeb.Common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageVO {
	private String search="all", keyword="", viewType="list"; /*검색유형, 검색어, 보기형태*/
	private int totalList; //DB에서 조회해온 총 글 건수
	private int pageList = 10; // 페이지당 보여질 목록 수
	private int blockPage = 10; //블럭당 보여질 페이지 수
	private int totalPage;	//총 페이지수 : 8 페이지 = 30 / 4 = 7 ... 2
	private int totalBlock;  //총 블록수 : 3 블록 = 8 / 3 = 2 ... 2
	private int curPage = 1;  //현재 페이지번호
//	 각 페이지의 끝 목록번호 :  총 목록수 - (페이지번호-1) * 페이지당 보여질 목록수  
//	 각 페이지의 시작 목록번호 :  끝 목록번호 - (페이지당 보여질 목록수-1)	
	private int endList, beginList;
	
	// 블록번호 : 페이지번호 / 블록당 보여질 페이지수
	private int curBlock;	
//	 각 블럭의 끝 페이지번호 : 블록번호 * 블록당 보여질 페이지수
//	 각 블럭의 시작 페이지번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
	private int endPage, beginPage;
	private List<Object> list; // 현재페이지에서의 글 목록
	
	public void setTotalList(int totalList) {
		this.totalList = totalList;
		
		//총 페이지수 : 8 페이지 = 212 / 10 = 21 ... 2 --> 22P
		totalPage = totalList / pageList;
		if( totalList % pageList > 0 ) ++totalPage;
		
		//총 블록수 : 3 블록 = 22 / 10 = 2 ... 2 --> 3B
		totalBlock = totalPage / blockPage;
		if( totalPage % blockPage > 0 ) ++totalBlock;
		
		//각 페이지의 끝 목록번호 :  총 목록수 - (페이지번호-1) * 페이지당 보여질 목록수  
		//각 페이지의 시작 목록번호 :  끝 목록번호 - (페이지당 보여질 목록수-1)
		endList = totalList - (curPage-1) * pageList;
		beginList = endList - (pageList-1);
		
		//블록번호 : 페이지번호 / 블록당 보여질 페이지수
		curBlock = curPage / blockPage;
		if( curPage % blockPage > 0 ) ++curBlock;
		
		//각 블럭의 끝 페이지번호 : 블록번호 * 블록당 보여질 페이지수
		//각 블럭의 시작 페이지번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
		// 3Block : 21, 22
		endPage = curBlock * blockPage;      //30
		beginPage = endPage - (blockPage-1); //21

		if( totalPage < endPage ) endPage = totalPage;	
		
	}
	
}
