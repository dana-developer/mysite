package mysite.vo;

public class BoardVo {
	private Long id;
	private String title;
	private String contents;
	private Long hit;
	private String regDate;
	private Long gNo;
	private Long oNo;
	private Long depth;
	private Long userId;
	private String userName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Long getgNo() {
		return gNo;
	}
	public void setgNo(Long gNo) {
		this.gNo = gNo;
	}
	public Long getoNo() {
		return oNo;
	}
	public void setoNo(Long oNo) {
		this.oNo = oNo;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "BoardVo [id=" + id + ", title=" + title + ", contents=" + contents + ", hit=" + hit + ", regDate="
				+ regDate + ", gNo=" + gNo + ", oNo=" + oNo + ", depth=" + depth + ", userId=" + userId + ", userName="
				+ userName + "]";
	}
	
}
