package mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	
	final int OFFSET = 5;
	final int PAGER_OFFSET = 5;
	
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public void addContents(BoardVo vo, Long userId) {
		vo.setUserId(userId);

		if(vo.getType().equals("reply") && vo.getId() != null) {
			BoardVo originVo = boardRepository.findById(vo.getId());
			vo.setGNo(originVo.getGNo());
			vo.setGNo(originVo.getGNo());
			vo.setDepth(originVo.getDepth());
			
			boardRepository.updateONo(vo);
			boardRepository.insertReply(vo);
		} else {
			int gNo = boardRepository.findMaxGroupId();
			boardRepository.insertOrigin(vo, gNo+1);
		}
	}
	
	public BoardVo getContents(Long id) {
		boardRepository.updateViewCnt(id);
		return boardRepository.findById(id);
	}
	
//	public BoardVo getContents(Long id, Long userId) {
//		
//	}
//	
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(Long id, Long userId) {
		boardRepository.deleteById(id, userId);
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {		
		Map<String, Object> results = new HashMap<String, Object>();
		List<BoardVo> list = null;
		int cntPages = 0;

		if(keyword == null) {
			list = boardRepository.findPageAll((currentPage - 1) * OFFSET, OFFSET);
			cntPages = boardRepository.countPages();

		} else {
			list = boardRepository.findPageAllByKeyword((currentPage - 1) * OFFSET, OFFSET, keyword);
			cntPages = boardRepository.countPagesByKeyword(keyword);
		}
		
		// view의 pagination를 위한 데이터 값 계산
		int totalPages = Math.ceilDiv(cntPages, OFFSET);
		int pageGroupNum = (currentPage / PAGER_OFFSET) + 1;
		
		if(currentPage % PAGER_OFFSET == 0) {
			pageGroupNum = (currentPage / PAGER_OFFSET);
		}
		int beginPage = (pageGroupNum-1) * PAGER_OFFSET + 1;
				
		results.put("list", list);
		results.put("currentPage", currentPage);
		results.put("beginPage", beginPage);
		results.put("endPage", beginPage+(PAGER_OFFSET-1));
		results.put("startPage", 1);
		results.put("totalPage", totalPages);
		results.put("cntPages", cntPages);
		results.put("keyword", keyword);
		return results;
	}
}
